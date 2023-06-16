package Controllers;

import Enums.ElevatorDirection;
import Enums.ElevatorStatus;
import Enums.ElevatorType;
import Models.AccelElevator;
import Models.ClassicElevator;
import Models.Elevator;
import Models.ServiceElevator;
import Strategies.ElevatorAllocationStrategy;
import Utils.Constants;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ElevatorSystem {
    private final List<Elevator> elevatorList = new ArrayList<>();
    private int numElevators = 0;
    private final ArrayDeque<Integer> requests = new ArrayDeque<>();
    private final ScheduledExecutorService requestProcessorScheduler;
    private ElevatorAllocationStrategy allocationStrategy;

    public ElevatorSystem() {
        this.requestProcessorScheduler = Executors.newSingleThreadScheduledExecutor();
        this.requestProcessorScheduler.scheduleAtFixedRate(this::requestProcessor, 100, 100, TimeUnit.MILLISECONDS);
    }

    // Add new elevator of required type to Controllers.ElevatorSystem
    public void addElevator(ElevatorType type) {
        System.out.println("Received request to add elevator of type:" + type);
        this.numElevators++;
        switch (type) {
            case CLASSIC:
                this.elevatorList.add(new ClassicElevator(this.numElevators));
                break;
            case SERVICE:
                this.elevatorList.add(new ServiceElevator(this.numElevators));
                break;
            case ACCEL:
                this.elevatorList.add(new AccelElevator(this.numElevators));
                break;
        }
    }

    // Simulates external switch to request for elevator from any floor
    public void requestElevator(int fromFloor) {
        this.requests.add(fromFloor);
    }

    // Simulates internal switch in an elevator to stop at any floor
    public void addStop(int elevatorId, int floor) {
        this.elevatorList.get(elevatorId - 1).addStop(floor);
    }

    // Method to process all requests coming from external switches
    private void requestProcessor() {
        Integer floor = this.requests.poll();
        if (floor != null) {
            System.out.println("Got elevator request for floor: " + floor);
            int closestElevatorId = this.allocationStrategy.getElevator(elevatorList, floor);
            if (closestElevatorId == 0) {
                System.out.println("No elevator available, trying again in a while!");
                this.requests.addFirst(floor);
                return;
            }
            System.out.println("Found closest elevator: " + closestElevatorId);
            this.elevatorList.get(closestElevatorId - 1).addStop(floor);
        }
    }

    // Method to print current status of all the elevators
    public void getStatus() {
        System.out.println("Status of all elevators:");
        for (Elevator elevator : this.elevatorList) {
            System.out.println("Models.Elevator ID: " + elevator.getId() +
                    " | Current Floor: " + elevator.getCurrentFloor() +
                    " | Direction: " + elevator.getDirection());
        }
    }

    public void setAllocationStrategy(ElevatorAllocationStrategy allocationStrategy) {
        this.allocationStrategy = allocationStrategy;
    }
}

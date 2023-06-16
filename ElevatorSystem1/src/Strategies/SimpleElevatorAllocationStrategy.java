package Strategies;

import Enums.ElevatorDirection;
import Enums.ElevatorStatus;
import Models.Elevator;
import Utils.Constants;

import java.util.List;

public class SimpleElevatorAllocationStrategy implements ElevatorAllocationStrategy{
    @Override
    public int getElevator(List<Elevator> elevatorList, int floor) {
        int closestElevatorId = 0;
        int closestDistance = Integer.MAX_VALUE;
        for (Elevator elevator : elevatorList) {
            if (elevator.getStatus() == ElevatorStatus.NOT_WORKING) continue;
            if (elevator.getCurrentCapacity() >= elevator.getMaxCapacity() - 30) continue;
            int distance;
            if (elevator.getDirection() == ElevatorDirection.UP) {
                if (floor >= elevator.getCurrentFloor()) {
                    distance = floor - elevator.getCurrentFloor();
                } else {
                    distance = (Constants.NUM_FLOORS - elevator.getCurrentFloor()) + (Constants.NUM_FLOORS - floor);
                }
            } else {
                if (floor <= elevator.getCurrentFloor()) {
                    distance = elevator.getCurrentFloor() - floor;
                } else {
                    distance = elevator.getCurrentFloor() + floor;
                }
            }
            if (distance < closestDistance) {
                closestElevatorId = elevator.getId();
                closestDistance = distance;
            }
        }
        return closestElevatorId;
    }
}

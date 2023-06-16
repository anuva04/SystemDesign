package org.anuva04.Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.anuva04.Enums.ElevatorStatus;
import org.anuva04.Enums.ElevatorDirection;
import org.anuva04.Utils.Constants;

public class Elevator {
    protected ScheduledExecutorService requestProcessorScheduler = Executors.newSingleThreadScheduledExecutor();
    private final int id;
    private int currentFloor;
    private final ElevatorStatus status;
    private final List<Boolean> floors;
    private ElevatorDirection direction;
    private int maxCapacity;
    private int currentCapacity = 0;

    public Elevator(int id) {
        this.id = id;
        this.currentFloor = 0;
        this.status = ElevatorStatus.WORKING;
        this.direction = ElevatorDirection.UP;
        this.floors = new ArrayList<>(Collections.nCopies(Constants.NUM_FLOORS, false));
    }

    // Simulates internal switch in an elevator to stop at any floor
    public void addStop(int floor) {
        System.out.println("Got request to stop at floor: " + floor + " for elevator ID: " + this.id);
        this.floors.set(floor, true);
    }

    // Method to process all requests coming from internal switches of the elevator
    protected void requestProcessor() {
        if (this.currentFloor == 0) this.direction = ElevatorDirection.UP;
        if (this.currentFloor == Constants.NUM_FLOORS - 1) this.direction = ElevatorDirection.DOWN;
        if (this.direction == ElevatorDirection.UP) {
            if (findAndProcessRequest(ElevatorDirection.UP)) return;
            if (findAndProcessRequest(ElevatorDirection.DOWN)) {
                this.direction = ElevatorDirection.DOWN;
            }
        } else {
            if (findAndProcessRequest(ElevatorDirection.DOWN)) return;
            if (findAndProcessRequest(ElevatorDirection.UP)) {
                this.direction = ElevatorDirection.UP;
            }
        }
    }

    private boolean findAndProcessRequest(ElevatorDirection direction) {
        if (direction == ElevatorDirection.UP) {
            for (int floor = this.currentFloor; floor < Constants.NUM_FLOORS; floor++) {
                if (this.floors.get(floor)) {
                    this.updateElevatorInfo(floor);
                    return true;
                }
            }
        } else {
            for (int floor = this.currentFloor; floor >= 0; floor--) {
                if (this.floors.get(floor)) {
                    this.updateElevatorInfo(floor);
                    return true;
                }
            }
        }
        return false;
    }

    private void updateElevatorInfo(int floor) {
        this.floors.set(floor, false);
        this.currentFloor = floor;
        Random random = new Random();
        int randomNumber = random.nextInt(this.maxCapacity + 30);
        this.setCurrentCapacity(randomNumber);
    }

    public ElevatorStatus getStatus() {
        return status;
    }

    public ElevatorDirection getDirection() {
        return direction;
    }

    public int getId() {
        return id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public void setCurrentCapacity(int currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
}

package Strategies;

import Models.Elevator;
import java.util.List;

public interface ElevatorAllocationStrategy {
    public int getElevator(List<Elevator> elevatorList, int floor);
}

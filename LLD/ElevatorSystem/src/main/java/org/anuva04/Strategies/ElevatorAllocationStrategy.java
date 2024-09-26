package org.anuva04.Strategies;

import org.anuva04.Models.Elevator;
import java.util.List;

public interface ElevatorAllocationStrategy {
    public int getElevator(List<Elevator> elevatorList, int floor);
}

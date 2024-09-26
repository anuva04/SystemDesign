package org.anuva04.Models;

import java.util.concurrent.TimeUnit;

public class AccelElevator extends Elevator {
    public AccelElevator(int id) {
        super(id);
        this.setMaxCapacity(500);
        this.requestProcessorScheduler.scheduleAtFixedRate(this::requestProcessor, 100, 100, TimeUnit.MILLISECONDS);
    }
}

package org.anuva04.Models;

import java.util.concurrent.TimeUnit;

public class ClassicElevator extends Elevator {
    public ClassicElevator(int id) {
        super(id);
        this.setMaxCapacity(500);
        this.requestProcessorScheduler.scheduleAtFixedRate(this::requestProcessor, 100, 1000, TimeUnit.MILLISECONDS);
    }
}

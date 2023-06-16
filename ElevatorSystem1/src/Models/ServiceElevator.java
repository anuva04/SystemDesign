package Models;

import java.util.concurrent.TimeUnit;

public class ServiceElevator extends Elevator {
    public ServiceElevator(int id) {
        super(id);
        this.setMaxCapacity(1000);
        this.requestProcessorScheduler.scheduleAtFixedRate(this::requestProcessor, 100, 1000, TimeUnit.MILLISECONDS);
    }
}

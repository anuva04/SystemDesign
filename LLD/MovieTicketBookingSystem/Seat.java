import java.util.concurrent.locks.ReentrantLock;

class Seat {
    private final String seatId;
    private volatile SeatStatus status;
    private final ReentrantLock lock;

    public Seat(String seatId) {
        this.seatId = seatId;
        status = SeatStatus.AVAILABLE;
        this.lock = new ReentrantLock();
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }

    public String getSeatId() {
        return seatId;
    }

    public ReentrantLock getLock() {
        return lock;
    }
}
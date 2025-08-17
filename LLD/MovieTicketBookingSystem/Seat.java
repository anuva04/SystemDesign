class Seat {
    private final String seatId;
    private volatile SeatStatus status;

    public Seat(String seatId) {
        this.seatId = seatId;
        status = SeatStatus.AVAILABLE;
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
}
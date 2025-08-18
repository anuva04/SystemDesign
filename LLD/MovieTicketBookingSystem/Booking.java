import java.util.*;

class Booking {
    private final String bookingId;
    private final Show show;
    private final String userId;
    private final List<String> bookedSeatIds;
    private final Date bookingDate;

    public Booking(Show show, String userId, List<String> bookedSeatIds) {
        this.bookingId = "booking_" + UUID.randomUUID().toString();
        this.show = show;
        this.userId = userId;
        this.bookedSeatIds = bookedSeatIds;
        this.bookingDate = new Date();
    }

    public Show getShow() {
        return show;
    }

    public List<String> getSeatIds() {
        return bookedSeatIds;
    }

    public String getUserId() {
        return userId;
    }

    public String getBookingId() {
        return bookingId;
    }
}
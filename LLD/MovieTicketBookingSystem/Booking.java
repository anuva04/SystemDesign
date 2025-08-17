import java.util.*;

class Booking {
    private final String bookingId;
    private static int nextBookingId = 1;
    private final Show show;
    private final String userId;
    private final List<Seat> bookedSeats;
    private final Date bookingDate;

    public Booking(Show show, String userId, List<Seat> bookedSeats) {
        this.bookingId = "booking" + nextBookingId++;
        this.show = show;
        this.userId = userId;
        this.bookedSeats = bookedSeats;
        this.bookingDate = new Date();
    }

    public Show getShow() {
        return show;
    }

    public List<Seat> getSeats() {
        return bookedSeats;
    }

    public String getUserId() {
        return userId;
    }

    public String getBookingId() {
        return bookingId;
    }
}
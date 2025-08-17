import java.util.stream.Collectors;
import java.util.concurrent.ConcurrentHashMap;
import java.util.*;

class BookingService {
    private final Map<String, Show> shows;
    private final Map<String, Booking> bookings;
    private final int MAX_SEATS_TO_BOOK = 10;

    public BookingService() {
        shows = new ConcurrentHashMap<>();
        bookings = new ConcurrentHashMap<>();
    }

    public void addShow(Show show) {
        shows.put(show.getShowId(), show);
    }

    public List<Show> getShowsForMovie(Movie movie) {
        return shows.values().stream()
                .filter(show -> show.getMovie().equals(movie))
                .collect(Collectors.toList());
    }

    public Booking bookSeats(Show show, String userId, List<Seat> seatsToBook) throws BookingException {
        if(seatsToBook.size() > MAX_SEATS_TO_BOOK) {
            throw new BookingException(String.format("Cannot book more than%d seats at once.", MAX_SEATS_TO_BOOK));
        }

        synchronized (show) {
            for(Seat seat : seatsToBook) {
                if(!show.getSeats().containsValue(seat)) {
                    throw new BookingException("Invalid seat selected for this show.");
                }
                if(seat.getStatus() != SeatStatus.AVAILABLE) {
                    throw new BookingException("Seat " + seat.getSeatId() + " is already " + seat.getStatus() + ".");
                }
            }

            for (Seat seat : seatsToBook) {
                seat.setStatus(SeatStatus.BOOKED);
            }
        }

        Booking newBooking = new Booking(show, userId, seatsToBook);
        bookings.put(newBooking.getBookingId(), newBooking);
        return newBooking;
    }

    public Booking getBookingForSeat(Show show, Seat seat) {
        for(Booking booking : bookings.values()) {
            if(booking.getShow().equals(show) && booking.getSeats().contains(seat)) {
                return booking;
            }
        }
        return null;
    }

    public List<Booking> getBookingsForUser(String userId) {
        return bookings.values().stream()
                .filter(booking -> booking.getUserId().equals(userId))
                .collect(Collectors.toList());
    }
}
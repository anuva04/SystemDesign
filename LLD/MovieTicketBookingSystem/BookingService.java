import java.util.stream.Collectors;
import java.util.concurrent.ConcurrentHashMap;
import java.util.*;
import java.util.concurrent.TimeUnit;

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

    public Booking bookSeats(Show show, String userId, List<String> seatIdsToBook) throws BookingException {
        if(seatIdsToBook.size() > MAX_SEATS_TO_BOOK) {
            throw new BookingException(String.format("Cannot book more than%d seats at once.", MAX_SEATS_TO_BOOK));
        }

        List<String> sortedSeatIds = seatIdsToBook.stream()
                .sorted().collect(Collectors.toList());

        for(String seatId : sortedSeatIds) {
            if(!show.getSeats().containsKey(seatId)) {
                throw new BookingException("Invalid seat selected for this show.");
            }
            if(show.getSeatStatus(seatId) != SeatStatus.AVAILABLE) {
                throw new BookingException("Seat " + seatId + " is already " + show.getSeatStatus(seatId) + ".");
            }
        }

        List<Seat> bookedSeats = new ArrayList<>();

        try {
            for(String seatId : sortedSeatIds) {
                Seat seat = show.getSeat(seatId);
                if(seat.getLock().tryLock(500, TimeUnit.MILLISECONDS)) {
                    try {
                        if(seat.getStatus() != SeatStatus.AVAILABLE) {
                            throw new BookingException("Seat " + seat.getSeatId() + " is already " + seat.getStatus() + ".");
                        }
                        seat.setStatus(SeatStatus.BOOKED);
                        bookedSeats.add(seat);
                    } finally {
                        seat.getLock().unlock();
                    }
                } else {
                    throw new BookingException("Could not acquire lock for seat " + seat.getSeatId() + " within the timeout.");
                }
            }
        } catch (BookingException | InterruptedException e) {
            for(Seat seat : bookedSeats) {
                seat.getLock().lock();
                try {
                    seat.setStatus(SeatStatus.AVAILABLE);
                } finally {
                    seat.getLock().unlock();
                }
            }

            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
                throw new BookingException("Booking was interrupted.");
            }
            throw (BookingException)e;
        }

        Booking newBooking = new Booking(show, userId, seatIdsToBook);
        bookings.put(newBooking.getBookingId(), newBooking);
        return newBooking;
    }

    public Booking getBookingForSeat(Show show, String seatId) {
        for(Booking booking : bookings.values()) {
            if(booking.getShow().equals(show) && booking.getSeatIds().contains(seatId)) {
                return booking;
            }
        }
        return null;
    }

    public SeatStatus getSeatStatus(Show show, String seatId) {
        return show.getSeatStatus(seatId);
    }

    public List<Booking> getBookingsForUser(String userId) {
        return bookings.values().stream()
                .filter(booking -> booking.getUserId().equals(userId))
                .collect(Collectors.toList());
    }
}
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.time.LocalDate;

class Show {
    private final String showId;
    private final Movie movie;
    private final Screen screen;
    private final Showtime time;
    private final LocalDate date;
    private final Map<String, Seat> seats;

    public Show(Movie movie, Screen screen, Showtime time, LocalDate date) {
        this.movie = movie;
        this.screen = screen;
        this.time = time;
        this.showId = "show_" + UUID.randomUUID().toString();
        this.date = date;
        this.seats = new ConcurrentHashMap<>();

        for(int row = 0; row < screen.getRows(); row++) {
            char rowChar = (char)('A' + row);
            for(int col = 1; col <= screen.getColumns(); col++) {
                String seatId = String.valueOf(rowChar) + col;
                seats.put(seatId, new Seat(seatId));
            }
        }
    }

    public String getShowId() {
        return showId;
    }

    public Movie getMovie() {
        return movie;
    }

    public Map<String, Seat> getSeats() {
        return seats;
    }

    public Screen getScreen() {
        return screen;
    }

    public Showtime getShowtime() {
        return time;
    }

    public Seat getSeat(String seatId) {
        return seats.get(seatId);
    }

    public SeatStatus getSeatStatus(String seatId) {
        return seats.get(seatId).getStatus();
    }

    public void setSeatStatus(String seatId, SeatStatus seatStatus) {
        seats.get(seatId).setStatus(seatStatus);
    }

    public LocalDate getDate() {
        return date;
    }
}
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

class Show {
    private final String showId;
    private static int nextShowId = 1;
    private final Movie movie;
    private final Screen screen;
    private final Showtime time;
    private final Map<String, Seat> seats;

    public Show(Movie movie, Screen screen, Showtime time) {
        this.movie = movie;
        this.screen = screen;
        this.time = time;
        this.showId = "show" + nextShowId++;
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
}
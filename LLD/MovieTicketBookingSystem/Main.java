import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Main {
    /**
     * TODOS (improvements)
     * 1. Check if using incrementing static int is recommended for generating IDs
     * 2. Add Date in Show and other related places
     * 3. Add list of shows for each movie and movie search service. getShowsForMovie method in BookingService will probably not be required thereafter
     * 4. Add getSeatAvailability(Show show, String seatId) method in BookingService
     * 5. Change seatsToBook to seatIdsToBook in bookSeats method in BookingService
     * 6. Change seat to seatId in getBookingForSeat method in BookingService
     * 7. Add Date in Show class and print date wherever shows are fetched
     */
    public static void main(String[] args) {
        // --- Setup Data (Simulating a database or data layer) ---
        // Create movies
        Movie movie1 = new Movie("The Martian", "A sci-fi film about an astronaut stranded on Mars.", 144);
        Movie movie2 = new Movie("Interstellar", "A team of explorers travel through a wormhole in space.", 169);
        Movie movie3 = new Movie("Dune", "Paul Atreides, a brilliant and gifted young man, journeys to the most dangerous planet in the universe.", 155);

        // Create a theatre with screens
        Theatre pvr = new Theatre("PVR Cinemas", "123 Main Street");
        // We only need to define the physical screens, not the seats on them.
        pvr.addScreen("Screen1", 5, 10);
        pvr.addScreen("Screen2", 8, 12);

        // Create a movie search service and populate it
        MovieSearchService movieSearchService = new MovieSearchService();
        movieSearchService.addMovie(movie1);
        movieSearchService.addMovie(movie2);
        movieSearchService.addMovie(movie3);

        // Create shows for the movies in the theatre
        // Now each show gets a unique set of seats created from the physical screen layout.
        Show show1 = new Show(movie1, pvr.getScreen("Screen1"), Showtime.EVENING);
        Show show2 = new Show(movie2, pvr.getScreen("Screen1"), Showtime.MATINEE);
        Show show3 = new Show(movie3, pvr.getScreen("Screen2"), Showtime.EVENING);
        Show show4 = new Show(movie1, pvr.getScreen("Screen2"), Showtime.MORNING);

        // Create a booking service
        BookingService bookingService = new BookingService();
        bookingService.addShow(show1);
        bookingService.addShow(show2);
        bookingService.addShow(show3);
        bookingService.addShow(show4);

        System.out.println("----- Welcome to the Movie Booking System! -----");

        // --- 1. Search for a movie ---
        System.out.println("\n--- Searching for movies with prefix 'The' ---");
        List<Movie> searchResults = movieSearchService.searchMovies("The");
        searchResults.forEach(movie -> System.out.println("Found movie: " + movie.getName()));

        // --- 2. View available shows for a movie ---
        System.out.println("\n--- Available shows for 'The Martian' ---");
        List<Show> availableShows = bookingService.getShowsForMovie(movie1);
        availableShows.forEach(show -> {
            System.out.printf("Movie: %s, Theatre: %s, Screen: %s, Time: %s%n",
                    show.getMovie().getName(), show.getScreen().getTheatre().getName(),
                    show.getScreen().getScreenId(), show.getShowtime());
        });

        // --- 3. Booking seats (Concurrent Scenario) ---
        System.out.println("\n--- Simulating Concurrent Bookings for 'The Martian' - Screen 1 (Evening show) ---");
        System.out.println("Initial seats in Screen 1: " + show1.getSeats().size());

        // Define seats to be booked by different users
        List<Seat> seatsToBookUser1 = new ArrayList<>();
        seatsToBookUser1.add(show1.getSeat("A1"));
        seatsToBookUser1.add(show1.getSeat("A2"));
        seatsToBookUser1.add(show1.getSeat("A3"));

        List<Seat> seatsToBookUser2 = new ArrayList<>();
        seatsToBookUser2.add(show1.getSeat("A3")); // Intentionally try to book the same seat
        seatsToBookUser2.add(show1.getSeat("A4"));

        ExecutorService executor = Executors.newFixedThreadPool(2);

        // User 1 attempts to book
        executor.submit(() -> {
            try {
                System.out.println("User 1 is attempting to book...");
                Booking booking = bookingService.bookSeats(show1, "user123", seatsToBookUser1);
                System.out.println("User 1 booking successful! Booking ID: " + booking.getBookingId());
            } catch (BookingException e) {
                System.out.println("User 1 booking failed: " + e.getMessage());
            }
        });

        // User 2 attempts to book (will fail due to A3 being taken)
        executor.submit(() -> {
            try {
                // Introduce a slight delay to ensure User 1's thread gets the lock first
                TimeUnit.MILLISECONDS.sleep(50);
                System.out.println("User 2 is attempting to book...");
                Booking booking = bookingService.bookSeats(show1, "user456", seatsToBookUser2);
                System.out.println("User 2 booking successful! Booking ID: " + booking.getBookingId());
            } catch (BookingException | InterruptedException e) {
                System.out.println("User 2 booking failed: " + e.getMessage());
            }
        });

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // --- 4. Check final seat status ---
        System.out.println("\n--- Final Seat Status on Screen 1 for 'The Martian' (Evening Show) ---");
        show1.getSeats().values().stream()
                .forEach(seat -> System.out.println("Seat " + seat.getSeatId() + " is " + seat.getStatus()));

        System.out.println("\n--- Final Seat Status on Screen 1 for 'Interstellar' (Matinee Show) ---");
        show2.getSeats().values().stream()
                .forEach(seat -> System.out.println("Seat " + seat.getSeatId() + " is " + seat.getStatus()));

        // --- 5. Fetch a user's bookings ---
        System.out.println("\n--- Fetching all bookings for user123 ---");
        List<Booking> user1Bookings = bookingService.getBookingsForUser("user123");
        user1Bookings.forEach(booking ->
                System.out.println("Booking ID: " + booking.getBookingId() + " for " + booking.getShow().getMovie().getName() + " at " + booking.getShow().getShowtime())
        );

        // --- 6. Find which user booked a specific seat ---
        System.out.println("\n--- Finding who booked seat A1 on show: " + show1.getShowId() + " ---");
        Booking bookingForSeatA1 = bookingService.getBookingForSeat(show1, show1.getSeat("A1"));
        if (bookingForSeatA1 != null) {
            System.out.println("Seat A1 was booked by user with ID: " + bookingForSeatA1.getUserId());
        } else {
            System.out.println("Seat A1 is not booked or the booking could not be found.");
        }
        System.out.println("\n--- Finding who booked seat B1 on show: " + show1.getShowId() + " ---");
        Booking bookingForSeatB1 = bookingService.getBookingForSeat(show1, show1.getSeat("B1"));
        if (bookingForSeatB1 != null) {
            System.out.println("Seat B1 was booked by user with ID: " + bookingForSeatB1.getUserId());
        } else {
            System.out.println("Seat B1 is not booked or the booking could not be found.");
        }

        // --- 7. Test Case: Booking same physical seat for different shows ---
        System.out.println("\n--- Test Case: Booking same physical seat for different shows ---");

        try {
            List<Seat> seatsToBookTest1 = new ArrayList<>();
            seatsToBookTest1.add(show1.getSeat("B5"));
            Booking booking1 = bookingService.bookSeats(show1, "testuserA", seatsToBookTest1);
            System.out.println("Booking for 'The Martian' successful! Booking ID: " + booking1.getBookingId());
        } catch (BookingException e) {
            System.out.println("Booking for 'The Martian' failed: " + e.getMessage());
        }

        // Book the same physical seat B5 for show2 (Interstellar)
        try {
            List<Seat> seatsToBookTest2 = new ArrayList<>();
            seatsToBookTest2.add(show2.getSeat("B5"));
            Booking booking2 = bookingService.bookSeats(show2, "testuserB", seatsToBookTest2);
            System.out.println("Booking for 'Interstellar' successful! Booking ID: " + booking2.getBookingId());
        } catch (BookingException e) {
            System.out.println("Booking for 'Interstellar' failed: " + e.getMessage());
        }

        System.out.println("\n--- Checking final status of seat B5 on both shows ---");
        // Check the status of the seat in show1
        Seat seatB5Show1 = show1.getSeat("B5");
        System.out.println("Status of seat B5 for '" + show1.getMovie().getName() + "': " + seatB5Show1.getStatus());

        // Check the status of the seat in show2
        Seat seatB5Show2 = show2.getSeat("B5");
        System.out.println("Status of seat B5 for '" + show2.getMovie().getName() + "': " + seatB5Show2.getStatus());
    }
}
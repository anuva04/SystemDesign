import java.util.stream.Collectors;
import java.util.*;

class MovieSearchService {
    private final List<Movie> movies;

    public MovieSearchService() {
        this.movies = new ArrayList<>();
    }

    public void addMovie(Movie movie) {
        this.movies.add(movie);
    }

    public List<Movie> searchMovies(String prefix) {
        return movies.stream()
                .filter(movie -> movie.getName().toLowerCase().startsWith(prefix.toLowerCase()))
                .collect(Collectors.toList());
    }
}
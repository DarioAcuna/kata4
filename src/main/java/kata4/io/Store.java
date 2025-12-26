package kata4.io;

import kata4.model.Movie;

import java.util.stream.Stream;

public interface Store {
    Stream<Movie> movies();
}

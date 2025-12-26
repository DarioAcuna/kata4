package kata4.task;

import kata4.model.Movie;
import kata4.viewmodel.Histogram;

import java.util.HashMap;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public class HistogramBuilder {
    private final Stream<Movie> movies;
    private final Map<String, String> labels;

    public static HistogramBuilder with(Stream<Movie> movies) {
        if(movies == null)throw new IllegalArgumentException("...");
        return new HistogramBuilder(movies);
    }

    private HistogramBuilder(Stream<Movie> movies) {
        this.movies = movies;
        this.labels = new HashMap<>();
    }

    public Histogram build(Function<Movie, Integer> binarize) {
        Histogram histogram = new Histogram(labels);
        movies.map(binarize).forEach(histogram::put);
        return histogram;
    }

    public HistogramBuilder title(String title) {
        this.labels.put("title", title);
        return this;
    }

    public HistogramBuilder x(String x) {
        this.labels.put("x", x);
        return this;
    }

    public HistogramBuilder y(String y) {
        this.labels.put("y", y);
        return this;
    }

    public HistogramBuilder legend(String legend) {
        this.labels.put("legend", legend);
        return this;
    }



}
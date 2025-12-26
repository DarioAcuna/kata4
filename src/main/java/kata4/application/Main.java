package kata4.application;

import kata4.model.Movie;
import kata4.task.HistogramBuilder;
import kata4.viewmodel.Histogram;

import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Stream<Movie> movies = new RemoteStore(Main::fromTsV).movies().filter(m->m.year()>1990).filter(m->m.year()<2025).limit(10_000);
        Histogram histogram = HistogramBuilder.with(movies).title("Movies per year").x("year").y("Frequency").legend("Movies").build(Movie::year);
        MainFrame.create().display(histogram).setVisible(true);
    }

    private static Movie fromTsV(String line) {
        return fromTsV(line.split("\t"));
    }

    private static Movie fromTsV(String[] split) {
        return new Movie(
                split[2],
                toInt(split[5]),
                toInt(split[7])
        );
    }

    private static int toInt(String s) {
        if(s.equals("\\N")) return -1;
        return Integer.parseInt(s);
    }
}

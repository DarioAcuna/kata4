package kata4.application;

import kata4.io.Store;
import kata4.model.Movie;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.function.Function;
import java.util.stream.BaseStream;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

public class RemoteStore implements Store {
    private final Function<String, Movie> deserializer;

    public RemoteStore(Function<String, Movie> deserializer) {
        this.deserializer = deserializer;
    }

    @Override
    public Stream<Movie> movies() {
        try {
            return loadFroam(new URL("https://datasets.imdbws.com/title.basics.tsv.gz"));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private Stream<Movie> loadFroam(URL url) throws IOException {
        return loadFroam(url.openConnection());
    }

    private Stream<Movie> loadFroam(URLConnection connection) throws IOException {
        return loadFroam(unzip(connection.getInputStream()));
    }

    private Stream<Movie> loadFroam(InputStream inputStream) throws IOException {
        return loadFroam(toReader(inputStream)).onClose(()->close(inputStream));
    }

    private Stream<Movie> loadFroam(BufferedReader reader) {
        return reader.lines().skip(1).map(deserializer);
    }

    private void close(InputStream inputStream) {
        try {
            inputStream.close();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private BufferedReader toReader(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    private InputStream unzip(InputStream inputStream) throws IOException {
        return new GZIPInputStream(new BufferedInputStream(inputStream));
    }
}

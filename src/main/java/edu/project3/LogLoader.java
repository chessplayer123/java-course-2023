package edu.project3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogLoader {
    private static final Logger LOGGER = LogManager.getLogger();

    private LogLoader() {
    }

    private static Logs loadFromGlob(String glob) throws IOException {
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + glob);

        Vector<InputStream> streams = new Vector<>();
        Path start = Path.of("");
        int depth = Integer.MAX_VALUE;
        List<String> filenames = new ArrayList<>();

        try (Stream<Path> files = Files.find(start, depth, (path, attrs) -> matcher.matches(path))) {
            files.forEach(path -> {
                try {
                    streams.add(new FileInputStream(path.toFile()));
                    filenames.add(path.getFileName().toString());
                } catch (FileNotFoundException exception) {
                    LOGGER.warn("Error occurred while trying to find the file '{}': {}", path, exception.getMessage());
                }
            });
        }

        return new Logs(new SequenceInputStream(streams.elements()), filenames);
    }

    private static Logs loadFromURL(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();

        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        String[] path = url.split("/");

        return new Logs(response.body(), List.of(path[path.length - 1]));
    }

    public static Logs load(String path) throws IOException, InterruptedException {
        if (path.startsWith("http")) {
            return loadFromURL(path);
        }
        return loadFromGlob(path);
    }

    public record Logs(InputStream stream, List<String> filenames) {}
}

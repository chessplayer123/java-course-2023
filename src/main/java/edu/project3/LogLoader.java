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
import java.util.Vector;
import java.util.stream.Stream;

public class LogLoader {
    private LogLoader() {
    }

    private static InputStream loadFromGlob(String glob) throws IOException {
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + glob);

        Vector<InputStream> streams = new Vector<>();
        Path start = Path.of("");
        int depth = Integer.MAX_VALUE;

        try (Stream<Path> files = Files.find(start, depth, (path, attrs) -> matcher.matches(path))) {
            files.forEach(path -> {
                try {
                    streams.add(new FileInputStream(path.toFile()));
                } catch (FileNotFoundException ignored) { }
            });
        }

        return new SequenceInputStream(streams.elements());
    }

    private static InputStream loadFromURL(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();

        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        return response.body();
    }

    public static InputStream load(String path) throws IOException, InterruptedException {
        if (path.startsWith("http")) {
            return loadFromURL(path);
        }
        return loadFromGlob(path);
    }
}

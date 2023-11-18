package edu.hw6;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HackerNews {
    private static final String TOP_STORIES_URL = "https://hacker-news.firebaseio.com/v0/topstories.json";
    private static final String MESSAGE_URL = "https://hacker-news.firebaseio.com/v0/item/%d.json";
    private static final Pattern MESSAGE_TITLE_PATTERN = Pattern.compile("\"title\":\"(?<title>.*)\",\"type\"");
    private static final Logger LOGGER = LogManager.getLogger();

    private HackerNews() {
    }

    public static long[] hackerNewsTopStories() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(TOP_STORIES_URL))
            .GET()
            .build();

        long[] topStories = new long[] {};

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String[] indexes = response
                .body()
                .substring(1, response.body().length() - 1)
                .split(",");
            topStories = Arrays.stream(indexes)
                .mapToLong(Long::parseLong)
                .toArray();
        } catch (IOException | InterruptedException cause) {
            LOGGER.error("Can't get top stories due to exception: {}", cause.toString());
        }

        return topStories;
    }

    public static String news(long id) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(MESSAGE_URL.formatted(id)))
            .GET()
            .build();

        String title = null;

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Matcher matcher = MESSAGE_TITLE_PATTERN.matcher(response.body());
            if (matcher.find()) {
                title = matcher.group("title");
            }
        } catch (IOException | InterruptedException cause) {
            LOGGER.error("Can't get news (id={}): {}", id, cause);
        }

        return title;
    }
}

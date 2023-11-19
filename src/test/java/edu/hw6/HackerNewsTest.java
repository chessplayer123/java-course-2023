package edu.hw6;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import java.net.http.HttpClient;

public class HackerNewsTest {
    @Test
    void topStoriesReturnsNotEmptyLongArray() {
        long[] topStories = HackerNews.hackerNewsTopStories();

        assertThat(topStories)
            .isNotNull()
            .isNotEmpty();
    }

    @Test
    void newsForInvalidIndexReturnsNull() {
        String actualTitle = HackerNews.news(-1);

        assertThat(actualTitle).isNull();
    }

    @Test
    void newsForValidIndexReturnsTitle() {
        String actualTitle = HackerNews.news(37570037);
        String expectedTitle = "JDK 21 Release Notes";

        assertThat(actualTitle).isEqualTo(expectedTitle);
    }
}

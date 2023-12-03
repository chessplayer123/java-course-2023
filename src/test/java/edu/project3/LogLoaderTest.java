package edu.project3;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class LogLoaderTest {
    @Test
    void loadFromGlob() throws IOException, InterruptedException {
        Path filePath1 = Path.of("src/main/resources/logs/short_nginx_logs1");
        Path filePath2 = Path.of("src/main/resources/logs/short_nginx_logs2");

        byte[] array1 = Files.readAllBytes(filePath1);
        byte[] array2 = Files.readAllBytes(filePath2);
        byte[] expectedData = new byte[array1.length + array2.length];

        ByteBuffer buffer = ByteBuffer.wrap(expectedData);
        buffer.put(array1);
        buffer.put(array2);

        byte[] actualData = LogLoader.load("**/short_nginx_logs*").stream().readAllBytes();

        assertThat(actualData).containsOnly(expectedData);
    }

    @Test
    void loadFromURL() throws IOException, InterruptedException {
        byte[] actualData = LogLoader
            .load("https://raw.githubusercontent.com/chessplayer123/java-course-2023/main/README.md")
            .stream()
            .readAllBytes();

        byte[] expectedData = """
            ![Build Status](https://github.com/chessplayer123/java-course-2023/actions/workflows/build.yml/badge.svg)

            Студент: `Еремкин Денис Витальевич`""".getBytes();

        assertThat(actualData).containsOnly(expectedData);
    }
}

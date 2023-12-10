package edu.project4;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.IOException;
import java.nio.file.Path;

public class ImageUtilsTest {
    @ParameterizedTest
    @ValueSource(strings = {
        "fractalImage.png",
        "fractal.bmp",
        "image.jpg",
        "file.jpeg",
    })
    void imageUtilsSave_shouldCreateFileInAppropriateFormat(String filename, @TempDir Path tempDir) throws IOException {
        Path filePath = tempDir.resolve(filename);
        FractalImage image = FractalImage.create(1920, 1080, Pixel.create(0, 0, 0));

        ImageUtils.save(filePath, image);

        assertThat(filePath.toFile())
            .exists()
            .isFile()
            .isNotEmpty();
    }

    @Test
    void imageUtilsSave_shouldThrowExceptionOnUnexpectedFormat(@TempDir Path tempDir) {
        String filename = "text.c";
        FractalImage image = FractalImage.create(1920, 1080, Pixel.create(0, 0, 0));
        Path filePath = tempDir.resolve(filename);

        assertThatThrownBy(() -> ImageUtils.save(filePath, image))
            .isInstanceOf(IOException.class)
            .hasMessage("image format isn't supported");
    }
}

package edu.hw6;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class OutputStreamsComposerTest {
    @Test
    void createdFileExists(@TempDir Path tempDir) throws IOException {
        Path createdFile = tempDir.resolve("output.txt");

        OutputStreamsComposer.writeToFileViaComposition(createdFile);

        assertThat(createdFile).exists();
    }

    @Test
    void createdFileContainsExpectedText(@TempDir Path tempDir) throws IOException {
        Path createdFile = tempDir.resolve("output.txt");

        OutputStreamsComposer.writeToFileViaComposition(createdFile);

        List<String> actualText = Files.readAllLines(createdFile);
        List<String> expectedText = OutputStreamsComposer.MESSAGE.lines().toList();

        assertThat(actualText).isEqualTo(expectedText);
    }

    @Test
    void methodThrowsExceptionWhenFileCanNotBeCreated() {
        Path pathToFile = Path.of("/randomNotExistentDir/randomNotExistentFile.txt");

        assertThatThrownBy(() -> OutputStreamsComposer.writeToFileViaComposition(pathToFile))
            .isInstanceOf(IOException.class)
            .hasCauseInstanceOf(IOException.class)
            .hasMessage("can't write to file");
    }

    @Test
    void writeToExistentFileOverrideItsContent(@TempDir Path tempDir) throws IOException {
        Path createdFile = tempDir.resolve("file.txt");
        Files.writeString(createdFile, "Coding like poetry should be short and concise. ― Santosh Kalwar");

        OutputStreamsComposer.writeToFileViaComposition(createdFile);

        List<String> actualText = Files.readAllLines(createdFile);
        List<String> expectedText = OutputStreamsComposer.MESSAGE.lines().toList();

        assertThat(actualText).isEqualTo(expectedText);
    }
}

package edu.hw6;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.assertj.core.util.Files;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.List;

public class FileClonerTest {
    static Arguments[] existentFilesAndExpectedClone() {
        return new Arguments[] {
            Arguments.of(
                List.of("source-file.txt"),
                "source-file.txt", "source-file - копия.txt"
            ),
            Arguments.of(
                List.of("filename.txt", "filename - копия.txt"),
                "filename.txt", "filename - копия (2).txt"
            ),
            Arguments.of(
                List.of("source-file.txt", "source-file - копия.txt", "source-file - копия (2).txt"),
                "source-file.txt", "source-file - копия (3).txt"
            ),
            Arguments.of(
                List.of("source-file.txt", "source", "-", "file.txt"),
                "source-file.txt", "source-file - копия.txt"
            ),
            Arguments.of(
                List.of("file-wo-extension"),
                "file-wo-extension", "file-wo-extension - копия"
            ),
            Arguments.of(
                List.of("file", "file - копия"),
                "file", "file - копия (2)"
            ),
            Arguments.of(
                List.of("file-wo-extension", "file-wo-extension - копия", "file-wo-extension - копия (3)"),
                "file-wo-extension", "file-wo-extension - копия (2)"
            ),
        };
    }

    @ParameterizedTest
    @MethodSource("existentFilesAndExpectedClone")
    void nextCloneCreationWithExistentFiles(
        List<String> existentFilenames,
        String srcFilename,
        String expectedCloneFilename,
        @TempDir Path tempDir
    ) throws IOException {
        FileCloner cloner = new FileCloner();

        existentFilenames.stream()
            .map(filename -> tempDir.resolve(filename).toString())
            .forEach(Files::newFile);

        File srcFile = tempDir.resolve(srcFilename).toFile();
        String fileContent = "text on few\nlines\n1\n2\n3";
        try(PrintStream writer = new PrintStream(srcFile)) {
            writer.print(fileContent);
        }

        File expectedClonedFile = tempDir.resolve(expectedCloneFilename).toFile();
        cloner.cloneFile(srcFile.toPath());

        assertThat(expectedClonedFile)
            .exists()
            .hasSameTextualContentAs(srcFile);
    }

    @Test
    void cloneOfNonExistentFileThrowsException(@TempDir Path tempDir) {
        FileCloner cloner = new FileCloner();

        Path pathToFile = tempDir.resolve("non-existent-file");

        assertThatThrownBy(() -> cloner.cloneFile(pathToFile))
            .isInstanceOf(IOException.class)
            .hasCauseInstanceOf(IOException.class)
            .hasMessage("can't create clone of file");
    }
}

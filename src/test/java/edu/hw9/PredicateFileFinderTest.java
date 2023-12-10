package edu.hw9;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class PredicateFileFinderTest {
    static Arguments[] fileExtensionDirTree() {
        return new Arguments[] {
            Arguments.of(
                List.of(
                    "subDir/file1.txt",
                    "secret.txt",
                    "dict.json",
                    "dir1/dir2/dir3/lecture1.txt",
                    "dir1/dir2/dir3/lecture2.txt",
                    "dir1/dir2/text.txt",
                    "dir1/dir2/sea.png",
                    "dir1/passwords.txt"
                ),
                List.of(
                    "subDir/file1.txt",
                    "secret.txt",
                    "dir1/dir2/dir3/lecture1.txt",
                    "dir1/dir2/dir3/lecture2.txt",
                    "dir1/dir2/text.txt",
                    "dir1/passwords.txt"
                )
            )
        };
    }

    @ParameterizedTest
    @MethodSource("fileExtensionDirTree")
    void extensionFileFinder_shouldReturnExpectedFiles(
        List<String> layout,
        List<String> expectedFilePaths,
        @TempDir Path tempDir
    ) throws IOException {
        for (String path : layout) {
            Files.createDirectories(tempDir.resolve(path).getParent());
            Files.createFile(tempDir.resolve(path));
        }

        Stream<File> actualFiles = FindUtils.findFilesWithExtension(tempDir, "txt");
        List<File> expectedFiles = expectedFilePaths
            .stream()
            .map(filePath -> tempDir.resolve(filePath).toFile())
            .toList();

        assertThat(actualFiles).containsExactlyInAnyOrderElementsOf(expectedFiles);
    }

    static Arguments[] fileSizeTree() {
        return new Arguments[] {
            Arguments.of(
                Map.of(
                    "empty.txt", 0,
                    "passwords.json", 20 * 1024,
                    "home/user1/.bash_history", 1024,
                    "home/user2/.bash_history", 100,
                    "etc/locale.gen", 12 * 1024,
                    "etc/hosts", 100
                ),
                List.of(
                    "home/user2/.bash_history",
                    "etc/hosts"
                )
            )
        };
    }

    @ParameterizedTest
    @MethodSource("fileSizeTree")
    void sizeFileFinder_shouldReturnExpectedFiles(
        Map<String, Integer> layout,
        List<String> expectedFilePaths,
        @TempDir Path tempDir
    ) throws IOException {
        for (var entry : layout.entrySet()) {
            Path path = tempDir.resolve(entry.getKey());
            Files.createDirectories(path.getParent());
            Files.createFile(path);
            Files.writeString(path, "0".repeat(entry.getValue()));
        }

        Stream<File> actualFiles = FindUtils.findFilesWithSize(tempDir, 100);
        List<File> expectedFiles = expectedFilePaths
            .stream()
            .map(filePath -> tempDir.resolve(filePath).toFile())
            .toList();

        assertThat(actualFiles).containsExactlyInAnyOrderElementsOf(expectedFiles);
    }

    static Arguments[] fileSystemTree() {
        return new Arguments[] {
            Arguments.of(
                List.of(
                    "subDir/file1.txt",
                    "subDir/file2.txt",
                    "secret.txt",
                    "dict.json",
                    "dir1/dir2/dir3/lecture1.txt",
                    "dir1/dir2/dir3/lecture2.txt",
                    "dir1/dir2/dir3/script.sh",
                    "dir1/dir2/text.txt",
                    "dir1/dir2/sea.png",
                    "dir1/dir2/game.exe",
                    "dir1/passwords.txt"
                ),
                List.of(
                    "dir1/dir2/dir3",
                    "dir1/dir2"
                )
            )
        };
    }

    @ParameterizedTest
    @MethodSource("fileSystemTree")
    void directoryFinder_shouldReturnDirectoriesWithMoreThanNFiles(
        List<String> layout,
        List<String> expectedDirPaths,
        @TempDir Path tempDir
    ) throws IOException {
        for (String path : layout) {
            Files.createDirectories(tempDir.resolve(path).getParent());
            Files.createFile(tempDir.resolve(path));
        }

        Stream<File> actualDirs = FindUtils.findDirectoriesWithMoreThanGivenFiles(tempDir, 2);
        List<File> expectedDirs = expectedDirPaths
            .stream()
            .map(filePath -> tempDir.resolve(filePath).toFile())
            .toList();

        assertThat(actualDirs).containsExactlyInAnyOrderElementsOf(expectedDirs);
    }

    @Test
    void finderThrowsExceptionWhenPathIsNotDirectory(@TempDir Path tempDir) throws IOException {
        Path filePath = tempDir.resolve("not-dir.txt");
        Files.createFile(filePath);

        assertThatThrownBy(() -> new PredicateFileFinder(filePath, file -> true))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("root must be directory");
    }
}

package edu.hw6;

import edu.hw6.Filters.AbstractFilter;
import edu.hw6.Filters.AttributesUtils;
import edu.hw6.Filters.GlobFilter;
import edu.hw6.Filters.MagicNumbersFilter;
import edu.hw6.Filters.RegexFilter;
import edu.hw6.Filters.SizeFilter;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class FilterTest {
    @Test
    void abstractFilterApplyAllFilters(@TempDir Path tempDir) throws IOException {
        Map<String, byte[]> files = Map.of(
            "blank-file.txt", new byte[] {},
            "picture-of-car.png", new byte[] {(byte) 0x89, 'P', 'N', 'G', '5', '6', '7', '8', '9', '0'},
            "picture.png", new byte[] {(byte) 0x89, 'P', 'N', 'G'},
            "bmp-picture.bmp", new byte[] {'B', 'M', '3', '4', '5', '6', '7'},
            "beautiful-sunrise.png", new byte[] {(byte) 0x89, 'P', 'N', 'G', '5', '6'},
            "main.py", "print('Hello World')".getBytes()
        );
        for (var entry : files.entrySet()) {
            Files.write(tempDir.resolve(entry.getKey()), entry.getValue());
        }

        String[] expectedFiles = new String[] {"picture-of-car.png", "beautiful-sunrise.png"};

        final AbstractFilter regularFile = Files::isRegularFile;
        DirectoryStream.Filter<Path> filter = regularFile
            .and(SizeFilter.inRange(5, 11))
            .and(MagicNumbersFilter.magicNumber(0x89, 'P', 'N', 'G'))
            .and(AttributesUtils.isReadable)
            .and(AttributesUtils.isWritable)
            .and(GlobFilter.globMatches("*.png"))
            .and(RegexFilter.regexContains("[-]"));

        try (DirectoryStream<Path> actualFiles = Files.newDirectoryStream(tempDir, filter)) {
            assertThat(actualFiles)
                .map(file -> file.getFileName().toString())
                .containsOnly(expectedFiles);
        }
    }

    @Test
    void lessThanAcceptFilesWithSizeLessThanGiven(@TempDir Path tempDir) throws IOException {
        Map<String, Integer> files = Map.of(
            "file1.txt", 100,
            "file2.txt", 15,
            "file3.txt", 99,
            "file4.txt", 1093
        );
        for (var entry : files.entrySet()) {
            try (FileWriter writer = new FileWriter(tempDir.resolve(entry.getKey()).toString())) {
                writer.write("0".repeat(entry.getValue()));
            }
        }
        String[] expectedFiles = new String[] {"file2.txt", "file3.txt"};

        DirectoryStream.Filter<Path> sizeFilter = SizeFilter.lessThan(100);

        try (DirectoryStream<Path> actualFiles = Files.newDirectoryStream(tempDir, sizeFilter)) {
            assertThat(actualFiles)
                .map(file -> file.getFileName().toString())
                .containsOnly(expectedFiles);
        }
    }

    @Test
    void largerThanAcceptFilesWithSizeMoreThanGiven(@TempDir Path tempDir) throws IOException {
        Map<String, Integer> files = Map.of(
            "file1.txt", 0,
            "file2.txt", 551,
            "file3.txt", 1000,
            "file4.txt", 552
        );
        for (var entry : files.entrySet()) {
            try (FileWriter writer = new FileWriter(tempDir.resolve(entry.getKey()).toString())) {
                writer.write("0".repeat(entry.getValue()));
            }
        }
        String[] expectedFiles = new String[] {"file3.txt", "file4.txt"};

        DirectoryStream.Filter<Path> sizeFilter = SizeFilter.largerThan(551);

        try (DirectoryStream<Path> actualFiles = Files.newDirectoryStream(tempDir, sizeFilter)) {
            assertThat(actualFiles)
                .map(file -> file.getFileName().toString())
                .containsOnly(expectedFiles);
        }
    }

    @Test
    void regexFilterAcceptsExpectedFiles(@TempDir Path tempDir) throws IOException {
        List<String> filenames = List.of("file-with-dashes.txt", "file1", "__init__.py", "empty-file.md");
        for (String filename : filenames) {
            Files.createFile(tempDir.resolve(filename));
        }

        String[] expectedFiles = new String[] {"file-with-dashes.txt", "empty-file.md"};

        DirectoryStream.Filter<Path> regexFilter = RegexFilter.regexContains("[-]");

        try (DirectoryStream<Path> actualFiles = Files.newDirectoryStream(tempDir, regexFilter)) {
            assertThat(actualFiles)
                .map(file -> file.getFileName().toString())
                .containsOnly(expectedFiles);
        }
    }

    @Test
    void magicNumberFilterAcceptsExpectedFiles(@TempDir Path tempDir) throws IOException {
        Map<String, byte[]> filesWithMagicNumbers  = Map.of(
            "mountains.png",  new byte[] {(byte) 0x89, 'P', 'N', 'G'},
            "ocean.bmp",      new byte[] {'B', 'M'},
            "main.cpp",       new byte[] {},
            "main",           new byte[] {(byte) 0x7F, 'E', 'L', 'F'},
            "skyscraper",     new byte[] {(byte) 0x89, 'P', 'N', 'G'}
        );
        for (var file : filesWithMagicNumbers.entrySet()) {
            Files.write(tempDir.resolve(file.getKey()), file.getValue());
        }

        String[] expectedFiles = new String[] {"skyscraper", "mountains.png"};

        DirectoryStream.Filter<Path> magicNumbersFilter = MagicNumbersFilter.magicNumber(0x89, 'P', 'N', 'G');

        try (DirectoryStream<Path> actualFiles = Files.newDirectoryStream(tempDir, magicNumbersFilter)) {
            assertThat(actualFiles)
                .map(file -> file.getFileName().toString())
                .containsOnly(expectedFiles);
        }
    }

    @Test
    void globFilterAcceptsExpectedFiles(@TempDir Path tempDir) throws IOException {
        List<String> filenames = List.of("essay.txt", "cmd.exe", "__init__.py", "README.md", "main.py");
        for (String filename : filenames) {
            Files.createFile(tempDir.resolve(filename));
        }

        String[] expectedFiles = new String[] {"main.py", "__init__.py"};

        DirectoryStream.Filter<Path> globFilter = GlobFilter.globMatches("*.py");

        try (DirectoryStream<Path> actualFiles = Files.newDirectoryStream(tempDir, globFilter)) {
            assertThat(actualFiles)
                .map(file -> file.getFileName().toString())
                .containsOnly(expectedFiles);
        }
    }
}

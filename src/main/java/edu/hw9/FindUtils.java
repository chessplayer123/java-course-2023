package edu.hw9;

import java.io.File;
import java.nio.file.Path;
import java.util.stream.Stream;

public final class FindUtils {
    private FindUtils() {
    }

    public static Stream<File> findFilesWithExtension(Path path, String extension) {
        return new PredicateFileFinder(path, file -> file.toPath().getFileName().toString().endsWith("." + extension))
            .compute();
    }

    public static Stream<File> findFilesWithSize(Path path, long expectedSize) {
        return new PredicateFileFinder(path, file -> file.isFile() && file.length() == expectedSize)
            .compute();
    }

    public static Stream<File> findDirectoriesWithMoreThanGivenFiles(Path path, int lowerFilesNum) {
        return new PredicateFileFinder(path, file -> file.isDirectory() && file.listFiles().length > lowerFilesNum)
            .compute();
    }
}

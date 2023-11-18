package edu.hw6.Filters;

import java.nio.file.DirectoryStream;
import java.nio.file.Path;

public final class AttributesUtils {
    private AttributesUtils() {
    }

    // Files::isReadable
    public static DirectoryStream.Filter<Path> isReadable = filePath -> filePath.toFile().canRead();

    // Files::isWritable
    public static DirectoryStream.Filter<Path> isWritable = filePath -> filePath.toFile().canWrite();

    // Files::isExecutable
    public static DirectoryStream.Filter<Path> isExecutable = filePath -> filePath.toFile().canExecute();

    // Files::isDirectory
    public static DirectoryStream.Filter<Path> isDirectory = filePath -> filePath.toFile().isDirectory();

    // Files::isRegularFile
    public static DirectoryStream.Filter<Path> isRegularFile = filePath -> filePath.toFile().isFile();
}

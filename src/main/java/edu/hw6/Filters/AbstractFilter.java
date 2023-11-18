package edu.hw6.Filters;

import java.nio.file.DirectoryStream;
import java.nio.file.Path;


@FunctionalInterface
public interface AbstractFilter extends DirectoryStream.Filter<Path> {
    default AbstractFilter and(DirectoryStream.Filter<Path> filter) {
        return path -> this.accept(path) && filter.accept(path);
    }
}

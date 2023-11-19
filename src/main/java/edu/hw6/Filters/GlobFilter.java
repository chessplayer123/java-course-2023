package edu.hw6.Filters;

import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;

public class GlobFilter implements DirectoryStream.Filter<Path> {
    private final PathMatcher pathMatcher;

    private GlobFilter(String glob) {
        pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + glob);
    }

    public static GlobFilter globMatches(String glob) {
        return new GlobFilter(glob);
    }

    @Override
    public boolean accept(Path path) {
        return pathMatcher.matches(path.getFileName());
    }
}

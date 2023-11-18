package edu.hw6.Filters;

import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexFilter implements DirectoryStream.Filter<Path> {
    private final Pattern filenamePattern;

    private RegexFilter(String pattern) {
        filenamePattern = Pattern.compile(pattern);
    }

    public static RegexFilter regexContains(String regexPattern) {
        return new RegexFilter(regexPattern);
    }

    @Override
    public boolean accept(Path path) {
        Matcher matcher = filenamePattern.matcher(path.getFileName().toString());
        return matcher.find();
    }
}

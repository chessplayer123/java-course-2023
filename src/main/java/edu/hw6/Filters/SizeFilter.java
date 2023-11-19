package edu.hw6.Filters;

import java.nio.file.DirectoryStream;
import java.nio.file.Path;

public class SizeFilter implements DirectoryStream.Filter<Path> {
    private final long lowerBound;
    private final long upperBound;

    private SizeFilter(long lowerBound, long upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public static SizeFilter largerThan(long lowerBound) {
        return new SizeFilter(lowerBound, Long.MAX_VALUE);
    }

    public static SizeFilter lessThan(long upperBound) {
        return new SizeFilter(-1, upperBound);
    }

    public static SizeFilter inRange(long lowerBound, long upperBound) {
        return new SizeFilter(lowerBound, upperBound);
    }

    @Override
    public boolean accept(Path path) {
        long sizeInBytes = path.toFile().length();
        return lowerBound < sizeInBytes && sizeInBytes < upperBound;
    }
}

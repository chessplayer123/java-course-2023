package edu.hw6.Filters;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class MagicNumbersFilter implements DirectoryStream.Filter<Path> {
    private final int[] magicNumbers;

    private MagicNumbersFilter(int[] magicNumbers) {
        this.magicNumbers = magicNumbers;
    }

    public static MagicNumbersFilter magicNumber(int... magicNumbers) {
        return new MagicNumbersFilter(magicNumbers);
    }

    @Override
    public boolean accept(Path path) throws IOException {
        byte[] bytes = Files.readAllBytes(path);
        if (bytes.length < magicNumbers.length) {
            return false;
        }

        for (int i = 0; i < magicNumbers.length; ++i) {
            if ((byte) magicNumbers[i] != bytes[i]) {
                return false;
            }
        }
        return true;
    }
}

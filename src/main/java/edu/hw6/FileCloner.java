package edu.hw6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileCloner {
    private Path getCloneFilePath(Path path) {
        FilenameAndExtension cloningFile = FilenameAndExtension.of(path.getFileName().toString());

        String copyFilename = "%s - копия%s".formatted(cloningFile.name, cloningFile.extension);
        Path parent = path.getParent();

        int copyNum = 2;
        while (Files.exists(parent.resolve(copyFilename))) {
            copyFilename = "%s - копия (%d)%s".formatted(cloningFile.name, copyNum, cloningFile.extension);
            ++copyNum;
        }

        return parent.resolve(copyFilename);
    }

    public void cloneFile(Path path) throws IOException {
        Path clonedFilePath = getCloneFilePath(path);
        try {
            Files.copy(path, clonedFilePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException cause) {
            throw new IOException("can't create clone of file", cause);
        }
    }

    private record FilenameAndExtension(String name, String extension) {
        public static FilenameAndExtension of(String filename) {
            int dotIdx = filename.lastIndexOf('.');
            if (dotIdx < 0) {
                return new FilenameAndExtension(filename, "");
            }
            return new FilenameAndExtension(filename.substring(0, dotIdx), filename.substring(dotIdx));
        }
    }
}

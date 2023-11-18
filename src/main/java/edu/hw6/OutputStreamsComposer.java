package edu.hw6;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import org.jetbrains.annotations.NotNull;

public class OutputStreamsComposer {
    public static final String MESSAGE = "Programming is learned by writing programs. ― Brian Kernighan";

    private OutputStreamsComposer() {
    }

    public static void writeToFileViaComposition(@NotNull Path path) throws IOException {
        try (OutputStream outputStream = Files.newOutputStream(path)) {
            CheckedOutputStream checkedOutputStream = new CheckedOutputStream(outputStream, new CRC32());
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(checkedOutputStream);
            OutputStreamWriter streamWriter = new OutputStreamWriter(bufferedOutputStream);
            PrintWriter printWriter = new PrintWriter(streamWriter);

            printWriter.print(MESSAGE);
            printWriter.flush();

            printWriter.close();
        } catch (IOException cause) {
            throw new IOException("can't write to file", cause);
        }
    }
}

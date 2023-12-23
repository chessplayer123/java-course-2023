package edu.project4;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import javax.imageio.ImageIO;

public final class ImageUtils {
    private static final Set<String> SUPPORTED_FORMATS = Set.of(ImageIO.getWriterFormatNames());

    private ImageUtils() {
    }

    public static void save(Path filepath, FractalImage fractalImage) throws IOException {
        String[] splitFileName = filepath.getFileName().toString().split("\\.");
        String format = splitFileName[splitFileName.length - 1];

        if (!SUPPORTED_FORMATS.contains(format)) {
            throw new IOException("image format isn't supported");
        }

        BufferedImage image = new BufferedImage(
            fractalImage.width(),
            fractalImage.height(),
            BufferedImage.TYPE_INT_RGB
        );

        for (int row = 0; row < fractalImage.height(); ++row) {
            for (int col = 0; col < fractalImage.width(); ++col) {
                image.setRGB(col, row, fractalImage.pixel(col, row).toRGB());
            }
        }

        ImageIO.write(image, format, filepath.toFile());
    }
}

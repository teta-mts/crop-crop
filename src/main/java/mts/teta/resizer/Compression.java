package mts.teta.resizer;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Compression {
    public void compress() throws IOException {
        File inputImage = new File("1.jpg");
        BufferedImage image = ImageIO.read(inputImage);
        int width = image.getWidth();
        int height = image.getHeight();
        Thumbnails.of(new File("1.jpg")).size(width, height).outputQuality(0.01).toFile(new File("3.jpg"));
    }
}

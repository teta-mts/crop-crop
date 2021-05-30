package mts.teta.resizer;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OutputFormat {
    public void setOutputFormat() throws IOException {
        BufferedImage image = ImageIO.read(new File("1.jpg"));
        ImageIO.write(image, "png", new File("p.png"));
    }
}

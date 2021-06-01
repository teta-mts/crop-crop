package mts.teta.resizer;

import marvin.image.MarvinImage;
import mts.teta.resizer.imageprocessor.BadAttributesException;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static marvinplugins.MarvinPluginCollection.gaussianBlur;

public class ImageProcessor {
    public void processImage(BufferedImage image, ResizerApp resizerApp) throws IOException, BadAttributesException {
        image = resize(image, resizerApp);
        image = setQuality(image, resizerApp);
        image = crop(image, resizerApp);
        image = blurImage(image, resizerApp);

        ImageIO.write(image, resizerApp.getOutputFormat(), resizerApp.outputFile);
    }

    private BufferedImage resize(BufferedImage image, ResizerApp resizerApp) throws IOException, BadAttributesException {
        if ((resizerApp.getWidth() == null) ||
            (resizerApp.getHeight() == null)) {
            return image;
        }

        return Thumbnails.of(image)
                .size(resizerApp.getWidth(), resizerApp.getHeight())
                .keepAspectRatio(false)
                .outputFormat(resizerApp.getOutputFormat())
                .asBufferedImage();

    }

    private BufferedImage setQuality(BufferedImage image, ResizerApp resizerApp) throws IOException, BadAttributesException {
        if (resizerApp.getQuality() == null) {
            return image;
        }
        if ((resizerApp.getQuality() < 1) || (resizerApp.getQuality() > 100)) {
            throw new BadAttributesException("Please check params!");
        }

        return Thumbnails.of(image)
                .size(image.getWidth(), image.getHeight())
                .outputQuality(resizerApp.getQuality() / 100.0)
                .asBufferedImage();
    }

    private BufferedImage crop(BufferedImage image, ResizerApp resizerApp) throws IOException, BadAttributesException {
        if (    (resizerApp.getCropWidth() == null) ||
                (resizerApp.getCropHeight() == null) ||
                (resizerApp.getCropX() == null) ||
                (resizerApp.getCropY() == null)) {
            return image;
        }

        return image.getSubimage(resizerApp.getCropX(), resizerApp.getCropY(), resizerApp.getCropWidth(), resizerApp.getCropHeight());
    }

    private BufferedImage blurImage(BufferedImage image, ResizerApp resizerApp) throws IOException, BadAttributesException {
        if (resizerApp.getBlurRadius() == null) {
            return image;
        }

        MarvinImage marvinImage = new MarvinImage(image);
        gaussianBlur(marvinImage, marvinImage, resizerApp.getBlurRadius());
        return marvinImage.getBufferedImageNoAlpha();
    }
}

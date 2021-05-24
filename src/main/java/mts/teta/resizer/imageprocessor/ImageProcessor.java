package mts.teta.resizer.imageprocessor;

import marvin.image.MarvinImage;
import marvin.util.MarvinAttributes;
import mts.teta.resizer.ResizerApp;
import net.coobird.thumbnailator.Thumbnails;
import org.marvinproject.image.blur.gaussianBlur.GaussianBlur;
import org.marvinproject.image.segmentation.crop.Crop;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageProcessor {

    public void processImage(BufferedImage image, ResizerApp resizerApp) throws IOException {
        if(resizerApp.getResizeWidth() != null) {
            image = resize(image, resizerApp);
        }
        if(resizerApp.getCropWidth() != null) {
            image = crop(image, resizerApp);
        }
        if(resizerApp.getBlurRadius() != null) {
            image = blur(image, resizerApp);
        }
        Thumbnails.Builder<BufferedImage> temp = Thumbnails.of(image).scale(1);
        if(resizerApp.getQuality() != null) {
            temp.outputQuality(resizerApp.getQuality() / 100f);
        }
        temp.outputFormat(resizerApp.getOutputFormat()).toFile(resizerApp.getOutputFilename());
    }

    private BufferedImage resize(BufferedImage image, ResizerApp resizerApp) throws IOException {
        Thumbnails.Builder<BufferedImage> temp =
                Thumbnails.of(image).forceSize(resizerApp.getResizeWidth(), resizerApp.getResizeHeight());
        if(resizerApp.getOutputFormat() != null) {
            temp.outputFormat(resizerApp.getOutputFormat());
        }
        return temp.asBufferedImage();
    }

    private BufferedImage crop(BufferedImage image, ResizerApp resizerApp) throws IOException {
        MarvinImage outImage = new MarvinImage();
        MarvinAttributes attributes = new MarvinAttributes();
        attributes.set("x", resizerApp.getX());
        attributes.set("y", resizerApp.getY());
        attributes.set("width", resizerApp.getCropWidth());
        attributes.set("height", resizerApp.getCropHeight());
        new Crop().process(new MarvinImage(image), outImage, attributes);
        return outImage.getBufferedImage();
        //ImageIO.write(outImage.getBufferedImage(), resizerApp.getOutputFormat(), new File(resizerApp.getOutputFilename()));
    }

    private BufferedImage blur(BufferedImage image, ResizerApp resizerApp) throws IOException {
        MarvinImage outImage = new MarvinImage();
        MarvinAttributes attributes = new MarvinAttributes();
        attributes.set("radius", resizerApp.getBlurRadius());
        new GaussianBlur().process(new MarvinImage(image), outImage, attributes);
        return outImage.getBufferedImage();
        //ImageIO.write(outImage.getBufferedImage(), resizerApp.getOutputFormat(), new File(resizerApp.getOutputFilename()));
    }
}

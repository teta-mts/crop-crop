package mts.teta.resizer;

import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;
import marvinplugins.MarvinPluginCollection;

import mts.teta.resizer.imageprocessor.BadAttributesException;
import net.coobird.thumbnailator.Thumbnails;

import java.awt.image.BufferedImage;
import java.io.IOException;


public class ImageProcessor {
    public void processImage(BufferedImage read, ResizerApp resizerApp) throws IOException, BadAttributesException {
        MarvinImage image = MarvinImageIO.loadImage(resizerApp.getInputFile().getAbsolutePath());
        try {
            if (resizerApp.isCropCheckBox()) {
                MarvinPluginCollection.crop(image.clone(), image, resizerApp.getCropX(), resizerApp.getCropY(), resizerApp.getCropWidth(), resizerApp.getCropHeight());
            }
            if (resizerApp.isBlurCheckBox()) {
                MarvinPluginCollection.gaussianBlur(image.clone(), image, resizerApp.getBlurRadius());
            }
            if (resizerApp.isResizeCheckBox() || resizerApp.isOutputFormatCheckbox() || resizerApp.isQualityCheckBox()) {
                read = image.getNewImageInstance();
                processByThumbnailator(read, resizerApp);
            } else
                MarvinImageIO.saveImage(image, resizerApp.getOutputFile().getAbsolutePath());
        } catch (IllegalArgumentException | NegativeArraySizeException e) {
            throw new BadAttributesException("Please check params!");
        }

    }

    private void processByThumbnailator(BufferedImage read, ResizerApp resizerApp) throws BadAttributesException, IOException {
        double quality = (double) resizerApp.getQuality() / 100;
        int width;
        int height;
        if (resizerApp.isResizeCheckBox()) {
            width = resizerApp.getResizeWidth();
            height = resizerApp.getResizeHeight();
        } else {
            width = read.getWidth();
            height = read.getHeight();
        }

        try {
            if (resizerApp.isOutputFormatCheckbox() && resizerApp.isQualityCheckBox()) {
                Thumbnails.of(read).forceSize(width, height).outputFormat(resizerApp.getOutputFormat()).outputQuality(quality).toFile(resizerApp.getOutputFile());
            } else if (resizerApp.isOutputFormatCheckbox()) {
                Thumbnails.of(read).forceSize(width, height).outputFormat(resizerApp.getOutputFormat()).toFile(resizerApp.getOutputFile());
            } else if (resizerApp.isQualityCheckBox()) {
                Thumbnails.of(read).forceSize(width, height).outputQuality(quality).toFile(resizerApp.getOutputFile());
            } else
                Thumbnails.of(read).forceSize(width, height).toFile(resizerApp.getOutputFile());
        } catch (IllegalArgumentException e) {
            throw new BadAttributesException("Please check params!");
        }
    }
}

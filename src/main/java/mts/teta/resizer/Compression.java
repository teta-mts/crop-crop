package mts.teta.resizer;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Compression {
    public void compress(String inputImagePath, String qualityValue) {
        Checks compressCheck = new Checks();
        if (compressCheck.checkImagePath(inputImagePath)) {
            if (compressCheck.checkInputImageFormat(inputImagePath)) {
                if (compressCheck.checkIfParameterIsNumber(qualityValue)) {
                    if (compressCheck.checkIfParameterIsAllowableSize(qualityValue, 0, 100)) {
                        getCompressedImage(inputImagePath, qualityValue);
                    }
                }
            }
        }
    }

    private void getCompressedImage(String inputImagePath, String qualityValue) {
        File inputImage = new File(inputImagePath);
        float qualityValueFloat = Float.parseFloat(qualityValue) / 100;
        ImageParams imageParameter = new ImageParams();
        imageParameter.setAllParamsForNewImage(inputImagePath, String.valueOf(qualityValueFloat));
        try {
            BufferedImage image = ImageIO.read(inputImage);
            Thumbnails.of(inputImage)
                    .size(image.getWidth(), image.getHeight())
                    .outputQuality(qualityValueFloat)
                    .toFile(new File(imageParameter.getNewFilePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
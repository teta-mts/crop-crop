package mts.teta.resizer;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Compression {
    public void compress(String inputImagePath, String qualityValue) {
        File inputImage = new File(inputImagePath);
        Checks compressCheck = new Checks();
        if (compressCheck.checkImagePath(inputImagePath)) {
            if (compressCheck.checkIfParameterIsNumber(qualityValue)) {
                float qualityValueFloat = Float.parseFloat(qualityValue) / 100;
                if (compressCheck.checkIfParameterIsAllowableSize(qualityValue, 0, 100)) {
                    ImageParams imageParameter = new ImageParams();
                    imageParameter.setImagePath(inputImagePath);
                    imageParameter.setImageName(inputImagePath);
                    imageParameter.setImagePostfixName(qualityValue);
                    imageParameter.setImageFormat(inputImagePath);

                    try {
                        BufferedImage image = ImageIO.read(inputImage);
                        Thumbnails.of(inputImage)
                                .size(image.getWidth(), image.getHeight())
                                .outputQuality(qualityValueFloat)
                                .toFile(imageParameter.getNewFilePath());
                    } catch (IOException e) {
                        System.out.println("Smth went wrong");
                    }
                }
            }
        }
    }
}
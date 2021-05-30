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
            if (compressCheck.checkIfParameterIsNumber(qualityValue)) {
                if (compressCheck.checkIfParameterIsAllowableSize(qualityValue, 0, 100)) {
                    getCompressedImage(inputImagePath, qualityValue);
                }
            }
        }
    }

    private void getCompressedImage(String inputImagePath, String qualityValue){
        File inputImage = new File(inputImagePath);
        ImageParams imageParameter = new ImageParams();
        float qualityValueFloat = Float.parseFloat(qualityValue) / 100;
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
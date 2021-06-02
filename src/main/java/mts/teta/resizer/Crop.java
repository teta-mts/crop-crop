package mts.teta.resizer;

import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static marvinplugins.MarvinPluginCollection.crop;

public class Crop {
    public void cropping(String inputImagePath, String x, String y, String width, String height) {
        Checks cropCheck = new Checks();
        if (cropCheck.checkImagePath(inputImagePath)) {
            if (cropCheck.checkInputImageFormat(inputImagePath)) {
                if (cropCheck.checkIfParameterIsNumber(x) && cropCheck.checkIfParameterIsNumber(y)
                        && cropCheck.checkIfParameterIsNumber(width) && cropCheck.checkIfParameterIsNumber(height)) {
                    BufferedImage image = null;
                    try {
                        image = ImageIO.read(new File(inputImagePath));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    assert image != null;
                    if (cropCheck.checkIfParameterIsAllowableSize(x, 0, image.getWidth())
                            && cropCheck.checkIfParameterIsAllowableSize(y, 0, image.getHeight())
                            && cropCheck.checkIfParameterIsAllowableSize(width, 0, image.getWidth() - Integer.parseInt(x))
                            && cropCheck.checkIfParameterIsAllowableSize(width, 0, image.getHeight() - Integer.parseInt(y))) {
                        getCroppedImage(inputImagePath, x, y, width, height);
                    }
                }
            }
        }
    }

    private void getCroppedImage(String inputImagePath, String x, String y, String width, String height) {
        ImageParams imageParameter = new ImageParams();
        imageParameter.setAllParamsForNewImage(inputImagePath, "crop_" + width + "_" + "height");
        MarvinImage image = MarvinImageIO.loadImage(inputImagePath);
        crop(image.clone(), image, Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(width), Integer.parseInt(height));
        MarvinImageIO.saveImage(image, imageParameter.getNewFilePath());
    }
}

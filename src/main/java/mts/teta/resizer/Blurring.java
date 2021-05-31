package mts.teta.resizer;

import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;

import static marvinplugins.MarvinPluginCollection.gaussianBlur;

public class Blurring {
    public void blurring(String inputImagePath, String radius) {
        Checks blurCheck = new Checks();
        if (blurCheck.checkImagePath(inputImagePath)) {
            if (blurCheck.checkIfParameterIsNumber(radius)) {
                if (blurCheck.checkIfParameterIsAllowableSize(radius, 0, 63)) {
                    getBlurredImage(inputImagePath, radius);
                }
            }
        }
    }

    private void getBlurredImage(String inputImagePath, String radius) {
        ImageParams imageParameter = new ImageParams();
        imageParameter.setAllParamsForNewImage(inputImagePath, radius);
        MarvinImage image = MarvinImageIO.loadImage(inputImagePath);
        gaussianBlur(image.clone(), image, Integer.parseInt(radius));
        MarvinImageIO.saveImage(image, imageParameter.getNewFilePath());
    }
}

package mts.teta.resizer;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;

public class Resizing {
    public void resize(String inputImagePath, String width, String height) {
        Checks resizeCheck = new Checks();
        if (resizeCheck.checkImagePath(inputImagePath)) {
            if (resizeCheck.checkInputImageFormat(inputImagePath)) {
                if (resizeCheck.checkIfParameterIsNumber(width) && resizeCheck.checkIfParameterIsNumber(height)) {
                    if (resizeCheck.checkIfParameterIsAllowableSize(width, 1, 1920)
                            && resizeCheck.checkIfParameterIsAllowableSize(height, 1, 1920)) {
                        getResizedImage(inputImagePath, Integer.parseInt(width), Integer.parseInt(height));
                    }
                }
            }
        }
    }

    private void getResizedImage(String inputImagePath, int width, int height) {
        ImageParams imageParameter = new ImageParams();
        imageParameter.setAllParamsForNewImage(inputImagePath, width + "_" + height);
        try {
            Thumbnails.of(new File(inputImagePath)).size(width, height).toFile(new File(imageParameter.getNewFilePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

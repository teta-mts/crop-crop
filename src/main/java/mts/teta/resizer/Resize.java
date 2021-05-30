package mts.teta.resizer;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;

public class Resize {
    public void resize(String imagePath, String width, String height) {
        Checks resizeCheck = new Checks();
        if(resizeCheck.checkImagePath(imagePath)) {
            if(resizeCheck.checkIfParameterIsNumber(width) && resizeCheck.checkIfParameterIsNumber(height)) {
                if(resizeCheck.checkIfParameterIsAllowableSize(width, 1, 1920)
                        && resizeCheck.checkIfParameterIsAllowableSize(height, 1, 1920)){
                    getResizedImage(imagePath, Integer.parseInt(width), Integer.parseInt(height));
                }
            }
        }
    }

    private void getResizedImage(String imagePath, int width, int height){
        ImageParams imageParameter = new ImageParams();
        imageParameter.setImagePath(imagePath);
        imageParameter.setImageName(imagePath);
        imageParameter.setImagePostfixName(width + "_" + height);
        imageParameter.setImageFormat(imagePath);
        try {
            Thumbnails.of(new File(imagePath)).size(width, height).toFile(imageParameter.getNewFilePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

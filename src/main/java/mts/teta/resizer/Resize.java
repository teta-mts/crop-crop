package mts.teta.resizer;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;

public class Resize {
    public Resize(String imagePath, String width, String height) throws IOException {
        if(checkImagePath(imagePath)){
            if(checkIfParameterIsNumber(width) && checkIfParameterIsNumber(height)){
                if(checkIfParameterIsAllowableSize(width) && checkIfParameterIsAllowableSize(height)){
                    resize(imagePath, Integer.parseInt(width), Integer.parseInt(height));
                }else System.out.println("The allowable size is more than 1 and less than 1920!");
            }else System.out.println("The width and the height must be positive integer!");
        }else System.out.println("The image doesn't exist! Try again.");
    }

    private void resize(String imagePath, int width, int height) throws IOException {
        String outputPath = outputImagePath(imagePath);
        String imageName = inputImageName(imagePath);
        String imageFormat = imageFormat(imagePath);
        Thumbnails.of(new File(imagePath))
                .size(width, height)
                .toFile(new File(outputPath + imageName + "_" + width + "_" + height + imageFormat));
    }

    private boolean checkImagePath(String imagePath){
        return new File(imagePath).exists();
    }

    //A parameter must be positive integer
    private boolean checkIfParameterIsNumber(String parameter) {
        for (char element : parameter.toCharArray()) {
            if (!Character.isDigit(element)) return false;
        }
        return true;
    }

    //The allowable size is more than 1 and less than 1920
    private boolean checkIfParameterIsAllowableSize(String parameter) {
        return Integer.parseInt(parameter) <= 1920 && Integer.parseInt(parameter) >= 1;
    }

    //Get the output image path as the same for the input image but without its name
    private String outputImagePath(String inputImagePath) {
        return inputImagePath.substring(0, inputImagePath.lastIndexOf("/"));
    }

    //Get the input image name
    private String inputImageName(String inputImagePath) {
        return inputImagePath.substring(inputImagePath.lastIndexOf("/"), inputImagePath.lastIndexOf("."));
    }

    private String imageFormat(String inputImagePath){
        return inputImagePath.substring(inputImagePath.lastIndexOf("."));
    }
}

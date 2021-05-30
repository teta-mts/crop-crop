package mts.teta.resizer;

import java.io.File;

public class Checks {
    //Check if the path of input image exists
    public boolean checkImagePath(String imagePath) {
        if (!new File(imagePath).exists()) {
            System.out.println("The image doesn't exist! Try again");
            return false;
        } else return true;
    }

    //A parameter for resizing must be positive integer
    public boolean checkIfParameterIsNumber(String parameter) {
        for (char element : parameter.toCharArray()) {
            if (!Character.isDigit(element)) {
                System.out.println("The input value must be positive integer");
                return false;
            }
        }
        return true;
    }

    //The allowable size for resizing is more than 1 and less than 1920 for resizing
    //A value for compression must be between 0 and 100
    public boolean checkIfParameterIsAllowableSize(String parameter, int from, int to) {
        int parameterInt = Integer.parseInt(parameter);
        if (parameterInt > to || parameterInt < from) {
            System.out.println("A parameter must be between " + from + " and " + to);
            return false;
        } else return true;
    }

    //If an image with the same name already exists than its copy with a serial number is created
    /*public File makeCopyImage(File image, String imagePath, String imageName, String imageFormat) {
        if (image.exists()) {
            ImageParams imageParameter = new ImageParams();

            imageParameter.setNewImagePath(imagePath);
            imageParameter.setNewImageName(imageName);
            imageParameter.setImagePostfixName("(1)");
            imageParameter.setNewImageFormat(imageFormat);

            File newImage = imageParameter.getNewFilePath();
            if (newImage.exists()) {
                StringBuilder serialNumber = new StringBuilder("");
                String newImageName = imageName + "(1)";

                for (char letterInter : newImageName.substring(
                        newImageName.lastIndexOf('('), newImageName.lastIndexOf(')'))
                        .toCharArray()) {
                    serialNumber.append(letterInter);
                }

                imageParameter.setNewImagePath(imagePath);
                imageParameter.setNewImageName(imageName);
                imageParameter.setImagePostfixName("(" + (Integer.parseInt(serialNumber.toString()) + 1) + ")");
                imageParameter.setNewImageFormat(imageFormat);

                return imageParameter.getNewFilePath();
            } else return newImage;
        } else return image;
    }*/
}

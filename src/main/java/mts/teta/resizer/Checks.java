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

    public boolean checkInputImageFormat(String inputImagePath) {
        String format = inputImagePath.substring(inputImagePath.lastIndexOf(".") + 1);
        if (format.equalsIgnoreCase("jpg") || format.equalsIgnoreCase("jpeg")
                || format.equalsIgnoreCase("png")) {
            return true;
        } else {
            System.out.println("Invalid format!");
            return false;
        }
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
    //A radius for blurring between 0 and 63
    public boolean checkIfParameterIsAllowableSize(String parameter, int from, int to) {
        int parameterInt = Integer.parseInt(parameter);
        if (parameterInt > to || parameterInt < from) {
            System.out.println("A parameter must be between " + from + " and " + to);
            return false;
        } else return true;
    }

    //The output format must be PNG/JPEG (JPG)
    public boolean checkOutputFormat(String outputFormat) {
        return outputFormat.equalsIgnoreCase("png") || outputFormat.equalsIgnoreCase("jpg")
                || outputFormat.equalsIgnoreCase("jpeg");
    }


}

package mts.teta.resizer;

public class ImageParams {
    private String imagePath;
    private String imageName;
    private String imageFormat;
    private String imagePostfixName;

    //Set the output image path as the same for the input image but without its name
    private void setImagePath(String inputImagePath) {
        this.imagePath = inputImagePath.substring(0, inputImagePath.lastIndexOf("/") + 1);
    }

    //Set the output image name as the same name of an input image
    private void setImageName(String inputImagePath) {
        this.imageName = inputImagePath.substring(inputImagePath.lastIndexOf("/") + 1, inputImagePath.lastIndexOf("."));
    }

    //Set the format of an output image from the input one
    private void setImageFormat(String inputImagePath) {
        this.imageFormat = inputImagePath.substring(inputImagePath.lastIndexOf(".") + 1);
    }

    //Set the postfix name for an output image from params indicating in a command
    private void setImagePostfixName(String imagePostfixName) {
        this.imagePostfixName = imagePostfixName;
    }

    //Set all params (path, name, postfix, name, format) to a new image
    public void setAllParamsForNewImage(String inputImagePath, String parameter) {
        setImagePath(inputImagePath);
        setImageName(inputImagePath);
        setImagePostfixName(parameter);
        setImageFormat(inputImagePath);
    }

    //Set all params (path, name, postfix, name, format) to an image with the new format
    public void setAllParamsForNewFormatImage(String inputImagePath, String newFormat) {
        setImagePath(inputImagePath);
        setImageName(inputImagePath);
        setImagePostfixName("");
        setImageFormat(newFormat);
    }

    //Get the file path of an output image
    public String getNewFilePath() {
        return imagePath + imageName + imagePostfixName + "." + imageFormat;
    }
}

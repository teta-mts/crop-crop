package mts.teta.resizer;

import java.io.File;

public class ImageParams {
    private String imagePath;
    private String imageName;
    private String imageFormat;
    private String imagePostfixName;

    //Get the output image path as the same for the input image but without its name
    public String getImagePath() {
        return imagePath;
    }

    //Get the input image name
    public String getImageName() {
        return imageName;
    }

    public String getImagePostfixName() {
        return imagePostfixName;
    }

    //Get the format of an image
    public String getImageFormat(){
        return imageFormat;
    }

    public void setImagePath(String inputImagePath) {
        this.imagePath = inputImagePath.substring(0, inputImagePath.lastIndexOf("/")+1);
    }

    public void setImageName(String inputImagePath) {
        this.imageName = inputImagePath.substring(inputImagePath.lastIndexOf("/")+1, inputImagePath.lastIndexOf("."));
    }

    public void setImageFormat(String inputImagePath){
        this.imageFormat = inputImagePath.substring(inputImagePath.lastIndexOf(".")+1);
    }

    public void setImagePostfixName(String imagePostfixName) {
        this.imagePostfixName = imagePostfixName;
    }

    public void setNewImageName(String newImageName) {
        this.imageName = newImageName;
    }

    public void setNewImageFormat(String newImageFormat){
        this.imageFormat = newImageFormat;
    }

    public void setNewImagePath(String newImagePath){
        this.imagePath = newImagePath;
    }

    public File getNewFilePath(){
        return new File(imagePath + imageName + imagePostfixName + "." + imageFormat);
    }
}

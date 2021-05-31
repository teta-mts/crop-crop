package mts.teta.resizer;

import picocli.CommandLine;
import java.io.File;

public class ConsoleAttributes {
//    private final String ERROR_ATTRIBUTES = "Please check params!";
//    private final String ERROR_FILE = "Can't read input file!";

//    private boolean moreZero(int x){
//        return x>0 ? true: false;
//    }
//    private boolean moreOrEqualsZero(int x){
//        return x>=0 ? true: false;
//    }

    private File inputFile;
    @CommandLine.Parameters(index = "0",arity = "1")
    public void setInputFile(File file) {
        this.inputFile = file;
    };

    private File outputFile;
    @CommandLine.Parameters(index = "1",arity = "1")
    public void setOutputFile(File file) {
        this.outputFile = file;
    }

    private Integer resizeWidth,resizeHeight;
    private boolean selectedResize;
    @CommandLine.Option(names = "--resize",arity = "2", description = "resize width heigth")
    private void resizeMass(Integer []resize) {
        //if (!moreZero(resize[0]) | !moreZero(resize[1])) throw new BadAttributesException(ERROR_ATTRIBUTES);
        setResizeWidth(resize[0]);
        setResizeHeight(resize[1]);
        //selectedResize = true;
    };


    private Integer quality;
    private boolean selectedQuality;
    @CommandLine.Option(names = {"--quality"}, description = "quality value")
    public void setQuality(Integer quality) {
        //if (quality < 1  | quality > 100) throw new BadAttributesException(ERROR_ATTRIBUTES);
        this.quality = quality;
        selectedQuality = true;
    };

    private Integer cropWidth,cropHeight,cropX,cropY;
    private boolean selectedCrop;
    @CommandLine.Option(names = {"--crop"}, arity = "4",description = "crop width height x y ")
    private void cropMass(Integer []crop) {
        setCropWidth(crop[0]);
        setCropHeight(crop[1]);
        setCropX(crop[2]);
        setCropY(crop[3]);
        // selectedCrop = true;
    };

    private Integer radius;
    private boolean selectedRadius;
    @CommandLine.Option(names = {"--blur"}, description = "blur radius")
    public void setRadius(Integer radius){
        this.radius = radius;
        selectedRadius = true;
    };

    private String outputFormat;
    private boolean selectedOutputFormat;
    @CommandLine.Option(names = {"--format"}, description = "output format")
    public void setOutputFormat(String outputFormat){
        this.outputFormat = outputFormat;
        selectedOutputFormat = true;
    };


    public File getInputFile() {
        return inputFile;
    }

    public File getOutputFile() {
        return outputFile;
    }

    public Integer getResizeWidth() {
        return resizeWidth;
    }

    public void setResizeWidth(Integer resizeWidth) {
        selectedResize = true;
        this.resizeWidth = resizeWidth;
    }

    public Integer getResizeHeight() {
        return resizeHeight;
    }

    public void setResizeHeight(Integer resizeHeight) {
        selectedResize = true;
        this.resizeHeight = resizeHeight;
    }

    public Integer getQuality() {
        return quality;
    }



    public Integer getCropWidth() {
        return cropWidth;
    }

    public void setCropWidth(Integer cropWidth) {
        //if(!moreZero(cropWidth)) throw  new BadAttributesException(ERROR_ATTRIBUTES);
        selectedCrop = true;
        this.cropWidth = cropWidth;
    }

    public Integer getCropHeight()
    {
        return cropHeight;
    }

    public void setCropHeight(Integer cropHeight) {
        //if(!moreZero(cropHeight)) throw  new BadAttributesException(ERROR_ATTRIBUTES);
        selectedCrop = true;
        this.cropHeight = cropHeight;
    }

    public Integer getCropX() {
        return cropX;
    }

    public void setCropX(Integer cropX){
        //if(!moreOrEqualsZero(cropX)) throw  new BadAttributesException(ERROR_ATTRIBUTES);
        selectedCrop = true;
        this.cropX = cropX;
    }

    public Integer getCropY() {
        return cropY;
    }

    public void setCropY(Integer cropY) {
        // if(!moreOrEqualsZero(cropY)) throw  new BadAttributesException(ERROR_ATTRIBUTES);
        selectedCrop = true;
        this.cropY = cropY;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public boolean isSelectedOutputFormat() {
        return selectedOutputFormat;
    }

    public boolean isSelectedResize() {
        return selectedResize;
    }

    public boolean isSelectedQuality() {
        return selectedQuality;
    }

    public boolean isSelectedCrop() {
        return selectedCrop;
    }

    public Integer getRadius() {
        return radius;
    }

    public boolean isSelectedRadius() {
        return selectedRadius;
    }
}

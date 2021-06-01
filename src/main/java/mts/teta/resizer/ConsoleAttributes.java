package mts.teta.resizer;

import java.io.File;

public class ConsoleAttributes {
    public File inputFile;
    public File outputFile;

    private Integer width;
    private Integer height;
    private Integer quality;
    private String outputFormat = "jpg";

    private Integer cropWidth;
    private Integer cropHeight;
    private Integer cropX;
    private Integer cropY;

    private Integer blurRadius;

    public Integer getBlurRadius() {
        return blurRadius;
    }

    public void setBlurRadius(Integer blurRadius) {
        this.blurRadius = blurRadius;
    }

    public Integer getCropWidth() {
        return cropWidth;
    }

    public void setCropWidth(Integer cropWidth) {
        this.cropWidth = cropWidth;
    }

    public Integer getCropHeight() {
        return cropHeight;
    }

    public void setCropHeight(Integer cropHeight) {
        this.cropHeight = cropHeight;
    }

    public Integer getCropX() {
        return cropX;
    }

    public void setCropX(Integer cropX) {
        this.cropX = cropX;
    }

    public Integer getCropY() {
        return cropY;
    }

    public void setCropY(Integer cropY) {
        this.cropY = cropY;
    }

    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    public void setResizeWidth(Integer width) {
        this.width = width;
    }

    public void setResizeHeight(Integer height) {
        this.height = height;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getQuality() {
        return quality;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    @Override
    public String toString() {
        return "ConsoleAttributes{" +
                "inputFile=" + inputFile +
                ", outputFile=" + outputFile +
                ", width=" + width +
                ", height=" + height +
                ", quality=" + quality +
                '}';
    }
}

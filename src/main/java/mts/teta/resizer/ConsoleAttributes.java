package mts.teta.resizer;

import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;

public class ConsoleAttributes {

    private int resizeWidth;
    private int resizeHeight;
    private boolean resizeCheckBox;
    String outputFormat;
    private boolean outputFormatCheckbox;
    private int quality;
    private boolean qualityCheckBox;
    private File inputFile;
    private File outputFile;
    private int cropX;
    private int cropY;
    private int cropWidth;
    private int cropHeight;
    private boolean cropCheckBox;
    private int blurRadius;
    private boolean blurCheckBox;

    @Option(names = "--resize", paramLabel = "Width Height", description = "resize the image", arity = "2", hideParamSyntax = true)
    public void setWidthAndHeight(int[] widthAndHeight) {
        setResizeWidth(widthAndHeight[0]);
        setResizeHeight(widthAndHeight[1]);
    }

    @Option(names = "--quality value", paramLabel = "quality", description = "JPEG/PNG compression level value =1..100")
    public void setQuality(int quality) {
        qualityCheckBox = true;
        this.quality = quality;
    }

    @Option(names = "--crop", description = "cut out one rectangular area of the image", arity = "4", paramLabel = "Width Height X Y", hideParamSyntax = true)
    public void setWidthAndHeightAndXAndY(int[] widthAndHeightAndXAndY) {
        cropWidth = widthAndHeightAndXAndY[0];
        cropHeight = widthAndHeightAndXAndY[1];
        cropX = widthAndHeightAndXAndY[2];
        cropY = widthAndHeightAndXAndY[3];
        cropCheckBox = true;
    }

    @Option(names = "--blur", paramLabel = "radius", description = "reduce image noise detail levels")
    public void setRadius(int radius) {
        blurRadius = radius;
        blurCheckBox = true;
    }

    @Option(names = "--format", paramLabel = "outputFormat", description = "Convert image to JPEG/PNG")
    public void setOutputFormat(String outputFormat){
        outputFormatCheckbox = true;
        this.outputFormat = outputFormat;
    }

    @Parameters(index = "0", description = "input file", hidden = true)
    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;

    }

    @Parameters(description = "output file", hidden = true)
    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    public void setResizeWidth(int resizeWidth) {
        resizeCheckBox = true;
        this.resizeWidth = resizeWidth;
    }

    public void setResizeHeight(int resizeHeight) {
        resizeCheckBox = true;
        this.resizeHeight = resizeHeight;
    }

    public File getInputFile() {
        return inputFile;
    }

    public int getResizeWidth() {
        return resizeWidth;
    }

    public int getResizeHeight() {
        return resizeHeight;
    }

    public int getQuality() {
        return quality;
    }

    public File getOutputFile() {
        return outputFile;
    }

    public int getBlurRadius() {
        return blurRadius;
    }

    public boolean isBlurCheckBox() {
        return blurCheckBox;
    }

    public int getCropX() {
        return cropX;
    }

    public int getCropY() {
        return cropY;
    }

    public int getCropWidth() {
        return cropWidth;
    }

    public int getCropHeight() {
        return cropHeight;
    }

    public boolean isCropCheckBox() {
        return cropCheckBox;
    }

    public boolean isResizeCheckBox() {
        return resizeCheckBox;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public boolean isOutputFormatCheckbox() {
        return outputFormatCheckbox;
    }

    public boolean isQualityCheckBox() {
        return qualityCheckBox;
    }
}

package mts.teta.resizer;


import mts.teta.resizer.imageprocessor.BadAttributesException;
import mts.teta.resizer.imageprocessor.ImageProcessor;
import picocli.CommandLine;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "convert", mixinStandardHelpOptions = true, version = "resizer 0.0.1", description = "...")
public class ResizerApp implements Callable<Integer> {

    String[] supportedFormats = {"JPEG", "PNG"};

    @CommandLine.Parameters(index = "0", description = "Input file")
    private String inputFilename;

    @CommandLine.Parameters(index = "1", description = "Output file")
    private String outputFilename;

    @CommandLine.Option(names = {"--resize"}, description = "resize the image")
    private Integer resizeWidth, resizeHeight;

    @CommandLine.Option(names = {"--quality"}, description = "JPEG/PNG compression level")
    private Integer qualityValue;

    @CommandLine.Option(names = {"--crop"}, description = "cut out one rectangular area of the image")
    private Integer cropWidth, cropHeight, x, y;

    @CommandLine.Option(names = {"--blur"}, description = "reduce image noise detail levels")
    private Integer blurRadius;

    @CommandLine.Option(names = {"--format"}, description = "the image format type")
    private String outputFormat = "JPEG";

    public static void main(String... args) {
        int exitCode = runConsole(args);
        System.exit(exitCode);
    }

    protected static int runConsole(String[] args) {
        return new CommandLine(new ResizerApp()).execute(args);
    }

    @Override
    public Integer call() throws Exception {
        if(!checkArgs()) {
            throw new BadAttributesException("Please check params!");
        }
        ImageProcessor imageProcessor = new ImageProcessor();
        imageProcessor.processImage(ImageIO.read(new File(inputFilename)), this);
        return 0;
    }

    private boolean checkArgs() {
        if(resizeWidth != null && resizeWidth < 0 || resizeHeight != null && resizeHeight < 0) {
            return false;
        }
        if(cropWidth != null && cropWidth < 0 || cropHeight != null && cropHeight < 0) {
            return false;
        }
        if(qualityValue != null && (qualityValue < 1 || qualityValue > 100)) {
            return false;
        }
        if(blurRadius != null && blurRadius < 0) {
            return false;
        }
        if(outputFormat != null && !Arrays.asList(supportedFormats).contains(outputFormat)) {
            return false;
        }
        return true;
    }

    public void setInputFile(File file) {
        inputFilename = file.getAbsolutePath();
    }

    public void setOutputFile(File file) {
        outputFilename = file.getAbsolutePath();
    }

    public void setQuality(int i) {
        qualityValue = i;
    }

    public void setResizeWidth(Integer reducedPreviewWidth) {
        resizeWidth = reducedPreviewWidth;
    }

    public void setResizeHeight(Integer reducedPreviewHeight) {
        resizeHeight = reducedPreviewHeight;
    }

    public String getOutputFilename() {
        return outputFilename;
    }

    public Integer getQuality() {
        return qualityValue;
    }

    public Integer getResizeWidth() {
        return resizeWidth;
    }

    public Integer getResizeHeight() {
        return resizeHeight;
    }

    public Integer getCropHeight() {
        return cropHeight;
    }

    public Integer getCropWidth() {
        return cropWidth;
    }

    public Integer getBlurRadius() {
        return blurRadius;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public String getOutputFormat() {
        return outputFormat;
    }
}

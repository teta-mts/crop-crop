package mts.teta.resizer;

import marvin.io.MarvinImageIO;
import mts.teta.resizer.imageprocessor.BadAttributesException;
import net.coobird.thumbnailator.Thumbnails;
import picocli.CommandLine;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.concurrent.Callable;
import marvin.image.MarvinImage;
import static marvinplugins.MarvinPluginCollection.*;


class TestConditions{

    @CommandLine.Command(name = "resizer", mixinStandardHelpOptions = true, version = "resizer 1.0.0", description = "Available formats: jpeg png", headerHeading  = "convert input-file [options ...] output-file\n" +
            "Options Settings:")

    @CommandLine.Option(names = {"-r","--resize width height"}, description = "resize the image")
    public Integer widthResizing;
    public Integer heightResizing;

    @CommandLine.Option(names = {"-q", "--quality value"},  description = "JPEG/PNG compression level")
    public Integer qualityValue;

    @CommandLine.Option(names = {"-c","--crop width height x y"}, description = "cut out one rectangular area of the image")
    public Integer widthCrop;
    public Integer heightCrop;
    public Integer X;
    public Integer Y;

    @CommandLine.Option(names = {"--blur {radius}"}, description = "reduce image noise detail levels")
    public Integer gaussianRadius;

    @CommandLine.Option(names = { "--format 'OutputFormat'"}, description = "the image format type")
    public String outputFormat;

    @CommandLine.Parameters() File inFile;

}

public class ResizerApp extends TestConditions implements Callable<Integer> {


    public static void main(String... args) {
        int exitCode = runConsole(args);
        System.exit(exitCode);
    }

    protected static int runConsole(String[] args) {
        return new CommandLine(new ResizerApp()).execute(args);
    }

    public File outFile;


    public void setInputFile(File inputFile){
        this.inFile = inputFile;
    }

    public void setOutputFile(File outputFile){
        this.outFile = outputFile;
    }

    public void setResizeHeight(Integer Resize_Height){
        this.heightResizing = Resize_Height;
    }

    public void setResizeWidth(Integer Resize_Width){
        this.widthResizing = Resize_Width;
    }

    public void setQuality(Integer quality){
        this.qualityValue = quality;
    }

    public void setgaussianRadius(Integer radius){
        this.gaussianRadius = radius;
    }

    public void setwidthCrop(Integer width){
        this.widthCrop = width;
    }

    public void setheightCrop(Integer height){
        this.heightCrop = height;
    }

    public void setX(Integer x){
        this.X = x;
    }

    public void setY(Integer y){
        this.Y = y;
    }

    @Override
    public Integer call() throws Exception {
        ImageProcessor imageProcessor = new ImageProcessor();
        imageProcessor.processImage(ImageIO.read(inFile),this);
        return 0;
    }
}

class ImageProcessor{

    public void processImage(BufferedImage image, ResizerApp resizerApp)
            throws BadAttributesException {

        MarvinImage inputImage = new MarvinImage(image);
        MarvinImage outputImage = new MarvinImage();

        if (resizerApp.X != null && resizerApp.Y != null && resizerApp.widthCrop != null &&
                resizerApp.heightCrop != null){
            if (resizerApp.X < 0 || resizerApp.Y < 0 || resizerApp.heightCrop < 0 ||
                    resizerApp.widthCrop < 0) throw new BadAttributesException("Please check params!");

            crop(inputImage, outputImage, resizerApp.X, resizerApp.Y,
                    resizerApp.widthCrop, resizerApp.heightCrop);
            String outputPath = new String(resizerApp.outFile.getAbsolutePath());
            MarvinImageIO.saveImage(outputImage,outputPath);
        }

        if (resizerApp.gaussianRadius != null) {
            if (resizerApp.gaussianRadius < 0) throw new BadAttributesException("Please check params!");
            gaussianBlur(inputImage.clone(), inputImage, resizerApp.gaussianRadius);
            String outputPath = new String(resizerApp.outFile.getAbsolutePath());
            System.out.println(inputImage.clone().getHeight());
            MarvinImageIO.saveImage(inputImage, outputPath);
        }

        if (resizerApp.widthResizing != null && resizerApp.heightResizing != null &&
                resizerApp.qualityValue != null){

            if (resizerApp.widthResizing < 0 || resizerApp.heightResizing < 0 || resizerApp.qualityValue < 0)
                throw new BadAttributesException("Please check params!");

            try {
                Integer QualityReduced = resizerApp.qualityValue/100;
                Thumbnails.of(image).size(resizerApp.widthResizing,resizerApp.heightResizing).outputQuality(QualityReduced).outputFormat(resizerApp.outputFormat).toFile(resizerApp.outFile);
            }
            catch (java.io.IOException e) {
            }

            System.out.println(resizerApp.heightResizing);
        }

    }
}


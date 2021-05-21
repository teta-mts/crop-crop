package mts.teta.resizer;

import net.coobird.thumbnailator.Thumbnails;
import picocli.CommandLine;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.concurrent.Callable;
import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;
import marvinplugins.MarvinPluginCollection;

class ConsoleAttributes{

    @CommandLine.Command(name = "resizer", mixinStandardHelpOptions = true, version = "resizer 0.0.1", description = "Available formats: jpeg png", headerHeading  = "convert input-file [options ...] output-file\n" +
            "Options Settings:")

    @CommandLine.Option(names = {"-r","--resize width height"}, required = false, description = "resize the image")
    public Integer ResizeWidth;
    public Integer ResizeHeight;

    @CommandLine.Option(names = {"q", "--quality value"},  required = false, description = "JPEG/PNG compression level")
    public Integer QualityVal;

    @CommandLine.Option(names = {"c","--crop width height x y"},  required = false, description = "cut out one rectangular area of the image")
    public Integer CropWidth;
    public Integer CropHeight;
    public Integer X;
    public Integer Y;


    @CommandLine.Option(names = {"--blur {radius}"},  required = false, description = "reduce image noise detail levels")
    public Integer BlurRadius;

    @CommandLine.Option(names = { "--format 'OutputFormat'"},  required = false,  description = "the image format type")
    public String OutFormat;

    @CommandLine.Parameters
    public File InputFile;

}

public class ResizerApp extends ConsoleAttributes implements Callable<Integer> {

    public static void main(String... args) {
        int exitCode = runConsole(args);
        System.exit(exitCode);
    }

    protected static int runConsole(String[] args) {
        return new CommandLine(new ResizerApp()).execute(args);
    }

    public File InputFile = super.InputFile;
    public File OutputFile;

    public Integer ResizeWidth = super.ResizeWidth;
    public Integer ResizeHeight = super.ResizeHeight;

    public Integer CropWidth = super.CropWidth;
    public Integer CropHeight = super.CropHeight;
    public Integer X = super.X;
    public Integer Y = super.Y;

    public Integer Quality;


    public void setInputFile(File Input_File){
        this.InputFile = Input_File;
    }

    public void setOutputFile(File Output_File){
        this.OutputFile = Output_File;
    }

    public void setCropStartPoint(Integer x, Integer y){
        this.X = x;
        this.Y = y;
    }

    public void setResizeHeight(Integer Resize_Height){
        this.ResizeHeight = Resize_Height;
    }

    public void setQuality(Integer quality){
        this.Quality = quality;
    }

    @Override
    public Integer call() throws Exception {
        ImageProcessor imageProcessor = new ImageProcessor();
        imageProcessor.processImage(ImageIO.read(InputFile),this);
        return 0;
    }
}

class ImageProcessor{

    public void processImage(BufferedImage ImageToProcess, ResizerApp resizerApp ){

        MarvinImage imageThumbnailated = new MarvinImage();

        Thumbnails.of(ImageToProcess).size(resizerApp.ResizeWidth, resizerApp.ResizeHeight);

        MarvinImage imageIn = MarvinImageIO.loadImage("src/test/resources/howto.jpg");
        MarvinImage imageCropped = new MarvinImage();

        MarvinPluginCollection.crop(imageIn, imageCropped, resizerApp.X, resizerApp.Y, resizerApp.CropWidth, resizerApp.CropHeight);

        MarvinImage imageBlured = new MarvinImage();
        MarvinPluginCollection.gaussianBlur(imageCropped, imageBlured, resizerApp.BlurRadius);

    }
}

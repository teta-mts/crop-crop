package mts.teta.resizer;

import mts.teta.resizer.imageprocessor.BadAttributesException;
import net.coobird.thumbnailator.Thumbnails;
import picocli.CommandLine;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.concurrent.Callable;
import marvin.image.MarvinImage;
import marvinplugins.MarvinPluginCollection;


class ConsoleAttributes{

    @CommandLine.Command(name = "resizer", mixinStandardHelpOptions = true, version = "resizer 0.0.1", description = "Available formats: jpeg png", headerHeading  = "convert input-file [options ...] output-file\n" +
            "Options Settings:")

    @CommandLine.Option(names = {"-r","--resize width height"}, description = "resize the image")
    public Integer ResizeWidth;
    public Integer ResizeHeight;

    @CommandLine.Option(names = {"-q", "--quality value"},  description = "JPEG/PNG compression level")
    public Integer QualityVal;

    @CommandLine.Option(names = {"-c","--crop width height x y"}, description = "cut out one rectangular area of the image")
    public Integer CropWidth;
    public Integer CropHeight;
    public Integer X;
    public Integer Y;

    @CommandLine.Option(names = {"--blur {radius}"}, description = "reduce image noise detail levels")
    public Integer BlurRadius;

    @CommandLine.Option(names = { "--format 'OutputFormat'"}, description = "the image format type")
    public String OutFormat;

    @CommandLine.Parameters() File InputFile;

}

public class ResizerApp extends ConsoleAttributes implements Callable<Integer> {


    public static void main(String... args) {
        int exitCode = runConsole(args);
        System.exit(exitCode);
    }

    protected static int runConsole(String[] args) {
        return new CommandLine(new ResizerApp()).execute(args);
    }

    public File OutputFile;


    public void setInputFile(File Input_File){
        this.InputFile = Input_File;
    }

    public void setOutputFile(File Output_File){
        this.OutputFile = Output_File;
    }

    public void setResizeHeight(Integer Resize_Height){
        this.ResizeHeight = Resize_Height;
    }

    public void setResizeWidth(Integer Resize_Width){
        this.ResizeWidth = Resize_Width;
    }

    public void setQuality(Integer quality){
        this.QualityVal = quality;
    }

    @Override
    public Integer call() throws Exception {
        ImageProcessor imageProcessor = new ImageProcessor();
        imageProcessor.processImage(ImageIO.read(InputFile),this);
        return 0;
    }
}

class ImageProcessor{

    public void processImage(BufferedImage ImageToProcess, ResizerApp resizerApp ) throws BadAttributesException {

        if (resizerApp.ResizeWidth!=null && resizerApp.ResizeHeight!=null){

            if (resizerApp.ResizeWidth<0 || resizerApp.ResizeHeight <0) throw new BadAttributesException("Please check params!");
            Thumbnails.of(ImageToProcess).size(resizerApp.ResizeWidth, resizerApp.ResizeHeight);
        }
        if (resizerApp.QualityVal!=null){

            if (resizerApp.QualityVal<0) throw new BadAttributesException("Please check params!");
            Integer QualityReduced = resizerApp.QualityVal/100;
            Thumbnails.of(ImageToProcess).outputQuality(QualityReduced);
        }

        Thumbnails.of(ImageToProcess).outputFormat(resizerApp.OutFormat);

        MarvinImage InputImage = new MarvinImage(ImageToProcess);
        MarvinImage OutputImage = new MarvinImage();

        if (resizerApp.X!=null && resizerApp.Y!=null && resizerApp.CropWidth!=null && resizerApp.CropHeight!=null ){

            if (resizerApp.X<0 || resizerApp.Y<0 || resizerApp.CropHeight<0 || resizerApp.CropWidth<0) throw new BadAttributesException("Please check params!");
            MarvinPluginCollection.crop(InputImage, OutputImage, resizerApp.X, resizerApp.Y, resizerApp.CropWidth, resizerApp.CropHeight);
        }

        if (resizerApp.BlurRadius!=null){

            if (resizerApp.BlurRadius<0) throw new BadAttributesException("Please check params!");
            MarvinPluginCollection.gaussianBlur(InputImage, OutputImage, resizerApp.BlurRadius);
        }

        try {
            BufferedImage OutputImageBuff =  OutputImage.getBufferedImage();
            System.out.println(resizerApp.OutputFile.getAbsolutePath());
            ImageIO.write(OutputImageBuff, "jpg", resizerApp.OutputFile);
        }
        catch (java.io.IOException e) {
        }


    }
}

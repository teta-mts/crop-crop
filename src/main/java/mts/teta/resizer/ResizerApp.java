package mts.teta.resizer;

import marvin.io.MarvinImageIO;
import mts.teta.resizer.imageprocessor.BadAttributesException;
import net.coobird.thumbnailator.Thumbnails;
import picocli.CommandLine;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Stack;
import java.util.concurrent.Callable;
import marvin.image.MarvinImage;
import marvinplugins.MarvinPluginCollection;


@CommandLine.Command(name = "resizer",
        version = "resizer 0.0.1",
        headerHeading  = "Version: name version https://gitlab.com/link/\n" +
                "Available formats: jpeg png\n",
        customSynopsis = "convert input-file [options ...] output-file",
        separator = " ")
class ConsoleAttributes{
    public Integer resizeWidth;
    public Integer resizeHeight;
    Integer cropX;
    Integer cropY;
    Integer cropWidth;
    Integer cropHeight;

    @CommandLine.Option(names = {"--blur"}, paramLabel = "{radius}", description = "reduce image noise detail levels")
    public Integer BlurRadius;

    @CommandLine.Option(names = { "--format"}, paramLabel = "'OutputFormat'",description = "the image format type")
    public String OutFormat;

    @CommandLine.Option(names = { "--quality"}, paramLabel = "value" ,description = "JPEG/PNG compression level")
    public Integer quality;

    @CommandLine.Parameters(index = "0", paramLabel = "input-file") File inputFile;

    @CommandLine.Parameters(index = "1", paramLabel = "output-file") File outputFile;


    @CommandLine.Option(names = "--resize", paramLabel = "width height", description = "resize the image", parameterConsumer = ResAttParameterConsumer.class)
    static ResizeAttributes resizeAtt;

    static class ResAttParameterConsumer implements CommandLine.IParameterConsumer {
        @Override
        public void consumeParameters(Stack<String> stack, CommandLine.Model.ArgSpec argSpec, CommandLine.Model.CommandSpec commandSpec) {
            if (stack.size() < 2) {
                throw new CommandLine.ParameterException(commandSpec.commandLine(), "Enter two parameters");
            }
            Integer ResX = Integer.parseInt(stack.pop());
            Integer ResY = Integer.parseInt(stack.pop());
            argSpec.setValue(new ResizeAttributes(ResX, ResY));
        }
    }

    static class ResizeAttributes{
        Integer width;
        Integer height;

        public ResizeAttributes(int ResX, int ResY) {
            width = ResX;
            height= ResY;
        }
    }

    @CommandLine.Option(names = {"--crop"}, paramLabel = "width height x y", description = "cut out one rectangular area of the image", parameterConsumer = CropParamsConverter.class)
    static CropAtt cropAtt;

    static class CropParamsConverter implements CommandLine.IParameterConsumer {
        @Override
        public void consumeParameters(Stack<String> stack, CommandLine.Model.ArgSpec argSpec, CommandLine.Model.CommandSpec commandSpec) {
            if (stack.size() < 4) {
                throw new CommandLine.ParameterException(commandSpec.commandLine(),"Enter two parameters");
            }
            Integer cropX = Integer.parseInt(stack.pop());
            Integer cropY = Integer.parseInt(stack.pop());
            Integer cropWidth = Integer.parseInt(stack.pop());
            Integer cropHeight = Integer.parseInt(stack.pop());

            argSpec.setValue(new CropAtt(cropWidth, cropHeight, cropX, cropY));
        }
    }

    static class CropAtt {
        Integer x;
        Integer y;
        Integer width;
        Integer height;


        public CropAtt(Integer width, Integer height, Integer x, Integer y) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;

        }
    }

}

public class ResizerApp extends ConsoleAttributes implements Callable<Integer> {


    public static void main(String... args) {
        //String[] args1= {"resizer --quality 10 Good_Will_Hunting_1997.jpg Good_Will_Hunting_1997_1.jpg"};
        int exitCode = runConsole(args);
        System.exit(exitCode);
    }

    protected static int runConsole(String[] args) {
        return new CommandLine(new ResizerApp()).execute(args);
    }

    public void setInputFile(File Input_File){
        this.inputFile = Input_File;
    }

    public void setOutputFile(File Output_File){
        this.outputFile = Output_File;
    }

    public void setResizeHeight(Integer Resize_Height){
        this.resizeHeight = Resize_Height;
    }

    public void setResizeWidth(Integer Resize_Width){
        this.resizeWidth = Resize_Width;
    }

    public void setQuality(Integer quality){
        this.quality = quality;
    }

    public void setBlurRadius(Integer radius){
        this.BlurRadius = radius;
    }

    public void setCropWidth(Integer width){
        this.cropWidth = width;
    }

    public void setCropHeight(Integer height){
        this.cropHeight = height;
    }

    public void setX(Integer x){
        this.cropX = x;
    }

    public void setY(Integer y){
        this.cropY = y;
    }



    @Override
    public Integer call() throws Exception {

        if(resizeAtt!=null){
            if(resizeAtt.height!=null && resizeAtt.width!=null) {
                resizeWidth = resizeAtt.width;
                resizeHeight = resizeAtt.height;
            }
        }

        if(cropAtt!=null){
            if(cropAtt.height!=null && cropAtt.width!=null && cropAtt.x!=null && cropAtt.y!=null) {
                cropX =cropAtt.x;
                cropY = cropAtt.y;
                cropWidth = cropAtt.width;
                cropHeight = cropAtt.height;
            }
        }

        ImageProcessor imageProcessor = new ImageProcessor();
        imageProcessor.processImage(ImageIO.read(inputFile),this);
        return 0;
    }
}

class ImageProcessor{

    public void processImage(BufferedImage ImageToProcess, ResizerApp resizerApp) throws BadAttributesException {

        MarvinImage inputImg = new MarvinImage(ImageToProcess);
        MarvinImage outputImg = new MarvinImage();

        if (resizerApp.cropX!=null && resizerApp.cropY!=null && resizerApp.cropWidth!=null && resizerApp.cropHeight!=null){
            if (resizerApp.cropX<0 || resizerApp.cropY<0 || resizerApp.cropHeight<0 || resizerApp.cropWidth<0) throw new BadAttributesException("Please check params!");

            MarvinPluginCollection.crop(inputImg, outputImg, resizerApp.cropX, resizerApp.cropY, resizerApp.cropWidth, resizerApp.cropHeight);
            String outputPath = resizerApp.outputFile.getAbsolutePath();
            MarvinImageIO.saveImage(outputImg,outputPath);

        } else {
            if(resizerApp.resizeWidth==null && resizerApp.resizeHeight==null && resizerApp.OutFormat!=null){
                try {
                    Thumbnails.of(ImageToProcess).size(resizerApp.resizeWidth, resizerApp.resizeWidth).outputFormat(resizerApp.OutFormat).toFile(resizerApp.outputFile);
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }

            }
        }

        if (resizerApp.BlurRadius!=null){
            if (resizerApp.BlurRadius<0) throw new BadAttributesException("Please check params!");
            MarvinPluginCollection.gaussianBlur(inputImg.clone(),inputImg, resizerApp.BlurRadius);
            String outputPath = resizerApp.outputFile.getAbsolutePath();
            MarvinImageIO.saveImage(inputImg, outputPath);
        }

        if (resizerApp.resizeWidth!=null && resizerApp.resizeHeight!=null && resizerApp.quality!=null){

            if (resizerApp.resizeWidth<0 || resizerApp.resizeHeight <0 || resizerApp.quality<0 || resizerApp.quality>100) throw new BadAttributesException("Please check params!");

            try {
                Thumbnails.of(ImageToProcess).size(resizerApp.resizeWidth, resizerApp.resizeHeight).keepAspectRatio(false).outputQuality(resizerApp.quality/100).outputFormat(resizerApp.OutFormat).toFile(resizerApp.outputFile);
                //Thumbnails.of(ImageToProcess).size(resizerApp.ResizeWidth, resizerApp.ResizeHeight).outputQuality(QualityReduced).outputFormat(resizerApp.OutFormat).toFile(resizerApp.outputFile);
            }
            catch (java.io.IOException e) {
            }


        } else {
            if(resizerApp.resizeWidth==null && resizerApp.resizeHeight==null && resizerApp.quality!=null){
                if (resizerApp.quality<0) throw new BadAttributesException("Please check params!");
                try {
                    Integer QualityReduced = resizerApp.quality/100;
                    Thumbnails.of(ImageToProcess).size(ImageToProcess.getWidth(), ImageToProcess.getHeight()).outputQuality(QualityReduced).outputFormat(resizerApp.OutFormat).toFile(resizerApp.outputFile);
                }
                catch (java.io.IOException e) {
                }
            }
        }



    }
}
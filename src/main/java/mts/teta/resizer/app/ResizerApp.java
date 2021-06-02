package mts.teta.resizer.app;


import mts.teta.resizer.imageprocessor.BadAttributesException;
import picocli.CommandLine;

import javax.imageio.IIOException;
import java.io.File;
import java.util.concurrent.Callable;

@CommandLine.Command(name="resizer",
        mixinStandardHelpOptions = true,
        version="resizer 0.0.1")

public class ResizerApp implements Callable<Integer> {
    @CommandLine.Parameters
    @CommandLine.Option(names="--resize", description = "resize your image: resize the image")
    private int resizeWidth;
    private int resizeHeight;
    @CommandLine.Option(names="--quality", description = "JPEG/MIFF/PNG compression level")
    private int quality;
    @CommandLine.Option(names="--crop", description = "Cut out one or more rectangular regions of the image")
    private int cropWidth;
    private int cropHeight;
    @CommandLine.Option(names="--blur", description = "Reduce image noise and reduce detail levels")
    private int radius;
    @CommandLine.Option(names="--format", description = "output format")
    private String typeOfFormat = "";
    @CommandLine.Option(names="-i", description = "input file")
    private File inputFile;
    @CommandLine.Option(names="-o", description = "output file")
    private File outFile;


    public static void main(String[] args) {
        int exitCode = runConsole(args);
        System.exit(exitCode);
    }

    protected static int runConsole(String[] args){
        return new CommandLine(new ResizerApp()).execute(args);

    }

    @Override
    public Integer call() throws Exception {
        try {
            System.out.println("*******");
            System.out.println(!inputFile.exists());
            if (!inputFile.exists()) throw new IIOException("Can't read input file!");
            new ImageProcessor().processImage(this);
        } catch (IIOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int getResizeWidth() {
        return resizeWidth;
    }

    public void setResizeWidth(int resizeWidth) {
        this.resizeWidth = resizeWidth;
    }

    public void setResizeHeight(int resizeHeight) {
        this.resizeHeight = resizeHeight;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getResizeHeight() {
        return resizeHeight;
    }

    public int getQuality() {
        return quality;
    }


    public int getCropWidth() {
        return cropWidth;
    }

    public int getCropHeight() {
        return cropHeight;
    }

    public int getRadius() {
        return radius;
    }

    public String getTypeOfFormat() {
        return typeOfFormat;
    }


    public File getInputFile() {
        return inputFile;
    }

    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    public File getOutFile() {
        return outFile;
    }

    public void setOutputFile(File outFile) {
        this.outFile = outFile;
    }
}



package mts.teta.resizer;

import picocli.CommandLine;
import picocli.CommandLine.Command;

import javax.imageio.ImageIO;
import java.util.concurrent.Callable;

@Command(name = "convert", mixinStandardHelpOptions = true, version = "resizer 0.0.1",separator=" ",customSynopsis="Usage: convert input-file [options...] output-file",header = "Available formats: jpeg png"
,optionListHeading = "Options:%n")
public class ResizerApp extends ConsoleAttributes implements Callable<Integer> {
    public static void main(String... args) {
        int exitCode = runConsole(args);
        System.exit(exitCode);
    }


    protected static int runConsole(String[] args) {
        return new CommandLine(new ResizerApp()).execute(args);
    }

    @Override
    public Integer call() throws Exception {
        ImageProcessor imageProcessor = new ImageProcessor();
        imageProcessor.processImage(ImageIO.read(getInputFile()), this);
        return 0;
    }
}

package mts.teta.resizer;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import java.security.MessageDigest;
import java.io.File;
import java.util.concurrent.Callable;


class ConsoleAttributes{
    @CommandLine.Command(name = "resizer", mixinStandardHelpOptions = true, version = "resizer 0.0.1", description = "How your day, bro?;)")

    @Parameters(index = "0", description = "The file whose checksum to calculate.")
    private File file;

    @Option(names = {"-u", "--resize width height",}, required = true, description = "rezize the image")
    private Integer resize_params;

    @Option(names = { "--quality value"}, required = true, description = "JPEG/PNG compression level")
    private Integer quality_val;

    @Option(names = {"--crop width height x y"}, required = true, description = "cut out one rectangular area of the image")
    private Integer crop_par;

    @Option(names = {"--blur {radius}"}, required = true, description = "reduce image noise detail levels")
    private Integer blur;

    @Option(names = { "--format 'OutputFormat'"}, required = true, description = "the image format type")
    private String out_format;

}

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
        //ImageProcessor imageProcessor = new ImageProcessor();
        //imageProcessor.processImage(ImageIO.read(inputFile), this);
        return 0;
    }
}

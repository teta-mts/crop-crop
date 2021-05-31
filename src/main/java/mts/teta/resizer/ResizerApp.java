package mts.teta.resizer;

import picocli.CommandLine;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "resizer", mixinStandardHelpOptions = true, version = "resizer 0.0.1", description = "...")
public class ResizerApp extends ConsoleAttributes  implements Callable<Integer> {
    private final String ERROR_FILE = "Can't read input file!";

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
        if(!getInputFile().exists()) throw new IIOException(ERROR_FILE);
        imageProcessor.processImage(ImageIO.read(getInputFile()), this);
        return 0;
    }

}

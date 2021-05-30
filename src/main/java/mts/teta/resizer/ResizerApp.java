package mts.teta.resizer;

import picocli.CommandLine;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Callable;


@CommandLine.Command(name = "resizer", mixinStandardHelpOptions = true, version = "resizer 0.0.1", description = "...")
public class ResizerApp implements Callable<Integer> {
    public static void main(String... args) throws IOException {
        //int exitCode = runConsole(args);
        //System.exit(exitCode);
        //Scanner scanner = new Scanner(System.in);
        //String name = scanner.nextLine();
        //String width = scanner.nextLine();
        //String height = scanner.nextLine();
        //Resize resize = new Resize(name, width, height);
        //System.out.println(name);
        //resize.resize();
        new Blurring().blurring();
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

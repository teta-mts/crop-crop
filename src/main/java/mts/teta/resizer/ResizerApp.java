package mts.teta.resizer;

import picocli.CommandLine;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "resizer", mixinStandardHelpOptions = true, version = "resizer 0.0.1", description = "...")
public class ResizerApp extends ConsoleAttributes implements Callable<Integer> {
    @CommandLine.Parameters(index = "0", paramLabel = "input-file")
    static private File inputFileArg;

    @CommandLine.Parameters(index = "1", paramLabel = "output-file")
    static private File outputFileArg;

    @CommandLine.Option(names = {"--quality"}, paramLabel = "value", description = "JPEG/PNG compression level")
    static private Integer qualityArg;

    @CommandLine.Option(names = {"--format"}, paramLabel = "\"outputFormat\"", description = "the image format type")
    static private String formatArg;

    @CommandLine.Option(names = {"--blur"}, paramLabel = "{radius}", description = "reduce image noise detail levels")
    static private Integer radiusArg;

    static class ResizeParams {
        Integer width;
        Integer height;

        public ResizeParams(Integer width, Integer height) {
            this.width = width;
            this.height = height;
        }
    }

    @CommandLine.Option(names = "--resize", paramLabel = "width height", description = "resize the image", parameterConsumer = ResizeParamsConverter.class)
    static private ResizeParams resizeParams;

    static class ResizeParamsConverter implements CommandLine.IParameterConsumer {
        @Override
        public void consumeParameters(Stack<String> stack, CommandLine.Model.ArgSpec argSpec, CommandLine.Model.CommandSpec commandSpec) {
            if (stack.size() < 2) {
                throw new CommandLine.ParameterException(commandSpec.commandLine(),
                        "Missing width and height for ResizeParams. Please specify 2 values.");
            }
            int x = Integer.parseInt(stack.pop());
            int y = Integer.parseInt(stack.pop());
            argSpec.setValue(new ResizeParams(x, y));
        }
    }

    static class CropParams {
        Integer width;
        Integer height;
        Integer x;
        Integer y;

        public CropParams(Integer width, Integer height, Integer x, Integer y) {
            this.width = width;
            this.height = height;
            this.x = x;
            this.y = y;
        }
    }

    @CommandLine.Option(names = "--crop", paramLabel = "width height x y", description = "cut out one rectangular area of the image", parameterConsumer = CropParamsConverter.class)
    static private CropParams cropParams;

    static class CropParamsConverter implements CommandLine.IParameterConsumer {
        @Override
        public void consumeParameters(Stack<String> stack, CommandLine.Model.ArgSpec argSpec, CommandLine.Model.CommandSpec commandSpec) {
            if (stack.size() < 4) {
                throw new CommandLine.ParameterException(commandSpec.commandLine(),
                        "Missing width, height, x and y for CropParams. Please specify 4 values.");
            }
            int w = Integer.parseInt(stack.pop());
            int h = Integer.parseInt(stack.pop());
            int x = Integer.parseInt(stack.pop());
            int y = Integer.parseInt(stack.pop());
            argSpec.setValue(new CropParams(w, h, x, y));
        }
    }

    public static void main(String... args) {
        int exitCode = runConsole(args);
        System.exit(exitCode);
    }

    protected static int runConsole(String[] args) {
        return new CommandLine(new ResizerApp()).execute(args);
    }

    @Override
    public Integer call() throws Exception {
        if (resizeParams != null) {
            if (resizeParams.width != null) setResizeWidth(resizeParams.width);
            if (resizeParams.height != null) setResizeHeight(resizeParams.height);
        }
        if (cropParams != null) {
            if ((cropParams.width != null) && (cropParams.height != null) && (cropParams.x != null) && (cropParams.y != null)) {
                setCropWidth(cropParams.width);
                setCropHeight(cropParams.height);
                setCropX(cropParams.x);
                setCropY(cropParams.y);
            }
        }
        if (qualityArg != null) setQuality(qualityArg);
        if (inputFileArg != null) setInputFile(inputFileArg);
        if (outputFileArg != null)  setOutputFile(outputFileArg);
        if (formatArg != null) setOutputFormat(formatArg);
        if (radiusArg != null) setBlurRadius(radiusArg);

        ImageProcessor imageProcessor = new ImageProcessor();
        imageProcessor.processImage(ImageIO.read(inputFile), this);
        return 0;
    }
}

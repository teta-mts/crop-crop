package mts.teta.resizer;

import picocli.CommandLine;

import java.util.Scanner;

@CommandLine.Command(name = "convert", mixinStandardHelpOptions = true, version = "version 0.0.3",
        description = "Available formats: jpeg png\n" +
                "Usage: convert input-file [options ...] output-file\n" +
                "Options Settings:")
class ResizerApp implements Runnable {
    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    @CommandLine.Parameters(description = "path of an input-file", interactive = true, hidden = true)
    String file;
    @CommandLine.Option(names = "--resize", description = "resize the image", interactive = true)
    String width;
    String height;

    @Override
    public void run() {
        String command = new Scanner(System.in).nextLine();
        String[] args = command.split(" ");
        if(args.length == 5) setParams(args);
        else throw new CommandLine.ParameterException(spec.commandLine(), "Error: enter an option setting");
        if(validate()) new Resizing().resize(file, width, height);
    }

    private boolean validate() {
        if (missing(file) || missing(width) || missing(height)) {
            throw new CommandLine.ParameterException(spec.commandLine(),
                    "Missing option: at least one of the " +
                            "'<file>', '<width>', or '<height>' options must be specified.");
        }else return true;
    }

    private boolean missing(String parameter) {
        return parameter == null || parameter.isEmpty();
    }

    private void setParams(String[] args){
        this.file = args[1];
        this.width = args[3];
        this.height = args[4];
    }

    public static void main(String... args) {
        CommandLine cmd = new CommandLine(new ResizerApp());
        cmd.parseArgs(args);
        int exitCode = cmd.execute(args);
        System.exit(exitCode);
    }
}

class BlurApp implements Runnable {
    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    @CommandLine.Parameters(description = "path of an input-file", interactive = true, hidden = true)
    String file;
    @CommandLine.Option(names = "--blur", description = "reduce image noise detail levels", interactive = true)
    String radius;

    @Override
    public void run() {
        String command = new Scanner(System.in).nextLine();
        String[] args = command.split(" ");
        if(args.length == 3) setParams(args);
        else throw new CommandLine.ParameterException(spec.commandLine(), "Error: enter an option setting");
        if(validate()) new Blurring().blurring(file, radius);
    }

    private boolean validate() {
        if (missing(file) || missing(radius)) {
            throw new CommandLine.ParameterException(spec.commandLine(),
                    "Missing option: at least one of the " +
                            "'<file>' or '<radius>' options must be specified.");
        }else return true;
    }

    private boolean missing(String parameter) {
        return parameter == null || parameter.isEmpty();
    }

    private void setParams(String[] args){
        this.file = args[1];
        this.radius = args[3];
    }

    public static void main(String... args) {
        CommandLine cmd = new CommandLine(new BlurApp());
        cmd.parseArgs(args);
        int exitCode = cmd.execute(args);
        System.exit(exitCode);
    }
}
    /* protected int runConsole(String[] args) {
        new ResizerApp().setParams(args);
        new Resize().resize(file, width, height);
        return new CommandLine(new ResizerApp()).execute(args);
    }*/



/*class Menu {
    @CommandLine.Option(names = "--resize width height", description = "resize the image")
    Void resize;

    @CommandLine.Command(name = "resize")

    @CommandLine.Option(names = "--quality value", description = "JPEG/PNG compression level")
    Void compress;

    @CommandLine.Option(names = "--crop width height x y", description = "@|fg(magenta)cut|@ out one rectangular area of the image")
    Void crop;

    @CommandLine.Option(names = "--blur {radius}", description = "reduce image noise detail levels")
    Void blur;

    @CommandLine.Option(names = "--format @|bold,fg(green)\"outputFormat\"|@", description = "the image @|fg(magenta)format type|@")
    Void outputFormat;

    //@CommandLine.Parameters(paramLabel = "FILE", description = "an image to change")
    //File file;
}

class PositionalParametersResize {
    @CommandLine.Parameters(index = "0")    File file;
    @CommandLine.Parameters(index = "1")    String width;
    @CommandLine.Parameters(index = "2")    String height;
}

class PositionalParametersCompress {
    @CommandLine.Parameters(index = "0")    File file;
    @CommandLine.Parameters(index = "1")    String qualityValue;
}

class PositionalParametersCrop {
    @CommandLine.Parameters(index = "0")    File file;
    @CommandLine.Parameters(index = "1")    String width;
    @CommandLine.Parameters(index = "2")    String height;
    @CommandLine.Parameters(index = "3")    String x;
    @CommandLine.Parameters(index = "4")    String y;
}

class PositionalParametersBlur {
    @CommandLine.Parameters(index = "0")    File file;
    @CommandLine.Parameters(index = "1")    String radius;
}

class PositionalParametersFormat {
    @CommandLine.Parameters(index = "0")    File file;
    @CommandLine.Parameters(index = "1")    String outputFormat;
}*/

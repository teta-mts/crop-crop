package mts.teta.resizer;

import picocli.CommandLine;

import java.util.Scanner;

@CommandLine.Command(name = "convert", mixinStandardHelpOptions = true, version = "version 0.0.3",
        description = "Available formats: jpeg png\n" +
                "Usage: convert input-file [options ...]\n" +
                "Options Settings:")
public class ResizerApp implements Runnable {
    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;
    @CommandLine.Parameters(description = "path of an input-file", interactive = true, hidden = true)
    String file;
    /*@CommandLine.Option(names = "--resize", description = "resize the image")
    boolean resize = true;
    @CommandLine.Option(names = "--quality", description = "JPEG/PNG compression level")
    boolean compression = true;
    @CommandLine.Option(names = "--crop", description = "@|fg(magenta)cut|@ out one rectangular area of the image")
    boolean crop = true;
    @CommandLine.Option(names = "--blur", description = "reduce image noise detail levels")
    boolean blur = true;
    @CommandLine.Option(names = "--format @|bold,fg(green)\"outputFormat\"|@", description = "the image @|fg(magenta)format type|@")
    boolean format = true;*/

    @Override
    public void run() {
        String commandString = new Scanner(System.in).nextLine();
        if (commandString != null) {
            if (!commandString.trim().isEmpty()) {
                menu(commandString);
            } else System.out.println("Write a command");
        } else System.out.println("Write a command");
    }

    private void menu(String commandString) {
        String[] arr = commandString.split(" ");
        if (arr[0].equalsIgnoreCase("convert")) {
            if (arr.length > 2) {
                this.file = arr[1];
                if (validate(this.file)) {
                    switch (arr[2]) {
                        case "--resize":
                            if (ifCommandLengthRight(arr, 5)) {
                                String width = arr[3];
                                String height = arr[4];
                                if (validate(width) && validate(height)) {
                                    new Resizing().resize(this.file, width, height);
                                }
                            }
                            break;
                        case "--compression":
                            if (ifCommandLengthRight(arr, 4)) {
                                String value = arr[3];
                                if (validate(value)) {
                                    new Compression().compress(this.file, value);
                                }
                            }
                            break;
                        case "crop":
                            if (ifCommandLengthRight(arr, 7)) {
                                String cropWidth = arr[5];
                                String cropHeight = arr[6];
                                String x = arr[3];
                                String y = arr[4];
                                if (validate(cropWidth) && validate(cropHeight) && validate(x) && validate(y)) {
                                    new Crop().cropping(this.file, x, y, cropWidth, cropHeight);
                                }
                            }
                            break;
                        case "--blur":
                            if (ifCommandLengthRight(arr, 4)) {
                                String radius = arr[3];
                                if (validate(radius)) {
                                    new Blurring().blurring(this.file, radius);
                                }
                            }
                            break;
                        case "--outputFormat":
                            if (ifCommandLengthRight(arr, 4)) {
                                String outputFormat = arr[3];
                                if (validate(outputFormat)) {
                                    new OutputFormat().outputFormat(this.file, outputFormat);
                                }
                            }
                            break;
                        default:
                            System.out.println("Invalid option");
                            break;
                    }
                }
            }
        } else System.out.println("Invalid command");
    }

    private boolean validate(String parameter) {
        if (parameter == null || parameter.isEmpty()) {
            throw new CommandLine.ParameterException(spec.commandLine(),
                    "Missing option: at least one of the options must be specified.");
        } else return true;
    }

    private boolean ifCommandLengthRight(String[] command, int length) {
        if (command.length != length) {
            System.out.println("Wrong command");
            return false;
        } else return true;
    }

    public static void main(String... args) {
        CommandLine cmd = new CommandLine(new ResizerApp());
        cmd.parseArgs(args);
        int exitCode = cmd.execute(args);
        System.exit(exitCode);
    }
}

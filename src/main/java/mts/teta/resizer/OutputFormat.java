package mts.teta.resizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OutputFormat {
    public void outputFormat(String inputImagePath, String outputFormat) {
        Checks formatChecks = new Checks();
        if (formatChecks.checkImagePath(inputImagePath)) {
            if (formatChecks.checkInputImageFormat(inputImagePath)) {
                if (formatChecks.checkOutputFormat(outputFormat)) {
                    getOutputFormat(inputImagePath, outputFormat);
                }
            }
        }
    }

    private void getOutputFormat(String inputImagePath, String outputFormat) {
        BufferedImage image;
        ImageParams imageParameter = new ImageParams();
        imageParameter.setAllParamsForNewFormatImage(inputImagePath, outputFormat);
        try {
            image = ImageIO.read(new File(inputImagePath));
            ImageIO.write(image, outputFormat, new File(imageParameter.getNewFilePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

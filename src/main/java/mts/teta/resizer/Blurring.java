package mts.teta.resizer;

import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;

import static marvinplugins.MarvinPluginCollection.gaussianBlur;

public class Blurring {
    public void blurring(){
        MarvinImage image = MarvinImageIO.loadImage("1.jpg");
        gaussianBlur(image.clone(), image, 1);
        MarvinImageIO.saveImage(image, "blur.jpg");
        }
}

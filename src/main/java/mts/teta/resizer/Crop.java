package mts.teta.resizer;

import marvin.*;
import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;

import static marvinplugins.MarvinPluginCollection.crop;

public class Crop {
    public void cropping(){
        MarvinImage image = MarvinImageIO.loadImage("1.jpg");
        crop(image.clone(), image, 100, 100, 200, 200);
        MarvinImageIO.saveImage(image, "crop.jpg");
    }
}

package mts.teta.resizer.app;


import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;
import marvinplugins.MarvinPluginCollection;
import mts.teta.resizer.imageprocessor.BadAttributesException;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ImageProcessor{
    private int quality;
    Command command;

    public void processImage(ResizerApp app) throws BadAttributesException, IOException {
        this.quality = app.getQuality();
        boolean flagThum = false;


        if (quality > 0){
            command = () -> {

                Thumbnails.of(app.getOutFile()).scale(1.).outputQuality(quality/100.).toFile(app.getOutFile());

            };
            flagThum = true;
        }
        if (quality < 0)  throw  new BadAttributesException("Please check params!");

        if(!app.getTypeOfFormat().equals("")){
            Thumbnails.of(app.getOutFile()).scale(1.).outputFormat(app.getTypeOfFormat()).toFile(app.getOutFile());
            flagThum = true;
        }
        if (flagThum){
            app.setInputFile(app.getOutFile());
        }
        MarvinImage imageIn = new MarvinImage(ImageIO.read(app.getInputFile()));
        MarvinImage imageOut = new MarvinImage(ImageIO.read(app.getOutFile()));

        if (app.getRadius() > 0){
            command = () -> MarvinPluginCollection.gaussianBlur(imageIn, imageOut, app.getRadius());
        }

        if (app.getResizeHeight() > 0 || app.getResizeWidth() > 0 || app.getCropHeight() > 0 || app.getCropWidth() > 0){

            int finalCropWidth = app.getCropWidth();
            int finalCropHeight = app.getCropHeight();
            command = () -> MarvinPluginCollection.crop(imageIn, imageOut, app.getResizeWidth(), app.getResizeHeight(), finalCropWidth, finalCropHeight);
        } else if(app.getResizeHeight() < 0 || app.getResizeWidth() < 0  || app.getCropHeight() <0 || app.getCropWidth() < 0) throw new BadAttributesException("Please check params!");

        MarvinImageIO.saveImage(imageOut, app.getOutFile().getAbsolutePath());
    }




}

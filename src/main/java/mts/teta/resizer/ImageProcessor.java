package mts.teta.resizer;

import marvin.image.MarvinImage;
import marvin.image.MarvinImageMask;
import marvin.io.MarvinImageIO;
import marvin.plugin.MarvinPlugin;
import marvin.util.MarvinImageUtils;
import marvinplugins.MarvinPluginCollection;
import mts.teta.resizer.imageprocessor.BadAttributesException;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import org.marvinproject.image.blur.gaussianBlur.GaussianBlur;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static marvinplugins.MarvinPluginCollection.crop;

public class ImageProcessor {
    private final String ERROR_ATTRIBUTES = "Please check params!";

    private boolean moreZero(int x){
        return x>0 ? true: false;
    }
    private boolean moreOrEqualsZero(int x){
        return x>=0 ? true: false;
    }
    private void throwBadAttributesException() throws BadAttributesException {
        throw new BadAttributesException(ERROR_ATTRIBUTES);
    }

    public void processImage(BufferedImage bufferedImage,ResizerApp resizerApp) throws IOException, BadAttributesException {

        if (resizerApp.isSelectedResize()){
            Integer resizeWidth = resizerApp.getResizeWidth();
            Integer resizeHeight = resizerApp.getResizeHeight();
            if (!moreZero(resizeWidth) | !moreZero(resizeHeight)) throwBadAttributesException();
            bufferedImage = Thumbnails.of(bufferedImage)
                    .forceSize(resizeWidth,resizeHeight)
                    .asBufferedImage();
        }

        if (resizerApp.isSelectedQuality()){
            Integer quality = resizerApp.getQuality();
            if (quality<1 | quality>100) throw new BadAttributesException(ERROR_ATTRIBUTES);
            bufferedImage = Thumbnails.of(bufferedImage)
                    .outputQuality(resizerApp.getQuality()/100.0)
                    .scale(1)
                    .asBufferedImage();
        }
//        if(resizerApp.isSelectedOutputFormat()){
//            String outputFormat = resizerApp.getOutputFormat();
//            if(!outputFormat.equals("JPEG") & !outputFormat.equals("PNG")) throwBadAttributesException();
//            bufferedImage = Thumbnails.of(bufferedImage)
//                    .outputFormat(outputFormat)
//                    .scale(1)
//                    .asBufferedImage();
//        }

        MarvinImage image = new MarvinImage(bufferedImage);
        if (resizerApp.isSelectedCrop()){
            Integer cropWidth = resizerApp.getCropWidth();
            Integer cropHeight = resizerApp.getCropHeight();
            Integer cropX = resizerApp.getCropX();
            Integer cropY = resizerApp.getCropY();
            if (!moreZero(cropWidth) | !moreZero(cropHeight)) throwBadAttributesException();
            if (!moreOrEqualsZero(cropX) | !moreOrEqualsZero(cropY)) throwBadAttributesException();
            crop(image.clone(),
                    image,
                    resizerApp.getCropX(),
                    resizerApp.getCropY(),
                    resizerApp.getCropWidth(),
                    resizerApp.getCropHeight());
        }
        if (resizerApp.isSelectedRadius()){
            Integer radius = resizerApp.getRadius();
            if (!moreOrEqualsZero(radius)) throwBadAttributesException();
            MarvinPluginCollection.gaussianBlur(image.clone(),image,radius);
        }
//        if(resizerApp.isSelectedOutputFormat()){
//            String outputFormat = resizerApp.getOutputFormat();
//            if(!outputFormat.equals("JPEG") & !outputFormat.equals("PNG")) throwBadAttributesException();
//            File file = resizerApp.getOutputFile();
//            Thumbnails.of(image.getBufferedImage())
//                    .outputFormat(outputFormat)
//                    .scale(1)
//                    .toFile(file);
//        }


        String name = resizerApp.getOutputFile().getAbsolutePath();
        if(resizerApp.isSelectedOutputFormat()){
            String outputFormat = resizerApp.getOutputFormat().toLowerCase();
            if(!outputFormat.equals("jpeg") & !outputFormat.equals("png")) throwBadAttributesException();
            int end = name.lastIndexOf('.');
            if (end == -1)  end = name.length();
            name = name.substring(0,end)+"." + outputFormat;
        }
        else {
            String outputFormat = resizerApp.getOutputFile().getName();
            outputFormat = outputFormat.substring(outputFormat.lastIndexOf('.')+1);
            if(!outputFormat.equals("jpeg") & !outputFormat.equals("png")&& !outputFormat.equals("jpg"))
                throwBadAttributesException();
        }

        MarvinImageIO.saveImage(image, name);
    }

}

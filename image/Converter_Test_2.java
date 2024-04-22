package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;


public class Converter_Test_2 implements TextGraphicsConverter {
    private int width;
    private int height;
    private double maxRatio;
    //  private int newWidth;
    //  private int newHeight;
    private TextColorSchema schema;

    public Converter_Test_2() {
        schema = new ColorShema_Test();
    }

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = getBufferedImage(url);
        return convertImg(img);
    }

    @Override
    public void setMaxWidth(int width) {

        this.width = width;
    }

    @Override
    public void setMaxHeight(int height) {

        this.height = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {

        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {

        this.schema = schema;
    }


    private BufferedImage getBufferedImage(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));
        double ratio;
        if (img.getWidth() / img.getHeight() > img.getHeight() / img.getWidth()) {
            ratio = (double) img.getWidth() / img.getHeight();
        } else {
            ratio = (double) img.getHeight() / img.getWidth();
        }
        if (maxRatio != 0 && ratio > maxRatio) {
            throw new BadImageSizeException(ratio, maxRatio);
        }
        return img;
    }

    private String convertImg(BufferedImage img) {
        int newWidth = img.getWidth();
        int newHeight = img.getHeight();
        WritableRaster bwRaster = getWritableRaster(img);
        char[][] textGraphics = new char[newHeight][newWidth];
        for (int i = 0; i < textGraphics.length; i++) {
            for (int j = 0; j < textGraphics[i].length; j++) {
                int color = bwRaster.getPixel(j, i, new int[3])[0];
                textGraphics[i][j] = schema.convert(color);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (char[] pixel : textGraphics) {
            for (char c : pixel) {
                sb.append(c);
                sb.append(c);
            }
            sb.append("\n");

        }
        return sb.toString();
    }


    private WritableRaster getWritableRaster(BufferedImage img) {
        int newWidth;
        int newHeight;
        if (img.getWidth() > width || img.getHeight() > height) {
            double tmpWidth;
            double tmpHeight;
            if (width != 0) {
                tmpWidth = (double) img.getWidth() / width;
            } else {
                tmpWidth = 1;
            }
            if (height != 0) {
                tmpHeight = (double) img.getHeight() / height;
            } else {
                tmpHeight = 1;
            }
            double divider = Math.max(tmpWidth, tmpHeight);
            newWidth = (int) (img.getWidth() / divider);
            newHeight = (int) (img.getHeight() / divider);
        } else {
            newWidth = img.getWidth();
            newHeight = img.getHeight();
        }
        Image scaleImage = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaleImage, 0, 0, null);

        return bwImg.getRaster();
    }
}


package ru.netology.graphics.image;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Converter_Test_3 implements TextGraphicsConverter {
    protected double ratio;
    private int width;
    private int height;
    private double maxRatio;
    private TextColorSchema schema;

    public Converter_Test_3() {
        schema = new ColorSchema();
    }

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {

        BufferedImage img = ImageIO.read(new URL(url));
        // соотношение сторон
        if (img.getWidth() > img.getHeight()) {
            ratio = img.getWidth() / img.getHeight();
        } else if (img.getHeight() > img.getWidth()) {
            ratio = img.getHeight() / img.getWidth();
        }
        if (ratio > maxRatio && maxRatio != 0) throw new BadImageSizeException(ratio, maxRatio);

        // максимальная ширина длинна картинки
        if (img.getWidth() > width || img.getHeight() > height) {
            if (img.getWidth() > img.getHeight()) {
                setMaxWidth(img.getWidth() / (img.getWidth() / width));
                setMaxHeight(img.getHeight() / (img.getWidth() / width));
            } else {
                setMaxWidth(img.getWidth() / (img.getHeight() / height));
                setMaxHeight(img.getHeight() / (img.getHeight() / height));
            }
        } else {
            width = img.getWidth();
            height = img.getHeight();
        }

        char[][] picture = new char[height][width];

        Image scaledImage = img.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        var bwRaster = bwImg.getRaster();

        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                int color = bwRaster.getPixel(w, h, new int[3])[0];
                char c = schema.convert(color);
                picture[h][w] = c;

            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < picture.length; i++) {
            for (int j = 0; j < picture[i].length; j++) {
                sb.append(picture[i][j]);
                sb.append(picture[i][j]);
            }
            sb.append("\n");

        }
        return sb.toString();
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
}
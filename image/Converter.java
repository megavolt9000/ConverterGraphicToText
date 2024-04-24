package ru.netology.graphics.image;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;


public class Converter implements TextGraphicsConverter {
    private int width;
    private int height;
    private double maxRatio;
    private TextColorSchema schema;

    public Converter() {
        schema = new ColorSchema();
    }

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        double ratio;
        int newWidth;
        int newHeight;
        // качаем картинку из интернета
        BufferedImage img = ImageIO.read(new URL(url));

        //проверка картинки на соотношение сторон

        if (img.getWidth() > width || img.getHeight() > height) {

            if (img.getWidth() / img.getHeight() > img.getHeight() / img.getWidth()) {
                ratio = (double) img.getWidth() / img.getHeight();
            } else {
                ratio = (double) img.getHeight() / img.getWidth();
            }
            if (maxRatio != 0 && ratio > maxRatio) {
                throw new BadImageSizeException(ratio, maxRatio);
            }
        }
        //блок  проверки картинки на максимальные ширину и высоту
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

        //создаем символьный массив под новые размеры картинки
        char[][] image = new char[newHeight][newWidth];
        // ссылка новую картинку
        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        //создаем пустую BW картинку
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();

        //  копируем в нее новую картинку
        graphics.drawImage(scaledImage, 0, 0, null);

        //перебираем пиксели и заменяем на символы
        WritableRaster bwRaster = bwImg.getRaster();
        for (int h = 0; h < newHeight; h++) {
            for (int w = 0; w < newWidth; w++) {
                int color = bwRaster.getPixel(w, h, new int[3])[0];
                char c = schema.convert(color);
                image[h][w] = c;

            }
        }
        //Собираем строку из символов
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[i].length; j++) {
                sb.append(image[i][j]);
                sb.append(image[i][j]);
            }
            sb.append("\n");

        }
        return sb.toString();


    } //End метода convert


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





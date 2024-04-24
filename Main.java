package ru.netology.graphics;


import ru.netology.graphics.image.Converter;
import ru.netology.graphics.image.TextGraphicsConverter;
import ru.netology.graphics.server.GServer;

public class Main {
    public static void main(String[] args) throws Exception {
        // String url = "https://raw.githubusercontent.com/netology-code/java-diplom/main/pics/simple-test.png";

        TextGraphicsConverter converter = new Converter(); // Создайте тут объект вашего класса конвертера
        ///converter.setMaxHeight(300);
        /// converter.setMaxWidth(150);
        GServer server = new GServer(converter); // Создаём объект сервера
        server.start(); // Запускаем
        converter.setMaxHeight(150);
        converter.setMaxWidth(75);
        // Или то же, но с выводом на экран:
        //String url = "https://raw.githubusercontent.com/netology-code/java-diplom/main/pics/simple-test.png";
        //String imgTxt = converter.convert(url);
        //System.out.println(imgTxt);

    }
}

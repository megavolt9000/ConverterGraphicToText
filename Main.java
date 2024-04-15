package ru.netology.graphics;

import ru.netology.graphics.image.Converter_Test_2;
import ru.netology.graphics.image.TextGraphicsConverter;
import ru.netology.graphics.server.GServer;

public class Main {
    public static void main(String[] args) throws Exception {
        // String url = "https://raw.githubusercontent.com/netology-code/java-diplom/main/pics/simple-test.png";

        TextGraphicsConverter converter = new Converter_Test_2(); // Создайте тут объект вашего класса конвертера


        GServer server = new GServer(converter); // Создаём объект сервера
        server.start(); // Запускаем

        // Или то же, но с выводом на экран:
        //String url = "https://raw.githubusercontent.com/netology-code/java-diplom/main/pics/simple-test.png";
        //String imgTxt = converter.convert(url);
        //System.out.println(imgTxt);

    }
}

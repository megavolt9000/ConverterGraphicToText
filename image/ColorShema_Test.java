package ru.netology.graphics.image;

public class ColorShema_Test implements TextColorSchema{
    @Override
    public char convert(int color) {

        return "#$@%*+-.".charAt(color / 32);


        /**
         //return 0;
         if (color >=240)
         return '.';
         if (color >=210)
         return '-';
         else if (color >=190)
         return '-';
         else if (color >=170)
         return '+';
         else if (color >=140)
         return '*';
         else if (color >=110)
         return '%';
         else if (color >=80)
         return '@';
         else if (color >=40)
         return '$';
         else    return '#';
         */
    }

}

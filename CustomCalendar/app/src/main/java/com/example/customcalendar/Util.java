package com.example.customcalendar;

import android.view.View;

/**
 * Created by iagomendesfucolo on 24/03/17.
 */

public class Util {


    public static int getImageMood(int i){

        return 1;
    }


    public static boolean resolveDate(int monthDate, int actualMonth){

        if (monthDate != actualMonth)
            return false;

        return true;
    }

    public static boolean setImageVisiblity(int[] date, int day, int month, int year){
        return date[0] == day && date[1] == month - 1 && date[2] == year;
    }

    public static String getMonth(int month) {
        switch (month) {
            case 1:
                return "Janeiro";
            case 2:
                return "Fevereiro";
            case 3:
                return "Março";
            case 4:
                return "Abril";
            case 5:
                return "Maio";
            case 6:
                return "Junho";
            case 7:
                return "Julho";
            case 8:
                return "Agosto";
            case 9:
                return "Setembro";
            case 10:
                return "Outubro";
            case 11:
                return "Novembro";
            case 12:
                return "Dezembro";
        }
        return "";
    }
}

package com.example.weatherapp.Common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {

    /*********************** Class with general purpose information for main classes********************/

    public static final String API_ID = "1b310b85b1568ab001985151316be353";
    public static String[] latitude={"-2.9005499","40.6643","3.42158","-0.225219","43.7001114","40.8762100","-12.0431805","10.4880104","52.3740311","51.5085297"};
    public static String[] longitude={"-79.0045319","-73.9385","-76.5205","-78.5248","-79.4162979","-74.0298600","-77.0282364","-66.8791885","4.8896899","-0.12574"};
    public static String[] cities={"Cuenca,Ec","New York,Us","Cali, Co","Quito,Ec","Toronto,Ca","Bogota,Co","Lima,Pe","Caracas,Ve","Amsterdam,Nl","London,Uk"};
    public static String[]  month = {"January", "February", "March", "April", "May", "Jun", "July", "August", "September", "October", "November", "December"};


    public static String convertUnixToDate(long dt) {
        Date date = new Date(dt*1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
        String formatted = simpleDateFormat.format(date);
        formatted = month[Integer.parseInt(formatted)];
        return formatted;
    }


    public static String convertUnixToDay(long dt) {
        Date date = new Date(dt*1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
        String formatted = simpleDateFormat.format(date);
        return formatted;
    }


    public static String convertUnixToWeekDay(long dt) {
        Date date = new Date(dt*1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE");
        String formatted = simpleDateFormat.format(date);
        return formatted;
    }


    public static String convertUnixToHour(long sunrise) {
        Date date = new Date(sunrise*1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String formatted = simpleDateFormat.format(date);
        return formatted;

    }
    public static String convertUnixToHourSimple(long sunrise) {
        Date date = new Date(sunrise*1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        String formatted = simpleDateFormat.format(date);
        return formatted;

    }
}

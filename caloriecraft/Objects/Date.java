package com.example.caloriecraft.Objects;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Date {
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String date;
    public Date (){

    }

    public String getTodayDate(){
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        date = simpleDateFormat.format(calendar.getTime());
        return date;
    }
}

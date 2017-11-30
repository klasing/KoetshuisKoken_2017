package com.example.koetshuiskoken;

import android.util.Log;

public class MyDayOfWeek {
    //************************************************************************
    //*                 declare
    //************************************************************************
    private static final String LOG_TAG = "***" + MyDayOfWeek.class.getSimpleName();

    private int iColor;
    private String strNameOfDay, strDate;

    public MyDayOfWeek(String strNameOfDay, int iColor) {
        Log.i(LOG_TAG, "<<constructor>> MyDayOfWeek()");

        this.strNameOfDay = strNameOfDay;
        this.iColor = iColor;
    }

    //************************************************************************
    //*                 getColor
    //************************************************************************
    public int getColor() {
        Log.i(LOG_TAG, "getColor()");
        return iColor;
    }

    //************************************************************************
    //*                 getNameOfDay
    //************************************************************************
    public String getNameOfDay() {
        Log.i(LOG_TAG, "getNameOfDay()");
        return strNameOfDay;
    }

    //************************************************************************
    //*                 setDate
    //************************************************************************
    public void setDate(String strDate) {
        Log.i(LOG_TAG, "setDate()");
        this.strDate = strDate;
    }
    //************************************************************************
    //*                 getDate
    //************************************************************************
    public String getDate() {
        Log.i(LOG_TAG, "getDate()");
        return strDate;
    }
}
package com.example.koetshuiskoken;

import android.util.Log;

public class MyDayOfWeek {
    private static final String LOG_TAG = "***" + MyDayOfWeek.class.getSimpleName();

    protected int iColor;
    protected String strNameOfDay, strDate;

    public MyDayOfWeek() {
        Log.d(LOG_TAG, "<<constructor>> MyDayOfWeek()");
    }

    public MyDayOfWeek(String _strNameOfDay, int _iColor) {
        Log.i(LOG_TAG, "<<constructor>> DayOfWeek()");

        strNameOfDay = _strNameOfDay;
        iColor = _iColor;
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
    //*                 getDate
    //************************************************************************
    public String getDate() {
        Log.i(LOG_TAG, "getDate()");
        return strDate;
    }

}

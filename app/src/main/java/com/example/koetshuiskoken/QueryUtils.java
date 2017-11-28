package com.example.koetshuiskoken;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public final class QueryUtils {
    private static final String LOG_TAG = "***" + QueryUtils.class.getSimpleName();

    protected static final int NOF_DAY = 7;
    private static SimpleDateFormat sdf;// = new SimpleDateFormat("dd-MM-yy");
    protected static String strCurrentDate;
    protected static int iCurrentDow = -1;
    protected static MyDayOfWeek[] aMyDayOfWeek = new MyDayOfWeek[NOF_DAY];

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
        // So this will never happen.
        Log.d(LOG_TAG,"<<constructor>> QueryUtils()");
    }

    //************************************************************************
    //*                 fetchMyDayOfWeekData
    //************************************************************************
    /**
     * Return a list of {@link MyDayOfWeek} objects.
     */
    public static List<MyDayOfWeek> fetchMyDayOfWeekData() {
        Log.i(LOG_TAG,"QueryUtils.fetchMyDayOfWeekData()");
        getDate();
        setArrayDow();
        List<MyDayOfWeek> listMyDayOfWeek = new ArrayList<MyDayOfWeek>(NOF_DAY);
        int iIdx = iCurrentDow - 1;
        for (int i = 0; i < NOF_DAY; i++) {
            listMyDayOfWeek.add(aMyDayOfWeek[iIdx]);
            iIdx = (iIdx + 1) % NOF_DAY;
        }
        return listMyDayOfWeek;
    }

    //************************************************************************
    //*                 getDate
    //************************************************************************
    /**
     * Find the current Date to place on the Panel4Week.
     * Find also the current day of the week, to have an offset in the array aDayOfWeek.
     * sunday == 1 ... saturday == 7
     * The variable iCurrentDow holds the offset.
     */
    private static void getDate() {
        Log.i(LOG_TAG, "QueryUtils.getDate()");
        Date currentDate = new Date();
        sdf = new SimpleDateFormat(MainActivity.context.getResources().getString(R.string.sdf_format));
        strCurrentDate = sdf.format(currentDate);
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(strCurrentDate));
            iCurrentDow = cal.get(Calendar.DAY_OF_WEEK);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            ;
        }
        Log.i("***", "current date: " + strCurrentDate + " dow " + iCurrentDow);
    }

    //************************************************************************
    //*                 setArrayDow
    //************************************************************************
    /**
     * Place: name of day, color for the day and date string
     * into the array aDayOfWeek.
     */
    private static void setArrayDow() {
        Log.i(LOG_TAG, "QueryUtils.setArrayDow()");
        aMyDayOfWeek[0] = new MyDayOfWeek(MainActivity.context.getResources().getString(R.string.sun),
                MainActivity.context.getResources().getColor(R.color.colorSun));
        aMyDayOfWeek[1] = new MyDayOfWeek(MainActivity.context.getResources().getString(R.string.mon),
                MainActivity.context.getResources().getColor(R.color.colorMon));
        aMyDayOfWeek[2] = new MyDayOfWeek(MainActivity.context.getResources().getString(R.string.tue),
                MainActivity.context.getResources().getColor(R.color.colorTue));
        aMyDayOfWeek[3] = new MyDayOfWeek(MainActivity.context.getResources().getString(R.string.wed),
                MainActivity.context.getResources().getColor(R.color.colorWed));
        aMyDayOfWeek[4] = new MyDayOfWeek(MainActivity.context.getResources().getString(R.string.thu),
                MainActivity.context.getResources().getColor(R.color.colorThu));
        aMyDayOfWeek[5] = new MyDayOfWeek(MainActivity.context.getResources().getString(R.string.fri),
                MainActivity.context.getResources().getColor(R.color.colorFri));
        aMyDayOfWeek[6] = new MyDayOfWeek(MainActivity.context.getResources().getString(R.string.sat),
                MainActivity.context.getResources().getColor(R.color.colorSat));

        int iDow = iCurrentDow - 1;
        String strDate = strCurrentDate;
        for (int i = 0; i < NOF_DAY; i++) {
            aMyDayOfWeek[iDow].strDate = strDate;
            iDow = (iDow + 1) % NOF_DAY;
            try {
                Calendar cal = Calendar.getInstance();
                cal.setTime(sdf.parse(strDate));
                cal.add(Calendar.DATE, 1);
                strDate = sdf.format(cal.getTime());
            } catch(ParseException e) {
                e.printStackTrace();
            } finally {
                ;
            }
        }
    }
}
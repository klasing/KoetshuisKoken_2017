package com.example.koetshuiskoken;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the dinners stored in Firebase as JsonObjects
 */
public class Dinners {
    //************************************************************************
    //*                 declare
    //************************************************************************
    private static final String LOG_TAG = "***" + Dinners.class.getSimpleName();

    List<JsonObject> listDinner = new ArrayList<>(QueryUtils.NOF_DAY);

    protected Dinners() {
        Log.i(LOG_TAG, "<<constructor>> Dinners()");
        for (int i = 0; i < QueryUtils.NOF_DAY; i++)
            listDinner.add(null);
    }
    //************************************************************************
    //*                 setJsonObject
    //************************************************************************
    protected void setJsonObject(int index, JsonObject jsonObject) {
        listDinner.add(index, jsonObject);
    }

    //************************************************************************
    //*                 getJsonObject
    //************************************************************************
    protected JsonObject getJsonObject(int index) {
        return listDinner.get(index);
    }

    //************************************************************************
    //*                 toString
    //************************************************************************
    @Override
    public String toString() {
        String strReturn = "listDinner: \n";
        for (JsonObject jsonObject : listDinner) {
            strReturn += (jsonObject != null) ? jsonObject.toString() + "\n" : "null\n";
        }
        return strReturn;
    }
}
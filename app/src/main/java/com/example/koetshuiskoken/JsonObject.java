package com.example.koetshuiskoken;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Klasing on 30-11-2017
 */
public class JsonObject {
    //************************************************************************
    //*                 declare
    //************************************************************************
    private static final String LOG_TAG = "***" + MainActivity.class.getSimpleName();

    private String nameDay, dateDay, nameCook, descriptionMeal;
    private List<String> listEating = new ArrayList<>();

    public JsonObject() {
        Log.i(LOG_TAG, "<<constructor>> JsonObject()");
    }

    public JsonObject(String nameDay, String dateDay, String nameCook,
                      String descriptionMeal, ArrayList<String>listEating) {
        Log.i(LOG_TAG, "<<constructor>> JsonObject()");
        this.nameDay = nameDay;
        this.dateDay = dateDay;
        this.nameCook = nameCook;
        this.descriptionMeal = descriptionMeal;
        this.listEating.addAll(listEating);
    }

    public void setNameDay(String nameDay) {
        this.nameDay = nameDay;
    }
    public String getetNameDay() {
        return nameDay;
    }

    public void setDateDay(String dateDay) {
        this.dateDay = dateDay;
    }
    public String getNameDate() {
        return dateDay;
    }

    public void setNameCook(String nameCook) {
        this.nameCook = nameCook;
    }
    public String getNameCook() {
        return nameCook;
    }

    public void setDescriptionMeal(String descriptionMeal) {
        this.descriptionMeal = descriptionMeal;
    }
    public String getDescriptionMeal() {
        return descriptionMeal;
    }

    public void setEating(ArrayList<String> listEating) {
        this.listEating.clear();
        this.listEating.addAll(listEating);
    }
    public List<String> getEating() {
        return listEating;
    }
}
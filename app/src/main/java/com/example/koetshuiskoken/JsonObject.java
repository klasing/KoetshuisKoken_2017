package com.example.koetshuiskoken;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Klasing on 30-11-2017
 */
@IgnoreExtraProperties
public class JsonObject {
    //************************************************************************
    //*                 declare
    //************************************************************************
    private static final String LOG_TAG = "***" + MainActivity.class.getSimpleName();

    private String nameDay;
    private String dateDay;
    private String nameCook;
    private String descriptionMeal;
    private List<String> listEating = new ArrayList<>();
    private String key;

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
    @JsonSetter("nameDay")
    public void setNameDay(String nameDay) {
        this.nameDay = nameDay;
    }
    @JsonGetter("nameDay")
    public String getNameDay() {
        return nameDay;
    }

    @JsonSetter("dateDay")
    public void setDateDay(String dateDay) {
        this.dateDay = dateDay;
    }
    @JsonGetter("dateDay")
    public String getDateDay() {
        return dateDay;
    }

    @JsonSetter("nameCook")
    public void setNameCook(String nameCook) {
        this.nameCook = nameCook;
    }
    @JsonGetter("nameCook")
    public String getNameCook() {
        return nameCook;
    }

    @JsonSetter("descriptionMeal")
    public void setDescriptionMeal(String descriptionMeal) {
        this.descriptionMeal = descriptionMeal;
    }
    @JsonGetter("descriptionMeal")
    public String getDescriptionMeal() {
        return descriptionMeal;
    }

    @JsonSetter("listEating")
    public void setListEating(ArrayList<String> listEating) {
        this.listEating.clear();
        this.listEating.addAll(listEating);
    }
    @JsonGetter("listEating")
    public List<String> getListEating() {
        return listEating;
    }

    @JsonIgnore
    @Override
    public String toString() {
        String strReturn = key + "\n" +
                nameDay + " " + dateDay + "\n" +
                nameCook + " " + descriptionMeal + "\n";
        Iterator<String> iteElement = listEating.iterator();
        while (iteElement.hasNext()) {
            strReturn += iteElement.next() + " ";
        }
        return strReturn;
    }

    @JsonIgnore
    public void setKey(String key) {
        this.key = key;
    }
    @JsonIgnore
    public String getKey() {
        return key;
    }
}
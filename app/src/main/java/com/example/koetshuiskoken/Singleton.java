package com.example.koetshuiskoken;

import android.util.Log;

/**
 * Singleton object transfers a JsonObject from MainActivity to DinnerActivity
 */
public class Singleton {
    //************************************************************************
    //*                 declare
    //************************************************************************
    private static final String LOG_TAG = "***" + Singleton.class.getSimpleName();

    private static Singleton mInstance = null;
    private JsonObject jsonObject = null;

    private Singleton() {
        Log.i(LOG_TAG, "<<constructor>> Singleton()");
    }

    protected static Singleton getInstance() {
        if (mInstance == null) {
            mInstance = new Singleton();
        }
        return mInstance;
    }

    protected void setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    protected JsonObject getJsonObject() {
        return jsonObject;
    }
}
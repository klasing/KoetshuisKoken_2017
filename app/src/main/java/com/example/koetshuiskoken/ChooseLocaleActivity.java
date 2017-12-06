package com.example.koetshuiskoken;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ChooseLocaleActivity extends AppCompatActivity {
    //************************************************************************
    //*                 declare
    //************************************************************************
    private static final String LOG_TAG = "***" + ChooseLocaleActivity.class.getSimpleName();

    //************************************************************************
    //*                 onCreate
    //************************************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(LOG_TAG, "onCreate()");
        setContentView(R.layout.activity_choose_locale);

        TextView tvLocaleEnglish = findViewById(R.id.locale_english);
        TextView tvLocaleDutch = findViewById(R.id.locale_dutch);

        tvLocaleEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "locale_english.onClick()");
            }
        });

        tvLocaleDutch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "locale_dutch.onClick()");
            }
        });
    }
}
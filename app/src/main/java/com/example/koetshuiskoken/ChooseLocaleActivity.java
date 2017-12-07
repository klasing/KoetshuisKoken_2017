package com.example.koetshuiskoken;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckedTextView;

public class ChooseLocaleActivity extends AppCompatActivity {
    //************************************************************************
    //*                 declare
    //************************************************************************
    private static final String LOG_TAG = "***" + ChooseLocaleActivity.class.getSimpleName();

    private Intent resultIntent = new Intent();

    //************************************************************************
    //*                 onCreate
    //************************************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(LOG_TAG, "onCreate()");
        setContentView(R.layout.activity_choose_locale);
        // set title according to locale
        setTitle(R.string.choose_locale);
        // set a 'home' button on actionbar
        // it's actually a left pointing arrow
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CheckedTextView locale_english = findViewById(R.id.locale_english);
        CheckedTextView locale_dutch = findViewById(R.id.locale_dutch);

        // get the language setting from the locale in MainActivity
        String language = getIntent().getStringExtra("language");
        // check the CheckedTextView to what is coming in
        if (language.equals("en")) {
            locale_english.setChecked(true);
            locale_dutch.setChecked(false);

            // set value for return, if user doesn't set a CheckedTextView
            resultIntent.putExtra("language", "en");
        }
        if (language.equals("nl")) {
            locale_english.setChecked(false);
            locale_dutch.setChecked(true);

            // set value for return, if user doesn't set a CheckedTextView
            resultIntent.putExtra("language", "nl");
        }

        locale_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "locale_english.onClick()");
                CheckedTextView loc_english = findViewById(R.id.locale_english);
                CheckedTextView loc_dutch = findViewById(R.id.locale_dutch);

                loc_english.setChecked(true);
                loc_dutch.setChecked(false);

                resultIntent.putExtra("language", "en");
            }
        });

        locale_dutch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "locale_dutch.onClick()");
                CheckedTextView loc_english = findViewById(R.id.locale_english);
                CheckedTextView loc_dutch = findViewById(R.id.locale_dutch);

                loc_english.setChecked(false);
                loc_dutch.setChecked(true);

                resultIntent.putExtra("language", "nl");
            }
        });

    }

    //************************************************************************
    //*                 onOptionsItemSelected
    //************************************************************************
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(LOG_TAG, "onOptionsItemSelected()");
        int id = item.getItemId();
        if (id == android.R.id.home) {
            //onBackPressed();
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
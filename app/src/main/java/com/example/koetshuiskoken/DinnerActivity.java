package com.example.koetshuiskoken;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DinnerActivity extends AppCompatActivity {
    //************************************************************************
    //*                 declare
    //************************************************************************
    private static final String LOG_TAG = "***" + DinnerActivity.class.getSimpleName();

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDinnerDatabaseReference;
    private boolean bAddNew = true;

    //************************************************************************
    //*                 onCreate
    //************************************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(LOG_TAG, "onCreate()");
        setContentView(R.layout.activity_dinner);

        // set title on actionbar according to locale
        this.setTitle(getResources().getString(R.string.dinner_activity));

        // set an icon on the actionbar
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setIcon(R.drawable.ic_launcher_koetshuis_koken);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        // filling the activity_dinner.xml with extra data,
        // supplied by the MainActivity
        Bundle bundle = getIntent().getExtras();
        // color for background
        int iColor = bundle.getInt("color");
        View root = findViewById(R.id.activity_dinner);
        root.setBackgroundColor(iColor);
        // name of day in header
        String strNameDay = bundle.getString("name_day");
        TextView name_day = findViewById(R.id.name_day);
        name_day.setText(strNameDay);
        // date in header
        String strDateDay = bundle.getString("date_day");
        TextView date_day = findViewById(R.id.date_day);
        date_day.setText(strDateDay);

        final String url = bundle.getString("FIREBASE_URL");

        mFirebaseDatabase = FirebaseDatabase.getInstance(url);
        mDinnerDatabaseReference =
                mFirebaseDatabase.getReference().child("dinner");

        JsonObject jsonObject;
        if ((jsonObject = Singleton.getInstance().getJsonObject()) != null) {
            // get name cook from json into edittext_cook
            EditText edittext_cook = (EditText) findViewById(R.id.edittext_cook);
            edittext_cook.setText(jsonObject.getNameCook());
            // get description meal from json into edittext_meal
            EditText edittext_meal = (EditText) findViewById(R.id.edittext_meal);
            edittext_meal.setText(jsonObject.getDescriptionMeal());
            // get guests from json into edittext_eating
            EditText edittext_eating = (EditText) findViewById(R.id.edittext_eating);
            for (String strElement : jsonObject.getListEating()) {
                edittext_eating.append(strElement + '\n');
            }
            bAddNew = false;
        }
        //********************************************************************
        // testing the JsonObject
//        List<String> listEating = new ArrayList<>();
//        listEating.add("Anne");
//        listEating.add("Mark");
//        JsonObject jsonObject = new JsonObject("Sunday", "December 3 2017",
//                "Jim", "Meatloaf", (ArrayList<String>)listEating);
//
//        // filling the activity_dinner.xml with JsonObject data
//        EditText edittext_cook = findViewById(R.id.edittext_cook);
//        edittext_cook.setText(jsonObject.getNameCook());
//        EditText edittext_meal = findViewById(R.id.edittext_meal);
//        edittext_meal.setText(jsonObject.getDescriptionMeal());
//        EditText edittext_eating = findViewById(R.id.edittext_eating);
//        for (String strElement : jsonObject.getEating()) {
//            edittext_eating.append(strElement + '\n');
//        }
        //********************************************************************
        Button button_ok = (Button) findViewById(R.id.button_ok);
        Button button_save = (Button) findViewById(R.id.button_save);
        Button button_cancel = (Button) findViewById(R.id.button_cancel);

        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "button_ok.onClick()");
                // finish this activity and return to MainActivity
                finish();
            }
        });
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "button_save.onClick()");
                //************************************************************
                // test sample
//                List<String> listEating = new ArrayList<>();
//                listEating.add("Anne");
//                listEating.add("Mark");
//                JsonObject jsonObject = new JsonObject("SUN", "12-03-2017",
//                        "Jim", "Meatloaf", (ArrayList<String>)listEating);
                //************************************************************
                JsonObject jsonObject = new JsonObject();
                // get name_day into json
                TextView name_day = (TextView) findViewById(R.id.name_day);
                jsonObject.setNameDay(name_day.getText().toString());
                // get date day into json
                TextView date_day = (TextView) findViewById(R.id.date_day);
                jsonObject.setDateDay(date_day.getText().toString());
                // get name cook into json
                EditText edittext_cook = (EditText) findViewById(R.id.edittext_cook);
                jsonObject.setNameCook(edittext_cook.getText().toString());
                // get description meal into json
                EditText edittext_meal = (EditText) findViewById(R.id.edittext_meal);
                jsonObject.setDescriptionMeal(edittext_meal.getText().toString());
                // get guests into json
                EditText edittext_eating = (EditText) findViewById(R.id.edittext_eating);
                String strEating = edittext_eating.getText().toString();
                if (strEating.length() > 0) {
                    if (strEating.charAt(strEating.length() - 1) != '\n') strEating += "\n";
                    List<String> listEating = new ArrayList<String>();
                    String regex = "\n";
                    Pattern p = Pattern.compile(regex);
                    Matcher m = p.matcher(strEating);
                    int i = 0;
                    int iStartSubstr = 0;
                    while (m.find()) {
                        String strSubstr = strEating.substring(iStartSubstr, m.start());
                        listEating.add(strSubstr);
                        iStartSubstr = m.end();
                    }
                    jsonObject.setListEating((ArrayList<String>) listEating);
                }
                if (bAddNew) {
                    // place new dinner under /KoetshuisKoken2017/dinner
                    mDinnerDatabaseReference.push().setValue(jsonObject);
                } else {
                    Log.i(LOG_TAG, "update existing record");
                    // place update dinner under /KoetshuisKoken2017/dinner/[key]
                    mDinnerDatabaseReference.child(Singleton.getInstance().getJsonObject().getKey()).setValue(jsonObject);

                }
                // finish this activity and return to MainActivity
                finish();
            }
        });
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "button_cancel.onClick()");
                // finish this activity and return to MainActivity
                finish();
            }
        });
    }

    //************************************************************************
    //*                 onStart
    //************************************************************************
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOG_TAG, "onStart()");
    }

    //************************************************************************
    //*                 onResume
    //************************************************************************
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume()");
    }

}
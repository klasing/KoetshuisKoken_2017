package com.example.koetshuiskoken;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //************************************************************************
    //*                 declare
    //************************************************************************
    private static final String LOG_TAG = "***" + MainActivity.class.getSimpleName();

    // adapter for the list day of week
    private DayOfWeekAdapter mAdapter;

    protected static Context context;

    //************************************************************************
    //*                 onCreate
    //************************************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(LOG_TAG, "onCreate()");
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();

        // Find a reference to the {@link ListView} in the layout.
        ListView dowListView = (ListView) findViewById(R.id.list);
        // Create a new adapter that takes an empty listDayOfWeek as input.
        mAdapter = new DayOfWeekAdapter(this, new ArrayList<MyDayOfWeek>());
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface.
        dowListView.setAdapter(mAdapter);
        // Set an item click listener on the ListView, which starts an intent
        // to open a form with information about the selected day of week.
        dowListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.i(LOG_TAG, "DayOfWeek[" + position + "].onItemClick()");
                // Find the current DayOfWeek that was clicked on
                MyDayOfWeek currentDayOfWeek = mAdapter.getItem(position);
                Intent intentDinnerActivity = new Intent(MainActivity.this,
                        DinnerActivity.class);
                setExtras(intentDinnerActivity,
                        currentDayOfWeek.getColor(),
                        currentDayOfWeek.getNameOfDay(),
                        currentDayOfWeek.getDate());
                startActivity(intentDinnerActivity);
            }
        });
        // Add a array of {@link DayOfWeek}s to the adapter's
        // data set. This will trigger the ListView to update.
        mAdapter.addAll(QueryUtils.fetchDayOfWeekData());

    }
    //************************************************************************
    //*                 setExtras
    //************************************************************************
    /**
     * Bring the GUI characteristics to the activity.
     */
    private void setExtras(Intent intent, int iColor,
                           String strNameDay, String strDateDay) {
        Log.i(LOG_TAG, "setExtras()");
        intent.putExtra("color", iColor);
        intent.putExtra("name_day", strNameDay);
        intent.putExtra("date_day", strDateDay);
    }
}

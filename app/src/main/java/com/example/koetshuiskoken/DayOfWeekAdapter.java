package com.example.koetshuiskoken;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class DayOfWeekAdapter extends ArrayAdapter<MyDayOfWeek> {
    //************************************************************************
    //*                 declare
    //************************************************************************
    private static final String LOG_TAG = "***" + DayOfWeekAdapter.class.getSimpleName();

    public DayOfWeekAdapter(Context context, List<MyDayOfWeek> listDayOfWeek) {
        super(context, 0, listDayOfWeek);
        Log.i(LOG_TAG, "<<constructor>> DayOfWeekAdapter()");
    }

    //************************************************************************
    //*                 getView
    //************************************************************************
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i(LOG_TAG, "getView()");

        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.dow_list_item, parent, false);
        }

        // Find the DayOfWeek at the given position in the listDayOfWeek.
        MyDayOfWeek currentDayOfWeek = getItem(position);

        // Set the background color for a list item.
        listItemView.setBackgroundColor(currentDayOfWeek.getColor());

        // Find the TextView with view ID name_day.
        TextView nameDayView = (TextView) listItemView.findViewById(R.id.name_day);
        nameDayView.setBackgroundColor(currentDayOfWeek.getColor());
        nameDayView.setText(currentDayOfWeek.getNameOfDay());

        // Find the TextView with view ID date_day.
        TextView dateView = (TextView) listItemView.findViewById(R.id.date_day);
        dateView.setBackgroundColor(currentDayOfWeek.getColor());
        dateView.setText(currentDayOfWeek.getDate());

        // Return the list item view that is now showing the appropriate data.
        return listItemView;
    }
}
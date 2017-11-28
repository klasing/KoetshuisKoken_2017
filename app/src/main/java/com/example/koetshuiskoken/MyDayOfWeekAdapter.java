package com.example.koetshuiskoken;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MyDayOfWeekAdapter extends ArrayAdapter<MyDayOfWeek> {
    private static final String LOG_TAG = "***" + MyDayOfWeekAdapter.class.getSimpleName();

    public MyDayOfWeekAdapter(Context context, List<MyDayOfWeek> listMyDayOfWeek) {
        super(context, 0, listMyDayOfWeek);
        Log.d(LOG_TAG, "<<constructor>> MyDayOfWeekAdapter()");
    }

    //************************************************************************
    //*                 getView
    //************************************************************************
    /**
     * Returns a list item view that displays information about the DayOfWeek at the given position
     * in the list of DayOfWeek.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i(LOG_TAG, "DayOfWeekAdapter.getView()");
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.dow_list_item, parent, false);
        }

        // Find the DayOfWeek at the given position in the listDayOfWeek.
        MyDayOfWeek currentMyDayOfWeek = getItem(position);

        // Set the background color for a list item.
        listItemView.setBackgroundColor(currentMyDayOfWeek.getColor());

        // Find the TextView with view ID name_day.
        TextView nameDayView = (TextView) listItemView.findViewById(R.id.name_day);
        nameDayView.setBackgroundColor(currentMyDayOfWeek.getColor());
        nameDayView.setText(currentMyDayOfWeek.getNameOfDay());

        // Find the TextView with view ID date_day.
        TextView dateView = (TextView) listItemView.findViewById(R.id.date_day);
        dateView.setBackgroundColor(currentMyDayOfWeek.getColor());
        dateView.setText(currentMyDayOfWeek.getDate());

        // Return the list item view that is now showing the appropriate data.
        return listItemView;
    }
}
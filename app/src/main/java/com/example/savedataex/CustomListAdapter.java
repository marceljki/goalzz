package com.example.savedataex;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CustomListAdapter extends ArrayAdapter {

    final static String WINNER_GREEN = "#74C804";
    final static String LOSER_GREY = "#999999";

    //to reference the Activity
    private final Activity context;

    //to store the list of names
    private final String[] nameArray;

    //to store the list of reps
    private final int[] repsArray;

    //to store the list of reps
    private final int[] currentRepsArray;

    //to store the list of deadlines
    private final Date[] deadlineArray;

    public CustomListAdapter(Activity context, String[] nameArrayParam, int[] repsArrayParam, int[] currentRepsArrayParam, Date[] deadlineArrayParam){
        super(context,R.layout.listview_row , nameArrayParam);
        this.context = context;
        this.nameArray = nameArrayParam;
        this.repsArray = repsArrayParam;
        this.currentRepsArray = currentRepsArrayParam;
        this.deadlineArray = deadlineArrayParam;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTV = (TextView) rowView.findViewById(R.id.nameTV);
        TextView repsTV = (TextView) rowView.findViewById(R.id.repsTV);
        TextView deadlineTV = (TextView) rowView.findViewById(R.id.deadlineTV);
        ProgressBar progressBar = (ProgressBar) rowView.findViewById(R.id.progressBar);
        View rowViewDisplay = (View) rowView.findViewById(R.id.rowViewDisplay);

        // Setting up the progress bar
        progressBar.setMax(repsArray[position]);
        progressBar.setProgress(currentRepsArray[position]);

        //this code sets the values of the objects to values from the arrays
        String repsOutOf = currentRepsArray[position] + "/" + repsArray[position];
        nameTV.setText(nameArray[position]);
        if (currentRepsArray[position]>=repsArray[position]){
            repsOutOf = "MISSON COMPLETED";
            rowViewDisplay.setBackgroundColor(Color.parseColor(WINNER_GREEN));
        }
        repsTV.setText(repsOutOf);
        String days = days_left(deadlineArray[position])+ " days left";
        if (days_left(deadlineArray[position])<0){
            days = "EXPIRED";
            rowViewDisplay.setBackgroundColor(Color.parseColor(LOSER_GREY));
        }

        deadlineTV.setText(days);
        return rowView;
    }

    private int days_left(Date deadline){
        Date date = new Date();
        long difference = deadline.getTime()-date.getTime();
        int days_left = (int) TimeUnit.DAYS.convert(difference,TimeUnit.MILLISECONDS);
        return days_left;
    }
}

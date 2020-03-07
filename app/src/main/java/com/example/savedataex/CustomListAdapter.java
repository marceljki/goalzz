package com.example.savedataex;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

public class CustomListAdapter extends ArrayAdapter {

    //to reference the Activity
    private final Activity context;

    //to store the list of names
    private final String[] nameArray;

    //to store the list of reps
    private final int[] repsArray;

    //to store the list of deadlines
    private final Date[] deadlineArray;

    public CustomListAdapter(Activity context, String[] nameArrayParam, int[] repsArrayParam, Date[] deadlineArrayParam){
        super(context,R.layout.listview_row , nameArrayParam);
        this.context = context;


        this.nameArray = nameArrayParam;
        this.repsArray = repsArrayParam;
        this.deadlineArray = deadlineArrayParam;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTV = (TextView) rowView.findViewById(R.id.nameTV);
        TextView repsTV = (TextView) rowView.findViewById(R.id.repsTV);
        TextView deadlineTV = (TextView) rowView.findViewById(R.id.deadlineTV);

        //this code sets the values of the objects to values from the arrays
        nameTV.setText(nameArray[position]);
        repsTV.setText(repsArray[position]);
        deadlineTV.setText(deadlineArray[position].toString());

        return rowView;
    }
}

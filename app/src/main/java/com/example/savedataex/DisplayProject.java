package com.example.savedataex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DisplayProject extends AppCompatActivity {

    MyDBHandler dbHandler;
    Projects project;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_project);
        dbHandler = new MyDBHandler(this, null, null, 4);

        // Initialize all views
        TextView name = (TextView) findViewById(R.id.d_projectname);
        TextView days_left = (TextView) findViewById(R.id.d_days_left);
        TextView reps = (TextView) findViewById(R.id.d_reps);
        progressBar = (ProgressBar) findViewById(R.id.d_progressBar);

        // getting the project
        String projectname = getIntent().getStringExtra("projectname");
        project = dbHandler.getProjectbyName(projectname);
        // Setting values
        name.setText(project.getProjectname().toUpperCase());
        days_left.setText(project.days_left() + " days left");
        reps.setText(project.getCurrent_reps() + "/" + project.getReps());
        progressBar.setMax(project.getReps());
        progressBar.setProgress(project.getCurrent_reps());

    }

    public void submitButtonClicked(View view) {
        EditText how_much = (EditText) findViewById(R.id.how_much);
        if (!how_much.getText().toString().isEmpty()) {
            int sum = Integer.parseInt(how_much.getText().toString()) + project.getCurrent_reps();
            if (sum >= project.getReps()) {
                progressBar.setProgress(project.getReps());
                dbHandler.increaseReps(project.getProjectname(), sum);
            } else {
                progressBar.setProgress(sum);
                dbHandler.increaseReps(project.getProjectname(), sum);
            }
        }
        else how_much.setError("put a number");
    }
}

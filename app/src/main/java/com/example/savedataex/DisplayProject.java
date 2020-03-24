package com.example.savedataex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayProject extends AppCompatActivity {

    MyDBHandler dbHandler;
    Projects project;
    ProgressBar progressBar;
    TextView reps;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_project);
        dbHandler = new MyDBHandler(this, null, null, 4);

        // Initialize all views
        TextView name = (TextView) findViewById(R.id.d_projectname);
        TextView days_left = (TextView) findViewById(R.id.d_days_left);
        reps = (TextView) findViewById(R.id.d_reps);
        progressBar = (ProgressBar) findViewById(R.id.d_progressBar);
        View displayLayout = (View) findViewById(R.id.displayLayout);
        TextView displayDescription = (TextView) findViewById(R.id.display_description);

        // getting the project
        String projectname = getIntent().getStringExtra("projectname");
        project = dbHandler.getProjectbyName(projectname);
        // Setting values
        name.setText(project.getProjectname().toUpperCase());
        days_left.setText(project.days_left() + " days left");
        reps.setText(project.getCurrent_reps() + "/" + project.getReps());
        displayDescription.setText(project.getDescription());
        progressBar.setMax(project.getReps());
        progressBar.setProgress(project.getCurrent_reps());
        if (project.expired()){
            days_left.setText("EXPIRED");
            displayLayout.setBackgroundColor(Color.GRAY);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.project_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String name = project.getProjectname();
        intent = new Intent(DisplayProject.this, firstActivity.class);
        switch (item.getItemId()){
            case R.id.delete:
                dbHandler.deleteProduct(name);
                Toast.makeText(this, name + " deleted", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                return true;
            case R.id.edit:
                dbHandler.setRepsInDB(project.getProjectname(),project.getPrev_reps());
                startActivity(intent);
                return true;
            default: return super.onOptionsItemSelected(item);
        }

    }

    public void submitButtonClicked(View view) {
        EditText how_much = (EditText) findViewById(R.id.how_much);
        if (!how_much.getText().toString().isEmpty()) {
            int currentR = project.getCurrent_reps();
            dbHandler.setColumnPreviousReps(project.getProjectname(),currentR);
            project.setPrev_reps(currentR);
            int sum = Integer.parseInt(how_much.getText().toString()) + project.getCurrent_reps();
            if (sum >= project.getReps()) {
                progressBar.setProgress(project.getReps());
                reps.setText(project.getReps() + "/" + project.getReps());
                dbHandler.setRepsInDB(project.getProjectname(), sum);
            } else {
                progressBar.setProgress(sum);
                dbHandler.setRepsInDB(project.getProjectname(), sum);
                reps.setText(sum + "/" + project.getReps());
            }
            intent = new Intent(DisplayProject.this, firstActivity.class);
            startActivity(intent);
        }
        else how_much.setError("put a number");
    }
}

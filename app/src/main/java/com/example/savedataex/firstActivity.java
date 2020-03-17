package com.example.savedataex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;

public class firstActivity extends AppCompatActivity {

     MyDBHandler dbHandler;
     List<Projects> projects;
     ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // TODO Inspect the code https://appsandbiscuits.com/listview-tutorial-android-12-ccef4ead27cc

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        dbHandler = new MyDBHandler(this, null, null, 4);
        projects = dbHandler.getProjects();
        // creating and initializing the arrays of deadline name and reps
        int number_of_projects = projects.size();
        String[] nameArray = new String[number_of_projects];
        int[] repsArray = new int[number_of_projects];
        int[] currentRepsArray = new int[number_of_projects];
        Date[] deadlineArray = new Date[number_of_projects];




        for (int i = 0; i < number_of_projects;i++){
            Projects current = projects.get(i);
            nameArray[i] = current.getProjectname();
            repsArray[i] = current.getReps();
            currentRepsArray[i] = current.getCurrent_reps();
            deadlineArray[i] = current.getDeadline();
        }

        CustomListAdapter adapter = new CustomListAdapter(this,nameArray, repsArray,currentRepsArray,deadlineArray);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }

    public void floatingAddButtonClicked(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



}

package com.example.savedataex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

public class firstActivity extends AppCompatActivity {

     MyDBHandler dbHandler;
     List<Projects> projects;
     ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

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

        // Every item in the listview has an onclicklistener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // why the fuck do I need a package context
                Intent intent = new Intent(firstActivity.this, DisplayProject.class);
                // Attach data(Projectname) to Intent so activity can be started individually
                intent.putExtra("projectname", projects.get(position).getProjectname());
                startActivity(intent);
            }
        });
    }

    public void floatingAddButtonClicked(View view){
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

}

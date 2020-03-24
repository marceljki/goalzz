package com.example.savedataex;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText projectname;
    EditText reps, description;
    TextView text, showDeadline;
    MyDBHandler dbHandler;
    Projects project;
    java.util.Date testDate = new java.util.Date();
    Button addButton, deadlineButton;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        projectname = (EditText) findViewById(R.id.projectname);
        reps = (EditText) findViewById(R.id.reps);
        dbHandler = new MyDBHandler(this, null, null, 1);
        deadlineButton = (Button) findViewById(R.id.calender);
        addButton = (Button) findViewById(R.id.addButton);
        description = (EditText) findViewById(R.id.add_description);

        reps.addTextChangedListener(notnull);
        projectname.addTextChangedListener(notnull);
    }
    // This part is for enabling the add-button when everything is filled out
    private TextWatcher notnull = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String _projectname = projectname.getText().toString().trim();
            String _reps = reps.getText().toString().trim();
            addButton.setEnabled(!_projectname.isEmpty() && !_reps.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    // ATTENTION : Always give views a parameter so the onClick - Methods can be used
   public void addButtonClicked(View view){
        int repsAsInt = Integer.parseInt(reps.getText().toString());
        // Before the addButton is clicked:
       //           -> is the deadline after today?
       //           -> are the reps over 0 ?
        if(testDate.before(new Date())){
            showToast("Use a Deadline AFTER today");
            deadlineButton.setHint("Deadline");
        }
        else if(repsAsInt <= 0){
            reps.setText("");
            reps.setError("Reps can not be zero");
        }
        else {
            // Add projects to database
            // if project is already in DB (since the primary key is the name)
            // the DB throws a SQL Error and this method throws an error on the EditText
            project = new Projects(projectname.getText().toString(), repsAsInt, testDate, description.getText().toString());
            try {
                dbHandler.addProject(project);
                // show a message
                showToast(projectname.getText().toString() + " added");
                // link to @firstactivity
                Intent intent = new Intent(this, firstActivity.class);
                startActivity(intent);
                resetTextViews();
            }catch (Exception e){
                projectname.setError("Name already exists");
            }

        }
    }

    public void deadlineButtonClicked(View view){
        DialogFragment datePicker = new com.example.savedataex.DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "date picker");
    }
    // This section is for opening the Calender
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        testDate = c.getTime();
        String currentDateString = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(c.getTime());
        deadlineButton.setHint(currentDateString);
    }

    // Resets every view and the deadline
    public void resetTextViews(){
        projectname.setText(""); // Set the projectname field to "" after pressing the button
        reps.setText("");
        deadlineButton.setHint("Deadline");
    }

    private void showToast(String string){
        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }


}

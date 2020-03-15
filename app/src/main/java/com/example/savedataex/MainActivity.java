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


public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText projectname;
    EditText reps;
    TextView text, showDeadline;
    MyDBHandler dbHandler;
    Projects project;
    java.util.Date testDate = new java.util.Date();
    Button addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        projectname = (EditText) findViewById(R.id.projectname);
        reps = (EditText) findViewById(R.id.reps);
        text = (TextView) findViewById(R.id.textView);
        dbHandler = new MyDBHandler(this, null, null, 1);
        showDeadline = (TextView) findViewById(R.id.showDeadline);
        addButton = (Button) findViewById(R.id.addButton) ;

        reps.addTextChangedListener(notnull);
        projectname.addTextChangedListener(notnull);
    }

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

        if(testDate.before(new Date())){
            showToast("Use a Deadline after today, you stupid lil fish");
            showDeadline.setText("");
        }
        else {
            // Add projects to database
            project = new Projects(projectname.getText().toString(), Integer.parseInt(reps.getText().toString()), testDate);
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
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(c.getTime());

        TextView showdeadline = (TextView) findViewById(R.id.showDeadline);

        showdeadline.setText(currentDateString);

        testDate = c.getTime();

    }

    public void deleteButtonClicked(View view){
        dbHandler.deleteProduct(projectname.getText().toString());
        resetTextViews();
    }

    public void resetTextViews(){
        projectname.setText(""); // Set the projectname field to "" after pressing the button
        reps.setText("");
        showDeadline.setText("");
    }

    private void showToast(String string){
        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }


}

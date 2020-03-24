package com.example.savedataex;


import android.content.ContentValues;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Projects {
    private int id;
    private String projectname;
    private int reps;
    private java.util.Date deadline;
    private int current_reps;
    private int prev_reps;
    private String description;
    public static final DateFormat DATEFORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public Projects(){

    }
    public Projects(String projectname, int reps,int current_reps,int prev_reps, java.util.Date deadline, String description){
        this.projectname = projectname;
        this.reps = reps;
        this.current_reps = current_reps;
        this.prev_reps = prev_reps;
        this.deadline = deadline;
        this.description = description;
    }
    public Projects(String projectname, int reps, java.util.Date deadline, String description){
        this(projectname,reps,0,0,deadline,description);
    }

    public Projects(String projectname, int reps, java.util.Date deadline){
        this(projectname,reps,deadline,"");
    }

        // **********************GETTER AND SETTER************************
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrev_reps() {
        return prev_reps;
    }

    public void setPrev_reps(int prev_reps) {
        this.prev_reps = prev_reps;
    }

    public String getDescription() {
        return description;
    }

    public int getCurrent_reps() {
        return current_reps;
    }

    public void setCurrent_reps(int current_reps) {
        this.current_reps = current_reps;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public java.util.Date getDeadline() {
        return deadline;
    }

    public void setDeadline(java.util.Date deadline) {
        this.deadline = deadline;
    }
    // ********************GETTER AND SETTER EEEEND**************************

    // calculates the days left to the deadline
     int days_left(){
       Date date = new Date();
       long difference = deadline.getTime()-date.getTime();
       int days_left = (int) TimeUnit.DAYS.convert(difference,TimeUnit.MILLISECONDS);
       return days_left;
    }
    boolean expired(){
        return (this.days_left() < 0);
    }

    @Override
    public String toString(){
        return "Name: "+projectname+"| Reps: "+ current_reps +"/" +reps+"| Deadline: "+ DATEFORMAT.format(deadline);
    }
}

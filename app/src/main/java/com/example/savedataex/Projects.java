package com.example.savedataex;


import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Projects {
    private int id;
    private String projectname;
    private int reps;
    private java.util.Date deadline;
    protected static final DateFormat DATEFORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public Projects(){

    }

    public Projects(String projectname, int reps, java.util.Date deadline){
        this.projectname = projectname;
        this.reps = reps;
        this.deadline = deadline;
    }
        // **********************GETTER AND SETTER************************
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString(){
        return "Name: "+projectname+"| Reps: "+ reps+"| Deadline: "+ DATEFORMAT.format(deadline);
    }
}

package com.example.savedataex;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class MyDBHandler extends SQLiteOpenHelper{


    private static final int DATABASE_VERSION = 7;
    private static final String DATABASE_NAME = "projects.db ";
    public static final String TABLE_PROJECTS = "projects";
    public static final String COLUMN_PROJECT_NAME =  "projectname";
    public static final String COLUMN_REPS = "reps";
    public static final String COLUMN_CURRENT_REPS = "current_reps";
    public static final String COLUMN_PREVIOUS_REPS = "prev_reps";
    public static final String COLUMN_DEADLINE = "deadline";
    public static final String COLUMN_DESCRIPTION = "description";



    public MyDBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override // The very first time the App is running; So create DB here
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_PROJECTS + "(" +
                COLUMN_PROJECT_NAME + " TEXT PRIMARY KEY, " +
                COLUMN_REPS + " INTEGER, "+
                COLUMN_CURRENT_REPS + " INTEGER DEFAULT 0," +
                COLUMN_PREVIOUS_REPS + " INTEGER DEFAULT 0," +
                COLUMN_DEADLINE + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT DEFAULT ''" +
                ");";

        // ^^ this is now sql sourcecode
        db.execSQL(query); // Exec Query
    }

    @Override // If DB_Version is upgraded, then this method is called
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECTS);
        onCreate(db);
    }
    // Delete Table if db structure is upgraded ^

    //Add a new row
    public void addProject(Projects project){
        ContentValues values = new ContentValues(); // List of values
        java.sql.Date sqlDate= new java.sql.Date(project.getDeadline().getTime());

        values.put(COLUMN_PROJECT_NAME, project.getProjectname());
        values.put(COLUMN_REPS, project.getReps());
        values.put(COLUMN_CURRENT_REPS, project.getCurrent_reps());
        values.put(COLUMN_PREVIOUS_REPS, project.getPrev_reps());
        values.put(COLUMN_DEADLINE, Projects.DATEFORMAT.format(sqlDate));
        values.put(COLUMN_DESCRIPTION,project.getDescription());

        SQLiteDatabase db = getWritableDatabase();
        db.insertOrThrow(TABLE_PROJECTS, null, values);

        db.close();
    }


    //Delete a product
    public void deleteProduct(String projectname){
        SQLiteDatabase db = getWritableDatabase(); // Gets a ref to DB
        db.execSQL("DELETE FROM " + TABLE_PROJECTS +
                " WHERE " + COLUMN_PROJECT_NAME + "=\"" + projectname + "\";");
    }
    // Print as string
    public String DBToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_PROJECTS + " WHERE 1";

        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while (!c.isAfterLast()){
            if (c.getString(c.getColumnIndex("deadline")) != null){
                dbString += c.getString(c.getColumnIndex("deadline"));
                dbString += "\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }

    public Cursor getProjectsAsCursor(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+ TABLE_PROJECTS, null);
        return data;
    }

    // @returns all Projects that are currently in the database
    public List<Projects> getProjects(){
        if (dBisEmpty()){
            return new ArrayList<Projects>();
        }
        List<Projects> projectsList = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_PROJECTS, null);
        while(cursor.moveToNext()) {
                // gets all information
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_NAME));
                int reps = cursor.getInt(cursor.getColumnIndex(COLUMN_REPS));
                int current_reps = cursor.getInt(cursor.getColumnIndex(COLUMN_CURRENT_REPS));
                int prev_reps = cursor.getInt(cursor.getColumnIndex(COLUMN_CURRENT_REPS));
                String deadString = cursor.getString(cursor.getColumnIndex(COLUMN_DEADLINE));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));

                 // converts the string date into date date
                try {
                    java.util.Date dead = Projects.DATEFORMAT.parse(deadString);

                    projectsList.add(new Projects(name, reps, current_reps,prev_reps, dead, description));
                } catch (Exception e) {
                    System.out.println("Something went wrong");
                }

            db.close();
        }

        return projectsList;
    }

     Projects getProjectbyName(String name){
        String query = "SELECT * FROM " + TABLE_PROJECTS +
                        " WHERE " + COLUMN_PROJECT_NAME + "=\"" + name + "\"";
        SQLiteDatabase db = getReadableDatabase();
        // Cursor to get the data of the requested row
        Cursor data = db.rawQuery(query, null);
        // Note: cursor starts at -1, so it is needed to step one step forward
        data.moveToNext();
       // Getting the data in the variables and creating a project
        String pName = data.getString(data.getColumnIndex(COLUMN_PROJECT_NAME));
        int reps = data.getInt(data.getColumnIndex(COLUMN_REPS));
        int current_reps = data.getInt(data.getColumnIndex(COLUMN_CURRENT_REPS));
        int prev_reps = data.getInt(data.getColumnIndex(COLUMN_PREVIOUS_REPS));
        String description = data.getString(data.getColumnIndex(COLUMN_DESCRIPTION));
        Date deadline = new Date();
        try {
             deadline = Projects.DATEFORMAT.parse(data.getString(data.getColumnIndex(COLUMN_DEADLINE)));
        }
        catch (Exception e){
            System.out.println("Something went wrong in @getprojectbyname");
        }
        return new Projects(pName,reps,current_reps,prev_reps,deadline,description);
    }

    private boolean dBisEmpty(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_PROJECTS, null);
        return (mCursor.getCount() == 0);
    }

    void setRepsInDB(String name, int newReps){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(COLUMN_CURRENT_REPS,newReps);
        db.update(TABLE_PROJECTS,c, COLUMN_PROJECT_NAME + "=\"" + name + "\"", null);
    }
    void setColumnPreviousReps(String name, int prevReps){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(COLUMN_PREVIOUS_REPS,prevReps);
        db.update(TABLE_PROJECTS,c, COLUMN_PROJECT_NAME + "=\"" + name + "\"", null);
    }
    void setColumnDescription(String name, String description){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(COLUMN_DESCRIPTION,description);
        db.update(TABLE_PROJECTS,c, COLUMN_PROJECT_NAME + "=\"" + name + "\"", null);
    }
}

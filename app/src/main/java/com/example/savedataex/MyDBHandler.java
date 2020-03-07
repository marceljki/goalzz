package com.example.savedataex;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import androidx.annotation.Nullable;


import java.util.ArrayList;
import java.util.List;



public class MyDBHandler extends SQLiteOpenHelper{


    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "projects.db ";
    public static final String TABLE_PROJECTS = "projects";
    public static final String COLUMN_ID =  "_id";
    public static final String COLUMN_PROJECT_NAME =  "projectname";
    public static final String COLUMN_REPS = "reps";
    public static final String COLUMN_DEADLINE = "deadline";

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    public MyDBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override // The very first time the App is running; So create DB here
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_PROJECTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PROJECT_NAME + " TEXT, " +
                COLUMN_REPS + " INTEGER, "+
                COLUMN_DEADLINE + " TEXT" +
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
        values.put(COLUMN_DEADLINE, dateFormat.format(sqlDate));
        // TODO There is a difference between this date and the date that is in getProjects


        SQLiteDatabase db = getWritableDatabase();

        db.insert(TABLE_PROJECTS, null, values);

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
            return null;
        }
        List<Projects> projectsList = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_PROJECTS, null);
        while(cursor.moveToNext()) {
                // gets all information
                String name = cursor.getString(cursor.getColumnIndex("projectname"));
                int reps = cursor.getInt(cursor.getColumnIndex("reps"));
                String deadString = cursor.getString(cursor.getColumnIndex("deadline"));

                // converts the string date into date date
                try {
                    java.util.Date dead = dateFormat.parse(deadString);
                    // TODO date is wrong

                    projectsList.add(new Projects(name, reps, dead));
                } catch (Exception e) {
                    System.out.println("Something went wrong");
                }

            db.close();
        }
//        // THATS JUST FOR DEBUGGING PLEASE DELETE LATER
//        for (Projects p: projectsList){
//            System.out.println(p.toString());
//        }

        return projectsList;
    }

    private boolean dBisEmpty(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_PROJECTS, null);
        return (mCursor.getCount() == 0);
    }
}

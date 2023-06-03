package com.example.taskman.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.taskman.pojo.Task;

import java.util.ArrayList;

public class TaskDatabase extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "taskmandatabase";

    /**
     * Task Table
     */
    public static final String TABLE_TASKS = "task";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ACTIVITY = "activity";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_DUEDATE = "duedate";
    public static final String COLUMN_DUEMONTH = "duemonth";
    public static final String COLUMN_DUEYEAR = "dueyear";
    public static final String COLUMN_COMPLETION = "completion";
    public static final String COLUMN_PICTURE = "picture";
    public static final String COLUMN_USEDEFAULT = "usedefault";

    public static final String CREATE_TASKS_TABLE = "CREATE TABLE " +
            TABLE_TASKS +"("+COLUMN_ID+" INTEGER PRIMARY KEY," +
            COLUMN_ACTIVITY +" TEXT, " + COLUMN_TYPE + " TEXT, " +
            COLUMN_DUEDATE + " INTEGER, " + COLUMN_DUEMONTH + " INTEGER, " +COLUMN_DUEYEAR + " INTEGER, " +
            COLUMN_COMPLETION + " INTEGER, " + COLUMN_PICTURE + " TEXT, " + COLUMN_USEDEFAULT + " INTEGER)";

    public TaskDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Program database upgrade statements
    }


    //CRUD
    //Create Task
    public void addTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues(); //Think bundle
        values.put(COLUMN_ACTIVITY, task.getActivity());
        values.put(COLUMN_TYPE, task.getType());
        values.put(COLUMN_DUEDATE, task.getDueDate());
        values.put(COLUMN_DUEMONTH, task.getDueMonth());
        values.put(COLUMN_DUEYEAR, task.getDueYear());
        values.put(COLUMN_COMPLETION, task.getCompletion());
        values.put(COLUMN_PICTURE, task.getPictureResource());
        values.put(COLUMN_USEDEFAULT, task.getUseDefault());
        db.insert(TABLE_TASKS, null, values);
        db.close();
        Log.d("SQL", "Task Added");
    }

    //Read Task
    public Task getTask(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Task task = null;
        Cursor cursor =
                db.query(TABLE_TASKS, new String[]{COLUMN_ID, COLUMN_ACTIVITY,
                                COLUMN_TYPE, COLUMN_DUEDATE, COLUMN_DUEMONTH, COLUMN_DUEYEAR,
                                COLUMN_COMPLETION, COLUMN_PICTURE, COLUMN_USEDEFAULT},
                        COLUMN_ID + "= ?", new String[]{String.valueOf(id)},
                        null, null, null);  // Result Set ->  0, 1, 2, 3, 4, 5
        if(cursor.moveToFirst()){
            task = new Task(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getInt(6),
                    cursor.getString(7),
                    cursor.getInt(8));
        }
        db.close();
        return task;
    }

    //Read All Tasks
    public ArrayList<Task> getAllTasks(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Task> tasks = new ArrayList<>();
        Cursor cursor =
                db.rawQuery("SELECT * FROM " + TABLE_TASKS, null);  // Result Set ->  0, 1, 2, 3, 4, 5
        while(cursor.moveToNext()){
            tasks.add(new Task(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getInt(6),
                    cursor.getString(7),
                    cursor.getInt(8)));
        }
        db.close();
        return tasks;
    }

    public void updateTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ACTIVITY, task.getActivity());
        values.put(COLUMN_TYPE, task.getType());
        values.put(COLUMN_DUEDATE, task.getDueDate());
        values.put(COLUMN_DUEMONTH, task.getDueMonth());
        values.put(COLUMN_DUEYEAR, task.getDueYear());
        values.put(COLUMN_COMPLETION, task.getCompletion());
        values.put(COLUMN_PICTURE, task.getPictureResource());
        values.put(COLUMN_USEDEFAULT, task.getUseDefault());
        db.update(TABLE_TASKS, values, COLUMN_ID + "=?",
                new String[]{String.valueOf(task.getId())});
        db.close();
    }

    public void deleteTask(int task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, COLUMN_ID + "=?",
                new String[]{String.valueOf(task)});
        db.close();
    }
}

package com.example.taskman.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Task implements Parcelable {
   
    private int id;
    private String activity;
    private String type;
    private int dueDate;
    private int dueMonth;
    private int dueYear;
    private String pictureResource;
    private int completion; // 0 = TO-DO, 1 = IN-PROGRESS, 2 = DONE 

    public Task(String activity, String type, int dueDate, int dueMonth, int dueYear) {
        this.activity = activity;
        this.type = type;
        this.dueDate = dueDate;
        this.dueMonth = dueMonth;
        this.dueYear = dueYear;
        this.completion = 0;
    }

    //DB Read specific
    public Task(int id, String activity, String type, int dueDate, int dueMonth, int dueYear) {
        this.id = id;
        this.activity = activity;
        this.type = type;
        this.dueDate = dueDate;
        this.dueMonth = dueMonth;
        this.dueYear = dueYear;
        this.completion = 0;
    }

    public Task(){

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDueDate() {
        return dueDate;
    }

    public void setDueDate(int dueDate) {
        this.dueDate = dueDate;
    }

    public int getDueMonth() {
        return dueMonth;
    }

    public void setDueMonth(int dueMonth) {
        this.dueMonth = dueMonth;
    }

    public int getDueYear() {
        return dueYear;
    }

    public void setDueYear(int dueYear) {
        this.dueYear = dueYear;
    }

    public int getCompletion() {
        return completion;
    }

    public void setCompletion(int completion) {
        this.completion = completion;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.activity);
        dest.writeString(this.type);
        dest.writeInt(this.dueDate);
        dest.writeInt(this.dueMonth);
        dest.writeInt(this.dueYear);
        dest.writeInt(this.completion);
    }

    protected Task(Parcel in) {
        this.id = in.readInt();
        this.activity= in.readString();
        this.type = in.readString();
        this.dueDate = in.readInt();
        this.dueMonth = in.readInt();
        this.dueYear = in.readInt();
        this.completion = in.readInt();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}

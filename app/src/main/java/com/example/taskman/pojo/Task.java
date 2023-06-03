package com.example.taskman.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable {
   
    private int id;
    private String activity;
    private String type;
    private int dueDate;
    private int dueMonth;
    private int dueYear;
    private int completion; // 0 = TO-DO, 1 = IN-PROGRESS, 2 = DONE


    private String pictureResource;
    private int useDefaultPic = 1; // 1 = true, 0 = false

    public Task(String activity, String type, int dueDate, int dueMonth, int dueYear) {
        this.activity = activity;
        this.type = type;
        this.dueDate = dueDate;
        this.dueMonth = dueMonth;
        this.dueYear = dueYear;
        this.completion = 0;

        switch (type){
            case "educational":
                this.pictureResource = "https://images.unsplash.com/photo-1532012197267-da84d127e765?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1887&q=80";
                break;
            case "recreation":
                this.pictureResource = "https://images.unsplash.com/photo-1605050825077-289f85b6cf43?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80";
                break;
            case "social":
                this.pictureResource = "https://images.unsplash.com/photo-1543269865-cbf427effbad?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80";
                break;
            case "diy":
                this.pictureResource = "https://images.unsplash.com/photo-1595814433015-e6f5ce69614e?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80";
                break;
            case "charity":
                this.pictureResource = "https://images.unsplash.com/photo-1532629345422-7515f3d16bb6?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80";
                break;
            case "cooking":
                this.pictureResource = "https://images.unsplash.com/photo-1506368249639-73a05d6f6488?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=387&q=80";
                break;
            case "relaxation":
                this.pictureResource = "https://images.unsplash.com/photo-1520809227329-2f94844a9635?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80";
                break;
            case "music":
                this.pictureResource = "https://images.unsplash.com/photo-1510915361894-db8b60106cb1?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80";
                break;
            case "busywork":
            default:
                this.pictureResource = "https://images.unsplash.com/photo-1512758017271-d7b84c2113f1?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80";
        }
    }

    //DB Read specific
    public Task(int id, String activity, String type, int dueDate, int dueMonth, int dueYear, int completion, String pictureResource, int useDefaultPic) {
        this.id = id;
        this.activity = activity;
        this.type = type;
        this.dueDate = dueDate;
        this.dueMonth = dueMonth;
        this.dueYear = dueYear;
        this.completion = completion;
        this.pictureResource = pictureResource;
        this.useDefaultPic = useDefaultPic;
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

    public String getPictureResource() {
        return pictureResource;
    }

    public void setPictureResource(String pictureResource) {
        this.pictureResource = pictureResource;
    }
    public int getUseDefault() {
        return useDefaultPic;
    }

    public void setUseDefaultPic(int useDefaultPic) {
        this.useDefaultPic = useDefaultPic;
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

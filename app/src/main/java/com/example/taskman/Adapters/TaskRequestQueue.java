package com.example.taskman.Adapters;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class TaskRequestQueue {

    public static TaskRequestQueue instance;
    private RequestQueue requestQueue;
    private static Context context;

    private TaskRequestQueue(Context context){
        this.context = context;
    }

    public static TaskRequestQueue getInstance(Context context){
        if(instance == null){
            instance = new TaskRequestQueue(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }
}

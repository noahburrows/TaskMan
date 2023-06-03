package com.example.taskman.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskman.R;
import com.example.taskman.pojo.Task;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class CustomTaskAdapter extends RecyclerView.Adapter<CustomTaskAdapter.CustomViewHolder> {

    private ArrayList<Task> tasks;
    private Context context;

    public CustomTaskAdapter(ArrayList<Task> tasks, Context context){
        this.tasks = tasks;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_list_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.activity.setText(task.getActivity());
        holder.type.setText("Type: " + task.getType());
        int completion = task.getCompletion();
        if (completion == 1){
            holder.taskbox.setStrokeColor(ContextCompat.getColor(this.context, R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        if(tasks != null){
            return tasks.size();
        }
        return 0;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{
        protected TextView activity;
        protected TextView type;
        protected MaterialCardView taskbox;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.activity = itemView.findViewById(R.id.activity);
            this.type = itemView.findViewById(R.id.type);
            this.taskbox = itemView.findViewById(R.id.taskbox);
        }
    }
}

package com.example.taskman.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskman.Database.TaskDatabase;
import com.example.taskman.Fragments.CreateUpdateFragment;
import com.example.taskman.R;
import com.example.taskman.pojo.Task;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

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
        Picasso.get().load(task.getPictureResource()).placeholder(R.drawable.ic_menu_camera).into(holder.taskImage);
        System.out.println(task.getPictureResource());
        int completion = task.getCompletion();
        if (completion == 1){
            holder.taskbox.setStrokeColor(ContextCompat.getColor(this.context, R.color.black));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                extra.putInt(CreateUpdateFragment.ACTION_TYPE,
                        CreateUpdateFragment.UPDATE);
                extra.putParcelable(CreateUpdateFragment.TASK,
                        tasks.get(holder.getAdapterPosition()));
                Navigation.findNavController(v).navigate(R.id.nav_create_update,
                        extra);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(tasks != null){
            return tasks.size();
        }
        return 0;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        protected TextView activity;
        protected TextView type;
        protected MaterialCardView taskbox;
        protected ImageView taskImage;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.activity = itemView.findViewById(R.id.activity);
            this.type = itemView.findViewById(R.id.type);
            this.taskbox = itemView.findViewById(R.id.taskbox);
            this.taskImage = itemView.findViewById(R.id.taskImage);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            new AlertDialog.Builder(context)
                    .setTitle("Delete")
                    .setMessage("Are you sure you want to delete this task?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            TaskDatabase db = new TaskDatabase(context);
                            db.deleteTask(tasks.get(getLayoutPosition()).getId());
                            tasks.remove(getLayoutPosition());
                            notifyItemRemoved(getAdapterPosition());
                            db.close();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
            return false;
        }
    }
}

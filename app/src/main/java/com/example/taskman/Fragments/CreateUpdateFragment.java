package com.example.taskman.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.taskman.Database.TaskDatabase;
import com.example.taskman.R;
import com.example.taskman.pojo.Task;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateUpdateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateUpdateFragment extends Fragment {

    /**
     * These constants will be used to signal the fragment
     * UPDATE - for updating tasks
     * CREATE - for creating tasks
     */
    public static final int UPDATE = 1;
    public static final int CREATE = 2;
    Task task;

    /*
        labels used for the bundle name/value pairs
     */
    public static final String TASK = "task";
    public static final String ACTION_TYPE = "action_type";

    public CreateUpdateFragment() {
        // Required empty public constructor
    }

    public static CreateUpdateFragment newInstance() {
        CreateUpdateFragment fragment = new CreateUpdateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_update, container, false);
        EditText taskTitle = view.findViewById(R.id.taskTitle);
        EditText taskType = view.findViewById(R.id.taskType);
        Button submit = view.findViewById(R.id.submitButton);

        //if we have a bundle
        if(getArguments() != null){
            //if the user wants to update a task
            if(getArguments().getInt(ACTION_TYPE) == UPDATE){
                task = getArguments().getParcelable(TASK);
                submit.setText("Update Task");
                if(task != null){
                    //Populate the edit text fields on the screen with the task's values
                    taskTitle.setText(task.getActivity());
                    taskType.setText(task.getType());
                }
            }
            else{
                task = new Task();
                submit.setText("Create Task");
            }
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    task.setActivity(taskTitle.getText().toString());
                    task.setType(taskType.getText().toString());
                    task.setDueDate(2);
                    task.setDueMonth(3);
                    task.setDueYear(4);
                    //TODO dismiss keyboard
                    if(view.requestFocus()){
                        InputMethodManager inputMethodManager = (InputMethodManager)
                                getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
                    }

                    TaskDatabase db = new TaskDatabase(getContext());
                    if(getArguments().getInt(ACTION_TYPE) == UPDATE){
                        db.updateTask(task);
                    }
                    else if(getArguments().getInt(ACTION_TYPE) == CREATE){
                        db.addTask(task);
                    }
                    db.close();
                    //TODO move back
                    Navigation.findNavController(view).popBackStack();
                }
            });
        }


        return view;    }
}
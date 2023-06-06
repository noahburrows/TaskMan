package com.example.taskman.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taskman.Database.TaskDatabase;
import com.example.taskman.R;
import com.example.taskman.pojo.Task;

import java.util.Calendar;

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
    public static final int PIC_CODE = 1235;
    Task task;
    ImageView selectImage;

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
        TextView dueDate = view.findViewById(R.id.dueDate);
        Button dueDateButton = view.findViewById(R.id.dueDateButton);
        Button reminderButton = view.findViewById(R.id.reminderButton);

        selectImage = view.findViewById(R.id.taskImageSelect);

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, PIC_CODE);
            }
        });

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
                    if(task.getDueDate()!=-1){
                        dueDate.setText(task.getDueDate()+"/"+(task.getDueMonth()+1)+"/"+task.getDueYear());
                        reminderButton.setEnabled(true);
                    }
                }
            }
            else{
                task = new Task();
                task.setDueDate(-1);
                task.setDueMonth(-1);
                task.setDueYear(-1);
                submit.setText("Create Task");
            }

            dueDateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar c = Calendar.getInstance();

                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int day = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            getContext(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    dueDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                    task.setDueDate(dayOfMonth);
                                    task.setDueMonth(monthOfYear);
                                    task.setDueYear(year);
                                    reminderButton.setEnabled(true);
                                }
                            },
                            year, month, day);
                    datePickerDialog.show();
                }
            });

            reminderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar beginTime = Calendar.getInstance();
                    beginTime.set(task.getDueYear(), task.getDueMonth(), task.getDueDate(), 7, 30);

                    //Calendar endTime = Calendar.getInstance();
                    //endTime.set(2012, 0, 19, 8, 30);

                    Intent intent = new Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)
                            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                                    beginTime.getTimeInMillis())
                            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                                    beginTime.getTimeInMillis())
                            .putExtra(CalendarContract.Events.ALL_DAY, true)
                            .putExtra(CalendarContract.Events.TITLE, task.getActivity());

                    startActivity(intent);
                }
            });

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(taskTitle.getText().toString()=="" || taskType.getText().toString()==""
                    ||taskTitle.getText().toString().isEmpty() || taskType.getText().toString().isEmpty()) {
                        new AlertDialog.Builder(getContext())
                                .setTitle("Error")
                                .setMessage("Please give your task a name and a type.")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton("OK", null)
                                .show();
                        return;
                    }
                    task.setActivity(taskTitle.getText().toString());
                    task.setType(taskType.getText().toString());

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


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Match the request 'pic id with requestCode
        if (requestCode == PIC_CODE) {
            // BitMap is data structure of image file which store the image in memory
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            // Set the image in imageview for display
            selectImage.setImageBitmap(photo);
            task.setPictureResource(photo.toString());
            task.setUseDefaultPic(0);
        }
    }
}
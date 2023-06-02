package com.example.taskman.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskman.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AboutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutFragment newInstance(String param1, String param2) {
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_about, container, false);
        ViewPager2 viewPager2 = view.findViewById(R.id.aboutViewPager);
        viewPager2.setAdapter(new AboutFragment.CustomViewPager2Adapter(getActivity()));
        viewPager2.setPageTransformer(new DepthPageTransformer());
        return view;
    }

    private class CustomViewPager2Adapter extends FragmentStateAdapter {

        public CustomViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        //Add the four characters to the potential fragments.
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    return AboutPageFragment.newInstance("About TaskMan", "TaskMan is an app designed to let you keep track of all the things" +
                            " you need to get done, and more!\n" +
                            "Set goals for yourself, find new goals, and brag about them!", R.drawable.task_man_logo);
                case 1:
                    return AboutPageFragment.newInstance("What is a task?", "A task can be anything you want to get done.\n" +
                            "Tasks can include details about the type of task you wish to complete (Educational, Recreational, etc.) as well as" +
                            " information about due dates and completion status.", R.drawable.task_man_logo);
                case 2:
                    return AboutPageFragment.newInstance("Adding Tasks", "To create a new task, tap the + button in the bottom corner of the tasks page. " +
                            "This will take you to a form that allows you to fill in all the required information regarding your task. " +
                            "You also have the option of adding a picture to a task. If no picture is taken, a default picture will be used instead.", R.drawable.ic_baseline_add_24);
                case 3:
                    return AboutPageFragment.newInstance("Updating Tasks", "To update or change a task after it is completed, simply tap that task " +
                            "from the task page to re-open it. From here you are free to change anything about the task, including its completion status. " +
                            "\nTasks that are complete become green, tasks that are still ongoing are yellow, and tasks that haven't been started yet " +
                            "are grey.", R.drawable.ic_baseline_edit_24);
                case 4:
                    return AboutPageFragment.newInstance("Using the Calendar", "When viewing a task, you can tap the calendar icon next to the " +
                            "due date to create a reminder in your phone's calendar.", R.drawable.ic_baseline_calendar_24);
                case 5:
                    return AboutPageFragment.newInstance("Posting to Social Media", "After completing a task, a button will appear that allows you " +
                            "to post about that task to Reddit. Tap that button to open up your internet browser and create your post!", R.drawable.reddit_icon);
                case 6:
                    return AboutPageFragment.newInstance("Task Randomizer", "Not sure what task you want to get started with today? Check out " +
                            "the task randomizer! This page allows you to generate random ideas for things to do to stave off the boredom. " +
                            "You can refine the types of things it suggests by visiting the settings page.", R.drawable.ic_baseline_idea_24);
                default:
                    return AboutPageFragment.newInstance("About", "Details", R.drawable.ic_baseline_add_24);
            }

        }

        //Pages:
        // About the app, what is a task, adding tasks, updating tasks, using the calendar, posting to reddit, task randomizer
        @Override
        public int getItemCount() {
            return 7;
        }

    }

    @RequiresApi(21)
    public class DepthPageTransformer implements ViewPager2.PageTransformer {
        private static final float MIN_SCALE = 0.75f;
        private static final float MIN_ALPHA = 0.15f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1f);
                view.setTranslationX(0f);
                view.setTranslationZ(0f);
                view.setScaleX(1f);
                view.setScaleY(1f);

            } else if (position < 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position*(1-MIN_ALPHA));

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);
                // Move it behind the left page
                view.setTranslationZ(-1f);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }
}
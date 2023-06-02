package com.example.taskman.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taskman.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutPageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int mParam3;

    public AboutPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutPageFragment newInstance(String param1, String param2, int param3) {
        AboutPageFragment fragment = new AboutPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putInt(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getInt(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_page, container, false);
        if(mParam1 != null) {
            TextView name = view.findViewById(R.id.aboutName);
            name.setText(mParam1);
        }
        if(mParam2 != null){
            TextView charDescription = view.findViewById(R.id.aboutDescription);
            charDescription.setText(mParam2);
        }
        if(mParam3 != 0){
            ImageView charImage = view.findViewById(R.id.aboutImage);
            charImage.setImageResource(mParam3);
        }
        return view;
    }
}
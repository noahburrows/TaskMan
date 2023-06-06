package com.example.taskman.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.taskman.Adapters.TaskRequestQueue;
import com.example.taskman.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RandomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RandomFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RandomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RandomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RandomFragment newInstance(String param1, String param2) {
        RandomFragment fragment = new RandomFragment();
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
        View view =  inflater.inflate(R.layout.fragment_random, container, false);

        TextView taskName = view.findViewById(R.id.randomTaskName);
        TextView taskType = view.findViewById(R.id.randomTaskType);
        TextView taskPrice = view.findViewById(R.id.randomTaskPrice);
        TextView taskDifficulty = view.findViewById(R.id.randomTaskDifficulty);

        String url = "https://www.boredapi.com/api/activity/";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject mainObject) {
                try {
                    //No main object needed for Bored API, JSON is direct
                    //JSONObject mainObject = response.getJSONObject("main");
                    taskName.setText(mainObject.getString("activity"));
                    taskType.setText(mainObject.getString("type"));
                    if(mainObject.getDouble("price")==0){
                        taskPrice.setText("Free!");
                    } else {
                        taskPrice.setText((int) (mainObject.getDouble("price") * 10) + "/10");
                    }
                    taskDifficulty.setText((int)(mainObject.getDouble("accessibility")*10)+"/10");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        TaskRequestQueue.getInstance(getContext()).getRequestQueue().add(request);

        return view;
    }
}
package com.example.taskman.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.taskman.Adapters.TaskRequestQueue;
import com.example.taskman.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

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

        ImageView taskImage = view.findViewById(R.id.taskImageRandom);

        Button refresh = view.findViewById(R.id.refreshButton);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences( this.getContext());

        String url = "https://www.boredapi.com/api/activity?maxprice="+(sharedPref.getInt("Budget",5)/10.0)+"&maxaccessibility="+(sharedPref.getInt("Difficulty",5)/10.0);

        String typePref = sharedPref.getString("Type", "All");
        if(typePref!="All"){
            url = url+"&type="+typePref;
        }

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
                        taskPrice.setText(Math.round(mainObject.getDouble("price") * 100)/10.0 + "/10");
                    }
                    taskDifficulty.setText(Math.round(mainObject.getDouble("accessibility")*100)/10.0 + "/10");

                    String pictureResource;
                    switch (mainObject.getString("type")){
                        case "education":
                            pictureResource = "https://images.unsplash.com/photo-1532012197267-da84d127e765";
                            break;
                        case "recreational":
                            pictureResource = "https://images.unsplash.com/photo-1605050825077-289f85b6cf43";
                            break;
                        case "social":
                            pictureResource = "https://images.unsplash.com/photo-1543269865-cbf427effbad";
                            break;
                        case "diy":
                            pictureResource = "https://images.unsplash.com/photo-1595814433015-e6f5ce69614e";
                            break;
                        case "charity":
                            pictureResource = "https://images.unsplash.com/photo-1532629345422-7515f3d16bb6";
                            break;
                        case "cooking":
                            pictureResource = "https://images.unsplash.com/photo-1506368249639-73a05d6f6488";
                            break;
                        case "relaxation":
                            pictureResource = "https://images.unsplash.com/photo-1520809227329-2f94844a9635";
                            break;
                        case "music":
                            pictureResource = "https://images.unsplash.com/photo-1510915361894-db8b60106cb1";
                            break;
                        case "busywork":
                        default:
                            pictureResource = "https://images.unsplash.com/photo-1512758017271-d7b84c2113f1";
                    }
                    Picasso.get().load(pictureResource).resize(250, 120).centerCrop().placeholder(R.drawable.ic_menu_camera).into(taskImage);

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

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskRequestQueue.getInstance(getContext()).getRequestQueue().add(request);
            }
        });

        return view;
    }
}
package com.example.taskman.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taskman.R;


public class CreditsFragment extends Fragment {

    public CreditsFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_credits, container, false);

        TextView imageLink = view.findViewById(R.id.provider);
        TextView apiLink = view.findViewById(R.id.api);
        TextView supportLink = view.findViewById(R.id.support);


        imageLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse("https://unsplash.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        apiLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse("https://www.boredapi.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        supportLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // Only email apps handle this.
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"w0772238@myscc.ca"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "TaskMan Support");
                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });


        return view;
    }
}
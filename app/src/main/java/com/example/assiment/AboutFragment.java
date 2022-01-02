package com.example.assiment;


import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.VideoView;


public class AboutFragment extends Fragment {

    CardView aboutus , aboutErbil , video;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);


        aboutErbil = view.findViewById(R.id.abouterbil);
        aboutus = view.findViewById(R.id.aboutus);
        video = view.findViewById(R.id.erbilvideo);

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext() , AboutUS.class);
                startActivity(intent);
            }
        });

        aboutErbil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext() , AboutErbil.class);
                startActivity(intent);
            }
        });

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext() , VideoErbil.class);
                startActivity(intent);
            }
        });

        return view;
    }
}

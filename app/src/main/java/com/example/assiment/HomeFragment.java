package com.example.assiment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;


public class HomeFragment extends Fragment {

    ViewFlipper viewFlipper;
    Button btnErbil , AddIteam;

    String admin = "";
    boolean check = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_home, container, false);

        btnErbil = view.findViewById(R.id.erbil);
        int img[] = {R.drawable.erbil , R.drawable.b,R.drawable.erbil3,R.drawable.erbil4 , R.drawable.k};

        btnErbil = view.findViewById(R.id.erbil);
        AddIteam = view.findViewById(R.id.AddIteam);
        viewFlipper = view.findViewById(R.id.images);

        for (int i = 0 ; i < img.length;i++){
            flippImage(img[i]);
        }

        final Shared shared = new Shared(getContext());
        if (shared.getUser() != null) {
            admin = shared.getUser();
        }


        btnErbil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext() , Main2Activity.class);
                intent.putExtra("message" , shared.getUser());
                startActivity(intent);

            }
        });


        if (!(admin.equals("ziad") || admin.equals("saad") || admin.equals("amen")|| admin.equals("umer") || admin.equals("uniys"))){
            AddIteam.setVisibility(AddIteam.INVISIBLE);
            if (check == false && !admin.equals("nothing")) {
                Toast.makeText(view.getContext(), " Welcome user " + admin, Toast.LENGTH_SHORT).show();
                check = true;
            }
        }
        else {
            if (check == false) {
                Toast.makeText(view.getContext(), "Welcome Admin " + admin, Toast.LENGTH_SHORT).show();
                check = true;
            }
        }

        AddIteam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext() , AddPlace.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void flippImage(int image){
        ImageView imageView = new ImageView(getActivity());
        imageView.setBackgroundResource(image);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);

        viewFlipper.setInAnimation(getActivity() , android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(getActivity() , android.R.anim.slide_out_right);
    }
}

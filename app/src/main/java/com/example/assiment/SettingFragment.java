package com.example.assiment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    public SettingFragment() {
        // Required empty public constructor
    }

    Button changepassword , apply;
    RadioGroup radioGroup;
    RadioButton buttons;
    Switch switchcolor;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        loadLocal();

       changepassword = view.findViewById(R.id.changePass);
        apply = view.findViewById(R.id.apply);
        radioGroup = view.findViewById(R.id.group);
        switchcolor = view.findViewById(R.id.switchColor);
        final Shared shared = new Shared(getContext());
        if (shared.getMode()){
            switchcolor.setChecked(true);
        }
        switchcolor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 shared.setMode(isChecked);
            }
        });


       changepassword.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getContext() , ChangePassword.class);
               startActivity(intent);
           }
       });


       apply.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Shared shared = new Shared(getContext());
               int radioId = radioGroup.getCheckedRadioButtonId();
               buttons = view.findViewById(radioId);
               switch (radioId){
                   case R.id.kurdish:
                       setLanguage("ku");
                       Toast.makeText(getContext() , ""+getResources().getString(R.string.Kurdish) , Toast.LENGTH_SHORT).show();
                       break;
                   case R.id.english:
                       setLanguage("en");
                       Toast.makeText(getContext() , ""+getResources().getString(R.string.English) , Toast.LENGTH_SHORT).show();
                       break;
                   case R.id.arabic:
                       setLanguage("ar");
                       Toast.makeText(getContext() , ""+getResources().getString(R.string.Arabic) , Toast.LENGTH_SHORT).show();
                       break;
               }
               getActivity().recreate();
           }
       });


       return view;
    }

    public void setLanguage(String lan){
        Locale locale = new Locale(lan);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getContext().getResources().updateConfiguration(config,getContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getContext().getSharedPreferences("setting", Context.MODE_PRIVATE). edit();
        editor.putString("My Lang", lan);
        editor.apply();
    }

    private void loadLocal(){
        SharedPreferences preferences = getContext().getSharedPreferences("setting",Context.MODE_PRIVATE);
        String language = preferences.getString("My Lang", "");
        setLanguage(language);
    }

}

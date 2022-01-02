package com.example.assiment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import java.util.TreeMap;

public class Shared {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    final boolean lang = false;
    String La = "M";

    int mode  = 0;
    String FileName = "sdfile";
    String Data = "b";
    String profile = "p";
    String name;
    String email;

    public Shared(Context context){
        this.context =  context;
        sharedPreferences = context.getSharedPreferences(FileName , mode);
        editor = sharedPreferences.edit();
    }

    public void setMode(Boolean lang){
        editor.putBoolean(La , lang);
        editor.commit();
    }


    public Boolean getMode(){
        return sharedPreferences.getBoolean(La , false);
    }

    public void setUser(String name){
        editor.putString(profile , name);
        editor.commit();
    }

    public String getUser(){
        return sharedPreferences.getString(profile , name);
    }

    public String getEmail() {
        return sharedPreferences.getString("email" , email);
    }

    public void setEmail(String email) {
        editor.putString("email" , email);
        editor.commit();
    }

    public void secondTime(){
        editor.putBoolean(Data , true);
        editor.commit();
    }
    public void trigActivity(){
        editor.putBoolean(Data , false);
        editor.putString(profile , "nothing");
        editor.commit();
    }

    public void firstTime(){
        if (!this.login()){
            Intent intent = new Intent(context , LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }
    }

    public boolean login(){
        return sharedPreferences.getBoolean(Data , false);
    }
}

package com.example.assiment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import java.util.Locale;

public class AboutErbil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Shared shared = new Shared(getApplicationContext());
        boolean mode = shared.getMode();
        loadLocal();
        if (mode == true){
            setTheme(R.style.darkTheme);
        }
        else {
            setTheme(R.style.AppTheme);
        }
        setContentView(R.layout.activity_about_erbil);

        this.setTitle(getResources().getString(R.string.aboutERBIL));
    }
    public void setLanguage(String lan){
        Locale locale = new Locale(lan);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("setting",MODE_PRIVATE). edit();
        editor.putString("My Lang", lan);
        editor.apply();
    }

    public void loadLocal(){
        SharedPreferences preferences = getSharedPreferences("setting",MODE_PRIVATE);
        String language = preferences.getString("My Lang", "");
        setLanguage(language);
    }
}

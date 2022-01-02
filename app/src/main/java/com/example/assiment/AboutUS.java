package com.example.assiment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class AboutUS extends AppCompatActivity {

    Button saad , ziad, amen ,yunis, umer;

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
        setContentView(R.layout.activity_about_u_s);

        this.setTitle(getResources().getString(R.string.aboutUS));

        saad = findViewById(R.id.saad);
        ziad = findViewById(R.id.ziad);
        amen = findViewById(R.id.amen);
        yunis = findViewById(R.id.ynis);
        umer = findViewById(R.id.umer);

        saad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFacebook("100005524155358");
            }
        });
        ziad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFacebook("100033580870763");
            }
        });
        amen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFacebook("amen.gardi.9");
            }
        });
        yunis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFacebook("yunis.muhamad.5");
            }
        });
        umer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFacebook("100015478590680");
            }
        });

    }
    public void goToFacebook(String id){
        try {
            Uri uri = Uri.parse("fb://page/" + id);
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);
        }
        catch (ActivityNotFoundException e){
            Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.facebook.com/" + id));
            startActivity(intent);
        }
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

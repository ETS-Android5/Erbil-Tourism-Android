package com.example.assiment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

public class Main2Activity extends AppCompatActivity {

    Button hotel , resturant , touresm , shop;

    public static SQLiteHelper sqLiteHelper;

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
        setContentView(R.layout.activity_main2);
        final String string =  getIntent().getStringExtra("message");

        this.setTitle(getResources().getString(R.string.listPlace));

        hotel = findViewById(R.id.hotel);
        resturant = findViewById(R.id.resturant);
        touresm = findViewById(R.id.touresm);
        shop = findViewById(R.id.shop);


        hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this , HotelList.class);
                intent.putExtra("place" , string);
                startActivity(intent);
            }
        });


        resturant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this , ResturantList.class);
                intent.putExtra("place" , string);
                startActivity(intent);
            }
        });

        touresm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this , TourismList.class);
                intent.putExtra("place" , string);
                startActivity(intent);
            }
        });

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this , ShopList.class);
                intent.putExtra("place" , string);
                startActivity(intent);
            }
        });

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

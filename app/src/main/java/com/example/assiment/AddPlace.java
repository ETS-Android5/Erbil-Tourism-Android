package com.example.assiment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Locale;

public class AddPlace extends AppCompatActivity {

    EditText edtName, edtPlace , idFB , cor1 , cor2;
    Button addHotel, addResturant , addTourism , addShop , btnimage;
    ImageView imageView;

    final int REQUEST_CODE_GALLERY = 999;

    public static MainActivity  MainApp;


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
            setTheme(R.style .AppTheme);
        }
        setContentView(R.layout.activity_add_place);

        this.setTitle(getResources().getString(R.string.AddIteam));

        addHotel = findViewById(R.id.addhotel);
        addResturant = findViewById(R.id.addresturant);
        addTourism = findViewById(R.id.addtourism);
        addShop = findViewById(R.id.addshop);
        imageView = findViewById(R.id.imageView);
        edtName = findViewById(R.id.edtName);
        edtPlace = findViewById(R.id.edtPrice);
        idFB = findViewById(R.id.id);
        cor1 = findViewById(R.id.cor1);
        cor2 = findViewById(R.id.cor2);

        btnimage = findViewById(R.id.btnChoose);

        btnimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        AddPlace.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });


        addHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    MainApp.sqLiteHelper.insertHotel(
                            edtName.getText().toString().trim(),
                            edtPlace.getText().toString().trim(),
                            imageViewToByte(imageView),
                             idFB.getText().toString().trim(),
                            cor1.getText().toString().trim(),
                            cor2.getText().toString().trim()
                    );
                    Toast.makeText(getApplicationContext(), ""+getResources().getString(R.string.added), Toast.LENGTH_SHORT).show();
                    edtName.setText("");
                    edtPlace.setText("");
                    idFB.setText("");
                    cor1.setText("");
                    cor2.setText("");
                    imageView.setImageResource(R.mipmap.ic_launcher);
                }
                catch (Exception e){
                    Toast.makeText(AddPlace.this , "one filled is empaty" ,Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        addResturant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    MainApp.sqLiteHelper.insertResturant(
                            edtName.getText().toString().trim(),
                            edtPlace.getText().toString().trim(),
                            imageViewToByte(imageView),
                            idFB.getText().toString().trim()
                            ,cor1.getText().toString().trim(),
                            cor2.getText().toString().trim()
                    );
                    Toast.makeText(getApplicationContext(), ""+getResources().getString(R.string.added), Toast.LENGTH_SHORT).show();
                    edtName.setText("");
                    edtPlace.setText("");
                    idFB.setText("");
                    cor1.setText("");
                    cor2.setText("");
                    imageView.setImageResource(R.mipmap.ic_launcher);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        addTourism.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                   MainApp.sqLiteHelper.insertTourism(
                            edtName.getText().toString().trim(),
                            edtPlace.getText().toString().trim(),
                            imageViewToByte(imageView),
                            idFB.getText().toString().trim()
                           ,cor1.getText().toString().trim(),
                           cor2.getText().toString().trim()
                    );
                    Toast.makeText(getApplicationContext(), ""+getResources().getString(R.string.added), Toast.LENGTH_SHORT).show();
                    edtName.setText("");
                    edtPlace.setText("");
                    cor1.setText("");
                    cor2.setText("");
                    idFB.setText("");
                    imageView.setImageResource(R.mipmap.ic_launcher);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        addShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                   MainApp.sqLiteHelper.insertShop(
                            edtName.getText().toString().trim(),
                            edtPlace.getText().toString().trim(),
                            imageViewToByte(imageView),
                            idFB.getText().toString().trim()
                           ,cor1.getText().toString().trim(),
                           cor2.getText().toString().trim()
                    );
                    Toast.makeText(getApplicationContext(), ""+getResources().getString(R.string.added), Toast.LENGTH_SHORT).show();
                    edtName.setText("");
                    edtPlace.setText("");
                    idFB.setText("");
                    cor1.setText("");
                    cor2.setText("");
                    imageView.setImageResource(R.mipmap.ic_launcher);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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

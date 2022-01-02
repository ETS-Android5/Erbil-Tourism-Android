package com.example.assiment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    Button login , signup ;
    EditText namelogin , passwordlogin;
    TextView txterror ,textView;
    public boolean checkuser = false;
    public boolean checkpassword = false ;
    Switch switchcolor;
    String activity = "";

    final String ziad = "ziad";
    final String passwordZiad = "ziad1234";
    final String emailz = "ziad.assad.10@gmail.com";
    final String saad = "saad";
    final String passwordsaad = "saad1234";
    final String emails = "saad.masood.saad@gmail.com ";
    final String uniys = "uniys";
    final String passworduniys = "uniys1234";
    final String emaily = "Yunissyan11@gmail.com";
    final String amin = "amen";
    final String passwordamen = "amen1234";
    final String emaila = "amingardi1999@gmail.com";
    final String umer = "umer";
    final String passwordumer = "umer1234";
    final String emailu = "umershaqlawa7@gmail.com";

    public static SQLiteHelper sqLiteHelper;

    RelativeLayout relativeLayout;
    boolean mode;

    String name = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocal();
        final Shared shared = new Shared(getApplicationContext());
        mode = shared.getMode();

        if (mode == true){
            setTheme(R.style.darkTheme);
        }
        else {
            setTheme(R.style.AppTheme);
        }
        setContentView(R.layout.activity_main);

        this.setTitle(getResources().getString(R.string.login));

        login = findViewById(R.id.ligin);
        signup = findViewById(R.id.signup);
        namelogin = findViewById(R.id.namelogin);
        passwordlogin = findViewById(R.id.passwordlogin);
        txterror = findViewById(R.id.error);
        switchcolor = findViewById(R.id.switchColor2);
        relativeLayout = findViewById(R.id.logback);

        if (mode){
            switchcolor.setChecked(true);
            relativeLayout.setBackgroundResource(R.drawable.darkback);
        }

        switchcolor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                shared.setMode(isChecked);
                recreate();
            }
        });


        if (shared.login()){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("z" , activity);
            startActivity(intent);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = namelogin.getText().toString();
                final String password = passwordlogin.getText().toString();
                switch (name){
                    case ziad:
                        shared.setEmail(emailz);
                        break;
                    case uniys:
                        shared.setEmail(emaily);
                        break;
                    case umer:
                        shared.setEmail(emailu);
                        break;
                    case saad:
                        shared.setEmail(emails);
                        break;
                    case amin:
                        shared.setEmail(emaila);
                        break;
                }

                if (name.equals(ziad) || name.equals(saad) || amin.equals(name) || umer.equals(name)|| uniys.equals(name)){
                    checkuser = true;
                    if (passwordZiad.equals(password) || passwordamen.equals(password) || passwordsaad.equals(password) || passwordumer.equals(password) || passworduniys.equals(uniys)){
                        checkpassword = true;
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        activity = "admin";
                        intent.putExtra("z" , activity);
                        shared.secondTime();
                        shared.setUser(name);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        startActivity(intent);
                        finish();
                    }
                }
                else {
                    Cursor c = MainActivity.sqLiteHelper.getData("SELECT * FROM account");

                    while (c.moveToNext()) {
                        String username = c.getString(1);
                        if (username.equals(name)) {
                            String pass = c.getString(3);
                            checkuser = true;
                            if (pass.equals(password)) {
                                checkpassword = true;
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                activity = "user";
                                intent.putExtra("z" , activity);
                                shared.secondTime();
                                shared.setUser(name);
                                startActivity(intent);
                                break;
                            }
                        }
                    }
                }
                if (!checkuser){
                        txterror.setText(getResources().getString(R.string.Accounterror));
                        Toast.makeText(LoginActivity.this , ""+getResources().getString(R.string.Accounterror), Toast.LENGTH_SHORT).show();
                }
                else if(!checkpassword){
                    txterror.setText(getResources().getString(R.string.passerror));
                    Toast.makeText(LoginActivity.this , ""+getResources().getString(R.string.passerror), Toast.LENGTH_SHORT).show();
                }
                checkuser = false;
                checkpassword = false;

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this , Signup.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK){
            onBackPressed();
            System.exit(0);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=  getMenuInflater();
        inflater.inflate(R.menu.language,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.En:
                setLanguage("en");
                recreate();
                Toast.makeText(this,"English",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.gone:
                setLanguage("ku");
                recreate();
                Toast.makeText(this,"Kurdish",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.parent:
                setLanguage("ar");
                recreate();
                Toast.makeText(this,"Arabic",Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
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

    private void loadLocal(){
        SharedPreferences preferences = getSharedPreferences("setting", MODE_PRIVATE);
        String language = preferences.getString("My Lang", "");
        setLanguage(language);
    }

}

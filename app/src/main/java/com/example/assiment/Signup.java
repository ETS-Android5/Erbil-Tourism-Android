package com.example.assiment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


public class Signup extends AppCompatActivity {

    EditText username , email , password , Cpasswor;
    Button sign;
    TextView txterror;
    RelativeLayout siginupback;

    public SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocal();
        Shared shared = new Shared(getApplicationContext());
        boolean mode = shared.getMode();

        if (mode == true){

            setTheme(R.style.darkTheme);
        }
        else {
            setTheme(R.style .AppTheme);
        }
        setContentView(R.layout.activity_signup);

        this.setTitle(getResources().getString(R.string.signup));
        sqLiteHelper = new SQLiteHelper(this);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        Cpasswor = findViewById(R.id.cpassword);
        txterror = findViewById(R.id.error2);
        sign = findViewById(R.id.sign);
        siginupback = findViewById(R.id.signupback);

        if (mode == true){
            siginupback.setBackgroundResource(R.drawable.darkback);
        }

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String Email = email.getText().toString();
                String pass1 = password.getText().toString();
                String pass2 = Cpasswor.getText().toString();
                Cursor c = sqLiteHelper.getData("SELECT * FROM account");
                boolean exist = false;
                if (user.equals("saad") || user.equals("ziad")  || user.equals("umer") || user.equals("amen") || user.equals("yunis")){
                    exist = true;
                }
                else {

                    while (c.moveToNext()) {
                        String name = c.getString(1);
                        if (name.equals(user)) {
                            exist = true;
                        }
                    }
                }

                if (exist){
                    Toast.makeText(Signup.this , ""+getResources().getString(R.string.exist) , Toast.LENGTH_SHORT).show();
                    txterror.setText(getResources().getString(R.string.exist));
                }
                else if (user.length() < 4){
                    Toast.makeText(Signup.this, ""+ getResources().getString(R.string.userlength), Toast.LENGTH_SHORT).show();
                    txterror.setText(getResources().getString(R.string.userlength));
                }
                else {
                    if (user.equals("") || Email.equals("") || pass1.equals("") || pass2.equals("")) {
                        Toast.makeText(Signup.this, ""+ getResources().getString(R.string.empty), Toast.LENGTH_SHORT).show();
                        txterror.setText(getResources().getString(R.string.empty));
                    }
                    else if (pass1.length() < 6){
                        Toast.makeText(Signup.this, ""+getResources().getString(R.string.passlength), Toast.LENGTH_SHORT).show();
                        txterror.setText(getResources().getString(R.string.passlength));
                    }
                    else if (pass1.equals(pass2)) {
                        try {
                            MainActivity.sqLiteHelper.Insertaccount(
                                    username.getText().toString().trim(),
                                    email.getText().toString().trim(),
                                    password.getText().toString().trim()
                            );
                            username.setText("");
                            email.setText("");
                            password.setText("");
                            Cpasswor.setText("");
                            txterror.setText("");
                            Toast.makeText(Signup.this, ""+getResources().getString(R.string.added) , Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Signup.this , LoginActivity.class);
                            startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(Signup.this, ""+getResources().getString(R.string.notadded) , Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Signup.this, "" + getResources().getString(R.string.passerror), Toast.LENGTH_SHORT).show();
                    }
                }
                finish();
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
    private void loadLocal(){
        SharedPreferences preferences = getSharedPreferences("setting",MODE_PRIVATE);
        String language = preferences.getString("My Lang", "");
        setLanguage(language);
    }
}

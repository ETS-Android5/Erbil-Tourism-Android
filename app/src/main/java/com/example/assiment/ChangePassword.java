package com.example.assiment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class ChangePassword extends AppCompatActivity {

    EditText oldPass , newPass , conPass;
    Button changPass;
    TextView txterror;

    public SQLiteHelper sqLiteHelper;

    int id;
    String name;
    String email;

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
        setContentView(R.layout.activity_change_password);

        this.setTitle(getResources().getString(R.string.changepass));

        sqLiteHelper = new SQLiteHelper(this);

        oldPass = findViewById(R.id.yourPass);
        newPass = findViewById(R.id.newPass);
        conPass = findViewById(R.id.confirmPass);
        changPass = findViewById(R.id.changePass);
        txterror = findViewById(R.id.txtError);

        final String nameuser = shared.getUser();


        changPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old = oldPass.getText().toString();
                String newP = newPass.getText().toString();
                String CPass = conPass.getText().toString();

                String pass;

                boolean ch = false;

                Cursor c = sqLiteHelper.getData("SELECT * FROM account");
                while (c.moveToNext()){
                     id = c.getInt(0);
                     name = c.getString(1);
                     email = c.getString(2);
                     pass = c.getString(3);
                    if (name.equals(nameuser)){
                        if (old.equals(pass)){
                            ch = true;
                            break;
                        }
                    }
                }

                if (newP.length() < 6 || CPass.length() < 6){
                    Toast.makeText(ChangePassword.this , ""+getResources().getString(R.string.passlength) , Toast.LENGTH_SHORT).show();
                    txterror.setText(getResources().getString(R.string.passlength));
                }
               else if (newP.equals(CPass) && !newP.equals("") && !CPass.equals("") && ch == true){
                    try {

                        sqLiteHelper.UpdateAccount( name , email ,newPass.getText().toString().trim() , id );
                        oldPass.setText("");
                        newPass.setText("");
                        conPass.setText("");
                        txterror.setText("");
                    }
                    catch (Exception e){
                        Toast.makeText(ChangePassword.this , ""+getResources().getString(R.string.notadded) , Toast.LENGTH_SHORT).show();
                        txterror.setText(getResources().getString(R.string.notadded));
                    }
                }
                else {
                    Toast.makeText(ChangePassword.this , ""+getResources().getString(R.string.passerror) , Toast.LENGTH_SHORT).show();
                    txterror.setText(getResources().getString(R.string.passerror));
                }

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
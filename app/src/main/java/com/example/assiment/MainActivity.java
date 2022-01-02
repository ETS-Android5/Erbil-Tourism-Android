package com.example.assiment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.DhcpInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    private ProfileFragment profile;
    private SettingFragment setting;
    private HomeFragment home;
    private AboutFragment about;
    private Map map;
    private BottomNavigationView bottomNavigationView;
    FrameLayout frame;
    private String check;
    public static SQLiteHelper sqLiteHelper;
    public SharedPreferences.Editor editor;
    public String frag = "";
    public Shared shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocal();
         shared = new Shared(getApplicationContext());
        boolean mode = shared.getMode();

        if (mode == true){
            setTheme(R.style.darkTheme);
        }
        else {
            setTheme(R.style .AppTheme);
        }
        setContentView(R.layout.activity_main_app);
        sqLiteHelper = new SQLiteHelper(this);
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS Hotel(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, price VARCHAR, image BLOB , idFB VARCHAR , cor1 VARCHAR , cor2 VARCHAR)");
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS Resturant(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, price VARCHAR, image BLOB , idFB VARCHAR , cor1 VARCHAR , cor2 VARCHAR)");
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS Tourism(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, price VARCHAR, image BLOB , idFB VARCHAR , cor1 VARCHAR , cor2 VARCHAR)");
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS Shop(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, price VARCHAR, image BLOB , idFB VARCHAR , cor1 VARCHAR , cor2 VARCHAR)");
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS account(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, email VARCHAR, password VARCHAR)");

        this.setTitle(getResources().getString(R.string.app_name));

        final String[] nameHotel = getResources().getStringArray(R.array.arrayHotel);
        String[] addressHotel = getResources().getStringArray(R.array.addresHotel);
        int[] imageHotel = {R.drawable.cristal , R.drawable.mryanahotel, R.drawable.rotana, R.drawable.divanerbilhotel, R.drawable.grandplace , R.drawable.jouhayna,  R.drawable.laroch,R.drawable.plaza };
        final String[]  idFBHotel = {"599296466831080","746609945422238","189732581103615","471316699688048","828238530620496","107469667324629",
                "594479320583603","181545812020508" , "" , ""};
        String[] cor1h = {"36.1636046" , "36.1819825" , "36.1871013" , "36.1969667" , "36.2221815" , "36.1988282," , "36.210931" ,"36.208617" ,"0" ,"0"};
        String[] cor2h = {"44.0316362" , "43.9718127" , "43.9720581" , "43.9754015" , "43.9917081" , "43.961218" , "43.9944359" ,"43.9721398" ,"0" ,"0"};

        final String[] nameRest = { getString(R.string.ABC), getString(R.string.salehia), getString(R.string.janna),getString(R.string.flla),getString(R.string.ayam),getString(R.string.PregoRestaurant),
                getString(R.string.rabwah) , getString(R.string.tosty),getString(R.string.hiland),getString(R.string.kababyasin) ,getString(R.string.iceland)};
        final String[] addressRest = getResources().getStringArray(R.array.addresResturant);
        int[] imageRest = {R.drawable.abc2 , R.drawable.saliha , R.drawable.janna , R.drawable.fulla,R.drawable.ayam , R.drawable.prego,R.drawable.rabwah,R.drawable.tosty,R.drawable.hiland
                ,R.drawable.yasin,R.drawable.iceland2};
        final String[] idFBRest = {"524215461045057","1845557222438763","436512470067438","1852536978341659","799411753550740",
                "579651875486561","876082415864849","614041728970429","","108517806532915","827826407245715"};
        String[] cor1r = {"36.2563543" , "36.1658053" , "36.1972142" , "36.2015505" , "36.2094092" , "36.2321645" , "36.1890714" ,"36.1940773" ,"36.1960506" ,"36.224203", "36.1631542"};
        String[] cor2r = {"44.00563" , "44.0258421" , "43.8688785" , "44.0206388 " , "43.9803794" , "43.9952287" , "43.9602133" ,"43.9649297" ,"44.0563549" ,"44.0095967" ,"44.0280592"};

        String[] nameShop = getResources().getStringArray(R.array.arrayShop);
        String[] addressShop =  getResources().getStringArray(R.array.AddressShop);
        int[] imageShop = {R.drawable.royalmol , R.drawable.megamall, R.drawable.familymall,  R.drawable.tablomall, R.drawable.majidimall };
        String[] email = {"https://www.facebook.com/royalmallerbil/", "https://www.facebook.com/Mega-mall-hawler",
                "https://www.facebook.com/family.mall.erbil", "https://www.facebook.com/tablo-mall" ,
                "https://www.facebook.com/majidi-mall"};
        final String[] idFBShop = {"625637300879463","1001863673206481","291082240958434","247599745282671","1523480121234120"};

        String[] cor1s = {"36.2019013" , "36.2077617", "36.2097734" , "36.1713881", "36.1957522" };
        String[] cor2s =  {"44.0177919" , "44.0238557" ,"44.0430663" , "44.0114421" , "44.0623443"};

        String[] nameTourism = getResources().getStringArray(R.array.tourism);
        String[] addressTourism =getResources().getStringArray(R.array.addresstourism);
        int[] imageTourism = {R.drawable.familyfun , R.drawable.castl ,R.drawable.majidiland, R.drawable.mnara,R.drawable.mnarapark,R.drawable.shanadarpark,R.drawable.samipark};
        final String[] idFBTorism = {"" , "", "", "", "", "", "", "", "", "", "", "", "", "", ""};

        String[] cor1t = {"36.2106762" , "36.1934621" , "36.2090179" , "36.1888938" ,"36.1888938" , "36.1894486" , "36.1972754" };
        String[] cor2t = {"44.0471405", "44.0060423" , "44.1412267" , "44.0006377" ,"44.0006377" , "43.9838118" ,"43.938923" };

        Cursor cursor = MainActivity.sqLiteHelper.getData("SELECT * FROM Hotel");
        if (cursor.getCount()  < 8) {
            for (int i =0; i < nameHotel.length ; i++){
                Drawable im = getResources().getDrawable(imageHotel[i]);
                sqLiteHelper.insertHotel(nameHotel[i], addressHotel[i], imageViewToByte(im), idFBHotel[i] , cor1h[i], cor2h[i]);
            }
            for (int i =0; i < nameRest.length ; i++){
                Drawable im = getResources().getDrawable(imageRest[i]);
                sqLiteHelper.insertResturant(nameRest[i], addressRest[i], imageViewToByte(im), idFBRest[i] , cor1r[i] , cor2r[i]);
            }
            for (int i =0; i < nameShop.length ; i++){
                Drawable im = getResources().getDrawable(imageShop[i]);
                sqLiteHelper.insertShop(nameShop[i], addressShop[i], imageViewToByte(im), idFBShop[i] , cor1s[i] , cor2s[i]);
            }
            for (int i =0; i < nameTourism.length ; i++){
                Drawable im = getResources().getDrawable(imageTourism[i]);
                sqLiteHelper.insertTourism(nameTourism[i], addressTourism[i], imageViewToByte(im), idFBTorism[i] , cor1t[i] , cor2t[i]);
            }
        }


        final String messege = getIntent().getStringExtra("z");


        check = messege;

        sqLiteHelper = new SQLiteHelper(this);

        profile = new ProfileFragment();
        setting = new SettingFragment();
        home = new HomeFragment();
        about = new AboutFragment();
        map = new Map();
       bottomNavigationView = findViewById(R.id.naitive);
       frame = findViewById(R.id.frame);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                        setFragment(home);
                        frag = "h";
                        return true;

                    case (R.id.setting):
                        setFragment(setting);
                        frag = "s";
                        return true;

                    case R.id.about:
                        setFragment(about);
                        frag = "a";
                        return true;

                    case R.id.profile:
                        setFragment(profile);
                        frag = "p";
                        return true;

                    case R.id.Map:
                        setFragment(map);
                        frag = "m";
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("fragments" , frag);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        frag = savedInstanceState.getString("fragments");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (frag.equals("h")){
            setFragment(home);
        }
        else if (frag.equals("s")){
            setFragment(setting);
        }
        else if (frag.equals("m")){
            setFragment(map);
        }
        else if (frag.equals("p")){
            setFragment(profile);
        }
        else if (frag.equals("a")){
            setFragment(about);
        }
        else {
            setFragment(home);
        }
    }

    public static byte[] imageViewToByte(Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Shared shared = new Shared(getApplicationContext());
        shared.firstTime();
    }

    public String getString(){
        return check;
    }

    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame , fragment);
        fragmentTransaction.commit();
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

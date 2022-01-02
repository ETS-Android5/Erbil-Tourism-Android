package com.example.assiment;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Quoc Nguyen on 13-Dec-16.
 */

public class ResturantList extends AppCompatActivity {

    ListView gridView;
    ArrayList<Place> list;
    ResturantListAdapter adapter = null;
    String s;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Shared shared = new Shared(getApplicationContext());
        boolean mode = shared.getMode();

        if (mode == true){
            setTheme(R.style.darkTheme);
        }
        else {
            setTheme(R.style .AppTheme);
        }
        setContentView(R.layout.activity_resturant_list);

        this.setTitle(getResources().getString(R.string.listresturant));

        String str =  getIntent().getStringExtra("place");
        if (str.equals("ziad") || str.equals("saad") || str.equals("umer")|| str.equals("ynis")|| str.equals("amen")){
            s = "admin";
        }
        else{s = "user";}



        gridView = findViewById(R.id.gridView);
        list = new ArrayList<>();
        adapter = new ResturantListAdapter(this, R.layout.place_items, list);
        gridView.setAdapter(adapter);

        // get all data from sqlite
        Cursor cursor = MainActivity.sqLiteHelper.getData("SELECT * FROM Resturant");

        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String price = cursor.getString(2);
            byte[] image = cursor.getBlob(3);
            String idFB = cursor.getString(4);

            list.add(new Place(name, price, image, id, idFB));
        }
        adapter.notifyDataSetChanged();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,final int position, long id) {
                if (!s.equals("admin")){
                    CharSequence[] items = { getResources().getString(R.string.openFaceBook),"location" };
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ResturantList.this);

                    dialog.setTitle(getResources().getString(R.string.Choose));

                    dialog.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            if (item == 0) {
                                Cursor c = MainActivity.sqLiteHelper.getData("SELECT idFB FROM Resturant");
                                ArrayList<String> arrID = new ArrayList<String>();
                                while (c.moveToNext()) {
                                    arrID.add(c.getString(0));
                                }
                                openFacebook(arrID.get(position));
                            }
                            if (item == 1) {
                                Cursor c = MainActivity.sqLiteHelper.getData("SELECT cor1 FROM Resturant");
                                Cursor c2 = MainActivity.sqLiteHelper.getData("SELECT cor2 FROM Resturant");
                                Cursor name = MainActivity.sqLiteHelper.getData("SELECT name FROM Resturant");
                                ArrayList<String> arrID = new ArrayList<String>();
                                ArrayList<String> arrID2 = new ArrayList<String>();
                                ArrayList<String> arrID3 = new ArrayList<String>();
                                while (c.moveToNext() && c2.moveToNext() && name.moveToNext()) {
                                    arrID.add(c.getString(0));
                                    arrID2.add(c2.getString(0));
                                    arrID3.add(name.getString(0));
                                }
                                Intent intent = new Intent(ResturantList.this , MapsActivity2.class);
                                intent.putExtra("loca1" , Double.parseDouble(arrID.get(position)));
                                intent.putExtra("loca2" , Double.parseDouble(arrID2.get(position)));
                                intent.putExtra("Name" , arrID3.get(position));
                                intent.putExtra("eplac2" , "rest");
                                startActivity(intent);
                            }
                        }
                    });
                    dialog.show();
                }
                else {

                    CharSequence[] items = {getResources().getString(R.string.openFaceBook), "location" , getResources().getString(R.string.update), getResources().getString(R.string.Delete)};
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ResturantList.this);

                    dialog.setTitle(getResources().getString(R.string.Choose));
                    dialog.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            if (item == 0) {
                                Cursor c = MainActivity.sqLiteHelper.getData("SELECT idFB FROM Resturant");
                                ArrayList<String> arrID = new ArrayList<String>();
                                while (c.moveToNext()) {
                                    arrID.add(c.getString(0));
                                }
                                openFacebook(arrID.get(position));
                            }
                            if (item == 1) {
                                Cursor c = MainActivity.sqLiteHelper.getData("SELECT cor1 FROM Resturant");
                                Cursor c2 = MainActivity.sqLiteHelper.getData("SELECT cor2 FROM Resturant");
                                Cursor name = MainActivity.sqLiteHelper.getData("SELECT name FROM Resturant");
                                ArrayList<String> arrID = new ArrayList<String>();
                                ArrayList<String> arrID2 = new ArrayList<String>();
                                ArrayList<String> arrID3 = new ArrayList<String>();
                                while (c.moveToNext() && c2.moveToNext() && name.moveToNext()) {
                                    arrID.add(c.getString(0));
                                    arrID2.add(c2.getString(0));
                                    arrID3.add(name.getString(0));
                                }
                                Intent intent = new Intent(ResturantList.this , MapsActivity2.class);
                                intent.putExtra("loca1" , Double.parseDouble(arrID.get(position)));
                                intent.putExtra("loca2" , Double.parseDouble(arrID2.get(position)));
                                intent.putExtra("Name" , arrID3.get(position));
                                intent.putExtra("place2" , "rest");
                                startActivity(intent);
                            }
                            if (item == 2) {
                                // update
                                Cursor c = MainActivity.sqLiteHelper.getData("SELECT id FROM Resturant");
                                ArrayList<Integer> arrID = new ArrayList<Integer>();
                                while (c.moveToNext()) {
                                    arrID.add(c.getInt(0));
                                }
                                // show dialog update at here
                                showDialogUpdate(ResturantList.this, arrID.get(position));

                            }
                            if (item == 3) {
                                // delete
                                Cursor c = MainActivity.sqLiteHelper.getData("SELECT id FROM Resturant");
                                ArrayList<Integer> arrID = new ArrayList<Integer>();
                                while (c.moveToNext()) {
                                    arrID.add(c.getInt(0));
                                }
                                showDialogDelete(arrID.get(position));
                            }
                        }
                    });
                    dialog.show();
                }
            }
        });
    }
    public static byte[] imageViewToByte(Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public void openFacebook(final String id){
        Toast.makeText(this , "this id "+ id, Toast.LENGTH_SHORT).show();
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

    ImageView imageViewFood;
    private void showDialogUpdate(Activity activity, final int position){

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_place_activity);
        dialog.setTitle("Update");

        imageViewFood = (ImageView) dialog.findViewById(R.id.imageViewFood);
        final EditText edtName = (EditText) dialog.findViewById(R.id.edtName);
        final EditText edtPrice = (EditText) dialog.findViewById(R.id.edtPrice);
        Button btnUpdate = (Button) dialog.findViewById(R.id.btnUpdate);

        // set width for dialog
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        // set height for dialog
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

        imageViewFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // request photo library
                ActivityCompat.requestPermissions(
                        ResturantList.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        888
                );
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MainActivity.sqLiteHelper.updateResturant(
                            edtName.getText().toString().trim(),
                            edtPrice.getText().toString().trim(),
                            AddPlace.imageViewToByte(imageViewFood),
                            position
                    );
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), ""+getResources().getString(R.string.updateSec) ,Toast.LENGTH_SHORT).show();
                }
                catch (Exception error) {
                    Log.e("Update error", error.getMessage());
                }
                updateFoodList();
            }
        });
    }

    private void showDialogDelete(final int idFood){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(ResturantList.this);

        dialogDelete.setTitle(getResources().getString(R.string.Warning));
        dialogDelete.setMessage(getResources().getString(R.string.deletePlace));
        dialogDelete.setPositiveButton(getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    MainActivity.sqLiteHelper.deleteResturant(idFood);
                    Toast.makeText(getApplicationContext(), ""+getResources().getString(R.string.updateSec),Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Log.e("error", e.getMessage());
                }
                updateFoodList();
            }
        });

        dialogDelete.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }

    private void updateFoodList(){
        // get all data from sqlite
        Cursor cursor = MainActivity.sqLiteHelper.getData("SELECT * FROM Resturant");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String price = cursor.getString(2);
            byte[] image = cursor.getBlob(3);

            list.add(new Place(name, price, image, id,null));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 888){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 888);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 888 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageViewFood.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}

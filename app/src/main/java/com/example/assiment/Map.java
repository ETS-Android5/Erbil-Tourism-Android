package com.example.assiment;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class Map extends Fragment implements OnMapReadyCallback {

    GoogleMap mMap;
    boolean h;
    boolean r;
    boolean t;
    boolean s;
    int i = 0;
    boolean checkP = false;
    FusedLocationProviderClient mFusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);
        FloatingActionButton selectplace = view.findViewById(R.id.selectplace);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());


        selectplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] listItems = {"Hotel", "Restaurants", "Tourism", "Shop"};

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Choose items");

                final boolean[] checkedItems = new boolean[]{false, false, false, false}; //this will checked the items when user open the dialog
                builder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        switch (which) {
                            case 0:
                                i = 0;
                                if (checkedItems[which] == true) {
                                    h = true;
                                   listHotel();
                                }
                                break;
                            case 1:
                                i = 0;
                                if (checkedItems[which] == true) {
                                    r = true;
                                    listRestaurants();
                                }
                                break;
                            case 2:
                                i = 0;
                                if (checkedItems[which] == true) {
                                    t = true;
                                   listTourism();
                                }
                                break;
                            case 3:
                                i = 0;
                                if (checkedItems[which] == true) {
                                    s = true;
                                   listShop();
                                }
                                break;
                        }
                        Toast.makeText(getContext(), "The Number of found place => " + i, Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        return view;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("h1" , h);
        outState.putBoolean("r1" , r);
        outState.putBoolean("t1" , t);
        outState.putBoolean("s1" , s);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null){
            r = savedInstanceState.getBoolean("r1");
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    LatLng mylocation;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (h){
            listHotel();
        }
        else if (t){
            listTourism();
        }
        else if (s){
            listShop();
        }
        else if (r || checkP == true){
            listRestaurants();
        }


        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            mylocation= new LatLng(36.1879342,44.0097479);
            mMap.addMarker(new MarkerOptions().position(mylocation).title("Center Erbil"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(mylocation));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylocation,12));
            mMap.setMyLocationEnabled(true);

        } else {
            // Request permission.
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE);
        }
    }


    public void listHotel(){
        Cursor pos1 = MainActivity.sqLiteHelper.getData("SELECT cor1 FROM Hotel");
        Cursor pos2 = MainActivity.sqLiteHelper.getData("SELECT cor2 FROM Hotel");
        Cursor name = MainActivity.sqLiteHelper.getData("SELECT name FROM Hotel");
        ArrayList<String> arrID = new ArrayList<String>();
        ArrayList<String> arrID2 = new ArrayList<String>();
        ArrayList<String> arrID3 = new ArrayList<String>();
        int ic = R.drawable.ic_hotels;
        while (pos1.moveToNext() && pos2.moveToNext() && name.moveToNext()) {
            try {
                arrID.add(pos1.getString(0));
                arrID2.add(pos2.getString(0));
                arrID3.add(name.getString(0));
                positionPlaces(Double.parseDouble(arrID.get(i)), Double.parseDouble(arrID2.get(i)), arrID3.get(i), ic);
                i++;
            } catch (Exception e) {
                getReenterTransition();
            }
        }
    }

    public void listRestaurants(){
        Cursor pos1 = MainActivity.sqLiteHelper.getData("SELECT cor1 FROM Resturant");
        Cursor pos2 = MainActivity.sqLiteHelper.getData("SELECT cor2 FROM Resturant");
        Cursor name = MainActivity.sqLiteHelper.getData("SELECT name FROM Resturant");
        ArrayList<String> arrID = new ArrayList<String>();
        ArrayList<String> arrID2 = new ArrayList<String>();
        ArrayList<String> arrID3 = new ArrayList<String>();
        int ic = R.drawable.ic_rest;
        while (pos1.moveToNext() && pos2.moveToNext() && name.moveToNext()) {
            try {
                arrID.add(pos1.getString(0));
                arrID2.add(pos2.getString(0));
                arrID3.add(name.getString(0));
                positionPlaces(Double.parseDouble(arrID.get(i)), Double.parseDouble(arrID2.get(i)), arrID3.get(i), ic);
                i++;
            } catch (Exception e) {
                getReenterTransition();
            }
        }
    }

    public void listTourism(){
        Cursor pos1 = MainActivity.sqLiteHelper.getData("SELECT cor1 FROM Tourism");
        Cursor pos2 = MainActivity.sqLiteHelper.getData("SELECT cor2 FROM Tourism");
        Cursor name = MainActivity.sqLiteHelper.getData("SELECT name FROM Tourism");
        ArrayList<String> arrID = new ArrayList<String>();
        ArrayList<String> arrID2 = new ArrayList<String>();
        ArrayList<String> arrID3 = new ArrayList<String>();
        int ic = R.drawable.ic_parks;
        while (pos1.moveToNext() && pos2.moveToNext() && name.moveToNext()) {
            try {
                arrID.add(pos1.getString(0));
                arrID2.add(pos2.getString(0));
                arrID3.add(name.getString(0));
                positionPlaces(Double.parseDouble(arrID.get(i)), Double.parseDouble(arrID2.get(i)), arrID3.get(i), ic);
                i++;
            } catch (Exception e) {
                getReenterTransition();
            }
        }
    }

    public void listShop(){
        Cursor pos1 = MainActivity.sqLiteHelper.getData("SELECT cor1 FROM Shop");
        Cursor pos2 = MainActivity.sqLiteHelper.getData("SELECT cor2 FROM Shop");
        Cursor name = MainActivity.sqLiteHelper.getData("SELECT name FROM Shop");
        ArrayList<String> arrID = new ArrayList<String>();
        ArrayList<String> arrID2 = new ArrayList<String>();
        ArrayList<String> arrID3 = new ArrayList<String>();
        int ic = R.drawable.ic_shop;
        while (pos1.moveToNext() && pos2.moveToNext() && name.moveToNext()) {
            try {
                arrID.add(pos1.getString(0));
                arrID2.add(pos2.getString(0));
                arrID3.add(name.getString(0));
                positionPlaces(Double.parseDouble(arrID.get(i)), Double.parseDouble(arrID2.get(i)), arrID3.get(i), ic);
                i++;
            } catch (Exception e) {
                getReenterTransition();
            }
        }
    }


    public void positionPlaces(double p1 , double p2 , String name , int icon){
        mylocation= new LatLng(p1 , p2);
        mMap.addMarker(new MarkerOptions().position(mylocation).title(name).icon(BitmapDescriptorFactory.fromResource(icon)));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(mylocation));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylocation,10));
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (permissions.length == 1 &&
                    permissions[0] == android.Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE);
            }
        }
    }
}



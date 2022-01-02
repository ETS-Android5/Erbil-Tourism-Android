package com.example.assiment;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import Direction.FetchURL;
import Direction.TaskLoadedCallback;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    private static final int REQUEST_CODE = 101;
    double cor1 , cor2;
    String name;
    int icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        cor1 = getIntent().getDoubleExtra("loca1" ,0);
        cor2 = getIntent().getDoubleExtra("loca2" , 0);
        name = getIntent().getStringExtra("Name");
        String place = getIntent().getStringExtra("place2");
     //   Toast.makeText(MapsActivity2.this ,  name+ " :  " +cor1+" " + cor2 , Toast.LENGTH_SHORT).show();
//        Toast.makeText(MapsActivity2.this ,  place+" " , Toast.LENGTH_SHORT).show();
        if (place.equals("hotel")){
            Toast.makeText(MapsActivity2.this ,  place+" " , Toast.LENGTH_SHORT).show();
            icon = R.drawable.ic_hotels;
        }
        else if (place.equals("rest")){
            icon = R.drawable.ic_rest;
        }
        else if (place.equals("tour")){
            icon = R.drawable.ic_parks;
        }
        else if (place.equals("shop")){
            icon = R.drawable.ic_shop;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            LatLng mylocation= new LatLng(cor1, cor2);
            mMap.addMarker(new MarkerOptions().position(mylocation).title(name).icon(BitmapDescriptorFactory.fromResource(icon)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mylocation));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylocation,12));
            mMap.setMyLocationEnabled(true);
        } else {
            // Request permission.
            ActivityCompat.requestPermissions(MapsActivity2.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (permissions.length == 1 &&
                    permissions[0] == android.Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               // mMap.setMyLocationEnabled(true);
            } else {
                // Permission was denied. Display an error message.
            }
        }
    }

}

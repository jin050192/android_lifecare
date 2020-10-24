package com.example.lifecare.information;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lifecare.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class AboutUs extends AppCompatActivity {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        /*상단바 숨기기*/
        getSupportActionBar().hide();
    }

    private CameraUpdate zoomingLocation() {
        return CameraUpdateFactory.newLatLngZoom(new LatLng(37.478845, 126.878594), (float)21.0);
    }
}
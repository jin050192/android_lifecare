package com.example.lifecare.information;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.skt.Tmap.TMapView;

public class tMap  extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        RelativeLayout relativeLayout = new RelativeLayout(this);
        TMapView tMapView = new TMapView(this);

        tMapView.setSKTMapApiKey("l7xx0858646edac54643abd8cabd254c8e5c");

        tMapView.setCompassMode(true);
        tMapView.setIconVisibility(true);
        tMapView.setZoomLevel(15);
        tMapView.setMapType(TMapView.MAPTYPE_STANDARD);
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN);
        tMapView.setCenterPoint(36.370203, 127.345955);
        tMapView.setTrackingMode(true);
        tMapView.setSightVisible(true);
        relativeLayout.addView(tMapView);
        setContentView(relativeLayout);
    }
}

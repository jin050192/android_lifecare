package com.example.lifecare.information;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lifecare.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class googleMap extends AppCompatActivity implements OnMapReadyCallback {

    //구글맵참조변수
    GoogleMap mMap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);

        // SupportMapFragment을 통해 레이아웃에 만든 fragment의 ID를 참조하고 구글맵을 호출한다.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this); //getMapAsync must be called on the main thread.
    }

    @Override //구글맵을 띄울준비가 됬으면 자동호출된다.
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //지도타입 - 일반
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //마커 찍기
        manyMarker();
    }

    private String getJsonString() {
        String json = "";

        try {
            InputStream is = getAssets().open("googleMap.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return json;
    }

    ////////////////////////  구글맵 마커 여러개생성 및 띄우기 //////////////////////////
    public void manyMarker() {
        try{

            JSONArray jsonObject = new JSONArray(getJsonString());

//            JSONObject test = new JSONObject();
//            String aa = getJsonString();
//

//            JSONArray array = new JSONArray();
//            array.put(test);

            System.out.println("테스트 : " + jsonObject);

            //JSONArray googleArray = jsonObject.getJSONArray("googleMap");

            for(int i=0; i<jsonObject.length(); i++) {

                JSONObject mapjson = jsonObject.getJSONObject(i);
                System.out.println("mapjson : " + mapjson);

                MarkerOptions makerOptions = new MarkerOptions();
                makerOptions.position(new LatLng(mapjson.getDouble("lat"),mapjson.getDouble("lng") )).title(mapjson.getString("pharmacy")); // 타이틀.

                mMap.addMarker(makerOptions);

            }
        }catch (JSONException e) {
            e.printStackTrace();
        }

        //정보창 클릭 리스너
        mMap.setOnInfoWindowClickListener(infoWindowClickListener);

        //마커 클릭 리스너
        mMap.setOnMarkerClickListener(markerClickListener);

        // 카메라를 위치로 옮긴다.
       /* mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.52487, 126.92723)));*/

        LatLng seoul = new LatLng(37.478845, 126.878594);

        // 구글 맵에 표시할 마커에 대한 옵션 설정  (알파는 좌표의 투명도이다.)
        MarkerOptions makerOptions = new MarkerOptions();
        makerOptions
                .position(seoul)
                .title("Welcome to Lifecare")
                .snippet("라이프케어!!")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .alpha(0.5f);

        //줌 설정
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul,14));
    }

    //정보창 클릭 리스너
    GoogleMap.OnInfoWindowClickListener infoWindowClickListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            String markerId = marker.getId();
        }
    };

    //마커 클릭 리스너
    GoogleMap.OnMarkerClickListener markerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            String markerId = marker.getId();
            //선택한 타겟위치
            LatLng location = marker.getPosition();
            return false;
        }
    };
}
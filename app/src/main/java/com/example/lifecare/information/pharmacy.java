package com.example.lifecare.information;

import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lifecare.R;
import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;

public class pharmacy extends AppCompatActivity implements TMapGpsManager.onLocationChangedCallback {

    private boolean TrackingMode = true;
    private TMapView tMapView = null;
    private TMapGpsManager tmapgps = null;
    private static String TMapAPIKey = "l7xx0858646edac54643abd8cabd254c8e5c";

    @Override
    public void onLocationChange(Location location) {
        if (TrackingMode) {
            tMapView.setLocationPoint(location.getLongitude(), location.getLatitude());
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        RelativeLayout relativeLayout = new RelativeLayout(this);
        TMapView tMapView = new TMapView(this);
        tMapView.setHttpsMode(true);
        //발급 받은 API키  보안상 문제로 인하여 깃허브 커밋시 지워서 커밋할것!
        tMapView.setSKTMapApiKey(TMapAPIKey);
        relativeLayout.addView(tMapView);

        setUpMap();

        /*현재 보는 방향으로 설정*/
       /* tMapView.setCompassMode(true);*/

        /*현 위치 아이콘 설정*/
        tMapView.setIconVisibility(true);

        tMapView.setZoomLevel(15);
        tMapView.setMapType(TMapView.MAPTYPE_STANDARD);
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN);

        tmapgps = new TMapGpsManager(pharmacy.this);
        tmapgps.setMinTime(1000);
        tmapgps.setMinDistance(5);
        tmapgps.setProvider(tmapgps.NETWORK_PROVIDER); //네트워크에서 가져옴
        //tmapgps.OpenGps(); //가상으로 테스트시 이부분은 지워주어야 오류가 없음
        tMapView.setTrackingMode(true);
        tMapView.setSightVisible(true);
        TMapMarkerItem markerItem1 = new TMapMarkerItem();
        TMapMarkerItem markerItem2 = new TMapMarkerItem();
        TMapMarkerItem markerItem3 = new TMapMarkerItem();
        TMapMarkerItem markerItem4 = new TMapMarkerItem();
        TMapMarkerItem markerItem5 = new TMapMarkerItem();
        TMapMarkerItem markerItem6 = new TMapMarkerItem();
        TMapMarkerItem markerItem7 = new TMapMarkerItem();
        TMapMarkerItem markerItem8 = new TMapMarkerItem();
        TMapMarkerItem markerItem9 = new TMapMarkerItem();
        TMapMarkerItem markerItem10 = new TMapMarkerItem();


        markerItem1.setPosition(0.5f, 1.0f); // 마커의 중심점을 중앙, 하단으로 설정
        markerItem1.setTMapPoint( new TMapPoint(37.5155186, 127.0615148) ); // 마커의 좌표 지정
        markerItem1.setName("부활약국"); // 마커의 타이틀 지정
        tMapView.addMarkerItem("markerItem1", markerItem1); // 지도에 마커 추가

        markerItem2.setPosition(0.5f, 1.0f); // 마커의 중심점을 중앙, 하단으로 설정
        markerItem2.setTMapPoint( new TMapPoint(37.5124855, 127.0504907) ); // 마커의 좌표 지정
        markerItem2.setName("신삼성약국"); // 마커의 타이틀 지정
        tMapView.addMarkerItem("markerItem2", markerItem2); // 지도에 마커 추가

        markerItem3.setPosition(0.5f, 1.0f); // 마커의 중심점을 중앙, 하단으로 설정
        markerItem3.setTMapPoint( new TMapPoint(37.5121721, 127.0653425) ); // 마커의 좌표 지정
        markerItem3.setName("강남소화약국"); // 마커의 타이틀 지정
        tMapView.addMarkerItem("markerItem3", markerItem3); // 지도에 마커 추가

        markerItem4.setPosition(0.5f, 1.0f); // 마커의 중심점을 중앙, 하단으로 설정
        markerItem4.setTMapPoint( new TMapPoint(37.5106027, 127.0542916) ); // 마커의 좌표 지정
        markerItem4.setName("은약국"); // 마커의 타이틀 지정
        tMapView.addMarkerItem("markerItem4", markerItem4); // 지도에 마커 추가

        markerItem5.setPosition(0.5f, 1.0f); // 마커의 중심점을 중앙, 하단으로 설정
        markerItem5.setTMapPoint( new TMapPoint(37.4956227, 127.0271068) ); // 마커의 좌표 지정
        markerItem5.setName("서초삼성약국"); // 마커의 타이틀 지정
        tMapView.addMarkerItem("markerItem5", markerItem5); // 지도에 마커 추가

        markerItem6.setPosition(0.5f, 1.0f); // 마커의 중심점을 중앙, 하단으로 설정
        markerItem6.setTMapPoint( new TMapPoint(37.5350031, 127.1439027) ); // 마커의 좌표 지정
        markerItem6.setName("강동엠약국"); // 마커의 타이틀 지정
        tMapView.addMarkerItem("markerItem6", markerItem6); // 지도에 마커 추가

        markerItem7.setPosition(0.5f, 1.0f); // 마커의 중심점을 중앙, 하단으로 설정
        markerItem7.setTMapPoint( new TMapPoint(37.6184653, 127.0457034) ); // 마커의 좌표 지정
        markerItem7.setName("선약국"); // 마커의 타이틀 지정
        tMapView.addMarkerItem("markerItem7", markerItem7); // 지도에 마커 추가

        markerItem8.setPosition(0.5f, 1.0f); // 마커의 중심점을 중앙, 하단으로 설정
        markerItem8.setTMapPoint( new TMapPoint(37.4906353, 126.9082173) ); // 마커의 좌표 지정
        markerItem8.setName("대림온누리약국"); // 마커의 타이틀 지정
        tMapView.addMarkerItem("markerItem8", markerItem8); // 지도에 마커

        markerItem9.setPosition(0.5f, 1.0f); // 마커의 중심점을 중앙, 하단으로 설정
        markerItem9.setTMapPoint( new TMapPoint(37.5170222, 127.0208674) ); // 마커의 좌표 지정
        markerItem9.setName("가나안약국"); // 마커의 타이틀 지정
        tMapView.addMarkerItem("markerItem9", markerItem9); // 지도에 마커 추가

        markerItem10.setPosition(0.5f, 1.0f); // 마커의 중심점을 중앙, 하단으로 설정
        markerItem10.setTMapPoint( new TMapPoint(37.5078239, 127.0649277) ); // 마커의 좌표 지정
        markerItem10.setName("메디칼희민약국"); // 마커의 타이틀 지정
        tMapView.addMarkerItem("markerItem10", markerItem10); // 지도에 마커

        tMapView.setCenterPoint( 126.878594, 37.478845 );
        tMapView.setTrackingMode(true);
        tMapView.setSightVisible(true);

        setUpMap();

        setContentView(relativeLayout);
    }


    private void setUpMap() {
       ShelterApi parser = new ShelterApi();
        ArrayList<MapPoint> mapPoint = new ArrayList<MapPoint>();
        try {
            mapPoint = parser.apiParserSearch();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < mapPoint.size(); i++) {
            try{
                for (MapPoint entity : mapPoint) {
                    TMapPoint point = new TMapPoint(mapPoint.get(i).getXPos(), mapPoint.get(i).getYPos());
                    TMapMarkerItem markerItem1 = new TMapMarkerItem();

                    markerItem1.setPosition(0.5f, 1.0f);
                    markerItem1.setTMapPoint(point);
                    markerItem1.setName(entity.getYadmNm());
                    markerItem1.setTMapPoint(new TMapPoint(mapPoint.get(i).getXPos(), mapPoint.get(i).getYPos()));
                    //tMapView.setCenterPoint(mapPoint.get(i).getX(), mapPoint.get(i).getY());
                    tMapView.addMarkerItem("markerItem1" + i, markerItem1);
                }
            }catch(Exception e){

            }

        }
    }

}


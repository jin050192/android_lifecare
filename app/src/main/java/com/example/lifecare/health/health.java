package com.example.lifecare.health;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.example.lifecare.R;
import com.google.android.gms.location.FusedLocationProviderClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class health extends AppCompatActivity {
//1.일단 패키지 생성 액티비티->엠티엑티비티 생성 ->MainActivity로 가보자

    //날씨 변수
    private ImageView weather_image;
    private TextView wi;
    private TextView where;
    private TextView dgree;

    //식중독 지수
    private TextView foodpoison;
    //미세먼지 지수
    private TextView fineDust;
    //장소검색
    private EditText search;
    //좌표구하는 변수
    private FusedLocationProviderClient fusedLocationClient;

    //날씨 이미지 url
    String wImageUrl = "";
    //날씨 정보
    String wInfomation = "";
    //날씨 위치
    String wWhere = "";
    //온도
    String wDgree = "";
    //미세먼지
    String dust = "";
    //식중독 단계
    String jisu = "";

    //좌표
    String wido = "";
    String kyungdo = "";
    //검색어
    String first = "";
    //식중독지수 사이트
    String word3 = "http://poisonmap.mfds.go.kr/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        //권한확인
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //권한이 없을 경우
            //최초 권한 요청인지, 혹은 사용자에 의한 재요청인지 확인
            if (ActivityCompat.shouldShowRequestPermissionRationale(health.this, Manifest.permission.ACCESS_FINE_LOCATION) &&
                    ActivityCompat.shouldShowRequestPermissionRationale(health.this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // 사용자가 임의로 권한을 취소시킨 경우
                // 권한 재요청
                ActivityCompat.requestPermissions(health.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);

            } else {
                ActivityCompat.requestPermissions(health.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);

            }
        }

        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(location == null){
            location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            wido=Double.toString(location.getLatitude()) ;//위도
            kyungdo=Double.toString(location.getLongitude());//경도

            System.out.println("위도"+wido);
            System.out.println("경도"+kyungdo);
        }

        final LocationListener mLocationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {

                   wido=Double.toString(location.getLatitude()) ;//위도
                    kyungdo=Double.toString(location.getLongitude());//경도

                    System.out.println("위도"+wido);
                    System.out.println("경도"+kyungdo);
                }
                else {
                }
            }
            public void onProviderDisabled(String provider) {
            }
            public void onProviderEnabled(String provider) {
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
        };

        mLocationListener.onLocationChanged(location);

        //날씨 변수
        weather_image =findViewById(R.id.weather_image);
        wi =findViewById(R.id.wi);
        where=findViewById(R.id.where);
        dgree=findViewById(R.id.dgree);

        //식중독 지수
        foodpoison=findViewById(R.id.foodpoison);
        //미세먼지 지수
        fineDust=findViewById(R.id.fineDust);

    }

    @Override
    protected void onResume() {
        JsoupAsyncTask j = new JsoupAsyncTask();

        j.execute();
        super.onResume();
    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                //로케이션 파싱(구글)
                //검색어 불러오기
                String words="";
                //위치정보(좌표)
                String wich="";

                if(wido.equals("37.4220155")&&kyungdo.equals("-122.0839586")){
                    wich="";
                    System.out.println("좌표"+wich);
                }else{
                    wich=wido+","+kyungdo;
                    System.out.println("좌표"+wich);
                }

                Document loc=Jsoup.connect("https://www.google.co.kr/maps/place/"+wich).get();

                Elements nFirst=loc.getElementsByTag("meta").eq(11);

                words=nFirst.attr("content");

                System.out.println("가져온 값"+words);
                String[] a={};
                a =words.split(" ");
                first=a[0]+" "+a[1];
                if(first.equals("Find local")){
                    first="";
                }
                System.out.println("검색어 : "+first);

                //날씨 파싱(구글)
                Document doc = Jsoup.connect("https://www.google.com/search?rlz=1C1OKWM_enKR895KR895&sxsrf=ALeKk01pWao6aprfNgj5uQ_cqBw_GjqpCw%3A1602326693887&ei=pZCBX6DMNZiSr7wP3--L2Ak&q="+first+"+날씨").get();
                //웨더 이미지 cssQuery 는 css의 클래스 명, 아이디, 등등
                Elements image = doc.select("#wob_tci.wob_tci").eq(0);
                wImageUrl=image.attr("src");
                System.out.println("이미지"+wImageUrl);
                //날씨정보 가져오기
                Elements infomation = doc.select("#wob_dc.vk_gy.vk_sh").eq(0);
                wInfomation=infomation.text();
                System.out.println("날씨정보"+wInfomation);
                //위치정보 가져오기
                Elements where = doc.select("#wob_loc.wob_loc.mfMhoc.vk_gy.vk_h").eq(0);
                wWhere=where.text();
                System.out.println("위치정보"+wWhere);
                //온도정보 가져오기
                Elements c = doc.select("#wob_tm").eq(0);
                wDgree=c.text();
                System.out.println("온도정보"+wDgree);

                //미세먼지 네이버 에서 가져오기
                Document doc2 = Jsoup.connect("https://search.naver.com/search.naver?sm=top_hty&fbm=1&ie=utf8&query="+first+"날씨").get();
                //미세먼지 가져오기
                Elements nDust = doc2.select(".lv2").eq(1);
                dust=nDust.select(".num").text();
                System.out.println("미세먼지"+dust);

                //식중독 단계
                Document doc3 =Jsoup.connect(word3).get();
                Elements nJisu=doc3.select(".lv1");
                jisu=nJisu.text();



            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            //날시 이미지 뿌리기
            Glide.with(getApplicationContext()).load("https:"+wImageUrl).into(weather_image);
            //날씨정보 뿌리기
            wi.setText(wInfomation);
            //위치정보 뿌리기
            where.setText(wWhere);
            //온도 뿌리기
            dgree.setText(wDgree+"℃");
            //미세먼지 뿌리기
            fineDust.setText(dust);
            //식중독 단계 뿌리기
            foodpoison.setText(jisu+" 단계 입니다.");

        }
    }


}
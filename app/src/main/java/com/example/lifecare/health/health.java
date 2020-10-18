package com.example.lifecare.health;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.lifecare.EclipseConnect.SignInActivity;
import com.example.lifecare.MainActivity;
import com.example.lifecare.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class health extends AppCompatActivity {
    //택스트 색상
    String greenYellow="#FFADFF2F";
    String green="#FF008000";
    String darkOrange="#FFFF8C00";
    String crimson="#FFDC143C";

    //식중독1,2단계
    String coral="#FFFF7F50";
    String maroon="#FF8000000";

    TextView slide;
    String slideText="   ";

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
    //감기지수
    private TextView cold;
    String tCold = "";
    //폐질환 지수
    private TextView lungDisease;
    String tLungDisease = "";
    //뇌졸증지수
    private TextView brain;
    String tBrain = "";

    //날씨 이미지 url
    String wImageUrl = "";
    //날씨 정보
    String wInfomation = "";
    //온도
    String wDgree = "";
    //미세먼지
    String dust = "";
    //식중독 단계
    int f = 0;
    String foodp = "";
    private LineChart lineChart;
    private BarChart barChart2;

    //좌표
    String wido = "";
    String kyungdo = "";
    //검색어
    String first = "";
    String nowWhere = "";
    String now = "";
    //지수가져오기
    GetJISU get = new GetJISU();
    String jijum = "";
    //지수 뿌리기
    //그래프용 정보 오전
    float graph1 =0f;
    float graph2 =0f;
    float graph3 =0f;
    float graph4 =0f;
    float graph5 =0f;
    float graph6 =0f;
    float graph7 =0f;
    float graph8 =0f;
    //그래프용 정보 오후
    float graph9 =0f;
    float graph10 =0f;
    float graph11 =0f;
    float graph12 =0f;
    float graph13 =0f;
    float graph14 =0f;
    float graph15 =0f;
    float graph16 =0f;

    //날씨 day
    String today="";
    String todayp1="";
    String todayp2="";
    String todayp3="";
    String todayp4="";
    String todayp5="";
    String todayp6="";
    String todayp7="";

   private class sync extends AsyncTask <Void,Void,Void> {
        String juso="";
        String fp;
        Gson gs = new Gson();
        Map<String, Object> a;
        Map<String, Object> b;
        Map<String, Map> c;
        Map<String, List> d;
        Map<String, String> e;

       @Override
       protected void onProgressUpdate(Void... Params) {
           // 스레드가 수행되는 사이에 수행할 중간 작업(메인 스레드)
           //값샛팅
           //식중독

           // doInBackground()에서
           //publishProgress() 메소드를 호출하여 중간 작업 수행가능
       }

       @Override
       protected Void doInBackground(Void... voids) {
           try {
               //식중독
               juso = "http://apis.data.go.kr/1360000/HealthWthrIdxService/getFoodPoisoningIdx";
               fp = get.getJisu(juso, jijum);
               System.out.println(fp);
               a = gs.fromJson(fp, Map.class);
               b = (Map) a.get("response");
               c = (Map) b.get("body");
               d = (Map) c.get("items");
               e = (Map) d.get("item").get(0);
               if(!e.get("today").equals("")){
                   f = Integer.parseInt(e.get("today"));
               }else{
                   f = Integer.parseInt(e.get("tomorrow"));
               }

               //식중독
               if (f < 55) {
                   foodp = "관심";
                   foodpoison.setTextColor(Color.parseColor(coral));
                   slideText=slideText+" 식중독 관심 : 귀가 후 및 조리 전 손씻기를 생활화, ";

               } else if (55 <= f && f < 71) {
                   foodp = "주의";
                   foodpoison.setTextColor(Color.parseColor(maroon));
                   slideText=slideText+" 식중독 주의 : 식중독 예방 철저 조리시 반듯이 완전히 조리, ";

               } else if (71 <= f && f < 86) {
                   foodp = "경고";
                   foodpoison.setTextColor(Color.parseColor(darkOrange));
                   slideText=slideText+" 식중독 경고 : 음식물 관리 도구 소독 철저히, ";
               } else if (86 <= f) {
                   foodp = "위험";
                   foodpoison.setTextColor(Color.parseColor(crimson));
                   slideText=slideText+" 식중독 경고 : 식중독 의심시 의사 진료 요망 의심환자는 식품 조리 금지, ";
               }
               //감기
               juso = "http://apis.data.go.kr/1360000/HealthWthrIdxService/getColdIdx";
               fp = get.getJisu(juso, jijum);
               System.out.println(fp);
               a = gs.fromJson(fp, Map.class);
               b = (Map) a.get("response");
               c = (Map) b.get("body");
               d = (Map) c.get("items");
               e = (Map) d.get("item").get(0);

               int cnt=0;

               if(!e.get("today").equals("")){
                   cnt = Integer.parseInt(e.get("today"));
               }else{
                   cnt = Integer.parseInt(e.get("tomorrow"));
               }

               if(cnt==0){
                   tCold="낮음";
                   cold.setTextColor(Color.parseColor(greenYellow));
                   slideText=slideText+" 감기지수 낮음 : 건강한 생활 습관 유지, ";
               }else if(cnt==1){
                   tCold="보통";
                   cold.setTextColor(Color.parseColor(green));
                   slideText=slideText+" 감기지수 보통 : 수분 적절히 섭취, 개인위생 철저히, ";
               }else if (cnt==2){
                   cold.setTextColor(Color.parseColor(darkOrange));
                   tCold="높음";
                   slideText=slideText+" 감기지수 높음 : 체온 유지, 적정 실내온도 유지, ";
               }else if (cnt==3){
                   cold.setTextColor(Color.parseColor(crimson));
                   tCold="매우높음";
                   slideText=slideText+" 감기지수 매우높음 : 방한용품 사용, 외출 자제, ";
               }

               //폐질환
               juso = "http://apis.data.go.kr/1360000/HealthWthrIdxService/getAsthmaIdx";
               fp = get.getJisu(juso, jijum);
               System.out.println(fp);
               a = gs.fromJson(fp, Map.class);
               b = (Map) a.get("response");
               c = (Map) b.get("body");
               d = (Map) c.get("items");
               e = (Map) d.get("item").get(0);

               if(!e.get("today").equals("")){
                   cnt = Integer.parseInt(e.get("today"));
               }else{
                   cnt = Integer.parseInt(e.get("tomorrow"));
               }

               if(cnt==0){
                   lungDisease.setTextColor(Color.parseColor(greenYellow));
                   tLungDisease="낮음";
                   slideText=slideText+" 폐질환지수 낮음 : 건강관리 유의, ";
               }else if(cnt==1){
                   lungDisease.setTextColor(Color.parseColor(green));
                   tLungDisease="보통";
                   slideText=slideText+" 폐질환지수 보통 : 만성 폐질환 환자는 주의, ";
               }else if (cnt==2){
                   lungDisease.setTextColor(Color.parseColor(darkOrange));
                   tLungDisease="높음";
                   slideText=slideText+" 폐질환지수 높음 : 외부와 노출 주의 공기청정기 사용, ";
               }else if (cnt==3){
                   lungDisease.setTextColor(Color.parseColor(crimson));
                   tLungDisease="매우높음";
                   slideText=slideText+" 폐질환지수 매우높음 : 천식 환자는 각별히 주의, ";
               }
               //뇌졸증
               juso = "http://apis.data.go.kr/1360000/HealthWthrIdxService/getStrokeIdx";
               fp = get.getJisu(juso, jijum);
               System.out.println(fp);
               a = gs.fromJson(fp, Map.class);
               b = (Map) a.get("response");
               c = (Map) b.get("body");
               d = (Map) c.get("items");
               e = (Map) d.get("item").get(0);

               if(!e.get("today").equals("")){
                   cnt = Integer.parseInt(e.get("today"));
               }else{
                   cnt = Integer.parseInt(e.get("tomorrow"));
               }

               if(cnt==0){
                   tBrain="낮음";
                   brain.setTextColor(Color.parseColor(greenYellow));
                   slideText=slideText+" 뇌졸증지수 낮음 : 평소 뇌졸증 예방 관리, ";
               }else if(cnt==1){
                   brain.setTextColor(Color.parseColor(green));
                   tBrain="보통";
                   slideText=slideText+" 뇌졸증지수 보통 : 갑작스러운 체온변화 주의, ";
               }else if (cnt==2){
                   brain.setTextColor(Color.parseColor(darkOrange));
                   tBrain="높음";
                   slideText=slideText+" 뇌졸증지수 높음 : 외부 온도가 낮을경우 외출 금지, ";
               }else if (cnt==3){
                   brain.setTextColor(Color.parseColor(crimson));
                   tBrain="매우높음";
                   slideText=slideText+" 뇌졸증지수 매우높음 : 기왕력 환자들은 각결히 주의, ";
               }

           } catch (IOException e) {
               e.printStackTrace();
           }


           return null;
       }
       @Override
       protected void onPostExecute(Void result) {
           // 스레드 작업이 모두 끝난 후에 수행할 작업(메인 스레드)
           System.out.println("식중독"+f);
           //식중독 단계 뿌리기
           foodpoison.setText(foodp);
           //감기지수 뿌리기
           cold.setText(tCold);
           //폐질환지수 뿌리기
           lungDisease.setText(tLungDisease);
           //뇌졸증 뿌리기
           brain.setText(tBrain);

           //텍스트 슬라이드 뿌리기
           slide.setText(slideText);

           super.onPostExecute(result);
       }
   }

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

        if (location == null) {
            location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            try{
                wido = Double.toString(location.getLatitude());//위도
                kyungdo = Double.toString(location.getLongitude());//경도
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "위치서비스를 켜주세요", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }

        }
        final LocationListener mLocationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
                    try{
                        wido = Double.toString(location.getLatitude());//위도
                        kyungdo = Double.toString(location.getLongitude());//경도
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "위치서비스를 켜주세요", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }


                }
            }
            public void onProviderDisabled(String provider) {}
            public void onProviderEnabled(String provider) {}
            public void onStatusChanged(String provider, int status, Bundle extras) {}
        };

        try{
            mLocationListener.onLocationChanged(location);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "위치서비스를 켜주세요", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        //날씨 변수
        weather_image = findViewById(R.id.weather_image);
        wi = findViewById(R.id.wi);
        where = findViewById(R.id.where);
        dgree = findViewById(R.id.dgree);
        //식중독 지수
        foodpoison = findViewById(R.id.foodpoison);
        //미세먼지 지수
        fineDust = findViewById(R.id.fineDust);
        //감기 지수
        cold = findViewById(R.id.cold);
        //폐질환 지수
        lungDisease = findViewById(R.id.lungDisease);
        //뇌졸증지수
        brain = findViewById(R.id.brain);

        //텍스트뷰 슬라이드
        slide=findViewById(R.id.slide);
        slide.setSelected(true);
        slide.setSingleLine(true);



        //검색할 지역 샛팅
        //좌표를 통해 위치 가져오기
        final Geocoder geocoder = new Geocoder(health.this);
        List<Address> list = null;
        try {
            list = geocoder.getFromLocation(
                    Double.parseDouble(wido), // 위도
                    Double.parseDouble(kyungdo), // 경도
                    10); // 얻어올 값의 개수
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "위치서비스를 켜주세요", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        //현재 지점
        try {
            now = list.get(0).getAddressLine(0).split(" ")[1];



        //현재주소 셋팅

        nowWhere = list.get(0).getAddressLine(0);
        //검색어
        first=nowWhere.split(" ")[1];
        System.out.println("ddd"+first);
        //지점값
        switch (now) {
            case "서울특별시":
                jijum = "1100000000";
                break;
            case "부산광역시":
                jijum = "2600000000";
                break;
            case "대구광역시":
                jijum = "2700000000";
                break;
            case "인천광역시":
                jijum = "2800000000";
                break;
            case "광주광역시":
                jijum = "2900000000";
                break;
            case "대전광역시":
                jijum = "3000000000";
                break;
            case "울산광역시":
                jijum = "3100000000";
                break;
            case "세종특별자치시":
                jijum = "3600000000";
                break;
            case "경기도":
                jijum = "4100000000";
                break;
            case "강원도":
                jijum = "4200000000";
                break;
            case "충청북도":
                jijum = "4300000000";
                break;
            case "충청남도":
                jijum = "4400000000";
                break;
            case "전라북도":
                jijum = "4500000000";
                break;
            case "전라남도":
                jijum = "4600000000";
                break;
            case "경상북도":
                jijum = "4700000000";
                break;
            case "경상남도":
                jijum = "4800000000";
                break;
            case "제주특별자치도":
                jijum = "5000000000";
                break;
            case "이어도":
                jijum = "5019000000";
                break;
            default:
                jijum = "1100000000";

        }
        //지수
        sync s = new sync();
        s.execute();

        //온도
        JsoupAsyncTask j = new JsoupAsyncTask();
        j.execute();

        /*상단바 숨기기*/
        getSupportActionBar().hide();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "위치서비스를 켜주세요", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }
    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        //파싱 사이트
        Document doc;
        Document doc2;

        @Override
        protected Void doInBackground(Void... params) {
            try {

                //구글에서 가져올 이미지
                doc = Jsoup.connect("https://www.google.com/search?rlz=1C1OKWM_enKR895KR895&sxsrf=ALeKk01pWao6aprfNgj5uQ_cqBw_GjqpCw%3A1602326693887&ei=pZCBX6DMNZiSr7wP3--L2Ak&q="+first+"+날씨").get();
                System.out.println("검색어ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ"+first);
                Elements d=doc.select("#wob_wc.vk_c.nawv0d");
                Elements b=d.select("#wob_dp.wob_dfc");
                Elements c=b.select(".wob_t");

                //오전
                graph1=Integer.parseInt(c.eq(0).text());
                graph2=Integer.parseInt(c.eq(4).text());
                graph3=Integer.parseInt(c.eq(8).text());
                graph4=Integer.parseInt(c.eq(12).text());
                graph5=Integer.parseInt(c.eq(16).text());
                graph6=Integer.parseInt(c.eq(20).text());
                graph7=Integer.parseInt(c.eq(24).text());
                graph8=Integer.parseInt(c.eq(28).text());
                //오후
                graph9=Integer.parseInt(c.eq(2).text());
                graph10=Integer.parseInt(c.eq(6).text());
                graph11=Integer.parseInt(c.eq(10).text());
                graph12=Integer.parseInt(c.eq(14).text());
                graph13=Integer.parseInt(c.eq(18).text());
                graph14=Integer.parseInt(c.eq(22).text());
                graph15=Integer.parseInt(c.eq(26).text());
                graph16=Integer.parseInt(c.eq(30).text());
                //요일


                todayp1=b.select(".QrNVmd").eq(2).text();
                todayp2=b.select(".QrNVmd").eq(4).text();
                todayp3=b.select(".QrNVmd").eq(6).text();
                todayp4=b.select(".QrNVmd").eq(8).text();
                todayp5=b.select(".QrNVmd").eq(10).text();
                todayp6=b.select(".QrNVmd").eq(12).text();
                todayp7=b.select(".QrNVmd").eq(14).text();

                //네이버 미세먼지
                doc2 = Jsoup.connect("https://search.naver.com/search.naver?sm=top_hty&fbm=1&ie=utf8&query="+first+"날씨").get();
                //웨더 이미지 cssQuery 는 css의 클래스 명, 아이디, 등등
                Elements image = doc.select("#wob_tci.wob_tci").eq(0);
                wImageUrl=image.attr("src");
                //날씨정보 가져오기
                Elements infomation = doc.select("#wob_dc.vk_gy.vk_sh").eq(0);
                wInfomation=infomation.text();


                //온도정보 가져오기
                wDgree=doc.select("#wob_tm").eq(0).text();
                //미세먼지 가져오기
                dust=doc2.select(".indicator").eq(1).select(".num").eq(0).text();

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
            where.setText(nowWhere);
            //온도 뿌리기
            dgree.setText(wDgree+"℃");
            //온도그래프 뿌리기
            //그래프
            lineChart =(LineChart)findViewById(R.id.chart);
            List<Entry> entries= new ArrayList<>();
            //오전
            entries.add(new Entry(1f, graph1));
            entries.add(new Entry(2f, graph2));
            entries.add(new Entry(3f, graph3));
            entries.add(new Entry(4f, graph4));
            entries.add(new Entry(5f, graph5));
            entries.add(new Entry(6f, graph6));
            entries.add(new Entry(7f, graph7));
            entries.add(new Entry(8f, graph8));


            List<Entry> entries1= new ArrayList<>();
            entries1.add(new Entry(1f,graph9));
            entries1.add(new Entry(2f,graph10));
            entries1.add(new Entry(3f,graph11));
            entries1.add(new Entry(4f,graph12));
            entries1.add(new Entry(5f,graph13));
            entries1.add(new Entry(6f,graph14));
            entries1.add(new Entry(7f,graph15));
            entries1.add(new Entry(8f,graph16));

            //합쳐질곳
            LineData chartData = new LineData();

            LineDataSet set1 = new LineDataSet(entries,"최고기온");
            set1.setColor(Color.parseColor("#FFFF4500"));
            set1.setCircleColor(Color.parseColor("#FFFF4500"));
            //차트1 합침
            chartData.addDataSet(set1);

            LineDataSet set2 = new LineDataSet(entries1,"최저기온");
            set2.setColor(Color.parseColor("#FF228B22"));
            set2.setCircleColor(Color.parseColor("#FF228B22"));
            //차트2 합침
            chartData.addDataSet(set2);

            lineChart.setData(chartData);

            //차트 설정
            XAxis xAxis = lineChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextColor(Color.BLACK);
            xAxis.enableGridDashedLine(8, 24, 0);
            YAxis yLAxis = lineChart.getAxisLeft();


            //x축 마진주기

            xAxis.setAxisMinValue(0.8f);
            xAxis.setAxisMaxValue(8.2f);

            yLAxis.setAxisMaxValue(40f);
            yLAxis.setAxisMinValue(0f);
            yLAxis.setTextColor(Color.BLACK);
            YAxis yRAxis = lineChart.getAxisRight();
            yRAxis.setDrawLabels(false);
            yRAxis.setDrawAxisLine(false);
            yRAxis.setDrawGridLines(false);
            Description description = new Description();
            description.setText("");
            lineChart.setDoubleTapToZoomEnabled(false);
            lineChart.setDrawGridBackground(false);
            lineChart.setDescription(description);
            lineChart.animateY(500, Easing.EasingOption.EaseInCubic);

            String[] days={"","금일",todayp1,todayp2,todayp3,todayp4,todayp5,todayp6,todayp7};

            xAxis.setValueFormatter(new IndexAxisValueFormatter(days));
            
            //값뿌리기
            lineChart.invalidate();

            //미세먼지 뿌리기
            if(dust.equals("")){
                dust="20";
            }
            int cnt = Integer.parseInt(dust.replace("㎍/㎥",""));

            if(cnt<30){
                dust=dust+" (좋음)";
                fineDust.setTextColor(Color.parseColor(greenYellow));
            }else if(cnt>=30&&cnt<80){
                dust=dust+" (보통)";
                fineDust.setTextColor(Color.parseColor(green));
            }else if(cnt>=80&&cnt<150){
                dust=dust+" (나쁨)";
                fineDust.setTextColor(Color.parseColor(darkOrange));
            }else{
                dust=dust+" (매우 나쁨)";
                fineDust.setTextColor(Color.parseColor(crimson));
            }
            fineDust.setText(dust);
        }
    }
}


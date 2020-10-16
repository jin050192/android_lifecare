package com.example.lifecare.health;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
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

import com.bumptech.glide.Glide;
import com.example.lifecare.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;



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
    //감기지수
    private TextView cold;
    String tCold="";
    //폐질환 지수
    private TextView lungDisease;
    String tLungDisease="";
    //뇌졸증지수
    private  TextView brain;
    String tBrain="";

    //날씨 이미지 url
    String wImageUrl = "";
    //날씨 정보
    String wInfomation = "";
    //날씨 위치
    String[] wWhere = {};
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

    String[] nowlocation;
    String nowWhere;

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
        }
        final LocationListener mLocationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {

                    wido=Double.toString(location.getLatitude()) ;//위도
                    kyungdo=Double.toString(location.getLongitude());//경도
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
        //감기 지수
        cold=findViewById(R.id.cold);
        //폐질환 지수
        lungDisease=findViewById(R.id.lungDisease);
        //뇌졸증지수
        brain=findViewById(R.id.brain);


        //좌표를 통해 위치 가져오기
        final Geocoder geocoder = new Geocoder(health.this);
        List<Address> list = null;
        try {
            list = geocoder.getFromLocation(
                    Double.parseDouble(wido), // 위도
                    Double.parseDouble(kyungdo), // 경도
                    10); // 얻어올 값의 개수
        } catch (IOException e) {
            e.printStackTrace();
        }

        //주소
        String[] a={};
        a =list.get(1).getAddressLine(0).split(" ");
        //검색주소
        first=a[0]+" "+a[1];
        //전국위치
        String w = "서울시 부산시 대구시 인천시 강화군 서해5도 광주시 대전시 울산시 세종시 경기도 수원시 성남시 의정부 안양시 부천시 광명시 평택시 동두천 안산시 고양시 과천시 구리시 남양주 오산시 시흥시 군포시 의왕시 하남시 용인시 파주시 이천시 안성시 김포시 화성시 광주시 양주시 포천시 여주시 연천군 가평군 양평군 강원도 춘천시 원주시 강릉시 동해시 태백시 속초시 삼척시 홍천군 횡성군 영월군 평창군 정선군 철원군 화천군 양구군 인제군 고성군 양양군 충청북도 청주시 충주시 제천시 보은군 옥천군 영동군 증평군 진천군 괴산군 음성군 단양군 충청남도 천안시 공주시 보령시 아산시 서산시 논산시 계룡시 당진시 금산군 부여군 서천군 청양군 홍성군 예산군 태안군 전라북도 전주시 군산시 익산시 정읍시 남원시 김제시 완주군 진안군 무주군 장수군 임실군 순창군 고창군 부안군 전라남도 목포시 여수시 순천시 나주시 광양시 담양군 곡성군 구례군 고흥군 보성군 화순군 장흥군 강진군 해남군 영암군 무안군 함평군 영광군 장성군 완도군 진도군 신안군 흑산면 경상북도 포항시 경주시 김천시 안동시 구미시 영주시 영천시 상주시 문경시 경산시 군위군 의성군 청송군 영양군 영덕군 청도군 고령군 성주군 칠곡군 예천군 봉화군 울진군 울릉군 경상남도 창원시 진주시 통영시 사천시 김해시 밀양시 거제시 양산시 의령군 함안군 창녕군 고성군 남해군 하동군 산청군 함양군 거창군 합천군 제주특별자치도 제주시 서귀포시";
        nowlocation=w.split(" ");
        //현재위치
        nowWhere=list.get(1).getAddressLine(0);
        //현재위치 배열화
        wWhere=list.get(1).getAddressLine(0).split(" ");

        JsoupAsyncTask j = new JsoupAsyncTask();

        j.execute();
    }



    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        long beforeTime = System.currentTimeMillis();
        long afterTime;
        long secDiffTime;
        //파싱 사이트
        Document doc;
        Document doc2;
        Document doc3;
        Document doc4;
        Document doc5;
        Document doc6;

        @Override
        protected void onProgressUpdate(Void... Params) {
            try {

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
                //식중독 가져오기
                jisu=doc3.select(".lv1").text();

                //감기가능 지수 구하기
                String cold=doc4.getElementsByTag("td").text();
                cold=cold.replace("   "," ");
                cold=cold.replace("  "," ");
                String[] CA={};
                CA=cold.split(" ");

                //폐질환가능지수 구하기
                String lung=doc5.getElementsByTag("td").text();
                lung=lung.replace("   "," ");
                lung=lung.replace("  "," ");
                String[] LA={};
                LA=lung.split(" ");

                //뇌질환가능지수 구하기
                String brain=doc6.getElementsByTag("td").text();
                brain=brain.replace("   "," ");
                brain=brain.replace("  "," ");
                String[] BA={};
                BA=brain.split(" ");

                //현제위치의 결과값 구하기
                for(int i= 0; i<nowlocation.length;i++){
                    if(nowlocation[i].startsWith(wWhere[1].substring(0,2))){
                        tCold=CA[i];
                        tLungDisease=LA[i];
                        tBrain=BA[i];
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                //구글에서 가져올 이미지
                doc = Jsoup.connect("https://www.google.com/search?rlz=1C1OKWM_enKR895KR895&sxsrf=ALeKk01pWao6aprfNgj5uQ_cqBw_GjqpCw%3A1602326693887&ei=pZCBX6DMNZiSr7wP3--L2Ak&q="+first+"+날씨").get();
                afterTime = System.currentTimeMillis();
                secDiffTime = (afterTime - beforeTime)/1000;
                System.out.println("시간차이(m8) : "+secDiffTime);
                //네이버 미세먼지
                doc2 = Jsoup.connect("https://search.naver.com/search.naver?sm=top_hty&fbm=1&ie=utf8&query="+first+"날씨").get();
                afterTime = System.currentTimeMillis();
                secDiffTime = (afterTime - beforeTime)/1000;
                System.out.println("시간차이(m8) : "+secDiffTime);
                //식중독 데이터
                doc3 =Jsoup.connect("http://poisonmap.mfds.go.kr/").get();
                afterTime = System.currentTimeMillis();
                secDiffTime = (afterTime - beforeTime)/1000;
                System.out.println("시간차이(m8) : "+secDiffTime);
                //감기지수 데이터
                doc4=Jsoup.connect("https://www.weather.go.kr/weather/lifenindustry/li_asset/popup/imgdata_popup.jsp?CODE=D05&point=1").get();
                afterTime = System.currentTimeMillis();
                secDiffTime = (afterTime - beforeTime)/1000;
                System.out.println("시간차이(m8) : "+secDiffTime);
                //폐질환
                doc5 = Jsoup.connect("https://www.weather.go.kr/weather/lifenindustry/li_asset/popup/imgdata_popup.jsp?CODE=D01&point=1").get();
                afterTime = System.currentTimeMillis();
                secDiffTime = (afterTime - beforeTime)/1000;
                System.out.println("시간차이(m8) : "+secDiffTime);
                //뇌질환
                doc6=Jsoup.connect("https://www.weather.go.kr/weather/lifenindustry/li_asset/popup/imgdata_popup.jsp?CODE=D02&point=1").get();
                afterTime = System.currentTimeMillis();
                secDiffTime = (afterTime - beforeTime)/1000;
                System.out.println("시간차이(m8) : "+secDiffTime);
            } catch (IOException e) {
                e.printStackTrace();
            }
            publishProgress();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            //날시 이미지 뿌리기
            Glide.with(getApplicationContext()).load("https:"+wImageUrl).into(weather_image);
            //날씨정보 뿌리기
            wi.setText(wInfomation);
            //위치정보 뿌리기
            where.setText("현재 위치 : "+"\n"+nowWhere);
            //온도 뿌리기
            dgree.setText(wDgree+"℃");
            //미세먼지 뿌리기
            fineDust.setText(dust);
            //식중독 단계 뿌리기
            foodpoison.setText(jisu+" 단계");
            //감기지수 뿌리기
            cold.setText(tCold+" 단계");
            //폐질환지수 뿌리기
            lungDisease.setText(tLungDisease+" 단계");
            //뇌졸증 뿌리기
            brain.setText(tBrain+" 단계");

            afterTime = System.currentTimeMillis();
            secDiffTime = (afterTime - beforeTime)/1000;
            System.out.println("시간차이(m8) : "+secDiffTime);
        }
    }
}
/* 승운 실패
    public void getDust(){
        //미세먼지 네이버 에서 가져오기
        Document doc2 = null;
        try {
            doc2 = Jsoup.connect("https://search.naver.com/search.naver?sm=top_hty&fbm=1&ie=utf8&query="+first+"날씨").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //미세먼지 가져오기
        Elements nDust = doc2.select(".indicator").eq(1);
        dust=nDust.select(".num").eq(0).text();
        System.out.println("미세먼지 : "+dust);
    }

    public void getFoodPoison() throws IOException{
        //식중독 단계
        Document doc3 =Jsoup.connect(word3).get();
        Elements nJisu=doc3.select(".lv1");
        jisu=nJisu.text();
    }

    public void getCold() throws IOException{
        //감기 단계
        url="https://www.weather.go.kr/weather/lifenindustry/li_asset/popup/imgdata_popup.jsp?CODE=D05&point=1";
        doc4=Jsoup.connect(url)
                .header("origin",url)
                .header("referer",url)
                .ignoreContentType(true)
                .get();

        country=doc4.getElementsByTag("th");
    }

   public void getDataEdit() throws IOException{
       //위치가공
       String gagong=country.select("th").text();
       gagong=gagong.substring(48);
       gagong=gagong.replace("   "," ");
       gagong=gagong.replace("  "," ");


       //전국위치
       String[] location={};
       location=gagong.split(" ");
       //현제위치
       String[] now = {};
       now=wWhere.split(" ");

       //감기지수 구하기
       String cold=doc4.getElementsByTag("td").text();
       cold=cold.replace("   "," ");
       cold=cold.replace("  "," ");
       String[] CA={};
       CA=cold.split(" ");

       //폐질환가능지수 구하기
       url="https://www.weather.go.kr/weather/lifenindustry/li_asset/popup/imgdata_popup.jsp?CODE=D01&point=1";
       Document doc5 = Jsoup.connect(url).get();
       String lung=doc5.getElementsByTag("td").text();
       lung=lung.replace("   "," ");
       lung=lung.replace("  "," ");
       String[] LA={};
       LA=lung.split(" ");

       //뇌질환가능지수 구하기
       url="https://www.weather.go.kr/weather/lifenindustry/li_asset/popup/imgdata_popup.jsp?CODE=D02&point=1";
       Document doc6=Jsoup.connect(url).get();
       String brain=doc6.getElementsByTag("td").text();
       brain=brain.replace("   "," ");
       brain=brain.replace("  "," ");
       String[] BA={};
       BA=brain.split(" ");

       System.out.println("ddddddddddddddddddd"+now[0].substring(0,2));
   }
*/

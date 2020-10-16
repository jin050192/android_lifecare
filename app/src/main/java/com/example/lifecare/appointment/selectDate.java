package com.example.lifecare.appointment;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifecare.EclipseConnect.HttpClient;
import com.example.lifecare.EclipseConnect.Web;
import com.example.lifecare.MainActivity;
import com.example.lifecare.R;
import com.example.lifecare.VO.AppointmentVO;
import com.example.lifecare.VO.UserVO;
import com.google.gson.Gson;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class selectDate extends AppCompatActivity {

    ArrayList<Map<String, String>> m = new ArrayList<>();

    private RecyclerView recyclerView;
    private dateAdapter adapter;
    private ArrayList<AppointmentVO> arrayList;

    private String doctor_id;
    private String appoint_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);

         // 전 화면에서 받아온 값
        Intent getIntent = getIntent();
        doctor_id = getIntent.getStringExtra("doctor_id");

        selectDate.InnerTask task = new selectDate.InnerTask();
        Map<String, String> map = new HashMap<>();
        map.put("doctor_id", doctor_id);
        if(map.get("doctor_id") != null) {
            task.execute(map);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_selectDate);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();
        adapter = new dateAdapter(arrayList);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        NotificationManager notificationManager= (NotificationManager)selectDate.this.getSystemService(selectDate.this.NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(selectDate.this.getApplicationContext(),selectDate.class); //인텐트 생성.
        System.out.println("111111 ");

        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //현재 액티비티를 최상으로 올리고, 최상의 액티비티를 제외한 모든 액티비티를 없앤다.
        System.out.println("22222222 ");
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity( selectDate.this,0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setSmallIcon(R.drawable.app_icon).setTicker("HETT").setWhen(System.currentTimeMillis())
                .setNumber(1).setContentTitle("푸쉬 제목").setContentText("푸쉬내용")
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendingNotificationIntent).setAutoCancel(true).setOngoing(true);
        System.out.println("33333 ");
        //해당 부분은 API 4.1버전부터 작동합니다.
        //setSmallIcon - > 작은 아이콘 이미지
        //setTicker - > 알람이 출력될 때 상단에 나오는 문구.
        //setWhen -> 알림 출력 시간.
        //setContentTitle-> 알림 제목
        //setConentText->푸쉬내용
        notificationManager.notify(1, builder.build()); // Notification send
        System.out.println("44444 ");


    }


    //각 Activity 마다 Task 작성
    public class InnerTask extends AsyncTask<Map, Integer, String> {

        //doInBackground 실행되기 이전에 동작
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //작업을 쓰레드로 처리
        @Override
        protected String doInBackground(Map... maps) {
            //HTTP 요청 준비 - Post 로 바꿀것
            HttpClient.Builder http = new HttpClient.Builder("POST", Web.servletURL + "dateList"); //스프링 url
            //파라미터 전송
            http.addAllParameters(maps[0]);
            System.out.println("http ㅁㄴㅇㄻㄴㅇㄹ: " + http.getParameter());
            //HTTP 요청 전송
            HttpClient post = http.create();
            post.request();

            String body = post.getBody(); //Web의 Controller에서 리턴한 값
            return body;
        }

        //doInBackground 종료되면 동작
        /**
         * @param s : doInBackground에서 리턴한 body. JSON 데이터
         */
        @Override
        protected void onPostExecute(String s) {
            Intent intent = new Intent(selectDate.this, selectDate.class);

            //JSON으로 받은 데이터를 VO Obejct로 바꿔준다.
            if(s.length() > 0) {
                Gson gson = new Gson();

                m = gson.fromJson(s, m.getClass());
                //Map<String, Object>[] m = (Map<String, Object>[])gson.fromJson(s, Map[].class);

                for(Map<String, String> map : m){
                    AppointmentVO data = new AppointmentVO(map.get("appoint_num"), map.get("doctor_id"), map.get("appoint_date"));
                    arrayList.add(data);
                    appoint_num = map.get("appoint_num");
                    adapter.notifyDataSetChanged();
                }
            } else {
                Toast.makeText(getApplicationContext(), "날짜 리스트 불러오기 실패", Toast.LENGTH_SHORT).show();
            }

            adapter.setOnDateItemClicklistener(new OnDateItemClickListener() {
                    @Override
                    public void onItemClick(dateAdapter.dateViewHolder holder, View view, int position) {
                        // 해당 시간 선택하면....
                        AppointmentVO item = adapter.getItem(position);
                        // System.out.println(item.getAppoint_num());
                        AlertDialog.Builder builder = new AlertDialog.Builder(selectDate.this);
                        builder.setTitle("예약확인창");
                        builder.setMessage(item.getAppoint_date()+"으로예약을진행하시겠습니까?");
                        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                selectDate.InnerTask2 task = new selectDate.InnerTask2();
                                Map<String, String> map = new HashMap<>();
                                map.put("appoint_num", item.getAppoint_num());
                                map.put("doctor_id", item.getDoctor_id());
                                map.put("appoint_date", item.getAppoint_date());
                                map.put("customer_id", UserVO.getInstance().getId());
                                if(map.get("appoint_num") != null) {
                                    task.execute(map); // 밑에 스프링과 연동 시키기
                                }
                            }
                        });
                        builder.setNegativeButton("아니오",null);
                        builder.setNeutralButton("취소",null);
                        builder.create().show();
                    }
            });
        }
    }

    //각 Activity 마다 Task 작성
    public class InnerTask2 extends AsyncTask<Map, Integer, String> {

        //doInBackground 실행되기 이전에 동작
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //작업을 쓰레드로 처리
        @Override
        protected String doInBackground(Map... maps) {
            //HTTP 요청 준비 - Post 로 바꿀것
            HttpClient.Builder http = new HttpClient.Builder("POST", Web.servletURL + "addReservation"); //스프링 url
            //파라미터 전송
            http.addAllParameters(maps[0]);
            System.out.println("http ㅁㄴㅇㄻㄴㅇㄹ: " + http.getParameter());
            //HTTP 요청 전송
            HttpClient post = http.create();
            post.request();

            String body = post.getBody(); //Web의 Controller에서 리턴한 값
            return body;
        }

        //doInBackground 종료되면 동작

        /**
         * @param s : doInBackground에서 리턴한 body. JSON 데이터
         */
        @Override
        protected void onPostExecute(String s) {

            //JSON으로 받은 데이터를 VO Obejct로 바꿔준다.
            if (s.length() > 0) {
                Gson gson = new Gson();
                Map<String, String> map = new HashMap<String, String>();
                map = gson.fromJson(s, map.getClass());

                if (map.get("insertCnt") != null && map.get("insertCnt") != "") {
                    Intent intent = new Intent(selectDate.this.getApplicationContext(), confirmReservation.class);
                    //Map<String, Object>[] m = (Map<String, Object>[])gson.fromJson(s, Map[].class);
                    StyleableToast.makeText(getApplicationContext(), "진료예약에 성공했습니다.", Toast.LENGTH_LONG, R.style.mytoast).show();
                     startActivity(intent);

                }
            } else {
                Toast.makeText(getApplicationContext(), "진료 예약이 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(selectDate.this, MainActivity.class);
        startActivity(intent);
    }
}
package com.example.lifecare.appointment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.lifecare.EclipseConnect.HttpClient;
import com.example.lifecare.EclipseConnect.Web;
import com.example.lifecare.MainActivity;
import com.example.lifecare.R;
import com.example.lifecare.VO.AppointmentVO;
import com.example.lifecare.VO.ReservationVO;
import com.example.lifecare.VO.UserVO;
import com.google.gson.Gson;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class confirmReservation extends AppCompatActivity {

    ArrayList<Map<String, String>> m = new ArrayList<>();

    private RecyclerView recyclerView;
    private reservationAdapter adapter;
    private ArrayList<ReservationVO> arrayList;

    private String appoint_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_reservation);

        String customer_id = UserVO.getInstance().getId();
        System.out.println(customer_id);
        confirmReservation.InnerTask task = new confirmReservation.InnerTask();
        Map<String, String> map = new HashMap<>();
        map.put("customer_id", customer_id);
        if(map.get("customer_id") != null) {
            task.execute(map);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_reservationList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();
        adapter = new reservationAdapter(arrayList);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
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
            HttpClient.Builder http = new HttpClient.Builder("POST", Web.servletURL + "reservationList"); //스프링 url
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
            Intent intent = new Intent(confirmReservation.this, confirmReservation.class);

            //JSON으로 받은 데이터를 VO Obejct로 바꿔준다.
            if(s.length() > 0) {
                Gson gson = new Gson();

                m = gson.fromJson(s, m.getClass());

                for (Map<String, String> map : m) {
                    ReservationVO data = new ReservationVO(map.get("doctor_major"), map.get("doctor_name"), map.get("reservation_date"), map.get("appoint_num"));
                    arrayList.add(data);
                    adapter.notifyDataSetChanged();
                }
            } else {
                Toast.makeText(getApplicationContext(), "예약 리스트 불러오기 실패", Toast.LENGTH_SHORT).show();
            }

            adapter.setOnReservationItemClickListener(new OnReservationItemClickListener() {
                @Override
                public void onItemClick(reservationAdapter.reservationViewHolder holder, View view, int position) {
                    // 해당 시간 클릭하면
                    ReservationVO item = adapter.getItem(position);
                    appoint_num = item.getAppoint_num();
                    AlertDialog.Builder builder = new AlertDialog.Builder(confirmReservation.this);
                    builder.setTitle("예약취소창");
                    builder.setMessage("해당 예약시간을 취소하시겠습니까?");
                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            System.out.println(appoint_num); // 확인
                            confirmReservation.InnerTask2 task = new confirmReservation.InnerTask2();
                            Map<String, String> map = new HashMap<>();
                            map.put("appoint_num", appoint_num);
                            task.execute(map); // 밑에 스프링과 연동 시키기
                        }
                    });
                    builder.setNegativeButton("아니오",  null);
                    builder.setNeutralButton("취소", null);
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
            HttpClient.Builder http = new HttpClient.Builder("POST", Web.servletURL + "cancelReservation"); //스프링 url
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

                if (map.get("deleteCnt") != null && map.get("deleteCnt") != "") {
                    Intent intent = new Intent(confirmReservation.this, confirmReservation.class);
                    //Map<String, Object>[] m = (Map<String, Object>[])gson.fromJson(s, Map[].class);
                    StyleableToast.makeText(getApplicationContext(), "진료취소에 성공했습니다.", Toast.LENGTH_LONG, R.style.mytoast).show();
                    startActivity(intent);
                }
            } else {
                Toast.makeText(getApplicationContext(), "예약취소가 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(confirmReservation.this, MainActivity.class);
        startActivity(intent);
    }
}
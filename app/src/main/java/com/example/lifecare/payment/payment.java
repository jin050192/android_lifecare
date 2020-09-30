package com.example.lifecare.payment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.lifecare.EclipseConnect.HttpClient;
import com.example.lifecare.EclipseConnect.Web;
import com.example.lifecare.MainActivity;
import com.example.lifecare.R;
import com.example.lifecare.VO.DiagnosisVO;
import com.example.lifecare.VO.UserVO;
import com.example.lifecare.appointment.confirmReservation;
import com.example.lifecare.appointment.diagnosis;
import com.example.lifecare.appointment.selectDate;
import com.google.gson.Gson;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class payment extends AppCompatActivity {

    ArrayList<Map<String, String>> m = new ArrayList<>();

    private RecyclerView recyclerView;
    private payAdapter adapter;
    private ArrayList<DiagnosisVO> arrayList;

    private String diagnosis_num;
    String customer_amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        String customer_id = UserVO.getInstance().getId();

        payment.InnerTask task = new payment.InnerTask();
        Map<String, String> map = new HashMap<>();
        map.put("customer_id", customer_id);
        if(map.get("customer_id") != null) {
            task.execute(map);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_payList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();
        adapter = new payAdapter(arrayList);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent intent = new Intent(payment.this, payment.class);
                startActivity(intent);
            }
        });
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
            HttpClient.Builder http = new HttpClient.Builder("POST", Web.servletURL + "payList"); //스프링 url
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
            Intent intent = new Intent(payment.this, payment.class);

            //JSON으로 받은 데이터를 VO Obejct로 바꿔준다.
            if(s.length() > 0) {
                Gson gson = new Gson();

                m = gson.fromJson(s, m.getClass());

                for (Map<String, String> map : m) {
                    DiagnosisVO data = new DiagnosisVO(map.get("diagnosis_num"), map.get("diagnosis_time"), map.get("doctor_major"), map.get("customer_amount"));
                    arrayList.add(data);
                    adapter.notifyDataSetChanged();
                }
            } else {
                Toast.makeText(getApplicationContext(), "결제 리스트 불러오기 실패", Toast.LENGTH_SHORT).show();
            }

            adapter.setOnpayItemClickListener(new OnpayItemClickListener() {
                @Override
                public void onItemClick(payAdapter.payViewHolder holder, View view, int position) {
                    // 해당 진료 클릭하면
                    DiagnosisVO item = adapter.getItem(position);
                    diagnosis_num = item.getDiagnosis_num();
                    customer_amount = item.getCustomer_amount();
                    AlertDialog.Builder builder = new AlertDialog.Builder(payment.this);
                    builder.setTitle("결제진행창");
                    builder.setMessage("해당 진료를 카카오페이로 결제하시겠습니까?");
                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            payment.InnerTask2 task2= new payment.InnerTask2();
                            Map<String, String> map = new HashMap<>();
                            map.put("diagnosis_num", diagnosis_num);
                            map.put("amount", customer_amount);
                            task2.execute(map); // 밑에 스프링과 연동 시키기

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
            HttpClient.Builder http = new HttpClient.Builder("POST", Web.servletURL + "kakaoPayGo"); //스프링 url
            //파라미터 전송
            http.addAllParameters(maps[0]);
            System.out.println("http ㅁㄴㅇㄻㄴㅇㄹ: " + http.getParameter());
            //HTTP 요청 전송
            HttpClient post = http.create();
            post.request();

            String body = post.getBody(); //Web의 Controller에서 리턴한 값
            System.out.println("----------" + body);
            return body;
        }

        //doInBackground 종료되면 동작

        /**
         * @param s : doInBackground에서 리턴한 body. JSON 데이터
         */
        @Override
        protected void onPostExecute(String s) {
            System.out.println(s);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(s));
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(payment.this, MainActivity.class);
        startActivity(intent);
    }
}
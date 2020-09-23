package com.example.lifecare.appointment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.lifecare.EclipseConnect.HttpClient;
import com.example.lifecare.EclipseConnect.SharedPreferenceHandler;
import com.example.lifecare.EclipseConnect.Web;
import com.example.lifecare.R;
import com.example.lifecare.VO.DoctorVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class selectDoctor extends AppCompatActivity {

    private RecyclerView recyclerView;
    private doctorAdapter adapter;
    private ArrayList<DoctorVO> arrayList;

    private String major;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_doctor);

        // 전 화면에서 받아온 값
        Intent getIntent = getIntent();
        major = getIntent.getStringExtra("major");

        selectDoctor.InnerTask task = new selectDoctor.InnerTask();
        Map<String, String> map = new HashMap<>();
        map.put("major", major);
        if(map.get("major") != null) {
            task.execute(map);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        adapter = new doctorAdapter(arrayList);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        DoctorVO data = new DoctorVO("jin050192", "진은성", "정신과", "부교수");
        arrayList.add(data);

        adapter.notifyDataSetChanged();
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
            HttpClient.Builder http = new HttpClient.Builder("POST", Web.servletURL + "doctorList"); //스프링 url
            //파라미터 전송
            http.addAllParameters(maps[0]);
            System.out.println("http ㅁㄴㅇㄻㄴㅇㄹ: " + http.getParameter()); // 여기까지 보임
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

            Log.d("JSON_RESULT ㅁㄴㅇㄹ", s);
            SharedPreferenceHandler sh = new SharedPreferenceHandler(getApplicationContext());

            //JSON으로 받은 데이터를 VO Obejct로 바꿔준다.
            if(s.length() > 0) {
                Gson gson = new Gson();
                Map<String, Object>[] m = gson.fromJson(s, new TypeToken<Map<String, Object>[]>(){}.getType());
                //Map<String, Object>[] m = (Map<String, Object>[])gson.fromJson(s, Map[].class);

                for(Map<String, Object> test : m){
                    System.out.println("test 출력 : "+test);
                }
/*
                if (!= null) {
                    // 페이지 이동
                    Intent intent = new Intent(selectDoctor.this, selectDoctor.class);
                    startActivity(intent);
                } else if (m.getEnabled() != "1") {
                    Toast.makeText(getApplicationContext(), "회원 정보가 올바르지 않습니다.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "가입 인증이 필요한 회원입니다.", Toast.LENGTH_LONG).show();
                }
*/
            } else {
                Toast.makeText(getApplicationContext(), "의료진 리스트 불러오기 실패", Toast.LENGTH_SHORT).show();
            }
                // 페이지 이동
                Intent intent = new Intent(selectDoctor.this, selectDoctor.class);
                startActivity(intent);
            }

        }
    }
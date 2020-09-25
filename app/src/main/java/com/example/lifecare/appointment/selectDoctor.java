package com.example.lifecare.appointment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.lifecare.EclipseConnect.HttpClient;
import com.example.lifecare.EclipseConnect.Web;
import com.example.lifecare.R;
import com.example.lifecare.VO.DoctorVO;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class selectDoctor extends AppCompatActivity {
    ArrayList<Map<String, String>> m = new ArrayList<>();

    private RecyclerView recyclerView;
    private doctorAdapter adapter;
    private ArrayList<DoctorVO> arrayList;

    private String major;

    private String doctor_id;
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
            Intent intent = new Intent(selectDoctor.this, selectDoctor.class);

            //JSON으로 받은 데이터를 VO Obejct로 바꿔준다.
            if(s.length() > 0) {
                Gson gson = new Gson();

                m = gson.fromJson(s, m.getClass());
                //Map<String, Object>[] m = (Map<String, Object>[])gson.fromJson(s, Map[].class);

                for(Map<String, String> map : m){
                    DoctorVO data = new DoctorVO(map.get("doctor_id"), map.get("doctor_name"), map.get("doctor_major"), map.get("doctor_position"));
                    arrayList.add(data);
                    doctor_id = map.get("doctor_id");
                    adapter.notifyDataSetChanged();
                }
            } else {
                Toast.makeText(getApplicationContext(), "의료진 리스트 불러오기 실패", Toast.LENGTH_SHORT).show();
            }
            // 페이지 이동
            // startActivity(intent);
            adapter.setOnItemClicklistener(new OnItemClickListener() {

                @Override
                public void onItemClick(doctorAdapter.doctorViewHolder holder, View view, int position) {
                    Intent intent = new Intent(selectDoctor.this, selectDate.class);
                    intent.putExtra("doctor_id", doctor_id);
                    startActivity(intent);
                }
            });
        }
    }
}
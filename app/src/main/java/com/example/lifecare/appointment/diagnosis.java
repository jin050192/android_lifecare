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
import com.example.lifecare.R;
import com.example.lifecare.VO.DiagnosisVO;
import com.example.lifecare.VO.ReservationVO;
import com.example.lifecare.VO.UserVO;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class diagnosis extends AppCompatActivity {

    ArrayList<Map<String, String>> m = new ArrayList<>();

    private RecyclerView recyclerView;
    private diagnosisAdapter adapter;
    private ArrayList<DiagnosisVO> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);

        String customer_id = UserVO.getInstance().getId();

        diagnosis.InnerTask task = new diagnosis.InnerTask();
        Map<String, String> map = new HashMap<>();
        map.put("customer_id", customer_id);
        if(map.get("customer_id") != null) {
            task.execute(map);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_diagnosisList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();
        adapter = new diagnosisAdapter(arrayList);
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
            HttpClient.Builder http = new HttpClient.Builder("POST", Web.servletURL + "diagnosisList"); //스프링 url
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
            Intent intent = new Intent(diagnosis.this, diagnosis.class);

            //JSON으로 받은 데이터를 VO Obejct로 바꿔준다.
            if(s.length() > 0) {
                Gson gson = new Gson();

                m = gson.fromJson(s, m.getClass());

                for (Map<String, String> map : m) {
                    DiagnosisVO data = new DiagnosisVO(map.get("diagnosis_num"), map.get("diagnosis_time"), map.get("doctor_name"), map.get("doctor_major"), map.get("disease_name"), map.get("drug"));
                    arrayList.add(data);
                    adapter.notifyDataSetChanged();
                }
            } else {
                Toast.makeText(getApplicationContext(), "진료 리스트 불러오기 실패", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
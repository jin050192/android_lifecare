package com.example.lifecare.drug;

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
import com.example.lifecare.EclipseConnect.SharedPreferenceHandler;
import com.example.lifecare.EclipseConnect.Web;
import com.example.lifecare.R;
import com.example.lifecare.VO.DoctorVO;
import com.example.lifecare.VO.DrugVO;
import com.example.lifecare.appointment.OnItemClickListener;
import com.example.lifecare.appointment.doctorAdapter;
import com.example.lifecare.appointment.selectDate;
import com.example.lifecare.appointment.selectDoctor;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class drugPhothResult extends AppCompatActivity {
        ArrayList<DrugVO> drugList = new ArrayList<>();

        DrugVO drug = DrugVO.getInstance();

        private RecyclerView recyclerView;
        private drugAdapter adapter;
        private ArrayList<DrugVO> arrayList;

        private String drug_num;
        private String result;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_drug_photh_result);

                Intent getIntent = getIntent();
                result = getIntent.getStringExtra("result");

                DrugTask task = new DrugTask();
                    Map<String, String> map = new HashMap<>();
                    map.put("result", result);

                if(map.get("result") != null){
                    System.out.print("result : " + result);
                    task.execute(map);
                }
                recyclerView = (RecyclerView) findViewById(R.id.v_drugResult);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(linearLayoutManager);

                arrayList = new ArrayList<>();
                adapter = new drugAdapter(arrayList);
                recyclerView.setAdapter(adapter);

                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                        linearLayoutManager.getOrientation());
                recyclerView.addItemDecoration(dividerItemDecoration);
        }
        //각 Activity 마다 Task 작성
        public class DrugTask extends AsyncTask<Map, Integer, String> {

        //doInBackground 실행되기 이전에 동작
        @Override
        protected void onPreExecute() { super.onPreExecute(); }

        //작업을 쓰레드로 처리
        @Override
        protected String doInBackground(Map... maps) {
                //HTTP 요청 준비 - Post 로 바꿀것
                HttpClient.Builder http = new HttpClient.Builder("POST", Web.servletURL + "drugPhotoSeaerch"); //스프링 url
                //파라미터 전송
                http.addAllParameters(maps[0]);
                //HTTP 요청 전송
                HttpClient post =http.create();
                post.request();

                String body = post.getBody(); //Web의 Controller에서 리턴한 값
                System.out.print("body : " + body);
                return body;

        }
        //doInBackground 종료되면 동작

        // @param s : doInBackground에서 리턴한 body. JSON 데이터

        @Override
        protected void onPostExecute(String s) {
                SharedPreferenceHandler sh = new SharedPreferenceHandler(getApplicationContext());
                //JSON으로 받은 데이터를 VO Obejct로 바꿔준다.
                if (s.length() > 0) {
                        Gson gson = new Gson();
                        drugList= gson.fromJson(s, drugList.getClass());

                        for(DrugVO vo : drugList){
                                DrugVO data = new DrugVO(vo.getDrug_number(), vo.getDrug_name(),vo.getDrug_enptname(),vo.getDrug_productimage(),vo.getDrug_frontShape(),vo.getDrug_color());
                                arrayList.add(data);
                                adapter.notifyDataSetChanged();
                        }
                } else {
                        Toast.makeText(getApplicationContext(), "의료진 리스트 불러오기 실패", Toast.LENGTH_SHORT).show();
                }
        }
//    public void onBackPressed() {
//        Intent intent = new Intent(drugPhoto.this, drugSearchMain.class);
//        startActivity(intent);
//    }

        // 페이지 이동
        // startActivity(intent);
        drugAdapter.
        }
}
package com.example.lifecare.drug;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SymbolTable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lifecare.EclipseConnect.HttpClient;
import com.example.lifecare.EclipseConnect.Web;
import com.example.lifecare.R;
import com.example.lifecare.VO.DrugVO;
import com.example.lifecare.appointment.selectDate;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class drugPhotoDetail extends AppCompatActivity {

    ArrayList<Map<String, String>> drugDetail = new ArrayList<>();


    private String drug_num;
    private String drug_image;
    private String drug_name;
    private String drug_emtp;
    private String drug_division;
    private String drug_lisenceDate;
    private String drug_category;
    private String drug_formulation;
    private String drug_color;
    private String drug_storage;
    private String drug_additives;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_photo_detail);

        Intent drugNumIntent = getIntent();
        drug_num = drugNumIntent.getStringExtra("drug_num");

        detailTask task = new detailTask();
        Map<String, String> map = new HashMap<>();
        map.put("drug_num", drug_num);

        if(map.get("drug_num") != null){
            System.out.print("drug_num : " + drug_num);
            task.execute(map);
        }
//        scrollView = findViewById(R.id.nested_content);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//
//        arrayList = new ArrayList<>();
//        adapter = new drugDetailAdapter(arrayList);
//
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(scrollView.getContext(),
//                linearLayoutManager.getOrientation());
    }
    //각 Activity 마다 Task 작성
    public class detailTask extends AsyncTask<Map, Integer, String> {

        //doInBackground 실행되기 이전에 동작
        @Override
        protected void onPreExecute() { super.onPreExecute(); }

        //작업을 쓰레드로 처리
        @Override
        protected String doInBackground(Map... maps) {
            //HTTP 요청 준비 - Post 로 바꿀것
            HttpClient.Builder http = new HttpClient.Builder("POST", Web.servletURL + "drugPhotoDetail"); //스프링 url
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
            // SharedPreferenceHandler sh = new SharedPreferenceHandler(getApplicationContext());
            Intent intent = new Intent(drugPhotoDetail.this, drugPhotoDetail.class);

            //JSON으로 받은 데이터를 VO Obejct로 바꿔준다.
            if (s.length() > 0) {
                Gson gson = new Gson();
                drugDetail= gson.fromJson(s, drugDetail.getClass());

                for(Map<String, String> map : drugDetail){
//                    DrugVO data = new DrugVO(map.get("drug_image"), map.get("drug_name"), map.get("drug_emtp"), map.get("drug_division"), map.get("drug_lisenceDate"), map.get("drug_category"), map.get("drug_formulation"), map.get("drug_color"), map.get("drug_num"), map.get("drug_storage"), map.get("drug_additives"), map.get("drug_effect"), map.get("drug_precautions"));
                    drug_image = map.get("drug_image");
                    drug_name = map.get("drug_name");
                    drug_emtp = map.get("drug_emtp");
                    drug_division = map.get("drug_division");
                    drug_lisenceDate = map.get("drug_lisenceDate");
                    drug_category = map.get("drug_category");
                    drug_formulation = map.get("drug_formulation");
                    drug_color = map.get("drug_color");
                    drug_storage = map.get("drug_storage");
                    drug_additives = map.get("drug_additives");

                    ImageView d_image = findViewById(R.id.drug_image);
                    TextView d_name = findViewById(R.id.drug_name);
                    TextView d_emtp = findViewById(R.id.drug_emtp);
                    TextView d_division = findViewById(R.id.drug_division);
                    TextView d_lisenceDate = findViewById(R.id.drug_lisenceDate);
                    TextView d_category = findViewById(R.id.drug_category);
                    TextView d_formulation = findViewById(R.id.drug_formulation);
                    TextView d_color = findViewById(R.id.drug_color);
                    TextView d_num  = findViewById(R.id.drug_num);
                    TextView d_storage  = findViewById(R.id.drug_storage);
                    TextView d_additives = findViewById(R.id.drug_additives);


                    Glide.with(d_image.getContext()).load(drug_image)
                            .placeholder(R.drawable.pill2)
                            .error(R.drawable.pill1)
                            .into(d_image);

//                    try {
//                        URL url = new URL(drug_image);
//
//                        URLConnection conn = url.openConnection();
//                        conn.connect();
//                        BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
//                        Bitmap bm = BitmapFactory.decodeStream(bis);
//                        bis.close();
//                        d_image.setImageBitmap(bm);
//                    } catch (Exception e) {
//                    }

                    d_name.setText(drug_name);
                    d_emtp.setText(drug_emtp);
                    d_division.setText(drug_division);
                    d_lisenceDate.setText(drug_lisenceDate);
                    d_category.setText(drug_category);
                    d_formulation.setText(drug_formulation);
                    d_color.setText(drug_color);
                    d_num.setText(drug_num);
                    d_storage.setText(drug_storage);
                    d_additives.setText(drug_additives);
                }
            } else {
                Toast.makeText(getApplicationContext(), "의약품 조회 실패", Toast.LENGTH_SHORT).show();
            }
        }
   }
}
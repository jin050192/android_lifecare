package com.example.lifecare.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.lifecare.EclipseConnect.HttpClient;
import com.example.lifecare.EclipseConnect.SharedPreferenceHandler;
import com.example.lifecare.EclipseConnect.SignInActivity;
import com.example.lifecare.EclipseConnect.Web;
import com.example.lifecare.MainActivity;
import com.example.lifecare.R;
import com.example.lifecare.VO.UserVO;
import com.google.gson.Gson;
import com.kakao.auth.Session;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.HashMap;
import java.util.Map;

public class KakaoLoginCheck2 extends AppCompatActivity {
    UserVO userVO = UserVO.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_login);

        System.out.println("****************************");
        if(userVO.getId() != ""){
            KakaoLoginCheck2.InnerTask task = new KakaoLoginCheck2.InnerTask();
            Map<String, String> map = new HashMap<>();

            map.put("kakaoId",userVO.getId());

            task.execute(map);
        }else{
            Intent intent = new Intent(KakaoLoginCheck2.this, SignInActivity.class);
            StyleableToast.makeText(getApplicationContext(), "카카오로그인에 실패하였습니다.", Toast.LENGTH_LONG, R.style.mytoast).show();
            startActivity(intent);
        }
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
            HttpClient.Builder http = new HttpClient.Builder("POST", Web.servletURL + "kakaoSignIn"); //스프링 url
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
            Log.d("JSON_RESULT ㅁㄴㅇㄹ", s);
            SharedPreferenceHandler sh = new SharedPreferenceHandler(getApplicationContext());
            //JSON으로 받은 데이터를 VO Obejct로 바꿔준다.
            if(s.length() > 0) {
                Gson gson = new Gson();
                UserVO m = gson.fromJson(s, UserVO.class);
                if (m.getId() != null && m.getId() != "") {
                    // 페이지 이동
                    Intent intent = new Intent(KakaoLoginCheck2.this, MainActivity.class);

                    //로그인 유지
//                    userVO.setId(m.getId());
//                    userVO.setEnabled(m.getEnabled());
//                    userVO.setCustomer_echeck(m.getCustomer_echeck());

                    //아이디 저장 & 자동로그인
                    //sh.keepId(m.getId());

                    StyleableToast.makeText(getApplicationContext(), m.getId()+"님 로그인 되었습니다.", Toast.LENGTH_LONG, R.style.mytoast).show();
                    startActivity(intent);
                } else if (m.getEnabled() != "1") {
                    Intent intent = new Intent(KakaoLoginCheck2.this, SignInActivity.class);
                    Toast.makeText(getApplicationContext(), "회원 정보가 올바르지 않습니다.", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(KakaoLoginCheck2.this, SignInActivity.class);
                    Toast.makeText(getApplicationContext(), "가입 인증이 필요한 회원입니다.", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
            } else {
                Intent intent = new Intent(KakaoLoginCheck2.this, SignInActivity.class);
                Toast.makeText(getApplicationContext(), "카카오에 연동되어 있는 아이디가 없습니다.", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        }
    }
}
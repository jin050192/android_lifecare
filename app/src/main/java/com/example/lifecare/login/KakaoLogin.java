package com.example.lifecare.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.lifecare.EclipseConnect.SignInActivity;
import com.example.lifecare.MainActivity;
import com.example.lifecare.R;
import com.example.lifecare.VO.UserVO;
import com.kakao.auth.AuthType;
import com.kakao.auth.Session;
import com.muddzdev.styleabletoast.StyleableToast;

public class KakaoLogin extends AppCompatActivity {
    private SessionCallback sessionCallback = new SessionCallback();
    Session session;
    UserVO userVO = UserVO.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_login);

        session = Session.getCurrentSession();
        session.addCallback(sessionCallback);

        session.open(AuthType.KAKAO_LOGIN_ALL, KakaoLogin.this);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback);

        if(userVO.getId() != ""){
            Intent intent = new Intent(KakaoLogin.this, MainActivity.class);
            StyleableToast.makeText(getApplicationContext(), userVO.getId()+"님 로그인 되었습니다.", Toast.LENGTH_LONG, R.style.mytoast).show();
            startActivity(intent);
        }else{
            Intent intent = new Intent(KakaoLogin.this, SignInActivity.class);
            StyleableToast.makeText(getApplicationContext(), "카카오로그인에 실패하였습니다.", Toast.LENGTH_LONG, R.style.mytoast).show();
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 카카오톡|스토리 간편로그인 실행 결과를 받아서 SDK로 전달
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
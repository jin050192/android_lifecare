package com.example.lifecare.EclipseConnect;

import android.Manifest;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.lifecare.MainActivity;
import com.example.lifecare.R;
import com.example.lifecare.VO.UserVO;
import com.example.lifecare.login.KakaoLoginCheck;
import com.example.lifecare.login.NaverLogin;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.muddzdev.styleabletoast.StyleableToast;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.json.JSONObject;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import static com.nhn.android.naverlogin.OAuthLogin.mOAuthLoginHandler;

/**
 * Created by psn on 2018-01-18.
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class SignInActivity extends AppCompatActivity {

    UserVO userVO = UserVO.getInstance();
    EditText edtId, edtPwd;
    Button btnSignIn;
    ImageView btnKakaoLogin;

    /*지문인식 부분*/
    private static final String KEY_NAME = "example_key";
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private Cipher cipher;
    private FingerprintManager.CryptoObject cryptoObject;
    private ImageView jimunLogin;
    /*지문인식 부분*/

    //네이버 로그인 관련
    private OAuthLoginButton mOAuthLoginButton;

    //client 정보
    public static String OAUTH_CLIENT_ID = "LFKH6Ooda771daTdxSSO";
    public static String OAUTH_CLIENT_SECRET = "GWXuQveGk8";
    public static String OAUTH_CLIENT_NAME = "홈페이지 연습";
    public static OAuthLogin mOAuthLoginInstance;
    private static Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext =this;

        edtId = (EditText) findViewById(R.id.id);
        edtPwd = (EditText) findViewById(R.id.pwd);
        btnSignIn = (Button) findViewById(R.id.btn_login);
        btnKakaoLogin = (ImageView) findViewById(R.id.kakao);
        jimunLogin = (ImageView) findViewById(R.id.jimun);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                InnerTask task = new InnerTask();
                Map<String, String> map = new HashMap<>();
                map.put("id", edtId.getText().toString());
                map.put("pwd", edtPwd.getText().toString());

                task.execute(map);
            }
        });

        btnKakaoLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, KakaoLoginCheck.class);
                startActivity(intent);
/*
                if(userVO.getId() != ""){
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    StyleableToast.makeText(getApplicationContext(), userVO.getId()+"님 로그인 되었습니다.", Toast.LENGTH_LONG, R.style.mytoast).show();
                    startActivity(intent);
                }else{
                    StyleableToast.makeText(getApplicationContext(), "로그인에 실패하였습니다", Toast.LENGTH_LONG, R.style.mytoast).show();
                }
                */
            }
        });

        /*지문로그인*/
        jimunLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Customer_fingerprint = "";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    /*생체인식기능 manifests 에 권한 추가후 사용한다*/
                    fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

                    /*잠금 해제와 해제된 잠금화면을 복구 시키는 역할을한다*/
                    keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

                    /*지문 상태별 알림*/
                    if (!fingerprintManager.isHardwareDetected()) {//Manifest에 Fingerprint 퍼미션을 추가해 워야 사용가능
                        Toast.makeText(SignInActivity.this, "지문을 사용할 수 없는 디바이스 입니다.", Toast.LENGTH_SHORT).show();
                    } else if (ContextCompat.checkSelfPermission(SignInActivity.this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(SignInActivity.this, "지문사용을 허용해 주세요.", Toast.LENGTH_SHORT).show();
                        /*잠금화면 상태를 체크한다.*/
                    } else if (!keyguardManager.isKeyguardSecure()) {
                        Toast.makeText(SignInActivity.this, "잠금화면을 설정해 주세요.", Toast.LENGTH_SHORT).show();
                    } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                        Toast.makeText(SignInActivity.this, "등록된 지문이 없습니다.", Toast.LENGTH_SHORT).show();
                    } else {//모든 관문을 성공적으로 통과(지문인식을 지원하고 지문 사용이 허용되어 있고 잠금화면이 설정되었고 지문이 등록되어 있을때)
                        Toast.makeText(SignInActivity.this, "손가락을 홈버튼에 대 주세요.", Toast.LENGTH_SHORT).show();

                        generateKey();
                        if (cipherInit()) {
                            //지문 핸들러실행
                            jimunTask task = new jimunTask();
                            FingerprintHandler fingerprintHandler = new FingerprintHandler(SignInActivity.this);
                            fingerprintHandler.startAutho(fingerprintManager, cryptoObject ,task);
                        }
                    }
                }
            }
        });

        //초기화
        initData();
    }

    public void onButtonClick(View v) throws Throwable {
        switch (v.getId()) {
            case R.id.buttonOAuthLoginImg: {
                mOAuthLoginInstance.startOauthLoginActivity(SignInActivity.this, mOAuthLoginHandler);
                break;
            }
            default:
                break;
        }
    }


    private void initData() {
        //초기화
        mOAuthLoginInstance = OAuthLogin.getInstance();
        mOAuthLoginInstance.init(mContext, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);

        mOAuthLoginButton = (OAuthLoginButton) findViewById(R.id.buttonOAuthLoginImg);
        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);

        //custom img로 변경시 사용
        //mOAuthLoginButton.setBgResourceId(R.drawable.btn_naver_white_kor);
    }

    /**
     * OAuthLoginHandler를 startOAuthLoginActivity() 메서드 호출 시 파라미터로 전달하거나 OAuthLoginButton
     객체에 등록하면 인증이 종료되는 것을 확인할 수 있습니다.
     */
    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                String accessToken = mOAuthLoginInstance.getAccessToken(mContext);
                String refreshToken = mOAuthLoginInstance.getRefreshToken(mContext);
                long expiresAt = mOAuthLoginInstance.getExpiresAt(mContext);
                String tokenType = mOAuthLoginInstance.getTokenType(mContext);

                redirectSignupActivity();
            } else {
                String errorCode = mOAuthLoginInstance.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext);
                Toast.makeText(mContext, "errorCode:" + errorCode + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
            }
        };
    };

    // 네이버 성공 후 이동할 액티비티
    protected void redirectSignupActivity() {

        new RequestApiTask().execute();

        finish();
    }

    // 네이버 로그인 성공시 토큰 받아오기


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
            HttpClient.Builder http = new HttpClient.Builder("POST", Web.servletURL + "androidSignIn"); //스프링 url
            //파라미터 전송
            http.addAllParameters(maps[0]);
            System.out.println("http ㅁㄴㅇㄻㄴㅇㄹ: " + http.getParameter());
            //HTTP 요청 전송
            HttpClient post =http.create();
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
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);

                    //로그인 유지
                    userVO.setId(m.getId());
                    userVO.setEnabled(m.getEnabled());
                    userVO.setCustomer_echeck(m.getCustomer_echeck());

                    //아이디 저장 & 자동로그인
                    sh.keepId(m.getId());

                    StyleableToast.makeText(getApplicationContext(), m.getId()+"님 로그인 되었습니다.", Toast.LENGTH_LONG, R.style.mytoast).show();
                    startActivity(intent);
                } else if (m.getEnabled() != "1") {
                    Toast.makeText(getApplicationContext(), "회원 정보가 올바르지 않습니다.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "가입 인증이 필요한 회원입니다.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {

        System.out.println("===========================  sign destroy");
        super.onDestroy();
    }

    private class RequestApiTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String url = "https://openapi.naver.com/v1/nid/me";
            String at = mOAuthLoginInstance.getAccessToken(mContext);
            return mOAuthLoginInstance.requestApi(mContext, at, url);
        }

        protected void onPostExecute(String content) {
            System.out.println("====================== content : "+content);
            System.out.println("====================== type : "+content.getClass());
            final Intent intent = new Intent(SignInActivity.this, NaverLogin.class);
            Gson gson = new Gson();
            Map<String,Object> responseInfo = gson.fromJson(content, Map.class);
            System.out.println("================================= a-b : "+(Map<String, Object>)responseInfo.get("response"));
            Map<String, Object> responseUserInfo=(Map<String, Object>)responseInfo.get("response");
            System.out.println("============== 정답  : " + (String)responseUserInfo.get("id"));

            intent.putExtra("id", (String)responseUserInfo.get("id"));
            startActivity(intent);
        }
    }

    /*지문인식 부분*/
    /*지문인식 실행*/
    public class jimunTask extends AsyncTask<Map, Integer, String> {

        //doInBackground 실행되기 이전에 동작
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //작업을 쓰레드로 처리
        @Override
        protected String doInBackground(Map... maps) {
            //HTTP 요청 준비 - Post 로 바꿀것
            HttpClient.Builder http = new HttpClient.Builder("POST", Web.servletURL + "jimunLogin"); //스프링 url

            //파라미터 전송
            http.addAllParameters(maps[0]);

            //HTTP 요청 전송
            HttpClient post = http.create();
            post.request();

            String body = post.getBody();

            return body;
        }

        @Override
        protected void onPostExecute(String s) {
            Gson gson = new Gson();
            UserVO m = gson.fromJson(s, UserVO.class);
            if(m == null){
                Toast.makeText(mContext, "등록되지않은 아이디 또는 지문입니다 로그인 하여 등록하여주십시오.", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(mContext, "로그인되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
            }
        }
    }
    /*지문인식 실행*/

    /*암호화*/
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean cipherInit(){
        try {
            cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }
        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException
                | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }
    /*암호화*/

    /*키생성*/
    protected void generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Failed to get KeyGenerator instance", e);
        }

        try {
            keyStore.load(null);
            keyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | CertificateException | IOException e){
            throw new RuntimeException(e);
        }
    }
    /*키생성*/
    /*지문인식 부분*/
}

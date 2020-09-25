package com.example.lifecare.ui.mypage;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.lifecare.EclipseConnect.FingerprintHandler2;
import com.example.lifecare.EclipseConnect.HttpClient;
import com.example.lifecare.EclipseConnect.SignInActivity;
import com.example.lifecare.EclipseConnect.Web;
import com.example.lifecare.MainActivity;
import com.example.lifecare.R;
import com.example.lifecare.VO.UserVO;
import com.google.gson.Gson;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import static android.content.Context.FINGERPRINT_SERVICE;
import static android.content.Context.KEYGUARD_SERVICE;

@RequiresApi(api = Build.VERSION_CODES.M)
public class mypage extends Fragment  {
    /*지문인식 부분*/
    private static final String KEY_NAME = "example_key";
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private Cipher cipher;
    private FingerprintManager.CryptoObject cryptoObject;
    /*지문인식 부분*/

    LinearLayout jimunadd;
    ImageView jumunaddImg;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);


        BtnOnClickListener onClickListener = new BtnOnClickListener() ;

        jimunadd = (LinearLayout)view.findViewById(R.id.jimunadd);
        jimunadd.setOnClickListener(onClickListener);

        jumunaddImg = (ImageView)view.findViewById(R.id.jumunaddImg);
        jumunaddImg.setOnClickListener(onClickListener);
        return view;
    }

    public void setJimun() {
        String Customer_fingerprint = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    /*생체인식기능 manifests 에 권한 추가후 사용한다*/
            fingerprintManager = (FingerprintManager) getActivity().getSystemService(FINGERPRINT_SERVICE);

                    /*잠금 해제와 해제된 잠금화면을 복구 시키는 역할을한다*/
            keyguardManager = (KeyguardManager) getActivity().getSystemService(KEYGUARD_SERVICE);

                    /*지문 상태별 알림*/
            if (!fingerprintManager.isHardwareDetected()) {//Manifest에 Fingerprint 퍼미션을 추가해 워야 사용가능
                Toast.makeText(getActivity(), "지문을 사용할 수 없는 디바이스 입니다.", Toast.LENGTH_SHORT).show();
            } else if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "지문사용을 허용해 주세요.", Toast.LENGTH_SHORT).show();
                        /*잠금화면 상태를 체크한다.*/
            } else if (!keyguardManager.isKeyguardSecure()) {
                Toast.makeText(getActivity(), "잠금화면을 설정해 주세요.", Toast.LENGTH_SHORT).show();
            } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                Toast.makeText(getActivity(), "등록된 지문이 없습니다.", Toast.LENGTH_SHORT).show();
            } else {//모든 관문을 성공적으로 통과(지문인식을 지원하고 지문 사용이 허용되어 있고 잠금화면이 설정되었고 지문이 등록되어 있을때)
                Toast.makeText(getActivity(), "손가락을 홈버튼에 대 주세요.", Toast.LENGTH_SHORT).show();

                generateKey();
                if (cipherInit()) {
                    //지문 핸들러실행
                    jimunAddTask task = new jimunAddTask();
                    FingerprintHandler2 fingerprintHandler = new FingerprintHandler2(getActivity());
                    fingerprintHandler.startAutho(fingerprintManager, cryptoObject, task);
                }
            }
        }
    }

    class BtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.jimunadd: {
                    setJimun();
                    System.out.println("================ ㅁㅇㅇ");
                    break;
                }
                case R.id.jumunaddImg :{
                    setJimun();
                    break;
                }
                default:
                    break;
            }
        }
    }


    /*지문인식 부분*/
    /*지문인식 실행*/
    public class jimunAddTask extends AsyncTask<Map, Integer, String> {

        //doInBackground 실행되기 이전에 동작
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //작업을 쓰레드로 처리
        @Override
        protected String doInBackground(Map... maps) {
            //HTTP 요청 준비 - Post 로 바꿀것
            HttpClient.Builder http = new HttpClient.Builder("POST", Web.servletURL + "jimunSave"); //스프링 url

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
                Toast.makeText(getActivity(), "지문등록에 실패하였습니다", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getActivity(), "지문이등록되었습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
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


}
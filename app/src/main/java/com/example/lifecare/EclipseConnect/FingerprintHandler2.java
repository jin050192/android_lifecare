package com.example.lifecare.EclipseConnect;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.lifecare.MainActivity;
import com.example.lifecare.VO.UserVO;
import com.example.lifecare.ui.mypage.mypage;

import java.util.HashMap;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHandler2 extends FingerprintManager.AuthenticationCallback{

    CancellationSignal cancellationSignal;
    private Context context;
    private String CUSTOMER_FINGERPRINT;
    mypage.jimunAddTask task;
    UserVO user = UserVO.getInstance();


    public FingerprintHandler2(Context context){
        this.context = context;
    }

    //메소드들 정의
    public void startAutho(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject, mypage.jimunAddTask task){
        cancellationSignal = new CancellationSignal();
        this.task = task;
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        this.update("인증 에러 발생" + errString, false);
    }

    @Override
    public void onAuthenticationFailed() {
        this.update("인증 실패", false);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        this.update("Error: "+ helpString, false);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        //해당기기에대한 고유번호를 받는 부분
        CUSTOMER_FINGERPRINT = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        this.update("", true);
    }

    public void stopFingerAuth(){
        if(cancellationSignal != null && !cancellationSignal.isCanceled()){
            cancellationSignal.cancel();
        }
    }

    private void update(String s, boolean b) {
        if(b == false){
        } else {//지문인증 성공
                Map<String, String> map = new HashMap<>();
                map.put("Customer_id",user.getId());
                map.put("Customer_fingerprint",CUSTOMER_FINGERPRINT);
                task.execute(map);
        }
    }
}
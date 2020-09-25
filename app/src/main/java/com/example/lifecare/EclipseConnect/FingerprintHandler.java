package com.example.lifecare.EclipseConnect;

import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.lifecare.MainActivity;

import java.util.HashMap;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHandler  extends FingerprintManager.AuthenticationCallback{

    CancellationSignal cancellationSignal;
    private Context context;
    private String CUSTOMER_FINGERPRINT;
    SignInActivity.jimunTask task;
    public FingerprintHandler(Context context){
        this.context = context;
    }

    //메소드들 정의

    public void startAutho(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject, SignInActivity.jimunTask task){
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
        Toast.makeText(context, CUSTOMER_FINGERPRINT, Toast.LENGTH_SHORT).show();
        this.update("", true);
    }

    public void stopFingerAuth(){
        if(cancellationSignal != null && !cancellationSignal.isCanceled()){
            cancellationSignal.cancel();
        }
    }

    private void update(String s, boolean b) {
        if(b == false){
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        } else {//지문인증 성공

            Map<String, String> map = new HashMap<>();
            map.put("Customer_fingerprint", CUSTOMER_FINGERPRINT);
            task.execute(map);
        }
    }
}

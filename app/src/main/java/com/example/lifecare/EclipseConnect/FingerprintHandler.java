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

    public FingerprintHandler(Context context){
        this.context = context;
    }

    //메소드들 정의

    public String startAutho(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject){
        cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);

       return CUSTOMER_FINGERPRINT;
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
        this.update("지문인식에 성공하였습니다.", true);
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
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        }
    }
}

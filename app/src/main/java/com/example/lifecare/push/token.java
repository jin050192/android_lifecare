package com.example.lifecare.push;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lifecare.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class token extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
        @Override
        public void onComplete(@NonNull Task<InstanceIdResult> task) {
            if (!task.isSuccessful()) {
                Log.w("FIREBASE", "getInstanceId failed", task.getException());
                return;
            }
            ////////////////////토큰이 계속 초기화가 되기때문에 sharedPreferences로 저장하여 초기화 방지////////////////////
//            SharedPreferences sharedPreferences;
//            sharedPreferences = getSharedPreferences("sFile1", MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
            String token = task.getResult().getToken(); // 사용자가 입력한 저장할 데이터
//            editor.putString("Token1", token); // key, value를 이용하여 저장하는 형태
//            editor.commit();
            ////////////////////토큰이 계속 초기화가 되기때문에 sharedPreferences로 저장하여 초기화 방지////////////////////

// Get new Instance ID token

//            sharedPreferences = getSharedPreferences("sFile1", MODE_PRIVATE);
//            token = sharedPreferences.getString("Token1", token); //key값과 value값으로 구분된 저장된 토큰값을 불러옵니다.
// Log and toast
//String msg = getString(R.string.msg_token_fmt, token);
            Log.d("FIREBASE", token);
            Toast.makeText(token.this, token, Toast.LENGTH_SHORT).show();
        }
    });
}
}
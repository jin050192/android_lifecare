package com.example.lifecare;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.lifecare.EclipseConnect.FingerprintHandler;
import com.example.lifecare.EclipseConnect.FingerprintHandler2;
import com.example.lifecare.EclipseConnect.HttpClient;
import com.example.lifecare.EclipseConnect.SharedPreferenceHandler;
import com.example.lifecare.EclipseConnect.SignInActivity;
import com.example.lifecare.EclipseConnect.Web;
import com.example.lifecare.VO.UserVO;
import com.example.lifecare.myPage.Mypage;
import com.example.lifecare.appointment.appointment;
import com.example.lifecare.drug.drugSearch;
import com.example.lifecare.information.hospitalRoom;
import com.example.lifecare.information.information;
import com.example.lifecare.ui.deeplearningcare.deeplearningcare;
import com.example.lifecare.ui.helth.helth;
import com.example.lifecare.ui.mypage.mypage;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.nhn.android.naverlogin.OAuthLogin;

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


import com.example.lifecare.ui.deeplearningcare.deeplearningcare;
import com.example.lifecare.ui.helth.helth;
import com.example.lifecare.ui.mypage.mypage;
import com.example.lifecare.ui.home.home;


@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private deeplearningcare deeplearningcare = new deeplearningcare();
    private helth helth =new helth();
    private mypage mypage = new mypage();
    private home home = new home();
    UserVO user = UserVO.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment, home).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());

        /*상단바 숨기기*/
        getSupportActionBar().hide();

    }

    /*마이페이지*/
    public void enterMypage(View w){
        UserVO userVO = UserVO.getInstance();
        if(userVO.getId() =="") {
            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(getApplicationContext(), Mypage.class);
            startActivity(intent);
        }

    }
    public void drugSearch(View w){
        Intent intent = new Intent(getApplicationContext(), drugSearch.class);
        startActivity(intent);
    }
    public void hospitalRoom(View w){
        Intent intent = new Intent(getApplicationContext(), hospitalRoom.class);
        startActivity(intent);
    }
    public void appointment(View w){
        Intent intent = new Intent(getApplicationContext(), appointment.class);
        startActivity(intent);
    }
    public void information(View w){
        Intent intent = new Intent(getApplicationContext(), information.class);
        startActivity(intent);
    }

    /* 네비게이션 아이템에대한 선택적 이벤트 발생 */
    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch(menuItem.getItemId())
            {
                case R.id.navigation_deeplearningcare :
                    transaction.replace(R.id.nav_host_fragment, deeplearningcare).commitAllowingStateLoss();
                    break;
                case R.id.navigation_helth:
                    transaction.replace(R.id.nav_host_fragment, helth).commitAllowingStateLoss();
                    break;
                case R.id.navigation_mypage:
                    /*로그인 안했을때*/
                    if(user.getId() == ""){
                        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                        startActivity(intent);
                    }else {
                        transaction.replace(R.id.nav_host_fragment, mypage).commitAllowingStateLoss();
                    }
                    break;
                case R.id.navigation_home :
                    transaction.replace(R.id.nav_host_fragment, home).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }
}
/*
순서(Main Thread)
1. aTask.execute()
2. onPreExecuted()
3. doInBackground()  : AsyuncTask aTask
4. publishProgress() :  AsyuncTask aTask
5. onProgressUpdate() : UI refresh
6. publishProgress() :  AsyuncTask aTask
7. onProgressUpdate() : UI refresh
...
8. return(result) : AsyuncTask aTask
9. onPostExecuted()
 */
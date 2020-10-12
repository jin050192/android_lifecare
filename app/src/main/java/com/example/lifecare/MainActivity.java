package com.example.lifecare;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.lifecare.Chating.ChatActivity;
import com.example.lifecare.Chating.ChatListActivity;
import com.example.lifecare.EclipseConnect.SignInActivity;
import com.example.lifecare.VO.UserVO;
import com.example.lifecare.appointment.appointment;
import com.example.lifecare.drug.drugSearchMain;
import com.example.lifecare.information.hospitalRoom;
import com.example.lifecare.information.information;
import com.example.lifecare.payment.payment;
import com.example.lifecare.ui.deeplearningcare.deeplearningcare;
import com.example.lifecare.ui.helth.helth;
import com.example.lifecare.ui.home.home;
import com.example.lifecare.ui.mypage.mypage;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nhn.android.naverlogin.OAuthLogin;


@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private deeplearningcare deeplearningcare = new deeplearningcare();
    private helth helth =new helth();
    private mypage mypage = new mypage();
    private home home = new home();
    UserVO user = UserVO.getInstance();

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment, home).commitAllowingStateLoss();

        /*하단바*/
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());

        /*상단바 숨기기*/
        getSupportActionBar().hide();
    }



    public void Chating(View w) {
        if (user.getId() == "") {

            builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("어플 종료");
            builder.setMessage("어플종료를 하시겠습니까?.");
            builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                public void onClick(
                        DialogInterface dialog, int id) {
                    //"예" 버튼 클릭시 실행하는 메소드
                    moveTaskToBack(true);
                    finishAndRemoveTask();
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            });
            builder.setNegativeButton("아니오", null);
        }
    }

    /*1:1 채팅*/
    public void chat(View w){
        System.out.println("=======================enterMypage : " + user.getId());
        if(user.getId() =="") {
            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
            startActivity(intent);
        }else if(user.getId().equals("admin")){
            Intent intent = new Intent(getApplicationContext(), ChatListActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
            startActivity(intent);
        }
    }
    public void drugSearch(View w){
        Intent intent = new Intent(getApplicationContext(), drugSearchMain.class);
        startActivity(intent);
    }
    public void covid19(View w){
        Intent intent = new Intent(getApplicationContext(), Covid.class);
        startActivity(intent);
    }
    public void hospitalRoom(View w){
        Intent intent = new Intent(getApplicationContext(), hospitalRoom.class);
        startActivity(intent);
    }
    public void appointment(View w){
        if(user.getId() =="") {
            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(getApplicationContext(), appointment.class);
            startActivity(intent);
        }
    }
    public void information(View w){
        Intent intent = new Intent(getApplicationContext(), information.class);
        startActivity(intent);
    }
    public void payment(View w){
        if(user.getId() =="") {
            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(getApplicationContext(), payment.class);
            startActivity(intent);
        }
    }

    // 네이버 로그아웃 테스트
    public void test(View w){
        Toast.makeText(this, "user :" + user.getId(), Toast.LENGTH_LONG).show();
        //logOut();
        //Toast.makeText(this, "로그아웃 작동", Toast.LENGTH_LONG).show();
    }

    public void logOut(){
        OAuthLogin mOAuthLoginInstance =SignInActivity.mOAuthLoginInstance;
        mOAuthLoginInstance.showDevelopersLog(true);
        mOAuthLoginInstance.init(this, SignInActivity.OAUTH_CLIENT_ID, SignInActivity.OAUTH_CLIENT_SECRET, SignInActivity.OAUTH_CLIENT_NAME);
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

    @Override
    public void onBackPressed() {
        builder.create().show();
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

/*
1.  onCreate() 함수
액티비티가 처음 생성될 때 호출되는 함수입니다. 처음 프로젝트를 생성하면 MainActivity에서 자동으로 생성해줄 만큼 액티비티 생명주기에 있어서 중요한 함수입니다. 해당 함수에서는 액티비티를 실행하기 위하여 정의된 사용자 UI를 올리기 위해 레이아웃 리소스 파일을 읽어와 객체를 생성하고 각종 초기화 작업을 진행하게 됩니다. 해당 함수 수 호출 후에는 항상 onStart() 함수가 같이 호출됩니다.
2.  onStart() 함수
액티비티가 사용자에게 표시되기 직전에 호출되는 함수입니다. 액티비티가 표시되면 onResume() 함수가 뒤따라 호출됩니다.
3.  onResume() 함수
액티비티가 시작되고 사용자와 상호작용을 시작하기전에 호출되는 함수입니다. 이 시점에 액티비티는 스택의 가장 상위에 존재하고 있고 해당 함수 뒤에는 onPause() 함수가 뒤따라 호출됩니다.
4. onPause() 함수
해당 액티비티에서 다른 액티비티로 전환되기 직전에 호출되는 함수입니다. 보통 해당 함수에서는 아직 저장되지 않은 데이터를 유지하기 위해 데이터를 저장하거나 시스템 자원을 소모하는 작업을 진행하는 경우 중단할 때 사용됩니다. 예를 들어 동영상을 재생하고 있었다가 다른 액티비티를 전환되는 경우 해당 함수에서 동영상을 멈추도록 하는 구현이 들어갈 수 있습니다. 해당 함수가 끝나기 전까지 액티비티 전환이 이루어지지 않기 때문에 해당 함수는 빠르게 작업이 이루어져야 합니다. 액티비티가 다시 표시되기 시작하면 (전환된 액티비티에서 이전 버튼 클릭) 다시 onResume() 함수가 호출되고 전환된 액티비티에 의해 액티비티가 가려지게 되면 onStop() 함수가 호출됩니다.
5. onStop() 함수
액티비티가 사용자에게 표시되지 않을 때 호출됩니다. 해당 함수는 액티비티가 소멸되는 경우에 호출될수도 있고 또는 다른 액티비티로 전환되면서 호출되는 경우도 있습니다. 사용자에게 다시 표시되기 상호작용이 가능한 상태가 될 경우 onRestart() 함수가 호출됩니다.
6. onDestroy()
액티비가 소멸되기전에 호출되는 함수입니다. 시스템이 메모리 확보를 위해 인스턴스를 제거하는 경우가 있고 또는 finish() 함수를 호출한 경우에 호출되는 함수입니다.
* */
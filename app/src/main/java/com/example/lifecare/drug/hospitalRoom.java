package com.example.lifecare.drug;

import androidx.appcompat.app.AppCompatActivity;
import com.example.lifecare.R;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class hospitalRoom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_room);

        final ImageButton btn1 = (ImageButton) findViewById(R.id.btn_tworoom);
        final ImageButton btn2 = (ImageButton) findViewById(R.id.btn_fourroom);
        btn1.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                // 이메일 중복확인 버튼이 활성화 되어 있을 경우에만 동작
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        // 아래 둘중 택 1
                        // 터치했을 때 버튼색 변경
                        btn1.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.colorAccent));
                        // 터치했을 때의 이미지 변경
                        btn1.setBackgroundResource(R.drawable.open_door);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        // 아래 둘중 택 1
                        // 터치해제 되었을 때 버튼색 변경
                        // 0의 경우 xml에
                        btn1.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.colorPrimaryDark));
                        // 터치해제 되었을 때 이미지 변경
                        btn1.setBackgroundResource(R.drawable.door);
                        break;
                    }
                }
                return false;
            }
        });
        btn2.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                // 이메일 중복확인 버튼이 활성화 되어 있을 경우에만 동작
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        // 아래 둘중 택 1
                        // 터치했을 때 버튼색 변경
                        btn2.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.colorAccent));
                        // 터치했을 때의 이미지 변경
                        btn2.setBackgroundResource(R.drawable.open_door);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        // 아래 둘중 택 1
                        // 터치해제 되었을 때 버튼색 변경
                        // 0의 경우 xml에
                        btn2.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.colorPrimaryDark));
                        // 터치해제 되었을 때 이미지 변경
                        btn2.setBackgroundResource(R.drawable.door);
                        break;
                    }
                }
                return false;
            }
        });
    }
}
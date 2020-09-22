package com.example.lifecare.information;

import androidx.appcompat.app.AppCompatActivity;
import com.example.lifecare.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class hospitalRoom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_room);

        ImageButton btn1 = (ImageButton) findViewById(R.id.btn_tworoom);
        ImageButton btn2 = (ImageButton) findViewById(R.id.btn_fourroom);

        btn1.setOnClickListener(room);
        btn2.setOnClickListener(room);
    }
    View.OnClickListener room = new
            View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch ((v.getId())) {
                        case R.id.btn_tworoom:
                             v.setBackgroundResource(R.drawable.open_door);
//                            Intent intent = new
//                                    Intent(hospitalRoom.this, 3d.class);
//                            startActivity(intent);
                            break;
                        case R.id.btn_fourroom:
                             v.setBackgroundResource(R.drawable.open_door);
//                            Intent intent = new
//                                    Intent(hospitalRoom.this, 3d.class);
//                            startActivity(intent);
                            break;
                    }
                }
            };
}
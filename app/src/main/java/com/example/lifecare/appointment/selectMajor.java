package com.example.lifecare.appointment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lifecare.R;

public class selectMajor extends AppCompatActivity {

    private Button btn_selectMajor1;
    private Button btn_selectMajor2;
    private Button btn_selectMajor3;
    private Button btn_selectMajor4;
    private Button btn_selectMajor5;
    private Button btn_selectMajor6;
    private Button btn_selectMajor7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_major);

        btn_selectMajor1 = findViewById(R.id.btn_selectMajor1);
        btn_selectMajor2 = findViewById(R.id.btn_selectMajor2);
        btn_selectMajor3 = findViewById(R.id.btn_selectMajor3);
        btn_selectMajor4 = findViewById(R.id.btn_selectMajor4);
        btn_selectMajor5 = findViewById(R.id.btn_selectMajor5);
        btn_selectMajor6 = findViewById(R.id.btn_selectMajor6);
        btn_selectMajor7 = findViewById(R.id.btn_selectMajor7);

        btn_selectMajor1.setOnClickListener(myClickListener);
        btn_selectMajor2.setOnClickListener(myClickListener);
        btn_selectMajor3.setOnClickListener(myClickListener);
        btn_selectMajor4.setOnClickListener(myClickListener);
        btn_selectMajor5.setOnClickListener(myClickListener);
        btn_selectMajor6.setOnClickListener(myClickListener);
        btn_selectMajor7.setOnClickListener(myClickListener);
    }

    View.OnClickListener myClickListener = new
            View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch ((v.getId())) {
                        case R.id.btn_selectMajor1:
                            Intent intent = new
                                    Intent(selectMajor.this, selectDoctor.class);
                            intent.putExtra("major", "내과");
                            startActivity(intent);
                            break;
                        case R.id.btn_selectMajor2:
                            intent = new
                                    Intent(selectMajor.this, selectDoctor.class);
                            intent.putExtra("major", "외과");
                            startActivity(intent);
                            break;
                        case R.id.btn_selectMajor3:
                            intent = new
                                    Intent(selectMajor.this, selectDoctor.class);
                            intent.putExtra("major", "정신과");
                            startActivity(intent);
                            break;
                        case R.id.btn_selectMajor4:
                            intent = new
                                    Intent(selectMajor.this, selectDoctor.class);
                            intent.putExtra("major", "신경과");
                            startActivity(intent);
                            break;
                        case R.id.btn_selectMajor5:
                            intent = new
                                    Intent(selectMajor.this, selectDoctor.class);
                            intent.putExtra("major", "산부인과");
                            startActivity(intent);
                            break;
                        case R.id.btn_selectMajor6:
                            intent = new
                                    Intent(selectMajor.this, selectDoctor.class);
                            intent.putExtra("major", "비뇨기과");
                            startActivity(intent);
                            break;
                        case R.id.btn_selectMajor7:
                            intent = new
                                    Intent(selectMajor.this, selectDoctor.class);
                            intent.putExtra("major", "안과");
                            startActivity(intent);
                            break;
                    }
                }
            };
}
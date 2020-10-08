package com.example.lifecare.health;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.lifecare.R;

public class health extends AppCompatActivity {
//1.일단 패키지 생성 액티비티->엠티엑티비티 생성 ->MainActivity로 가보자
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);
    }
}
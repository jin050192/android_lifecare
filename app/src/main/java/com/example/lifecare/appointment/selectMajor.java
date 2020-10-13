package com.example.lifecare.appointment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.lifecare.R;

public class selectMajor extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_major);
        getSupportActionBar().hide();
    }
    public void selectDoctor1(View w){
        Intent intent = new Intent(getApplicationContext(), selectDoctor.class);
        intent.putExtra("major", "내과");
        startActivity(intent);
    }

    public void selectDoctor2(View w){
        Intent intent = new Intent(getApplicationContext(), selectDoctor.class);
        intent.putExtra("major", "외과");
        startActivity(intent);
    }
    public void selectDoctor3(View w){
        Intent intent = new Intent(getApplicationContext(), selectDoctor.class);
        intent.putExtra("major", "정신과");
        startActivity(intent);
    }
    public void selectDoctor4(View w){
        Intent intent = new Intent(getApplicationContext(), selectDoctor.class);
        intent.putExtra("major", "신경과");
        startActivity(intent);
    }
    public void selectDoctor5(View w){
        Intent intent = new Intent(getApplicationContext(), selectDoctor.class);
        intent.putExtra("major", "산부인과");
        startActivity(intent);
    }
    public void selectDoctor6(View w){
        Intent intent = new Intent(getApplicationContext(), selectDoctor.class);
        intent.putExtra("major", "비뇨기과");
        startActivity(intent);
    }
    public void selectDoctor7(View w){
        Intent intent = new Intent(getApplicationContext(), selectDoctor.class);
        intent.putExtra("major", "안과");
        startActivity(intent);
    }
}
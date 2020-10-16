package com.example.lifecare.appointment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lifecare.MainActivity;
import com.example.lifecare.R;
import com.example.lifecare.information.hospitalRoom;
import com.example.lifecare.information.information;

public class appointment extends AppCompatActivity {
    private Button btn_appointment;
    private Button btn_ReservationChk;
    private Button btn_ResultChk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

    }

    public void selectMajor(View w){
        Intent intent = new Intent(getApplicationContext(), selectMajor.class);
        startActivity(intent);
    }
    public void confirmReservation(View w){
        Intent intent = new Intent(getApplicationContext(), confirmReservation.class);
        startActivity(intent);
    }
    public void diagnosis(View w){
        Intent intent = new Intent(getApplicationContext(), diagnosis.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(appointment.this, MainActivity.class);
        startActivity(intent);
    }
}
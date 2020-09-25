package com.example.lifecare.appointment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lifecare.R;
import com.example.lifecare.information.hospitalRoom;
import com.example.lifecare.information.information;

public class appointment extends AppCompatActivity {
    private Button btn_appointment;
    private Button btn_ReservationChk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        btn_appointment = findViewById(R.id.btn_appointment);
        btn_ReservationChk = findViewById(R.id.btn_ReservationChk);

        btn_appointment.setOnClickListener(myClickListener);
        btn_ReservationChk.setOnClickListener(myClickListener);


    }

    View.OnClickListener myClickListener = new
            View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch ((v.getId())) {
                        case R.id.btn_appointment:
                            Intent intent = new
                                    Intent(appointment.this, selectMajor.class);
                            startActivity(intent);
                            break;

                        case R.id.btn_ReservationChk:
                            intent = new
                                    Intent(appointment.this, confirmReservation.class);
                            startActivity(intent);
                            break;
                    }
                }
            };
}
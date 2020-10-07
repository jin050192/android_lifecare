package com.example.lifecare.information;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lifecare.R;


public class information extends AppCompatActivity {
    Button Room;
    Button Map;
    Button Parm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        Room = (Button) findViewById(R.id.btn_prevealRoom);
        Map = (Button) findViewById(R.id.btn_findMap);
        Parm = (Button) findViewById(R.id.btn_findPharm);

        Room.setOnClickListener(myClickListener);
        Map.setOnClickListener(myClickListener);
        Parm.setOnClickListener(myClickListener);

    }

    View.OnClickListener myClickListener = new
            View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch ((v.getId())) {
                        case R.id.btn_prevealRoom:
                            Intent intent = new
                                    Intent(information.this, hospitalRoom.class);
                            startActivity(intent);
                            break;
                        case R.id.btn_findMap:
                            Intent intent1 = new
                                    Intent(information.this, location.class);
                            startActivity(intent1);
                            break;

                        case R.id.btn_findPharm:
                            Intent intent2 = new
                                    Intent(information.this, pharmacy.class);
                            startActivity(intent2);
                            break;

                    }
                }
            };
}

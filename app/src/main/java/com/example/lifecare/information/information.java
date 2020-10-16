
package com.example.lifecare.information;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lifecare.MainActivity;
import com.example.lifecare.R;
import com.example.lifecare.appointment.appointment;


public class information extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
    }

    public void findMap(View w){
        Intent intent = new Intent(getApplicationContext(), location.class);
        startActivity(intent);
    }
    public void findPharm(View w){
        Intent intent = new Intent(getApplicationContext(), googleMap.class);
        startActivity(intent);
    }
    public void prevealRoom(View w){
        Intent intent = new Intent(getApplicationContext(), hospitalRoom.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(information.this, MainActivity.class);
        startActivity(intent);
    }
}

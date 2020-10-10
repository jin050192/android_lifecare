
package com.example.lifecare.information;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lifecare.R;


public class information extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        getSupportActionBar().hide();
    }

    public void findMap(View w){
        Intent intent = new Intent(getApplicationContext(), pharmacy.class);
        startActivity(intent);
    }
    public void findPharm(View w){
        Intent intent = new Intent(getApplicationContext(), location.class);
        startActivity(intent);
    }

    public void prevealRoom(View w){
        Intent intent = new Intent(getApplicationContext(), hospitalRoom.class);
        startActivity(intent);
    }

}

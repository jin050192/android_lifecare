package com.example.lifecare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.lifecare.EclipseConnect.SignInActivity;
import com.example.lifecare.appointment.appointment;
import com.example.lifecare.drug.drugSearch;
import com.example.lifecare.information.hospitalRoom;
import com.example.lifecare.information.information;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*하단바*/
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_helth, R.id.navigation_deeplearningcare,
                R.id.navigation_mypage, R.id.navigation_menu)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        /*상단바 숨기기*/
        getSupportActionBar().hide();
    }

    public void enterMypage(View w){
        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
        startActivity(intent);
    }
    public void drugSearch(View w){
        Intent intent = new Intent(getApplicationContext(), drugSearch.class);
        startActivity(intent);
    }
    public void hospitalRoom(View w){
        Intent intent = new Intent(getApplicationContext(), hospitalRoom.class);
        startActivity(intent);
    }
    public void appointment(View w){
        Intent intent = new Intent(getApplicationContext(), appointment.class);
        startActivity(intent);
    }
    public void information(View w){
        Intent intent = new Intent(getApplicationContext(), information.class);
        startActivity(intent);
    }
}
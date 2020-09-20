package com.example.lifecare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.lifecare.EclipseConnect.SharedPreferenceHandler;
import com.example.lifecare.EclipseConnect.SignInActivity;
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

        SharedPreferenceHandler sh = new SharedPreferenceHandler(getApplicationContext());
        Toast toast = Toast.makeText(getApplicationContext(), sh.getId(), Toast.LENGTH_SHORT);
        toast.show();
        System.out.println("id : "+sh.getId());
        startActivity(intent);
    }
}
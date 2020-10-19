package com.example.lifecare.food;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifecare.R;
import com.example.lifecare.VO.FoodVO;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import noman.weekcalendar.WeekCalendar;
import noman.weekcalendar.listener.OnDateClickListener;

public class FoodToday extends AppCompatActivity {

    private WeekCalendar weekCalendar;

    private RecyclerView recyclerView;
    private AdapterListFood mAdapter;

    private TextView totalKcal;
    private ProgressBar kcal_progress;
    private TextView totalCarbo;
    private ProgressBar carbo_progress;
    private TextView totalProtein;
    private ProgressBar protein_progress;
    private TextView totalFat;
    private ProgressBar fat_progress;

    private ArrayList<FoodVO> arrayList;


    SimpleDateFormat format1 = new SimpleDateFormat( "yyyy-MM-dd");
    Date time;

    float totalKcalInfo=0;
    float totalCarboInfo=0;
    float totalProteinInfo=0;
    float totalFatInfo=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_today);

        time = new Date();
        arrayList = new ArrayList<FoodVO>();

        recyclerView = (RecyclerView) findViewById(R.id.todayFoodRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        mAdapter = new AdapterListFood(FoodToday.this ,arrayList, R.layout.item_food);
        //recyclerView.setAdapter(mAdapter); 어뎁터에 정보넣기


        totalKcal = (TextView) findViewById(R.id.totalKcal);
        kcal_progress = (ProgressBar) findViewById(R.id.kcal_progress);

        carbo_progress = (ProgressBar) findViewById(R.id.carbo_progress);
        totalCarbo= (TextView)findViewById(R.id.totalCarbo);

        protein_progress = (ProgressBar) findViewById(R.id.protein_progress);
        totalProtein= (TextView)findViewById(R.id.totalProtein);

        fat_progress = (ProgressBar) findViewById(R.id.fat_progress);
        totalFat= (TextView)findViewById(R.id.totalFat);


        /*상단바 숨기기*/
        getSupportActionBar().hide();

        weekCalendar = findViewById(R.id.weekCalendar);

        DbOpenHelper mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();
        mDbOpenHelper.create();

        Cursor iCursor = mDbOpenHelper.selectColumns();
        while(iCursor.moveToNext()){
            String date = iCursor.getString(iCursor.getColumnIndex("date"));
            String foodName = iCursor.getString(iCursor.getColumnIndex("foodName"));
            String kcal = iCursor.getString(iCursor.getColumnIndex("kcal"));
            String carbo = iCursor.getString(iCursor.getColumnIndex("carbo"));
            String protein = iCursor.getString(iCursor.getColumnIndex("protein"));
            String fat = iCursor.getString(iCursor.getColumnIndex("fat"));
            int foodNum = iCursor.getInt(iCursor.getColumnIndex("foodNum"));
            int foodImg =  iCursor.getInt(iCursor.getColumnIndex("foodImg"));

            if(date.equals(format1.format(time))){
                FoodVO foodVO = new FoodVO();
                foodVO.setDate(date);
                foodVO.setFoodName(foodName);
                foodVO.setKcal(kcal);
                foodVO.setCarbo(carbo);
                foodVO.setProtein(protein);
                foodVO.setFat(fat);
                foodVO.setFoodNum(foodNum);
                foodVO.setFoodImg(foodImg);

                totalKcalInfo = totalKcalInfo+Float.valueOf(foodVO.getKcal());
                totalCarboInfo = totalCarboInfo+Float.valueOf(foodVO.getCarbo());
                totalProteinInfo = totalProteinInfo + Float.valueOf(foodVO.getProtein());
                totalFatInfo=totalFatInfo+Float.valueOf(foodVO.getFat());

                arrayList.add(foodVO);
            }


            //progress bar 설정
            kcal_progress.setProgress((int)totalKcalInfo);
            carbo_progress.setProgress((int)totalCarboInfo);
            protein_progress.setProgress((int)totalProteinInfo);
            fat_progress.setProgress((int)totalFatInfo);

            //정보 설정
            totalKcal.setText(Integer.toString((int)totalKcalInfo));
            totalCarbo.setText(Integer.toString((int)totalCarboInfo) + "/315g");
            totalProtein.setText(Integer.toString((int)totalProteinInfo) + "/97g");
            totalFat.setText(Integer.toString((int)totalFatInfo) + "/58g");
        }

        recyclerView.setAdapter(mAdapter);

        weekCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(DateTime dateTime) {
                Toast.makeText(FoodToday.this,
                        "당신의 선택" + dateTime.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
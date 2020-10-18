package com.example.lifecare.food;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lifecare.VO.FoodVO;

public class DbOpenHelper {
    public static int KeyNum = 0;
    private static final String DATABASE_NAME = "InnerDatabase(SQLite).db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(DataBases.CreateDB._CREATE0);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS "+DataBases.CreateDB._TABLENAME0);
            onCreate(db);
        }
    }

    public DbOpenHelper(Context context){
        this.mCtx = context;
    }

    public DbOpenHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void create(){
        mDBHelper.onCreate(mDB);
    }

    public void close(){
        mDB.close();
    }

    //입력
    public long insertColumn(FoodVO foodVO){
        ContentValues values = new ContentValues();
        values.put(DataBases.CreateDB.key, KeyNum);
        KeyNum++;
        values.put(DataBases.CreateDB.date, foodVO.getDate());
        values.put(DataBases.CreateDB.foodName, foodVO.getFoodName());
        values.put(DataBases.CreateDB.kcal, foodVO.getKcal());
        values.put(DataBases.CreateDB.carbo, foodVO.getCarbo());
        values.put(DataBases.CreateDB.protein, foodVO.getProtein());
        values.put(DataBases.CreateDB.fat, foodVO.getFat());
        values.put(DataBases.CreateDB.foodNum, foodVO.getFoodNum());
        values.put(DataBases.CreateDB.foodImg, foodVO.getFoodImg());

        return mDB.insert(DataBases.CreateDB._TABLENAME0, null, values);
    }

    public Cursor selectColumns(){
        return mDB.query(DataBases.CreateDB._TABLENAME0, null, null, null, null, null, null);
    }
}

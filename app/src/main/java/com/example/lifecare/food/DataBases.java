package com.example.lifecare.food;

import android.provider.BaseColumns;

public final class DataBases {

    public static final class CreateDB implements BaseColumns {
        public static final String key = "key";
        public static final String date = "date";
        public static final String foodName = "foodName";
        public static final String kcal = "kcal";
        public static final String carbo = "carbo";
        public static final String protein = "protein";
        public static final String fat = "fat";
        public static final String foodNum = "foodNum";
        public static final String foodImg = "foodImg";

        public static final String _TABLENAME0 = "foodTable";
        public static final String _CREATE0 = "create table if not exists "+_TABLENAME0+"("
                +key+" integer primary key autoincrement, "
                +date+" text not null , "
                +foodName+" text not null , "
                +kcal+" text not null , "
                +carbo+" text not null , "
                +protein+" text not null , "
                +fat+" text not null , "
                +foodNum+" integer not null ,"
                +foodImg+" integer not null );";
    }
}
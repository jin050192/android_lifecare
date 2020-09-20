package com.example.lifecare.EclipseConnect;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;



public class SharedPreferenceHandler {
    private final String PREF_NAME = "com.example.lifecare";
    public final static String PREF_INTRO_USER_AGREEMENT = "PREF_USER_AGREEMENT";
    public final static String PREF_MAIN_VALUE = "PREF_MAIN_VALUE";
    static Context mContext;

    public SharedPreferenceHandler(Context c) {
        mContext = c;
    }

    public void keepId(String id){
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("id", id);
        editor.commit();
        System.out.println("keepId 완료");
    }

    public String getId() {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);

        System.out.println("getId 진입");
        try {
            return pref.getString("id", null);
        } catch (Exception e) {
            return null;
        }
    }
}

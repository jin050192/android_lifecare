package com.example.lifecare.VO;

import java.util.ArrayList;

public class Roomkey {

    private static Roomkey roomkey= new Roomkey();
    public static Roomkey getInstance(){
        return roomkey;
    }

    private String key;
    private ArrayList<String> arrkey;


    public Roomkey(ArrayList<String> arrkey) {
        this.arrkey = arrkey;
    }

    public Roomkey() {

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ArrayList<String> getArrkey() {
        return arrkey;
    }

    public void setArrkey(ArrayList<String> arrkey) {
        this.arrkey = arrkey;
    }
}

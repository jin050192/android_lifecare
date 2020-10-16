package com.example.lifecare.health;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

public class cold {
    public static void main(String[] args) throws IOException{
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/HealthWthrIdxService/getColdIdx"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "uutsUEWuFzKy4nkWv1JphMk4xVf%2BH93E%2FuKNXhEd%2FoIsnfpj5x6rElE28Ey4p5RdAoQtdD1%2FL1y06T5nvtQC6Q%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("uutsUEWuFzKy4nkWv1JphMk4xVf%2BH93E%2FuKNXhEd%2FoIsnfpj5x6rElE28Ey4p5RdAoQtdD1%2FL1y06T5nvtQC6Q%3D%3D", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("XML", "UTF-8")); /*요청자료형식(XML/JSON)*/
        urlBuilder.append("&" + URLEncoder.encode("areaNo","UTF-8") + "=" + URLEncoder.encode("1100000000", "UTF-8")); /*서울지점*/
        urlBuilder.append("&" + URLEncoder.encode("time","UTF-8") + "=" + URLEncoder.encode("2016093006", "UTF-8")); /*‘16년 9월 30일 6시 발표*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;

        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300)
        {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else
        {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null){
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
    }

}

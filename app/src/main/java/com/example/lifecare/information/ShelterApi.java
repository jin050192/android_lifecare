package com.example.lifecare.information;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.ArrayList;

public class ShelterApi {
    private static String ServiceKey = "1yDgnRDIYuksG7FCf0aaQ01uax68WID8fnPSe7tTi8YzkNiBuQULLoXhkGhOdoMWRAJGkqI4gNQmnJeaLGkGlw%3D%3D"; //공공데이터 사이트를 통해 발급 받은 API키
    public ShelterApi() {
        try {
            apiParserSearch();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public ArrayList<MapPoint> apiParserSearch() throws Exception {
        URL url = new URL(getURLParam(null));

        System.out.println("===========================ShelterApi url = "+ url);

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        BufferedInputStream bis = new BufferedInputStream(url.openStream());

        System.out.println("================== bis "+bis);

        xpp.setInput(bis, "utf-8");

        String tag = null;
        int event_type = xpp.getEventType();

        ArrayList<MapPoint> mapPoint = new ArrayList<MapPoint>();

        String yadmNm = null,  xPos = null, yPos = null;
        boolean bYadmNm = false, bXPos = false, bYPos = false;

        while (event_type != XmlPullParser.END_DOCUMENT) {
            if (event_type == XmlPullParser.START_TAG) {
                tag = xpp.getName();
                if(tag.equals("yadmNm")) {
                    bYadmNm = true;
                }
                if(tag.equals("XPos")) {
                    bXPos = true;
                }
                if(tag.equals("YPos")) {
                    bYPos = true;
                }
            } else if (event_type == XmlPullParser.TEXT) {
                if(bYadmNm == true){
                    yadmNm = xpp.getText();
                    System.out.println("=================== BPLCNM : 출력" +  yadmNm);
                    bYadmNm = false;
                } if(bXPos == true){
                    xPos = xpp.getText();
                    System.out.println("=================== X : 출력" +  xPos);
                    bXPos = false;
                } if(bYPos ==true){
                    yPos = xpp.getText();
                    System.out.println("=================== Y : 출력" + yPos);
                    bYPos = false;
                }
            } else if (event_type == XmlPullParser.END_TAG) {
                tag = xpp.getName();
                if (tag.equals("row")) {
                    MapPoint entity = new MapPoint();

                    try{
                        entity.setYadmNm(yadmNm);
                        entity.setXPos(Double.valueOf(xPos));
                        entity.setXPos(Double.valueOf(yPos));
                        mapPoint.add(entity);
                    }catch(Exception e){
                        System.out.println("==============좌표없음");
                    }

                    System.out.println(mapPoint.size());
                }
            }
            event_type = xpp.next();
        }
        System.out.println(mapPoint.size());

        return mapPoint;
    }

    private String getURLParam(String search){
        //String url = " http://apis.data.go.kr/B551182/pharmacyInfoService/getParmacyBasisList?ServiceKey=" + ServiceKey;
        String url = "http://apis.data.go.kr/B551182/pharmacyInfoService/getParmacyBasisList?ServiceKey=" + ServiceKey/* + "&type=xml&pageNo=1&numOfRows=10&xPos=16&yPos=18"*/;
        return url;

    }

    public static void main(String[] args) {
        new ShelterApi();
    }
}

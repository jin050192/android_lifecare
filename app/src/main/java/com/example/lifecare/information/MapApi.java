package com.example.lifecare.information;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.ArrayList;

public class MapApi extends AppCompatActivity {

    public static class ShelterApi {

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

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            BufferedInputStream bis = new BufferedInputStream(url.openStream());
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
                        bYadmNm= true;
                    }
                    if(tag.equals("xPos")) {
                        bXPos = true;
                    }
                    if(tag.equals("yPos")){
                        bYPos = true;
                    }
                } else if (event_type == XmlPullParser.TEXT) {
                    if(bYadmNm == true){
                        yadmNm = xpp.getText();
                        bYadmNm = false;
                    } else if(bXPos == true){
                        xPos = xpp.getText();
                        bXPos = false;
                    }else if(bYPos ==true){
                        yPos = xpp.getText();
                        bYPos = false;
                    }
                } else if (event_type == XmlPullParser.END_TAG) {
                    tag = xpp.getName();
                    if (tag.equals("row")) {
                        MapPoint entity = new MapPoint();
                        entity.setYadmNm(yadmNm);
                        entity.setXPos(Double.valueOf(xPos));
                        entity.setyPos(Double.valueOf(yPos));
                        mapPoint.add(entity);
                        System.out.println(mapPoint.size());
                    }
                }
                event_type = xpp.next();
            }
            System.out.println("=====================사이즈" +mapPoint.size());

            for(MapPoint pt : mapPoint){
                System.out.print("====================포인트 : "+ pt.toString());
            }

            return mapPoint;
        }


        private String getURLParam(String search){
            String url = "http://apis.data.go.kr/B551182/pharmacyInfoService/getParmacyBasisList?ServiceKey=" + ServiceKey/* + "&type=xml&pageNo=1&numOfRows=10&xPos=16&yPos=18"*/;
            return url;
        }

    }

}

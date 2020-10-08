package com.example.lifecare;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lifecare.VO.Roomkey;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Covid extends AppCompatActivity {

    private String htmlPageUrl = "http://ncov.mohw.go.kr/bdBoardList_Real.do?brdId=1&brdGubun=11&ncvContSeq=&contSeq=&board_id=&gubun=";
    private String htmlPageUrl2 = "http://ncov.mohw.go.kr/baroView.do?brdId=4&brdGubun=41";
    private TextView kr1 = null;
    private TextView kr2 = null;
    private TextView kr3 = null;
    private TextView covidinformation = null;
    private TextView yesterday = null;

    private String kr1data= "";
    private String kr2data= "";
    private String kr3data= "";
    private String kr4data= "";
    private String kr5data= "";

    Roomkey rkey = Roomkey.getInstance();

    int cnt=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid);

        kr1 = (TextView)findViewById(R.id.kr1);
        kr2 = (TextView)findViewById(R.id.kr2);
        kr3 = (TextView)findViewById(R.id.kr3);
        yesterday = (TextView)findViewById(R.id.yesterday);
        covidinformation = (TextView)findViewById(R.id.covidinformation);
        covidinformation.setMovementMethod(new ScrollingMovementMethod());


        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();
        /*상단바 숨기기*/
        getSupportActionBar().hide();
    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                Document doc = Jsoup.connect(htmlPageUrl).get();
                Document doc2 = Jsoup.connect(htmlPageUrl2).get();

                //확진자수(한국)
                Elements krtitles1 = doc.select(".ca_value").eq(0);
                kr1data = krtitles1.text();

                //완치자(한국)
                Elements krtitles2 = doc.select(".ca_value").eq(2);
                kr2data = krtitles2.text();

                //사망자(한국)
                Elements krtitles3 = doc.select(".ca_value").eq(6);
                kr3data = krtitles3.text();

                //전일대비
                Elements krtitles4 = doc2.select(".ta_l").eq(10);
                kr4data = krtitles4.text();

                //전일대비
                Elements krtitles5 = doc2.select(".ta_l");
                kr5data = krtitles5.text();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            kr1.setText(kr1data);
            kr2.setText(kr2data);
            kr3.setText(kr3data);
            yesterday.setText(kr4data);
            covidinformation.setText(kr5data);
        }
    }
}

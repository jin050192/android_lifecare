package com.example.lifecare;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//그래프
//그래프

public class Covid extends AppCompatActivity {

    private String htmlPageUrl = "http://ncov.mohw.go.kr/bdBoardList_Real.do?brdId=1&brdGubun=11&ncvContSeq=&contSeq=&board_id=&gubun=";
    private String htmlPageUrl2 = "http://ncov.mohw.go.kr/baroView.do?brdId=4&brdGubun=41";
    private TextView kr1 = null;
    private TextView kr2 = null;
    private TextView kr3 = null;
    private TextView kr4 = null;

    private TextView kr1_1 = null;
    private TextView kr2_1 = null;
    private TextView kr3_1 = null;
    private TextView kr4_1 = null;

    private TextView text = null;

    private String kr1data= "";
    private String kr2data= "";
    private String kr3data= "";
    private String kr4data= "";

    private String textdata= "";

    private String kr1_1data= "";
    private String kr2_1data= "";
    private String kr3_1data= "";
    private String kr4_1data= "";

    private String agedata1 = "";
    private String agedata2 = "";
    private String agedata3 = "";
    private String agedata4 = "";
    private String agedata5 = "";
    private String agedata6 = "";
    private String agedata7 = "";
    private String agedata8 = "";

    private BarChart barChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid);

        kr1 = (TextView)findViewById(R.id.kr1); //확진환자
        kr2 = (TextView)findViewById(R.id.kr2); //격리해제
        kr3 = (TextView)findViewById(R.id.kr3); //격리중
        kr4 = (TextView)findViewById(R.id.kr4); //사망

        text = (TextView)findViewById(R.id.prevention); //예방수칙

        //전일대비
        kr1_1 = (TextView)findViewById(R.id.kr1_1); //확진환자
        kr2_1 = (TextView)findViewById(R.id.kr2_1); //격리해제
        kr3_1 = (TextView)findViewById(R.id.kr3_1); //격리중
        kr4_1 = (TextView)findViewById(R.id.kr4_1); //사망

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

                //확진환자
                Elements krtitles1 = doc.select(".ca_value").eq(0);
                kr1data = krtitles1.text();

                //확진환자(전일대비)
                Elements krtitles1_1 = doc.select(".ca_value").eq(1);
                Elements krtitles1_2 = krtitles1_1.select(".inner_value").eq(0);
                kr1_1data = krtitles1_2.text();

                //격리해제
                Elements krtitles2 = doc.select(".ca_value").eq(2);
                kr2data = krtitles2.text();

                //격리해제(전일대비)
                Elements krtitles2_1 = doc.select(".ca_value").eq(3);
                kr2_1data = krtitles2_1.text();

                //격리중
                Elements krtitles3 = doc.select(".ca_value").eq(4);
                kr3data = krtitles3.text();

                //격리중(전일대비)
                Elements krtitles3_1 = doc.select(".ca_value").eq(5);
                kr3_1data = krtitles3_1.text();

                //사망
                Elements krtitles4 = doc.select(".ca_value").eq(6);
                kr4data = krtitles4.text();

                //사망(전일대비)
                Elements krtitles4_1 = doc.select(".ca_value").eq(7);
                kr4_1data = krtitles4_1.text();

                //예방수칙
                Elements text_1 = doc2.select(".ta_l").eq(10);
                textdata = text_1.text();

                //예방수칙
                Elements datas0 = doc.select(".num tbody").eq(3);
                System.out.println("------------" + datas0);
                Elements datas1 = datas0.select("td span").eq(0);
                agedata1 = datas1.text();
                Elements datas2 = datas0.select("td span").eq(5);
                agedata2 = datas2.text();
                Elements datas3 = datas0.select("td span").eq(10);
                agedata3 = datas3.text();
                Elements datas4 = datas0.select("td span").eq(15);
                agedata4 = datas4.text();
                Elements datas5 = datas0.select("td span").eq(20);
                agedata5 = datas5.text();
                Elements datas6 = datas0.select("td span").eq(25);
                agedata6 = datas6.text();
                Elements datas7 = datas0.select("td span").eq(30);
                agedata7 = datas7.text();
                Elements datas8 = datas0.select("td span").eq(35);
                agedata8 = datas8.text();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            text.setText(textdata);

            kr1_1.setText(kr1_1data);
            kr2_1.setText(kr2_1data);
            kr3_1.setText(kr3_1data);
            kr4_1.setText(kr4_1data);

            ValueAnimator animator1 = ValueAnimator.ofInt(0, Integer.parseInt(kr1data.replace(",",""))); //0 is min number, 600 is max number
            animator1.setDuration(3000); //Duration is in milliseconds
            animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    kr1.setText(animation.getAnimatedValue().toString());
                }
            });
            animator1.start();

            ValueAnimator animator2 = ValueAnimator.ofInt(0, Integer.parseInt(kr2data.replace(",",""))); //0 is min number, 600 is max number
            animator2.setDuration(3000); //Duration is in milliseconds
            animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    kr2.setText(animation.getAnimatedValue().toString());
                }
            });
            animator2.start();

            ValueAnimator animator3 = ValueAnimator.ofInt(0, Integer.parseInt(kr3data.replace(",",""))); //0 is min number, 600 is max number
            animator3.setDuration(3000); //Duration is in milliseconds
            animator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    kr3.setText(animation.getAnimatedValue().toString());
                }
            });
            animator3.start();

            ValueAnimator animator4 = ValueAnimator.ofInt(0, Integer.parseInt(kr4data.replace(",",""))); //0 is min number, 600 is max number
            animator4.setDuration(3000); //Duration is in milliseconds
            animator4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    kr4.setText(animation.getAnimatedValue().toString());
                }
            });
            animator4.start();

            float graph1 = Integer.parseInt(agedata1.replace(",",""));
            float graph2 = Integer.parseInt(agedata2.replace(",",""));
            float graph3 = Integer.parseInt(agedata3.replace(",",""));
            float graph4 = Integer.parseInt(agedata4.replace(",",""));
            float graph5 = Integer.parseInt(agedata5.replace(",",""));
            float graph6 = Integer.parseInt(agedata6.replace(",",""));
            float graph7 = Integer.parseInt(agedata7.replace(",",""));
            float graph8 = Integer.parseInt(agedata8.replace(",",""));

            //그래프
            barChart = (BarChart)findViewById(R.id.chart);

            List<BarEntry> entries = new ArrayList<>();

            entries.add(new BarEntry(10f, graph1));
            entries.add(new BarEntry(20f, graph2));
            entries.add(new BarEntry(30f, graph3));
            entries.add(new BarEntry(40f, graph4));
            entries.add(new BarEntry(50f, graph5));
            entries.add(new BarEntry(60f, graph6));
            entries.add(new BarEntry(70f, graph7));
            entries.add(new BarEntry(80f, graph8));

            BarDataSet barDataSet = new BarDataSet(entries, "");
            barDataSet.setDrawValues(false);

            BarData barData = new BarData(barDataSet);
            barChart.setData(barData);
            barData.setBarWidth(5f);

            XAxis xAxis = barChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextColor(Color.BLACK);
            xAxis.enableGridDashedLine(8, 24, 0);

            ArrayList<String> age = new ArrayList();
            age.add("10대");
            age.add("20대");
            age.add("30대");
            age.add("40대");
            age.add("50대");
            age.add("60대");
            age.add("70대");
            age.add("80대");
            barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(age));


            YAxis yLAxis = barChart.getAxisLeft();
            yLAxis.setTextColor(Color.BLACK);

            YAxis yRAxis = barChart.getAxisRight();
            yRAxis.setDrawLabels(false);
            yRAxis.setDrawAxisLine(false);
            yRAxis.setDrawGridLines(false);

            Description description = new Description();
            description.setText("");

            barChart.setDoubleTapToZoomEnabled(false);
            barChart.setDrawGridBackground(false);
            barChart.setDescription(description);
            barChart.animateY(2000, Easing.EasingOption.EaseInCubic);
            barChart.invalidate();
            //그래프
            startCountAnimation();
        }
    }

    private void startCountAnimation() {

    }
}

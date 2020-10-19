package com.example.lifecare.drug;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.lifecare.EclipseConnect.Web;
import com.example.lifecare.R;

public class drugSearch extends AppCompatActivity {
    private  WebView webView;
    private String url = "http://192.168.219.133/lifecare/android_drugSearch";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_durg_search);

        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClientClass());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()){
            webView.goBack();
            return true;
        }
        return  super.onKeyDown(keyCode,event);
    }

    private class  WebViewClientClass extends  WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
           view.loadUrl(url);
           return  true;
        }
    }
}
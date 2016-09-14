package com.feicui.edu.highpart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        WebView wbv = (WebView) findViewById(R.id.wbv);

        //设置一些webview的属性
        wbv.getSettings().setJavaScriptEnabled(true);
//        wbv.getSettings().setBuiltInZoomControls(true);
        Intent intent = getIntent();
        if (intent != null) {
//            News news = (News) intent.getSerializableExtra("url");
             String url = intent.getStringExtra("url");
            //设置webview的客户端,
            wbv.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }
            });
            wbv.loadUrl(url);
        }
    }
}

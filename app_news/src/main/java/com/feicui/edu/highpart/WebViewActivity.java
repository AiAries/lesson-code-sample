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

        Intent intent = getIntent();
        if (intent != null) {
            String url = intent.getStringExtra("url");
//            final String url1 = "http://m.baidu.com/";
            //设置webview的客户端,
            wbv.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false/*!url.equals(url1)*/;
                }
            });
            wbv.loadUrl(url);
        }
    }
}

package com.feicui.edu.highpart;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import com.feicui.edu.highpart.common.CommonUtil;
import com.feicui.edu.highpart.common.Const;
import com.feicui.edu.highpart.common.OkHttpUtil;
import com.feicui.edu.highpart.common.SharedPreferenceUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        //Init toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置返回箭头可用
        //给图片设置的点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        WebView wbv = (WebView) findViewById(R.id.wbv);
        //设置一些webview的属性
        wbv.getSettings().setJavaScriptEnabled(true);//可以执行网页的javascript代码
//        wbv.getSettings().setSupportZoom(true);
//        wbv.getSettings().setBuiltInZoomControls(true);
        final Intent intent = getIntent();
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

        //发送评论
        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nid = intent.getIntExtra("nid",0);
                sendComment(nid);
            }
        });
    }

    private void sendComment(int nid) {
        EditText et_comment = (EditText) findViewById(R.id.et_comment);
        String content = et_comment.getText().toString().trim();
        //cmt_commit?ver=版本号&nid=新闻编号&token=用户令牌&imei=手机标识符&ctx=评论内容

        Map<String, String> p = new HashMap<>();
        p.put("ver", CommonUtil.getVersionCode(this) + "");
        p.put("nid", nid+"");
        p.put("token", SharedPreferenceUtil.getToken(this));
        p.put("imei", "8989"/*SystemUtils.getIMEI(this)*/);
        p.put("ctx", content);

        //发送评论给服务器,需要异步请求
        new UpLoadCommentTask().execute(p);
    }
    class UpLoadCommentTask extends AsyncTask< Map<String, String>, Void, String> {

        @Override
        protected String doInBackground(Map<String, String>... params) {
            try {
                return OkHttpUtil.postString(Const.URL_USER_COMMENT, params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                Toast.makeText(WebViewActivity.this, s, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(WebViewActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        }
    }
}

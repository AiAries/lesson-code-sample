package com.feicui.edu.highpart;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import com.feicui.edu.highpart.bean.BaseEntity;
import com.feicui.edu.highpart.bean.Comment;
import com.feicui.edu.highpart.bean.News;
import com.feicui.edu.highpart.biz.LocalCommentDBManager;
import com.feicui.edu.highpart.biz.LocalNewsDBManager;
import com.feicui.edu.highpart.biz.LocalNewsSQLiteOP;
import com.feicui.edu.highpart.common.CommonUtil;
import com.feicui.edu.highpart.common.Const;
import com.feicui.edu.highpart.common.OkHttpUtil;
import com.feicui.edu.highpart.common.SharedPreferenceUtil;
import com.feicui.edu.highpart.common.UrlComposeUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WebViewActivity extends AppCompatActivity {

    private WebView wbv;
    private EditText et_comment;
    private MenuItem item;
    private News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        //Init toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        et_comment = (EditText) findViewById(R.id.et_comment);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置返回箭头可用
        //给图片设置的点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        wbv = (WebView) findViewById(R.id.wbv);
        //设置一些webview的属性
        wbv.getSettings().setJavaScriptEnabled(true);//可以执行网页的javascript代码
//        wbv.getSettings().setSupportZoom(true);
//        wbv.getSettings().setBuiltInZoomControls(true);
        final Intent intent = getIntent();
        if (intent != null) {
            news = (News) intent.getSerializableExtra("news");
             String url = news.getLink();
            //设置webview的客户端,
            wbv.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }
            });
            wbv.loadUrl(url);
        }
        final int nid = news.getNid();
        //发送评论
        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComment(nid);
            }
        });

        //加载当前新闻评论的数量
        loadCommentCount(nid);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_comment_count,menu);
        item = menu.findItem(R.id.menu_comment_count);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == R.id.menu_comment_count) {
            //跳到展示评论页面
            Intent intent = new Intent(WebViewActivity.this, ShowCommentActivity.class);
            intent.putExtra("news", news);
            startActivity(intent);
        }else if (itemId == R.id.menu_favorite) {
            //本地收藏，存到sqlite
            MyApplication application = (MyApplication) getApplication();
            LocalNewsSQLiteOP localNewsSQLiteOP = application.localNewsSQLiteOP;
            String msg;
            if (LocalNewsDBManager.isExistNews(localNewsSQLiteOP, news.getNid())) {
                msg = "已收藏";
            } else {
                long insert = LocalNewsDBManager.insert(localNewsSQLiteOP, news);
                if (insert > 0) {
                    msg = "收藏成功";
                } else {
                    msg  = "收藏失败";
                }
            }
            Toast.makeText(WebViewActivity.this,msg , Toast.LENGTH_SHORT).show();
        }else if (itemId == R.id.menu_share) {
           //TODO 分享
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadCommentCount(int nid) {
        //cmt_num?ver=版本号& nid=新闻编号
        Map<String, String> p = new HashMap<>();
        p.put("ver", CommonUtil.getVersionCode(this) + "");
        p.put("nid", nid+"");
        String urlPath = UrlComposeUtil.getUrlPath(Const.URL_USER_COMMENT_COUNT, p);
        new LoadCommentCountTask().execute(urlPath);
    }

    class LoadCommentCountTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            String path = params[0];
            return OkHttpUtil.getString(path);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //解析json字符串
            Gson gson = new Gson();
            BaseEntity entity = gson.fromJson(s, BaseEntity.class);
            if (entity == null) {
                Toast.makeText(WebViewActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            } else {
                double count = (double) entity.getData();//评论数量
                item.setTitle("评论数"+(int)count);
            }

        }
    }

    private void sendComment(int nid) {

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
                Comment comment = new Comment();//自己制造数据
                comment.setContent(et_comment.getText().toString());
                comment.setStamp(CommonUtil.getDate());//评论的时间
                comment.setPortrait("file:///assets/a7.jpg");
                LocalCommentDBManager.insert(WebViewActivity.this, comment);
                Toast.makeText(WebViewActivity.this, s, Toast.LENGTH_SHORT).show();
                wbv.requestFocus();//获取焦点
                et_comment.setText("");//清空评论
            } else {
                Toast.makeText(WebViewActivity.this, s, Toast.LENGTH_SHORT).show();
            }
            CommonUtil.hideKeyBoard(WebViewActivity.this);
        }
    }
}

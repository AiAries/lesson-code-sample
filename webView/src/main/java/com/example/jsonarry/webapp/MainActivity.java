package com.example.jsonarry.webapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{

    String  url = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n" +
            "<HTML>\n" +
            "<HEAD>\n" +
            "<TITLE> New Document </TITLE>\n" +
            "<META NAME=\"Generator\" CONTENT=\"EditPlus\">\n" +
            "<META NAME=\"Author\" CONTENT=\"\">\n" +
            "<META NAME=\"Keywords\" CONTENT=\"\">\n" +
            "<META NAME=\"Description\" CONTENT=\"\">\n" +
            "</HEAD>\n" +
            "\n" +
            "<BODY>\n" +
            "<input type=\"button\" value=\"Say hello\" onClick=\"showAndroidToast('Hello Android!')\" />\n" +
            "<script type=\"text/javascript\">\n" +
            "    function showAndroidToast(toast) {\n" +
            "        Android.showToast(toast);\n" +
            "    }\n" +
            "</script>\n" +
            "</BODY>\n" +
            "</HTML>\n";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//       AssetManager assets = getAssets();
//        try
//        {
//            String[] list = assets.list("");
//            InputStream inputStream = assets.open(list[0]);
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"gb2312"));
//            String buffer = null;
//            StringBuilder sb = new StringBuilder();
//            while((buffer = bufferedReader.readLine())!=null)
//            {
//                sb.append(buffer).append("\n");
//            }
//            String url = sb.toString();
//            if (BuildConfig.DEBUG) Log.d("MainActivity", url);
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
        WebView webView = (WebView) findViewById(R.id.webView);
//        webView.loadUrl("file:///android_asset/login_03.html");//success
        webView.loadUrl("http://m.baidu.com/");
//        webView.loadUrl("http://192.168.1.10:8080/login.html");//failed to do
//        webView.loadData(url,"text/html",null);// success
        webView.setWebViewClient(new MyWebViewClient());
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
//        settings.setUseWideViewPort(true);
//        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);//使用内置的缩放装置
//        settings.setDisplayZoomControls(false);
//        webView.addJavascriptInterface(new WebAppInterface(this), "Android");

    }
    private class MyWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            /*String host = Uri.parse(url).getHost();
            Toast.makeText(MainActivity.this, host, Toast.LENGTH_SHORT).show();
            if (host.equals("m.baidu.com")) {
                // This is my web site, so do not override; let my WebView load the page
                return false;
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);*/
            return true;
        }
    }
}
 class WebAppInterface {
    Context mContext;
    /** Instantiate the interface and set the context */
    WebAppInterface(Context c) {
        mContext = c;
    }
    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }
}

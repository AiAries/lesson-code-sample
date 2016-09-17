package com.feicui.edu.highpart;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.feicui.edu.highpart.asyntask.HttpUtil;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
        Log.d("", "tearDown: ");
    }


    @Override
    protected void runTest() throws Throwable {
        super.runTest();
        String url = "http://192.168.2.35:8080/newsClient/news_sort?ver=1&imei=1";
        Log.d("ApplicationTest", "runTest: "+ HttpUtil.getJsonString(url));
        assertNotNull("strObj is  null",HttpUtil.getJsonString(url));
    }
}
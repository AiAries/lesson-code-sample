package com.feicui.edu.highpart;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
public class TestMainActivity extends ActivityInstrumentationTestCase2<MainActivity>{

    MainActivity activity;
    public TestMainActivity() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        assertNotNull(activity);
    }

    //这个方法是来测试点击按钮改变textview的文字
    public void testChangeTextView() throws Throwable {
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.changetext(null);
            }
        });
        assertEquals(MainActivity.TAG,activity.textView.getText().toString());
    }
}

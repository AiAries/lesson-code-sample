package edu.feicui.com.houserkeeper.util;

import android.util.Log;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class LogUtil {

    public static boolean isOpenDebugLog = true;

    public static void logD(String tag, String msg) {
        if (isOpenDebugLog)
            Log.d(tag, msg);
    }


}

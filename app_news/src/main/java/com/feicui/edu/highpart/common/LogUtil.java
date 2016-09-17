package com.feicui.edu.highpart.common;

import android.util.Log;

public class LogUtil {

	public static final String TAG = "LogUtil";
	public static boolean isDebug = true;
	public static boolean isInfo = true;
	public static boolean isWarn = true;
	public static boolean isError = true;

	public static void d(String tag, String msg) {
		if (isDebug)
			Log.d(tag, msg);
	}

	public static void d(String tag, String msg, Throwable t) {
		if (isDebug)
			Log.d(tag, msg, t);
	}

	public static void i(String tag, String msg) {
		if (isInfo)
			Log.i(tag, msg);
	}

	public static void i(String tag, String msg, Throwable t) {
		if (isInfo)
			Log.i(tag, msg, t);
	}

	public static void w(String tag, String msg) {
		if (isWarn)
			Log.w(tag, msg);
	}

	public static void w(String tag, String msg, Throwable t) {
		if (isWarn)
			Log.w(tag, msg, t);
	}

	public static void e(String tag, String msg) {
		if (isError)
			Log.e(tag, msg);
	}

	public static void e(String tag, String msg, Throwable t) {
		if (isError)
			Log.e(tag, msg, t);
	}
}

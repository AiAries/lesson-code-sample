package com.feicui.edu.highpart.common;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	private static Toast toast;
	
	public static void show(Context context ,String mag,int duration){
		if(toast == null){
			toast = Toast.makeText(context, mag, duration);
			
		}
		toast.show();
	}

}

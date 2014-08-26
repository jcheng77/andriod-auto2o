package com.cettco.buycar.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class User {

	public static boolean isLogin(Context context){
		SharedPreferences settings = context.getSharedPreferences("user", 0);
		return settings.getBoolean("islogin", false);
	}
	public static void setPhone(Context context,String phone){
		SharedPreferences settings = context.getSharedPreferences("user", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("phone", phone);
		editor.commit();
	}
	public static void login(Context context){
		SharedPreferences settings = context.getSharedPreferences("user", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("islogin", true);
		editor.commit();
	}
	public static void logout(Context context){
		SharedPreferences settings = context.getSharedPreferences("user", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("islogin", true);
		editor.commit();
	}
}

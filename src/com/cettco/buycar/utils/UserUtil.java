package com.cettco.buycar.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class UserUtil {

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
	public static String getPhone(Context context){
		SharedPreferences settings = context.getSharedPreferences("user", 0);
		return settings.getString("phone", "****");
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
		editor.putBoolean("islogin", false);
		editor.commit();
	}
	public static void setUserId(Context context,String id){
		SharedPreferences settings = context.getSharedPreferences("user", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("id", id);
		editor.commit();
	}
	public static String getUserId(Context context){
		SharedPreferences settings = context.getSharedPreferences("user", 0);
		return settings.getString("id", "-1");
	}
}

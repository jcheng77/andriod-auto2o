package com.cettco.buycar.activity;

import java.io.IOException;
import java.util.ArrayList;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.cettco.buycar.R;
import com.cettco.buycar.adapter.CarTrimViewPagerAdapter;
import com.cettco.buycar.utils.BaiduPushUtils;
import com.cettco.buycar.utils.db.DataBaseUtil;
import com.umeng.analytics.MobclickAgent;

import android.R.anim;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		PushManager.startWork(getApplicationContext(),
				PushConstants.LOGIN_TYPE_API_KEY,
				BaiduPushUtils.getMetaValue(SplashActivity.this, "api_key"));
		SharedPreferences settings = getSharedPreferences("installed", 0);
		if (settings.getBoolean("first", true)) {
			int versionCode = getVersionCode(SplashActivity.this);
			SharedPreferences.Editor editor = settings.edit();
			editor.putBoolean("first", false);
			editor.putInt("version", versionCode);
			editor.commit();
			//
			copyDataBaseToPhone("ormliteandroid_brand");
			copyDataBaseToPhone("ormliteandroid_color");
			copyDataBaseToPhone("ormliteandroid_maker");
			copyDataBaseToPhone("ormliteandroid_model");
			copyDataBaseToPhone("ormliteandroid_trim");
			//
			Intent intent = new Intent();
			intent.setClass(SplashActivity.this, IntroductionActivity.class);
			startActivity(intent);
			this.finish();
		} else {
			int versionCode = getVersionCode(SplashActivity.this);
			if (versionCode == settings.getInt("version", -1)) {
				Handler h = new Handler();
				h.postDelayed(new Runnable() {
					public void run() {
						Intent intent = new Intent();
						intent.setClass(SplashActivity.this, MainActivity.class);
						startActivity(intent);
						SplashActivity.this.finish();
					}

				}, 2000);
			} else {
				SharedPreferences.Editor editor = settings.edit();
				editor.putBoolean("first", false);
				editor.putInt("version", versionCode);
				editor.commit();
				//
				copyDataBaseToPhone("ormliteandroid_brand");
				copyDataBaseToPhone("ormliteandroid_color");
				copyDataBaseToPhone("ormliteandroid_maker");
				copyDataBaseToPhone("ormliteandroid_model");
				copyDataBaseToPhone("ormliteandroid_trim");
				//
				Intent intent = new Intent();
				intent.setClass(SplashActivity.this, IntroductionActivity.class);
				startActivity(intent);
				overridePendingTransition(android.R.anim.fade_out, android.R.anim.fade_in);
				this.finish();
			}
		}

	}

	private int getVersionCode(Context context) {
		int versionCode = 0;
		try {
			// 获取软件版本号，对应AndroidManifest.xml下android:versionCode
			versionCode = context.getPackageManager().getPackageInfo(
					"com.cettco.buycar", 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}

	private void copyDataBaseToPhone(String dbName) {
		DataBaseUtil util = new DataBaseUtil(this);
		// 判断数据库是否存在
		util.setDataBaseName(dbName);
		boolean dbExist = util.checkDataBase();
		if (dbExist) {
			try {
				util.deleteDataBase();
				util.copyDataBase();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i("tag", "The database is exist.");
		} else {// 不存在就把raw里的数据库写入手机
			try {
				util.copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
	}
}

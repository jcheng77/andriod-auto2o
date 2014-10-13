package com.cettco.buycar.activity;

import java.util.ArrayList;

import com.cettco.buycar.R;
import com.cettco.buycar.adapter.CarTypeViewPagerAdapter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;

public class SplashActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		SharedPreferences settings = getSharedPreferences("installed", 0);
		if (settings.getBoolean("first", true)) {
			SharedPreferences.Editor editor = settings.edit();
			editor.putBoolean("first", false);
			editor.commit();
			Intent intent = new Intent();
			intent.setClass(SplashActivity.this, IntroductionActivity.class);
			startActivity(intent);
			this.finish();
		}
		else{
			
	        Handler h = new Handler();
	        h.postDelayed(new Runnable() {
	            public void run() {
	            	Intent intent = new Intent();
	    			intent.setClass(SplashActivity.this, MainActivity.class);
	    			startActivity(intent);
	    			SplashActivity.this.finish();
	            }

	        }, 2000);
		}
		
	}

}

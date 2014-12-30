package com.cettco.buycar.activity;

import java.util.ArrayList;

import com.cettco.buycar.R;
import com.cettco.buycar.adapter.CarTrimViewPagerAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class IntroductionActivity extends Activity{
	private ArrayList<View> pagerList;
	private ViewPager viewPager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_introduction);
		viewPager = (ViewPager) findViewById(R.id.introduction_Pager);
		pagerList = new ArrayList<View>();
		
		for (int i = 1; i <=2; i++) {
			LayoutInflater inflater = getLayoutInflater().from(this);
			View view = inflater
					.inflate(R.layout.introduction_background_item, null);
			ImageView imageView = (ImageView)view.findViewById(R.id.introImgview);
			String bgName = "intro_bg"+i;
			imageView.setBackgroundResource(this.getResources().getIdentifier(bgName,
		            "drawable", this.getPackageName()));
			view.getHeight();
			pagerList.add(view);
		}
		LayoutInflater inflater = getLayoutInflater().from(this);
		View view = inflater
				.inflate(R.layout.introduction_background_item, null);
		ImageView imageView = (ImageView)view.findViewById(R.id.introImgview);
		imageView.setBackgroundResource(R.drawable.intro_bg3);
		Button enterButton=(Button)view.findViewById(R.id.intro_enterBtn);
		enterButton.setVisibility(View.VISIBLE);
		enterButton.setOnClickListener(enterClickListener);
		pagerList.add(view);
		CarTrimViewPagerAdapter carTypeViewPagerAdapter = new CarTrimViewPagerAdapter(
				pagerList);
		viewPager.setAdapter(carTypeViewPagerAdapter);
	}

	protected OnPageChangeListener viewChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}
	};
	protected OnClickListener enterClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(IntroductionActivity.this, MainActivity.class);
			startActivity(intent);
			IntroductionActivity.this.finish();
		}
	};
	public void enterClick(View view){
		Intent intent = new Intent();
		intent.setClass(IntroductionActivity.this, MainActivity.class);
		startActivity(intent);
		this.finish();
	}
}

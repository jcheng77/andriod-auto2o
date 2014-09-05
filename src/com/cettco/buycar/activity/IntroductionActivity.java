package com.cettco.buycar.activity;

import java.util.ArrayList;

import com.cettco.buycar.R;
import com.cettco.buycar.adapter.CarTypeViewPagerAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;

public class IntroductionActivity extends Activity{
	private ArrayList<View> pagerList;
	private ViewPager viewPager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_introduction);
		viewPager = (ViewPager) findViewById(R.id.pager);
		pagerList = new ArrayList<View>();
		LayoutInflater inflater = getLayoutInflater().from(this);
		for (int i = 0; i < 5; i++) {
			View view = inflater
					.inflate(R.layout.car_type_selection_item, null);
			view.getHeight();
			pagerList.add(view);
		}
		CarTypeViewPagerAdapter carTypeViewPagerAdapter = new CarTypeViewPagerAdapter(
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
}

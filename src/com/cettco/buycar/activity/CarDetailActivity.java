package com.cettco.buycar.activity;

import java.util.ArrayList;

import com.cettco.buycar.R;
import com.cettco.buycar.adapter.CarTypeViewPagerAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class CarDetailActivity extends ActionBarActivity{

	private ArrayList<View> pagerList;
	private ViewPager viewPager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cardetail);
		getActionBar().hide();
//		LinearLayout selectCarTypeLayout = (LinearLayout)findViewById(R.id.selectCarType_layout);
//		selectCarTypeLayout.setOnClickListener(selectCartypeClickListener);
		
		//Viewpager
		viewPager = (ViewPager)findViewById(R.id.pager);
		pagerList = new ArrayList<View>();
		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);   
		for(int i = 0;i<5;i++){
			View view = inflater.inflate(R.layout.car_type_selection_item, null);
			pagerList.add(view);
		}
		CarTypeViewPagerAdapter carTypeViewPagerAdapter = new CarTypeViewPagerAdapter(pagerList);
		viewPager.setAdapter(carTypeViewPagerAdapter);
		
		Button beginBiddingBtn = (Button)findViewById(R.id.begin_bid);
		beginBiddingBtn.setOnClickListener(beginBiddingClickListener);
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
	protected OnClickListener beginBiddingClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(CarDetailActivity.this, SubmitOrderActivity.class);
			startActivity(intent);
			
		}
	};
	protected OnClickListener selectCartypeClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(CarDetailActivity.this, SelectCarTypeActivity.class);
			startActivity(intent);
		}
	};
	public void exitClick(View view)
	{
		finish();
	}
}

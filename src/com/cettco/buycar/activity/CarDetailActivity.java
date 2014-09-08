package com.cettco.buycar.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cettco.buycar.R;
import com.cettco.buycar.adapter.CarTypeViewPagerAdapter;
import com.cettco.buycar.entity.CarColorListEntity;
import com.cettco.buycar.entity.CarTypeEntity;
import com.cettco.buycar.entity.TrimEntity;
import com.cettco.buycar.utils.Data;
import com.google.gson.Gson;

public class CarDetailActivity extends Activity {

	private ArrayList<View> pagerList;
	private ViewPager viewPager;
	private CarTypeEntity carTypeEntity;
	private ArrayList<TrimEntity> trimList;
	private ImageView carImageView;
	private TextView titleTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cardetail);
		//getActionBar().hide();
		// LinearLayout selectCarTypeLayout =
		// (LinearLayout)findViewById(R.id.selectCarType_layout);
		// selectCarTypeLayout.setOnClickListener(selectCartypeClickListener);

		// Viewpager
		titleTextView = (TextView)findViewById(R.id.title_text);
		titleTextView.setText("选择车型");
		carImageView = (ImageView)findViewById(R.id.carDetail_img);
		carTypeEntity = new Gson().fromJson(getIntent().getStringExtra("model"), CarTypeEntity.class);
		trimList = carTypeEntity.getTrims();
		viewPager = (ViewPager) findViewById(R.id.pager);
		pagerList = new ArrayList<View>();
		LayoutInflater inflater = getLayoutInflater().from(this);
		
		for (int i = 0; i < trimList.size(); i++) {
			View view = inflater
					.inflate(R.layout.car_type_selection_item, null);
			view.getHeight();
			TextView trimNameTextView = (TextView)view.findViewById(R.id.trim_name);
			TextView  trimPriceTextView = (TextView)view.findViewById(R.id.trim_guidePrice);
			trimNameTextView.setText(trimList.get(i).getName());
			trimPriceTextView.setText(trimList.get(i).getGuide_price());
			pagerList.add(view);
		}
		CarTypeViewPagerAdapter carTypeViewPagerAdapter = new CarTypeViewPagerAdapter(
				pagerList);
		viewPager.setAdapter(carTypeViewPagerAdapter);

		Button beginBiddingBtn = (Button) findViewById(R.id.begin_bid);
		beginBiddingBtn.setOnClickListener(beginBiddingClickListener);
		
		Data.IMAGE_CACHE.get(carTypeEntity.getPic_url(),carImageView);
	}
	@Override
	protected void onResume(){
		super.onResume();
		
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
			CarColorListEntity colorListEntity = new CarColorListEntity();
			colorListEntity.setColors(carTypeEntity.getColors());
			intent.putExtra("color", new Gson().toJson(colorListEntity));
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

	public void exitClick(View view) {
		finish();
	}
}

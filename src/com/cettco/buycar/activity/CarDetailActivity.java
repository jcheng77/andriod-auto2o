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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cettco.buycar.R;
import com.cettco.buycar.adapter.CarTypeViewPagerAdapter;
import com.cettco.buycar.entity.CarColorListEntity;
import com.cettco.buycar.entity.CarTypeEntity;
import com.cettco.buycar.entity.TrimEntity;
import com.cettco.buycar.utils.Data;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendPosition;
import com.google.gson.Gson;

public class CarDetailActivity extends Activity {

	private ArrayList<View> pagerList;
	private ViewPager viewPager;
	private CarTypeEntity carTypeEntity;
	private ArrayList<TrimEntity> trimList;
	private ImageView carImageView;
	private TextView titleTextView;
	private LineChart mChart;
	private RelativeLayout view4sLayout;
	private int[] mColors = new int[] { R.color.vordiplom_1, R.color.vordiplom_2, R.color.vordiplom_3 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cardetail);
		//getActionBar().hide();
		// LinearLayout selectCarTypeLayout =
		// (LinearLayout)findViewById(R.id.selectCarType_layout);
		// selectCarTypeLayout.setOnClickListener(selectCartypeClickListener);

		
		initChart();
		view4sLayout = (RelativeLayout)findViewById(R.id.view4sLinearLayout);
		view4sLayout.setOnClickListener(view4sClickListener);
		
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
	protected void initChart(){
		mChart = (LineChart) findViewById(R.id.car_price_chart);
		mChart.setDrawYValues(false);

        // enable value highlighting
        mChart.setHighlightEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(false);

        // enable scaling and dragging
        mChart.setDragScaleEnabled(false);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);
        updateChart();
//        Legend l = mChart.getLegend();
//        l.setPosition(LegendPosition.BELOW_CHART_CENTER);
        
	}
	protected void updateChart(){
		ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < 4; i++) {
            xVals.add((i) + "");
        }
		ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();

        for (int z = 0; z < 3; z++) {

            ArrayList<Entry> values = new ArrayList<Entry>();

            for (int i = 0; i < 4; i++) {
                double val = (Math.random() * 20) + 3;
                values.add(new Entry((float) val, i));
            }

            LineDataSet d = new LineDataSet(values, "DataSet " + (z + 1));
            d.setLineWidth(2.5f);
            d.setCircleSize(4f);
            
            int color = getResources().getColor(mColors[z % mColors.length]);
            d.setColor(color);
            d.setCircleColor(color);
            dataSets.add(d);
        }

        // make the first DataSet dashed
        dataSets.get(0).enableDashedLine(10, 10, 0);

        LineData data = new LineData(xVals, dataSets);
        mChart.setData(data);
        mChart.invalidate();
	}
	protected OnClickListener view4sClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(CarDetailActivity.this, DealersListActivity.class);
			startActivity(intent);
		}
	};
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
			intent.setClass(CarDetailActivity.this, BargainActivity.class);
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

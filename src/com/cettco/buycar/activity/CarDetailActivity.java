package com.cettco.buycar.activity;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;



import org.apache.http.Header;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
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
import android.widget.Toast;

import com.cettco.buycar.R;
import com.cettco.buycar.adapter.CarTrimViewPagerAdapter;
import com.cettco.buycar.entity.CarColorListEntity;
import com.cettco.buycar.entity.CarModelEntity;
import com.cettco.buycar.entity.OrderDetailEntity;
import com.cettco.buycar.entity.OrderItemEntity;
import com.cettco.buycar.entity.Tender;
import com.cettco.buycar.entity.TenderEntity;
import com.cettco.buycar.entity.CarTrimEntity;
import com.cettco.buycar.utils.MyApplication;
import com.cettco.buycar.utils.GlobalData;
import com.cettco.buycar.utils.HttpConnection;
import com.cettco.buycar.utils.db.DatabaseHelperModel;
import com.cettco.buycar.utils.db.DatabaseHelperOrder;
import com.cettco.buycar.utils.db.DatabaseHelperTrim;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendPosition;
import com.google.gson.Gson;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;
import com.viewpagerindicator.CirclePageIndicator;

public class CarDetailActivity extends Activity {

	private ArrayList<View> pagerList;
	private ViewPager viewPager;
	private CarModelEntity carTypeEntity;
	private List<CarTrimEntity> trimList;
	private ImageView carImageView;
	private TextView titleTextView;
	private LineChart mChart;
	private RelativeLayout view4sLayout;
	
	private RelativeLayout progressLayout;
	
	private String trim_id;
	private String trim_name;
	private int trim_selection=0;
	
	private int[] mColors = new int[] { R.color.vordiplom_1, R.color.vordiplom_2, R.color.vordiplom_3 };
	
	//
	private String trim;
	private String id;
	private String name;
	
	private String model_id;
	private int order_id;
	private OrderItemEntity orderItemEntity;
	
	private TextView brandMakerTextView;
	private TextView modelTextView;
	private TextView buyerCountTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cardetail);
		progressLayout = (RelativeLayout)findViewById(R.id.progressbar_relativeLayout);
		//getActionBar().hide();
		// LinearLayout selectCarTypeLayout =
		// (LinearLayout)findViewById(R.id.selectCarType_layout);
		// selectCarTypeLayout.setOnClickListener(selectCartypeClickListener);

		
		//initChart();
		
		//view4sLayout = (RelativeLayout)findViewById(R.id.view4sLinearLayout);
		//view4sLayout.setOnClickListener(view4sClickListener);
		
		
		
		titleTextView = (TextView)findViewById(R.id.title_text);
		titleTextView.setText("选择车型");
		carImageView = (ImageView)findViewById(R.id.carDetail_img);
		buyerCountTextView = (TextView)findViewById(R.id.car_detail_buyer_count);
		//String countStr = "现在有<font color='red'>"+new Random().nextInt(50)+"</font>位小伙伴";
		buyerCountTextView.setText(String.valueOf(new Random().nextInt(50)));
//		carTypeEntity = new Gson().fromJson(getIntent().getStringExtra("model"), CarModelEntity.class);
//		trimList = carTypeEntity.getTrims();
		
		order_id=getIntent().getIntExtra("order_id", -1);		
		DatabaseHelperOrder orderHelper = DatabaseHelperOrder.getHelper(this);
		try {
			orderItemEntity=orderHelper.getDao().queryBuilder().where().eq("order_id", order_id).queryForFirst();
			model_id = orderItemEntity.getModel_id();
			String[] name_array = orderItemEntity.getModel().split(" : ");
			//System.out.println(name_array);
			brandMakerTextView = (TextView)findViewById(R.id.carDetail_brandMaker_textview);
			modelTextView = (TextView)findViewById(R.id.carDetail_model_textview);
			brandMakerTextView.setText(name_array[0]+" "+name_array[1]);
			modelTextView.setText(name_array[2]);
//			if(name_array!=null&&name_array.length>=4){
//				brandMakerTextView.setText(name_array[0]+" "+name_array[1]+" "+name_array[2]);
//				//holder.trimTextView.setText(name_array[3]);
//			}if(name_array!=null&&name_array.length==3){
//				brandMakerTextView.setText(name_array[0]+" "+name_array[1]);
//				modelTextView.setText(name_array[2]);
//				//holder.trimTextView.setText(name_array[3]);
//			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		DatabaseHelperModel helperModel = DatabaseHelperModel
				.getHelper(this);
		try {
			carTypeEntity=helperModel.getDao().queryBuilder().where()
			.eq("id",model_id ).queryForFirst();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DatabaseHelperTrim helperTrim = DatabaseHelperTrim
				.getHelper(this);
		try {
			trimList = helperTrim.getDao().queryBuilder().where()
					.eq("model_id",model_id ).query();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//carTypeEntity.get
		viewPager = (ViewPager) findViewById(R.id.pager);
		pagerList = new ArrayList<View>();
		LayoutInflater inflater = getLayoutInflater().from(this);
		
		for (int i = 0; i < trimList.size(); i++) {
			View view = inflater
					.inflate(R.layout.item_trim, null);
			view.getHeight();
//			ImageView preImageView = (ImageView)view.findViewById(R.id.trim_previous_item);
//			ImageView nextImageView = (ImageView)view.findViewById(R.id.trim_next_item);
//			if(i==0&&trimList.size()>1){
//				preImageView.setVisibility(View.INVISIBLE);
//				nextImageView.setVisibility(View.VISIBLE);
//			}else if(i==0&&trimList.size()==1){
//				preImageView.setVisibility(View.INVISIBLE);
//				nextImageView.setVisibility(View.INVISIBLE);
//			}
//			else if (i==trimList.size()-1) {
//				preImageView.setVisibility(View.VISIBLE);
//				nextImageView.setVisibility(View.INVISIBLE);
//			}
			TextView trimNameTextView = (TextView)view.findViewById(R.id.trim_name);
			TextView  trimPriceTextView = (TextView)view.findViewById(R.id.trim_guidePrice);
			TextView  benefitTextView = (TextView)view.findViewById(R.id.trim_benefit);
			//TextView trimLowestPricetexTextView = (TextView)view.findViewById(R.id.trim_lowestPrice);
			trimNameTextView.setText(trimList.get(i).getName());
			trimPriceTextView.setText(trimList.get(i).getGuide_price());
			double guide = Double.valueOf((trimList.get(i).getGuide_price()));
			double low = Double.valueOf((trimList.get(i).getLowest_price()));
			benefitTextView.setText(String.format("%.1f", guide-low));
			//trimLowestPricetexTextView.setText(trimList.get(i).getLowest_price());
			pagerList.add(view);
		}
		if(trimList.size()>0){
			trim_id=trimList.get(0).getId();
		}
		CarTrimViewPagerAdapter carTypeViewPagerAdapter = new CarTrimViewPagerAdapter(
				pagerList);
		viewPager.setAdapter(carTypeViewPagerAdapter);
		
		
		CirclePageIndicator indicator = (CirclePageIndicator)findViewById(R.id.indicator);
        //mIndicator = indicator;
        indicator.setViewPager(viewPager);
        //indicator.setBackgroundColor(0xCCCCFF);
        //indicator.setRadius(10 * density);
        indicator.setPageColor(getResources().getColor(R.color.white));
        indicator.setFillColor(getResources().getColor(R.color.gray));
        indicator.setStrokeColor(getResources().getColor(R.color.deep_gray));
        indicator.setOnPageChangeListener(viewChangeListener);
        //indicator.setStrokeWidth(2 * density);
		//viewPager.isi

		Button beginBiddingBtn = (Button) findViewById(R.id.begin_bid);
		beginBiddingBtn.setOnClickListener(beginBiddingClickListener);
		
		MyApplication.IMAGE_CACHE.get(carTypeEntity.getPic_url(),carImageView);
		//orderItemEntity.setPic_url(carTypeEntity.getPic_url());
	}
	@Override
	protected void onResume(){
		super.onResume();
		MobclickAgent.onResume(this);
		getData();
		
	}
//	protected void initChart(){
//		mChart = (LineChart) findViewById(R.id.car_price_chart);
//		mChart.setDrawYValues(false);
//
//        // enable value highlighting
//        mChart.setHighlightEnabled(false);
//
//        // enable touch gestures
//        mChart.setTouchEnabled(false);
//
//        // enable scaling and dragging
//        mChart.setDragScaleEnabled(false);
//
//        // if disabled, scaling can be done on x- and y-axis separately
//        mChart.setPinchZoom(false);
//        updateChart();
////        Legend l = mChart.getLegend();
////        l.setPosition(LegendPosition.BELOW_CHART_CENTER);
//        
//	}
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
			intent.putExtra("trim_id", trim_id);
			startActivity(intent);
		}
	};
	protected OnPageChangeListener viewChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int index) {
			// TODO Auto-generated method stub
			//System.out.println("index:"+index);
			
			trim_selection = index;
			trim_id = trimList.get(index).getId();
			//System.out.println("trim_id:"+trim_id);
			trim_name = trimList.get(index).getName();
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			//System.out.println("scrolled");
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			//System.out.println("state changed");
		}
	};
	protected OnClickListener beginBiddingClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			orderItemEntity.setTrim_id(trim_id);
			orderItemEntity.setModel(orderItemEntity.getModel()+" : "+(trimList.size()>0?trimList.get(trim_selection).getName():""));
			orderItemEntity.setTime(new Date());
			DatabaseHelperOrder helper = DatabaseHelperOrder.getHelper(CarDetailActivity.this);
			try {
				helper.getDao().update(orderItemEntity);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Intent intent = new Intent();
			intent.setClass(CarDetailActivity.this, BargainPriceActivity.class);
			intent.putExtra("order_id", order_id);
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
	protected void getData() {
		String url = GlobalData.getBaseUrl() + "/cars/trims.json";
		RequestParams params = new RequestParams();
		params.put("model_id", model_id);
		
		HttpConnection.setCookie(getApplicationContext());
		HttpConnection.get(url,params,new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				//System.out.println("fail");
				Message message = new Message();
				message.what = 2;
				mHandler.sendMessage(message);
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				System.out.println("seccuss");
				try {
					String result = new String(arg2, "UTF-8");
					//System.out.println("count:"+result);
					Message message = new Message();
					message.what = 1;
					mHandler.sendMessage(message);
					// System.out.println("result:"+result);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
	}

	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				// dealerListAdapter.updateList(dealerList);
				//updateUI();
				break;
			case 2:
				break;
			}
		};
	};
	private void updateDatabase(){
		OrderItemEntity orderItemEntity = new OrderItemEntity();
		orderItemEntity.setPic_url(carTypeEntity.getPic_url());
		orderItemEntity.setTrim(trim);
		DatabaseHelperOrder helper = DatabaseHelperOrder.getHelper(this);
		try {
			int nums = (int) helper.getDao().queryBuilder().where().eq("name", carTypeEntity.getName()).countOf();
			if(nums==0)
				helper.getDao().create(orderItemEntity);
			else {
				helper.getDao().create(orderItemEntity);
				UpdateBuilder<OrderItemEntity, Integer> updateBuilder = helper.getDao().updateBuilder();
				// set the criteria like you would a QueryBuilder
				updateBuilder.where().eq("name", carTypeEntity.getName());
				// update the value of your field(s)
				updateBuilder.updateColumnValue("trim" /* column */, "trim" /* value */);
				updateBuilder.update();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//updateDatabase();
	}
	private void test() throws SQLException{
		String model_id="";
		DatabaseHelperTrim helperTrim = DatabaseHelperTrim
				.getHelper(this);
		trimList=helperTrim.getDao().queryBuilder().where().eq("model_id", model_id).query();
		
		//write data
		DatabaseHelperModel helperModel = DatabaseHelperModel
				.getHelper(this);
		CarModelEntity modelEntity = helperModel.getDao().queryBuilder().where().eq("id", model_id).queryForFirst();
	}
		public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		}
}

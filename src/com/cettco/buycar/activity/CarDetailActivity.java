package com.cettco.buycar.activity;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import org.apache.http.Header;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.Toast;

import com.cettco.buycar.R;
import com.cettco.buycar.adapter.CarTrimViewPagerAdapter;
import com.cettco.buycar.entity.CarColorListEntity;
import com.cettco.buycar.entity.CarModelEntity;
import com.cettco.buycar.entity.OrderItemEntity;
import com.cettco.buycar.entity.Tender;
import com.cettco.buycar.entity.TenderEntity;
import com.cettco.buycar.entity.CarTrimEntity;
import com.cettco.buycar.utils.Data;
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
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

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
	
	private int[] mColors = new int[] { R.color.vordiplom_1, R.color.vordiplom_2, R.color.vordiplom_3 };
	
	//
	private String trim;
	private String id;
	private String name;
	
	private String model_id;

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

		
		initChart();
		view4sLayout = (RelativeLayout)findViewById(R.id.view4sLinearLayout);
		view4sLayout.setOnClickListener(view4sClickListener);
		
		titleTextView = (TextView)findViewById(R.id.title_text);
		titleTextView.setText("选择车型");
		carImageView = (ImageView)findViewById(R.id.carDetail_img);
//		carTypeEntity = new Gson().fromJson(getIntent().getStringExtra("model"), CarModelEntity.class);
//		trimList = carTypeEntity.getTrims();
		
		model_id=getIntent().getStringExtra("model_id");
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
			ImageView preImageView = (ImageView)view.findViewById(R.id.trim_previous_item);
			ImageView nextImageView = (ImageView)view.findViewById(R.id.trim_next_item);
			if(i==0){
				preImageView.setVisibility(View.INVISIBLE);
				nextImageView.setVisibility(View.VISIBLE);
			}
			else if (i==trimList.size()-1) {
				preImageView.setVisibility(View.VISIBLE);
				nextImageView.setVisibility(View.INVISIBLE);
			}
			TextView trimNameTextView = (TextView)view.findViewById(R.id.trim_name);
			TextView  trimPriceTextView = (TextView)view.findViewById(R.id.trim_guidePrice);
			trimNameTextView.setText(trimList.get(i).getName());
			trimPriceTextView.setText(trimList.get(i).getGuide_price());
			pagerList.add(view);
		}
		if(trimList.size()>0){
			trim_id=trimList.get(0).getId();
		}
		CarTrimViewPagerAdapter carTypeViewPagerAdapter = new CarTrimViewPagerAdapter(
				pagerList);
		viewPager.setAdapter(carTypeViewPagerAdapter);

		Button beginBiddingBtn = (Button) findViewById(R.id.begin_bid);
		beginBiddingBtn.setOnClickListener(beginBiddingClickListener);
		
		Data.IMAGE_CACHE.get(carTypeEntity.getPic_url(),carImageView);
		//orderItemEntity.setPic_url(carTypeEntity.getPic_url());
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
		public void onPageSelected(int index) {
			// TODO Auto-generated method stub
			trim_id = trimList.get(index).getId();
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
			
			String tenderUrl=GlobalData.getBaseUrl()+"/tenders.json";
			Gson gson = new Gson();
			Tender tender = new Tender();
			tender.setDescription("test1");
			tender.setModel("宝马");
			tender.setColor_id(carTypeEntity.getColors().get(0).getId());
			tender.setTrim_id(trim_id);
			TenderEntity tenderEntity = new TenderEntity();
			tenderEntity.setTender(tender);
	        StringEntity entity = null;
	        try {
	        	System.out.println(gson.toJson(tenderEntity).toString());
				entity = new StringEntity(gson.toJson(tenderEntity).toString());
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        String cookieStr=null;
			String cookieName=null;
			PersistentCookieStore myCookieStore = new PersistentCookieStore(
					CarDetailActivity.this);
			if(myCookieStore==null){System.out.println("cookie store null");return;}
			List<Cookie> cookies = myCookieStore.getCookies();
			for (Cookie cookie : cookies) {
				String name =cookie.getName();
				cookieName=name;
				System.out.println(name);
				if(name.equals("_JustBidIt_session")){
					cookieStr=cookie.getValue();
					System.out.println("value:"+cookieStr);
					break;
				}
			}
			if(cookieStr==null||cookieStr.equals("")){
				Toast toast = Toast.makeText(CarDetailActivity.this, "未登录或登录信息失效，请重新登录", Toast.LENGTH_SHORT);
				toast.show();
				System.out.println("cookie null");
				return;
				}
			HttpConnection.getClient().addHeader("Cookie", cookieName+"="+cookieStr);
			HttpConnection.post(CarDetailActivity.this, tenderUrl, null, entity, "application/json;charset=utf-8", new JsonHttpResponseHandler(){

				@Override
				public void onFailure(int statusCode, Header[] headers,
						Throwable throwable, JSONObject errorResponse) {
					// TODO Auto-generated method stub
					super.onFailure(statusCode, headers, throwable, errorResponse);
					progressLayout.setVisibility(View.GONE);
					System.out.println("error");
					System.out.println("statusCode:"+statusCode);
					System.out.println("headers:"+headers);
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					// TODO Auto-generated method stub
					super.onFailure(statusCode, headers, responseString, throwable);
					progressLayout.setVisibility(View.GONE);
					Toast toast = Toast.makeText(CarDetailActivity.this, "提交失败", Toast.LENGTH_SHORT);
					toast.show();
				}

				@Override
				public void onSuccess(int statusCode, Header[] headers,
						JSONObject response) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, response);
					progressLayout.setVisibility(View.GONE);
					System.out.println("success");
					System.out.println("statusCode:"+statusCode);
					
//					for(int i=0;i<headers.length;i++){
//						System.out.println(headers[0]);
//					}
					System.out.println("response:"+response);
					if(statusCode==201){
						try {
							Toast toast = Toast.makeText(CarDetailActivity.this, "提交成功", Toast.LENGTH_SHORT);
							toast.show();
							int id = response.getInt("id");
							Intent intent = new Intent();
							CarColorListEntity colorListEntity = new CarColorListEntity();
							colorListEntity.setColors(carTypeEntity.getColors());
							intent.putExtra("color", new Gson().toJson(colorListEntity));
							intent.putExtra("tender_id", id);
							intent.setClass(CarDetailActivity.this, BargainActivity.class);
							startActivity(intent);
//							
//							Intent intent = new Intent();
//							intent.putExtra("tenderId", id);
//							intent.setClass(CarDetailActivity.this, MyOrderStatusActivity.class);
//							startActivity(intent);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
				}
				
			});
			progressLayout.setVisibility(View.VISIBLE);
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
		updateDatabase();
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
}

package com.cettco.buycar.activity;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONObject;

import cn.trinea.android.common.entity.HttpResponse;

import com.cettco.buycar.R;
import com.cettco.buycar.R.dimen;
import com.cettco.buycar.R.drawable;
import com.cettco.buycar.R.id;
import com.cettco.buycar.R.layout;
import com.cettco.buycar.R.menu;
import com.cettco.buycar.entity.DealerEntity;
import com.cettco.buycar.entity.DealerListEntity;
import com.cettco.buycar.entity.OrderItemEntity;
import com.cettco.buycar.entity.OrderItemListEntity;
import com.cettco.buycar.fragment.MyCarFragment;
import com.cettco.buycar.fragment.SettingsFragment;
import com.cettco.buycar.fragment.WelcomeFragment;
import com.cettco.buycar.utils.Data;
import com.cettco.buycar.utils.HttpConnection;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private SlidingMenu menu;
	private TextView currentCitytTextView;
	private LinearLayout settingLayout;
	private ImageButton drawerButton;
	private LinearLayout cityLayout;
	private LinearLayout accountLayout;
	private ImageButton addImageButton;
	
	private LinearLayout welcomelLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setTitle("Welcome");
		setContentView(R.layout.activity_main);
		//getActionBar().hide();

		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.menu);


		switchFragment(new WelcomeFragment());
		
		
		welcomelLayout = (LinearLayout)findViewById(R.id.welcome_linearLayout);
		welcomelLayout.setOnClickListener(welcomeClickListener);
		
		settingLayout = (LinearLayout) findViewById(R.id.settingLinearlayout);
		settingLayout.setOnClickListener(settingsClickListener);
		
		drawerButton = (ImageButton) findViewById(R.id.actionbar_drawer);
		drawerButton.setOnClickListener(drawerClickListener);
		
		cityLayout = (LinearLayout) findViewById(R.id.selectCityLinearLayout);
		cityLayout.setOnClickListener(selectCityClickListener);

		accountLayout = (LinearLayout) findViewById(R.id.accountLinearLayout);
		accountLayout.setOnClickListener(accountClickListener);

		addImageButton = (ImageButton) findViewById(R.id.addCar_btn);
		addImageButton.setOnClickListener(addCarClickListener);
		
		currentCitytTextView= (TextView)findViewById(R.id.menuCurrentCityTextView);
		SharedPreferences settings = getSharedPreferences("city_selection", 0);
		int selection = settings.getInt("city", 0);
		switch (selection) {
		case 0:
			currentCitytTextView.setText("上海");
			break;
		case 1:
			currentCitytTextView.setText("北京");
			break;
		default:
			break;
		}
		testJson();

	}
	void testJson(){
//		DealerEntity entity = new DealerEntity();
//		entity.setAddress("pudong");
//		entity.setDetail1("test");
//		entity.setDetail2("test");
//		entity.setId("1");
//		entity.setNakedPrice(1000);
//		entity.setSpecialPrice(100);
//		entity.setTotalPrice(10000);
//		DealerListEntity dealerListEntity = new DealerListEntity();
//		ArrayList<DealerEntity> tmpArrayList = new ArrayList<DealerEntity>();
//		tmpArrayList.add(entity);
//		dealerListEntity.setDealer(tmpArrayList);
//		ArrayList<String> tmp2 = new ArrayList<String>();
//		tmp2.add("step1");
//		tmp2.add("step2");
//		tmp2.add("step3");
//		dealerListEntity.setStatus(tmp2);
//		Gson gson = new Gson();
//		String result = gson.toJson(dealerListEntity);
//		System.out.println("dealer:"+result);
//		OrderItemEntity itemEntity = new OrderItemEntity();
//		itemEntity.setBidNum("4");
//		itemEntity.setId("2");
//		itemEntity.setName("volve");
//		itemEntity.setPrice("10000");
//		itemEntity.setStatus("4 4s bid");
//		itemEntity.setTrim("智能版");
//		ArrayList<OrderItemEntity> tmp = new ArrayList<OrderItemEntity>();
//		tmp.add(itemEntity);
//		
//		OrderItemListEntity itemList = new OrderItemListEntity();
//		itemList.setOrderitems(tmp);
//		Gson gson = new Gson();
//		String result = gson.toJson(itemList);
//		System.out.println("itemlist:"+result);
	}
	protected void getData(){
		String url = "";
		HttpConnection.get(url, new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				//Data.IMAGE_CACHE.get
			}
			
		});
	}
	@Override
	protected void onResume(){
		super.onResume();
		SharedPreferences settings = getSharedPreferences("city_selection", 0);
		int selection = settings.getInt("city", 0);
		switch (selection) {
		case 0:
			currentCitytTextView.setText("上海");
			break;
		case 1:
			currentCitytTextView.setText("北京");
			break;
		default:
			break;
		}
	}
	protected OnClickListener accountClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
//			Intent intent = new Intent();
//			intent.setClass(MainActivity.this, SignInActivity.class);
//			startActivity(intent);
			switchFragment(new MyCarFragment());
			setTitle("My car");
			menu.toggle();
		}
	};
	protected OnClickListener welcomeClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switchFragment(new WelcomeFragment());
			setTitle("Welcome");
			menu.toggle();
		}
	};
	protected OnClickListener addCarClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, CarListActivity.class);
			startActivity(intent);
			
		}
	};

	protected OnClickListener selectCityClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, SelectCityActivity.class);
//			ArrayList<String> myArrayList = new ArrayList<String>();
//			myArrayList.add("Beijin");
//			myArrayList.add("Shanghai");
//			myArrayList.add("Guangzhou");
//			intent.putStringArrayListExtra("list", myArrayList);
			startActivity(intent);
		}
	};
	protected OnClickListener drawerClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			menu.toggle();

		}
	};
	protected OnClickListener settingsClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
//			switchFragment(new SettingsFragment());
//			setTitle("Settings");
//			menu.toggle();
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, MipcaActivityCapture.class);
			startActivity(intent);
		}
	};

	protected void switchFragment(Fragment fragment) {
		getFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
	}

	public void exitClick(View view) {
		finish();
	}
}

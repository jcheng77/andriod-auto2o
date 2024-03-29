package com.cettco.buycar.activity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.http.Header;
import org.json.JSONObject;

import com.cettco.buycar.R;
import com.cettco.buycar.fragment.GuideFragment;
import com.cettco.buycar.fragment.MyCarFragment;
import com.cettco.buycar.fragment.SettingsFragment;
import com.cettco.buycar.utils.HttpConnection;
import com.cettco.buycar.utils.UpdateManager;
import com.cettco.buycar.utils.UserUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private SlidingMenu menu;
	private TextView currentCitytTextView;
	private LinearLayout settingLayout;
	private ImageButton drawerButton;
	private LinearLayout cityLayout;
	private LinearLayout mycarLayout;
	private LinearLayout guideLayout;
	private LinearLayout couponLly;
	private ImageButton addImageButton;
	private TextView titleTextView;

	// private LinearLayout welcomelLayout;
	// private LinearLayout logoutLayout;

	// private ImageView launchingImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setMyTitle("Welcome");
		setContentView(R.layout.activity_main);
		// getActionBar().hide();
		// launchingImageView =
		// (ImageView)findViewById(R.id.launching_imageview);
		titleTextView = (TextView) findViewById(R.id.welcome_actionbar_title_textview);

		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.menu);

		switchFragment(new MyCarFragment());

		// logoutLayout = (LinearLayout)findViewById(R.id.logoutLayout);
		// logoutLayout.setOnClickListener(logoutClickListener);

		// welcomelLayout =
		// (LinearLayout)findViewById(R.id.welcome_linearLayout);
		// welcomelLayout.setOnClickListener(welcomeClickListener);

		settingLayout = (LinearLayout) findViewById(R.id.settingLinearlayout);
		settingLayout.setOnClickListener(settingsClickListener);

		guideLayout = (LinearLayout) findViewById(R.id.guideLinearlayout);
		guideLayout.setOnClickListener(guideClickListener);
		
		couponLly=(LinearLayout) findViewById(R.id.couponLinearlayout);
		couponLly.setOnClickListener(couponClickListener);

		drawerButton = (ImageButton) findViewById(R.id.actionbar_drawer);
		drawerButton.setOnClickListener(drawerClickListener);

		cityLayout = (LinearLayout) findViewById(R.id.selectCityLinearLayout);
		cityLayout.setOnClickListener(selectCityClickListener);

		mycarLayout = (LinearLayout) findViewById(R.id.accountLinearLayout);
		mycarLayout.setOnClickListener(mycarClickListener);

		addImageButton = (ImageButton) findViewById(R.id.addCar_btn);
		addImageButton.setOnClickListener(addCarClickListener);

		currentCitytTextView = (TextView) findViewById(R.id.menuCurrentCityTextView);

		// Launching view
		// launchingImageView.setVisibility(View.GONE);
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
		// Intent intent = new Intent("com.cettco.buycar.checkupdate");
		// //intent.putExtra("msg", "hello receiver.");
		// sendBroadcast(intent);
		// testJson();
		// jpushSetAlias();
		SharedPreferences updatePreferences = getSharedPreferences("update", 0);
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyyMMdd", Locale.getDefault());
		Date date = new Date();
		String date_now = format.format(date);
		String lasted_update= updatePreferences.getString("time", "19700101");
		if(!date_now.equals(lasted_update)){
			UpdateManager manager = new UpdateManager(this);
			// 检查软件更新
			manager.checkUpdate();
		}
		

	}

	void testJson() {
		DateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:sss'Z'");
		Date date = new Date();
		System.out.println("date:" + dateFormat.format(date));
		String dtStart = "2010-10-15T09:27:37Z";
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:sss'Z'");
		try {
			Date date2 = format.parse(dtStart);
			System.out.println("date2:" + date2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void getData() {
		String url = "";
		HttpConnection.get(url, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				// Data.IMAGE_CACHE.get
			}

		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
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
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	// protected OnClickListener logoutClickListener = new OnClickListener() {
	//
	// @Override
	// public void onClick(View arg0) {
	// // TODO Auto-generated method stub
	// UserUtil.logout(MainActivity.this);
	// PersistentCookieStore myCookieStore = new PersistentCookieStore(
	// MainActivity.this);
	// if(myCookieStore==null)return;
	// myCookieStore.clear();
	// switchFragment(new WelcomeFragment());
	// setMyTitle("Welcome");
	// logoutLayout.setVisibility(View.GONE);
	// addImageButton.setVisibility(View.VISIBLE);
	// Toast toast = Toast.makeText(MainActivity.this, "注销成功",
	// Toast.LENGTH_SHORT);
	// toast.show();
	// //menu.toggle();
	// }
	// };
	protected OnClickListener mycarClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			// Intent intent = new Intent();
			// intent.setClass(MainActivity.this, SignInActivity.class);
			// startActivity(intent);
			// if(UserUtil.isLogin(MainActivity.this)){
			// switchFragment(new MyCarFragment());
			// setMyTitle("My car");
			// logoutLayout.setVisibility(View.VISIBLE);
			// addImageButton.setVisibility(View.GONE);
			// menu.toggle();
			// }
			// else {
			// Intent intent = new Intent();
			// intent.setClass(MainActivity.this, SignInActivity.class);
			// startActivity(intent);
			// }
			switchFragment(new MyCarFragment());
			setMyTitle("我的车");
			// logoutLayout.setVisibility(View.VISIBLE);
			// addImageButton.setVisibility(View.GONE);
			menu.toggle();
		}

	};
	// protected OnClickListener welcomeClickListener = new OnClickListener() {
	//
	// @Override
	// public void onClick(View arg0) {
	// // TODO Auto-generated method stub
	// //logoutLayout.setVisibility(View.GONE);
	// //addImageButton.setVisibility(View.VISIBLE);
	// switchFragment(new WelcomeFragment());
	// setMyTitle("首页");
	// menu.toggle();
	// }
	// };
	protected OnClickListener couponClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (UserUtil.isLogin(MainActivity.this)) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, CouponActivity.class);
				startActivity(intent);
			} else {
				Toast toast = Toast.makeText(MainActivity.this,"请登录", Toast.LENGTH_SHORT);
				toast.show();
			}
//			new AlertDialog.Builder(MainActivity.this)
//		    .setTitle("提示")
//		    .setMessage("请预支付订金到拍立行来锁定此优惠价格.")
//		    .setPositiveButton("再考虑一下", new DialogInterface.OnClickListener() {
//		        public void onClick(DialogInterface dialog, int which) { 
//		            // continue with delete
//		        }
//		     })
//		    .setNegativeButton("去支付", new DialogInterface.OnClickListener() {
//		        public void onClick(DialogInterface dialog, int which) { 
//		            // do nothing
//		        }
//		     })
//		    .setIcon(android.R.drawable.ic_dialog_alert)
//		     .show();
//			final Dialog dialog = new Dialog(MainActivity.this);
//			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//			dialog.setCanceledOnTouchOutside(false);
//			dialog.setContentView(R.layout.popup_accept);
//			dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//			dialog.show();
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
			// ArrayList<String> myArrayList = new ArrayList<String>();
			// myArrayList.add("Beijin");
			// myArrayList.add("Shanghai");
			// myArrayList.add("Guangzhou");
			// intent.putStringArrayListExtra("list", myArrayList);
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
			switchFragment(new SettingsFragment());
			setMyTitle("设置");
			// menu.
			// System.out.println(menu.isActivated());
			menu.toggle();
			// System.out.println(menu.isActivated());
			// Intent intent = new Intent();
			// intent.setClass(MainActivity.this, OrderHasDealerActivity.class);
			// startActivity(intent);
		}
	};
	protected OnClickListener guideClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switchFragment(new GuideFragment());
			setMyTitle("买车指南");
			// menu.
			// System.out.println(menu.isActivated());
			menu.toggle();
			// System.out.println(menu.isActivated());
			// Intent intent = new Intent();
			// intent.setClass(MainActivity.this, OrderHasDealerActivity.class);
			// startActivity(intent);
		}
	};

	protected void setMyTitle(String title) {
		titleTextView.setText(title);
	}

	protected void switchFragment(Fragment fragment) {
		getFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
	}

	public void exitClick(View view) {
		finish();
	}
}

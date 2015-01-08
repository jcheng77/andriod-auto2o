package com.cettco.buycar.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HandshakeCompletedListener;

import org.apache.http.Header;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.cettco.buycar.R;
import com.cettco.buycar.entity.Bargain;
import com.cettco.buycar.entity.BargainEntity;
import com.cettco.buycar.entity.CarColorListEntity;
import com.cettco.buycar.entity.CarTrimEntity;
import com.cettco.buycar.entity.OrderItemEntity;
import com.cettco.buycar.entity.Tender;
import com.cettco.buycar.entity.TenderEntity;
import com.cettco.buycar.utils.GlobalData;
import com.cettco.buycar.utils.HttpConnection;
import com.cettco.buycar.utils.UserUtil;
import com.cettco.buycar.utils.db.DatabaseHelperOrder;
import com.cettco.buycar.utils.db.DatabaseHelperTrim;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.umeng.analytics.MobclickAgent;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class BargainActivity extends Activity {

	public final int RESULT_COLOR = 0;
	public final int RESULT_TIME = 1;
	public final int RESULT_LOAN = 2;
	public final int RESULT_LOCATION = 3;
	public final int RESULT_PLATE = 4;
	public final int RESULT_SHOP = 5;

	public final int RESULT_UNAUTHORIZED = 6;
	public final int RESULT_UNPROCESSABLE = 7;

	// private TextView agreementTextView;

	private Button submitButton;

	private TextView colorTextView;
	private RelativeLayout colorLayout;
	private ArrayList<String> colors = new ArrayList<String>();

	private TextView getCarTimeTextView;
	private RelativeLayout getcarTimeLayout;
	private ArrayList<String> getcarTimeList;
	private int getcarTimeSelection = 0;

	private TextView loantTextView;
	private RelativeLayout loanLayout;
	private ArrayList<String> loanList;
	private int loanSelection = 0;

	private TextView locationTextView;
	private RelativeLayout locationLayout;
	private ArrayList<String> locationList;
	private int locationSelection = 0;

	private TextView plateTextView;
	private RelativeLayout plateLayout;
	private ArrayList<String> plateList;
	private int plateSelection = 0;

	private RelativeLayout shopLayout;
	private TextView shoptexTextView;
	private int tender_id;

	private EditText descriptionEditText;

	private RelativeLayout progressLayout;

	private int order_id;
	private String model_id;
	private String trim_id;
	private ArrayList<String> dealers = new ArrayList<String>();
	private OrderItemEntity orderItemEntity = new OrderItemEntity();
	private TextView titleTextView;

	// private EditText priceEditText;
	// private EditText userNameEditText;

	// private SeekBar priceSeekBar;
	private int price;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bargain);
		titleTextView = (TextView) findViewById(R.id.title_text);
		titleTextView.setText("购车需求");
		progressLayout = (RelativeLayout) findViewById(R.id.progressbar_relativeLayout);
		order_id = getIntent().getIntExtra("order_id", -1);
		// priceEditText = (EditText)
		// findViewById(R.id.activity_bargain_myprice_textview);
		DatabaseHelperOrder orderHelper = DatabaseHelperOrder.getHelper(this);
		try {
			orderItemEntity = orderHelper.getDao().queryBuilder().where()
					.eq("order_id", order_id).queryForFirst();
			model_id = orderItemEntity.getModel_id();
			trim_id = orderItemEntity.getTrim_id();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// String trim_id =
		DatabaseHelperTrim helperTrim = DatabaseHelperTrim.getHelper(this);
		try {
			CarTrimEntity trimEntity = helperTrim.getDao().queryBuilder()
					.where().eq("id", trim_id).queryForFirst();
			price = (int) (Double.parseDouble(trimEntity.getGuide_price()) * 10000);
			// priceEditText.setText(String.valueOf(price));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getArray();

		submitButton = (Button) findViewById(R.id.submit_bargain_price_btn);
		submitButton.setOnClickListener(submitBtnClickListener);

		colorLayout = (RelativeLayout) findViewById(R.id.activityBargainColorRelativeLayout);
		colorLayout.setOnClickListener(colorLayoutClickListener);
		colorTextView = (TextView) findViewById(R.id.activity_bargain_color_textview);

		getcarTimeLayout = (RelativeLayout) findViewById(R.id.activity_bargain_getcarTime_layout);
		getcarTimeLayout.setOnClickListener(getCarTimeClickListener);
		getCarTimeTextView = (TextView) findViewById(R.id.activity_bargain_getcarTime_textview);

		loanLayout = (RelativeLayout) findViewById(R.id.activity_bargain_loan_layout);
		loanLayout.setOnClickListener(loanClickListener);
		loantTextView = (TextView) findViewById(R.id.activity_bargain_loan_textview);

		locationLayout = (RelativeLayout) findViewById(R.id.activity_bargain_platelocation_layout);
		locationLayout.setOnClickListener(locationClickListener);
		locationTextView = (TextView) findViewById(R.id.activity_bargain_platelocation_textview);

		plateLayout = (RelativeLayout) findViewById(R.id.activity_bargain_haveplate_layout);
		plateLayout.setOnClickListener(plateClickListener);
		plateTextView = (TextView) findViewById(R.id.activity_bargain_haveplate_textview);

		shopLayout = (RelativeLayout) findViewById(R.id.activity_bargain_4s_layout);
		shopLayout.setOnClickListener(shopBtnClickListener);

		shoptexTextView = (TextView) findViewById(R.id.activity_bargain_4s_textview);

		descriptionEditText = (EditText) findViewById(R.id.activity_bargain_otherDescription_edittext);

		// userNameEditText =
		// (EditText)findViewById(R.id.activity_bargain_user_name_edittext);

		// priceSeekBar = (SeekBar)findViewById(R.id.price_seekbar);
		// priceSeekBar.setOnSeekBarChangeListener(priceBarChangeListener);

	}

	private void getArray() {
		Resources res = getResources();
		String[] tmp = res.getStringArray(R.array.getcarTime_array);
		getcarTimeList = new ArrayList<String>(Arrays.asList(tmp));
		// ArrayList<String> aa= (ArrayList<String>) Arrays.asList(tmp);
		tmp = res.getStringArray(R.array.loan_array);
		loanList = new ArrayList<String>(Arrays.asList(tmp));
		tmp = res.getStringArray(R.array.location_array);
		locationList = new ArrayList<String>(Arrays.asList(tmp));
		SharedPreferences settings = getSharedPreferences("city_selection", 0);
		int selection = settings.getInt("city", 0);
		if (selection == 0)
			locationList.set(0, locationList.get(0) + "(上海)");
		else if (selection == 1)
			locationList.set(0, locationList.get(0) + "(北京)");
		tmp = res.getStringArray(R.array.plate_array);
		plateList = new ArrayList<String>(Arrays.asList(tmp));
	}

	private void updateUI() {
		getCarTimeTextView.setText(getcarTimeList.get(getcarTimeSelection));
		loantTextView.setText(loanList.get(loanSelection));
		locationTextView.setText(locationList.get(locationSelection));
		plateTextView.setText(plateList.get(plateSelection));
		shoptexTextView.setText(Html.fromHtml("已选择<font color='#ff0033'>"
				+ dealers.size() + "</font>家4s店"));
		colorTextView.setText(Html.fromHtml("已选择<font color='#ff0033'>"
				+ colors.size() + "</font>种颜色"));
	}

	private OnSeekBarChangeListener priceBarChangeListener = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			int now = (progress - 50) * 5000 + price;
			// priceEditText.setText(String.valueOf(now));

		}
	};
	protected OnClickListener submitBtnClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			submit();
		}
	};

	private void submit() {
		String cookieStr = null;
		String cookieName = null;
		PersistentCookieStore myCookieStore = new PersistentCookieStore(
				BargainActivity.this);
		if (myCookieStore == null) {
			return;
		}
		List<Cookie> cookies = myCookieStore.getCookies();
		for (Cookie cookie : cookies) {
			String name = cookie.getName();
			cookieName = name;
			//System.out.println(name);
			if (name.equals("_JustBidIt_session")) {
				cookieStr = cookie.getValue();
				//System.out.println("value:" + cookieStr);
				break;
			}
		}
		if (cookieStr == null || cookieStr.equals("")) {
			Toast toast = Toast.makeText(BargainActivity.this, "请先登录",
					Toast.LENGTH_SHORT);
			toast.show();
			Intent intent = new Intent();
			intent.setClass(BargainActivity.this, SignInActivity.class);
			startActivity(intent);
			return;
		}
		String tenderUrl = GlobalData.getBaseUrl() + "/tenders.json?";
		// String price = priceEditText.getText().toString();
		// String userName = userNameEditText.getText().toString();
		String price = orderItemEntity.getPrice();
		String userName = orderItemEntity.getName();
		if (price == null || price.equals("")) {
			Toast toast = Toast.makeText(BargainActivity.this, "请填写价格",
					Toast.LENGTH_SHORT);
			toast.show();
			return;
		}
		if (colors == null || colors.size() == 0) {
			Toast toast = Toast.makeText(BargainActivity.this, "至少选择一种颜色",
					Toast.LENGTH_SHORT);
			toast.show();
			return;
		}
		if (dealers == null || dealers.size() == 0) {
			Toast toast = Toast.makeText(BargainActivity.this, "至少选择一家4s店",
					Toast.LENGTH_SHORT);
			toast.show();
			return;
		}
		if (userName == null || userName.equals("")) {
			Toast toast = Toast.makeText(BargainActivity.this, "请填写购买者姓名",
					Toast.LENGTH_SHORT);
			toast.show();
			return;
		}
		Tender tender = new Tender();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < colors.size(); i++) {
			buffer.append(colors.get(i) + ",");
		}
		if (buffer != null && buffer.length() > 0) {
			buffer.deleteCharAt(buffer.length() - 1);
		}
		tender.setColors_id(buffer.toString());
		tender.setGot_licence(String.valueOf(plateSelection));
		tender.setLoan_option(String.valueOf(loanSelection + 1));
		tender.setTrim_id(trim_id);
		tender.setPickup_time(String.valueOf(getcarTimeSelection));
		tender.setUser_name(userName);
		String locationString = locationList.get(locationSelection);
		tender.setLicense_location(locationString);
		tender.setPrice(price);
		String description = descriptionEditText.getText().toString();
		// tender.setde
		tender.setDescription(description);
		Map<String, String> shops = new HashMap<String, String>();
		for (int i = 0; i < dealers.size(); i++) {
			shops.put(dealers.get(i), "1");
		}
		tender.setShops(shops);
		TenderEntity tenderEntity = new TenderEntity();
		tenderEntity.setTender(tender);

		Gson gson = new Gson();
		//System.out.println(gson.toJson(tenderEntity).toString());
		StringEntity entity = null;
		try {
			// System.out.println(gson.toJson(bargainEntity).toString());
			entity = new StringEntity(gson.toJson(tenderEntity).toString(),
					"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		progressLayout.setVisibility(View.VISIBLE);
		HttpConnection.getClient().addHeader("Cookie",
				cookieName + "=" + cookieStr);
		HttpConnection.post(BargainActivity.this, tenderUrl, null, entity,
				"application/json;charset=utf-8",
				new JsonHttpResponseHandler() {

					@Override
					public void onFailure(int statusCode, Header[] headers,
							Throwable throwable, JSONObject errorResponse) {
						// TODO Auto-generated method stub
						super.onFailure(statusCode, headers, throwable,
								errorResponse);
						progressLayout.setVisibility(View.GONE);
						//System.out.println("error");
						//System.out.println("statusCode:" + statusCode);
						//System.out.println("headers:" + headers);
						if (statusCode == 401) {
							Message msg = new Message();
							msg.what = RESULT_UNAUTHORIZED;
							handler.sendMessage(msg);
						} else if (statusCode == 422) {
							Message msg = new Message();
							msg.what = RESULT_UNPROCESSABLE;
							handler.sendMessage(msg);
						} else {
							Toast toast = Toast.makeText(BargainActivity.this,
									"提交失败,请重新提交", Toast.LENGTH_SHORT);
							toast.show();
						}

					}

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) {
						// TODO Auto-generated method stub
						super.onSuccess(statusCode, headers, response);
						progressLayout.setVisibility(View.GONE);

						//System.out.println("success");
						//System.out.println("statusCode:" + statusCode);

						// for(int i=0;i<headers.length;i++){
						// System.out.println(headers[0]);
						// }
						//System.out.println("response:" + response);
						String tender_id = "";
						if (statusCode == 201) {

							try {
								//System.out.println("id:"
										//+ response.getString("id"));
								tender_id = response.getString("id");
								orderItemEntity.setId(response.getString("id"));
								orderItemEntity.setState(response
										.getString("state"));
								DatabaseHelperOrder orderHelper = DatabaseHelperOrder
										.getHelper(BargainActivity.this);
								orderHelper.getDao().update(orderItemEntity);
							} catch (JSONException | SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Toast toast = Toast.makeText(BargainActivity.this,
									"提交成功", Toast.LENGTH_SHORT);
							toast.show();
							Intent intent = new Intent();
							// intent.putExtra("tenderId", id);
							intent.setClass(BargainActivity.this,
									AliPayActivity.class);
							intent.putExtra("tender_id", tender_id);
							startActivity(intent);
							// Intent intent = new Intent();
							// // intent.putExtra("tenderId", id);
							// intent.setClass(BargainActivity.this,
							// MainActivity.class);
							// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							// startActivity(intent);

						}

					}

				});

	}

	protected OnClickListener colorLayoutClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(BargainActivity.this, SelectCarColorActivity.class);
			intent.putExtra("name", "选择颜色");
			intent.putExtra("tag", 1);
			intent.putExtra("model_id", model_id);
			// intent.putStringArrayListExtra("selected_colors", colors);
			startActivityForResult(intent, 0);
		}
	};
	protected OnClickListener getCarTimeClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(BargainActivity.this, MyBaseListActivity.class);
			intent.putExtra("name", "提车时间");
			intent.putExtra("tag", 1);
			intent.putStringArrayListExtra("list", getcarTimeList);
			startActivityForResult(intent, 1);
		}
	};
	protected OnClickListener loanClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(BargainActivity.this, MyBaseListActivity.class);
			intent.putExtra("name", "是否贷款");
			intent.putExtra("tag", 2);
			intent.putStringArrayListExtra("list", loanList);
			startActivityForResult(intent, 2);
		}
	};
	protected OnClickListener locationClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(BargainActivity.this, MyBaseListActivity.class);
			intent.putExtra("name", "车牌地点");
			intent.putExtra("tag", 3);
			intent.putStringArrayListExtra("list", locationList);
			startActivityForResult(intent, 3);
		}
	};
	protected OnClickListener plateClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(BargainActivity.this, MyBaseListActivity.class);
			intent.putExtra("name", "有无牌照");
			intent.putExtra("tag", 4);
			intent.putStringArrayListExtra("list", plateList);
			startActivityForResult(intent, 4);
		}
	};
	protected OnClickListener shopBtnClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(BargainActivity.this, SelectDealerActivity.class);
			intent.putExtra("name", "选择4s店");
			intent.putExtra("trim_id", trim_id);
			startActivityForResult(intent, 5);

		}
	};

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null)
			return;
		//System.out.println("resultcode :" + resultCode + " requestcode:"
				//+ requestCode);
		Bundle b = data.getExtras();
		switch (resultCode) { // resultCode为回传的标记，我在B中回传的是RESULT_OK
		case RESULT_OK:
			// data为B中回传的Intent
			// int position = b.getInt("result");
			if (requestCode == RESULT_COLOR) {
				colors = b.getStringArrayList("colors");
			} else if (requestCode == RESULT_TIME) {
				getcarTimeSelection = b.getInt("result");
			} else if (requestCode == RESULT_LOAN) {
				loanSelection = b.getInt("result");
			} else if (requestCode == RESULT_LOCATION) {
				locationSelection = b.getInt("result");
			} else if (requestCode == RESULT_PLATE) {
				plateSelection = b.getInt("result");
			} else if (requestCode == RESULT_SHOP) {
				dealers = b.getStringArrayList("dealers");
				//System.out.println("dealer size:" + dealers.size());
			}
			break;
		default:
			break;
		}
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case RESULT_UNAUTHORIZED:
				Toast toast = Toast.makeText(BargainActivity.this,
						"登陆信息失效，请重新登陆", Toast.LENGTH_SHORT);
				toast.show();
				UserUtil.logout(BargainActivity.this);
				PersistentCookieStore myCookieStore = new PersistentCookieStore(
						BargainActivity.this);
				if (myCookieStore != null)
					myCookieStore.clear();
				Intent intent = new Intent();
				intent.setClass(BargainActivity.this, SignInActivity.class);
				startActivity(intent);
				break;
			case RESULT_UNPROCESSABLE:
				Toast toast2 = Toast.makeText(BargainActivity.this,
						"每日订单提交已达上限，请明日再试", Toast.LENGTH_SHORT);
				toast2.show();
			default:
				break;
			}
		}

	};

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		updateUI();
		// Normal case behavior follows
	}

	public void exitClick(View view) {
		this.finish();
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

}

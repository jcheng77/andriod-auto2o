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

import org.apache.http.Header;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.cettco.buycar.R;
import com.cettco.buycar.entity.Bargain;
import com.cettco.buycar.entity.BargainEntity;
import com.cettco.buycar.entity.CarColorListEntity;
import com.cettco.buycar.entity.OrderItemEntity;
import com.cettco.buycar.entity.Tender;
import com.cettco.buycar.entity.TenderEntity;
import com.cettco.buycar.utils.GlobalData;
import com.cettco.buycar.utils.HttpConnection;
import com.cettco.buycar.utils.db.DatabaseHelperOrder;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BargainActivity extends Activity {

	public final int RESULT_COLOR = 0;
	public final int RESULT_TIME = 1;
	public final int RESULT_LOAN = 2;
	public final int RESULT_LOCATION = 3;
	public final int RESULT_PLATE = 4;
	public final int RESULT_SHOP = 5;

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

	private RelativeLayout progressLayout;

	private EditText priceEditText;

	private int order_id;
	private String model_id;
	private String trim_id;
	private ArrayList<String> dealers = new ArrayList<String>();
	private OrderItemEntity orderItemEntity = new OrderItemEntity();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bargain);
		progressLayout = (RelativeLayout) findViewById(R.id.progressbar_relativeLayout);
		order_id = getIntent().getIntExtra("order_id", -1);
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

		priceEditText = (EditText) findViewById(R.id.activity_bargain_myprice_textview);

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

	protected OnClickListener submitBtnClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub

			// AlertDialog.Builder builder = new AlertDialog.Builder(
			// BargainActivity.this);
			// builder.setTitle(R.string.alerttitle);
			// builder.setMessage(R.string.alertmsg)
			// .setPositiveButton("同意并继续",
			// new DialogInterface.OnClickListener() {
			// public void onClick(DialogInterface dialog,
			// int id) {
			// // FIRE ZE MISSILES!
			// dialog.dismiss();
			// // Intent intent = new Intent();
			// // intent.setClass(BargainActivity.this, AliPayActivity.class);
			// // startActivity(intent);
			// submit();
			// }
			// })
			// .setNegativeButton("取消",
			// new DialogInterface.OnClickListener() {
			// public void onClick(DialogInterface dialog,
			// int id) {
			// // User cancelled the dialog
			// dialog.dismiss();
			// }
			// });
			// // Create the AlertDialog object and return it
			// builder.create().show();
			// String url =
			// "http://wappaygw.alipay.com/service/rest.htm?req_data=<direct_trade_create_req><subject>12121212</subject><out_trade_no>12121021</out_trade_no><total_fee>1</total_fee><seller_account_name>che12121</seller_account_name><notify_url>http://www.alipay.com/waptest0504/servlet/NotifyReceiver</notify_url><out_user>outID123</out_user><merchant_url>http://www.alipay.com</merchant_url><pay_expire>10</pay_expire></direct_trade_create_req>&service=alipay.wap.trade.create.direct&sec_id=0001&partner=12112&req_id=11121212&sign=bDfw5%2Bctc3pxzl7emPxqOod4EiPu3BkE0 Um54g4whHT22CwLbOn1gzyE%2BU5SIleGPke2rNQ%3D&format=xml&v=2.0";
			// Intent intent = new Intent();
			// String url2 = "https://wappaygw.alipay.com/service/rest.htm";
			// intent.setClass(BargainActivity.this, AlipayWebActivity.class);
			// intent.putExtra("url", url2);
			// startActivity(intent);
			submit();
			// Intent intent = new Intent();
			// intent.setClass(BargainActivity.this, AliPayActivity.class);
			// startActivity(intent);
		}
	};

	private void submit(){
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
			System.out.println(name);
			if (name.equals("_JustBidIt_session")) {
				cookieStr = cookie.getValue();
				System.out.println("value:" + cookieStr);
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
		String price = priceEditText.getText().toString();
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
		Tender tender = new Tender();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < colors.size(); i++) {
			buffer.append(colors.get(i) + ",");
		}
		if (buffer != null && buffer.length() > 0) {
			buffer.deleteCharAt(buffer.length() - 1);
		}
		System.out.println(buffer.toString());
		tender.setColors_id(buffer.toString());
		tender.setGot_licence(String.valueOf(plateSelection));
		tender.setLoan_option(String.valueOf(loanSelection + 1));
		tender.setModel("111");
		tender.setTrim_id(trim_id);
		tender.setPickup_time(String.valueOf(getcarTimeSelection));
		// tender.setLicense_location(String.valueOf(locationSelection));
		String locationString = "";
		if (locationSelection == 0)
			locationString = "上海";
		else if (locationSelection == 1)
			locationString = "北京";
		;
		tender.setLicense_location(locationString);
		tender.setPrice(price);
		Map<String, String> shops = new HashMap<>();
		for (int i = 0; i < dealers.size(); i++) {
			shops.put(dealers.get(i), "1");
		}
		tender.setShops(shops);
		TenderEntity tenderEntity = new TenderEntity();
		tenderEntity.setTender(tender);

		Gson gson = new Gson();
		System.out.println(gson.toJson(tenderEntity).toString());
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
						System.out.println("error");
						System.out.println("statusCode:" + statusCode);
						System.out.println("headers:" + headers);
						Toast toast = Toast.makeText(BargainActivity.this,
								"提交失败", Toast.LENGTH_SHORT);
						toast.show();
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							String responseString, Throwable throwable) {
						// TODO Auto-generated method stub
						super.onFailure(statusCode, headers, responseString,
								throwable);
						progressLayout.setVisibility(View.GONE);
						System.out.println("2");
						Toast toast = Toast.makeText(BargainActivity.this,
								"提交失败", Toast.LENGTH_SHORT);
						toast.show();
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) {
						// TODO Auto-generated method stub
						super.onSuccess(statusCode, headers, response);
						progressLayout.setVisibility(View.GONE);

						System.out.println("success");
						System.out.println("statusCode:" + statusCode);

						// for(int i=0;i<headers.length;i++){
						// System.out.println(headers[0]);
						// }
						System.out.println("response:" + response);
						if (statusCode == 201) {

							try {
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
//							Intent intent = new Intent();
//							// intent.putExtra("tenderId", id);
//							intent.setClass(BargainActivity.this,
//									AliPayActivity.class);
//							startActivity(intent);
							Intent intent = new Intent();
							// intent.putExtra("tenderId", id);
							intent.setClass(BargainActivity.this,
									MainActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(intent);

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
			ArrayList<String> arrayList = new ArrayList<String>();
			arrayList.add("有牌照）");
			arrayList.add("无牌照");
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
		System.out.println("resultcode :" + resultCode + " requestcode:"
				+ requestCode);
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
				System.out.println("dealer size:" + dealers.size());
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateUI();
		// Normal case behavior follows
	}

	public void exitClick(View view) {
		this.finish();
	}

}

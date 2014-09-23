package com.cettco.buycar.activity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.cettco.buycar.R;
import com.cettco.buycar.adapter.SelectShopAdapter;
import com.cettco.buycar.entity.DealerEntity;
import com.cettco.buycar.entity.MyOrderEntity;
import com.cettco.buycar.entity.User;
import com.cettco.buycar.entity.UserEntity;
import com.cettco.buycar.utils.GlobalData;
import com.cettco.buycar.utils.HttpConnection;
import com.cettco.buycar.utils.UserUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

public class MyOrderStatusActivity extends Activity {
	private ArrayList<DealerEntity> dealerList;
	private ListView dealerListView;
	private SelectShopAdapter dealerListAdapter;
	private Button bargainButton;
	private Button cancleButton;
	private int tenderId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myorder_status);
		// getActionBar().hide();
		tenderId = getIntent().getIntExtra("tenderId", -1);
		
		dealerListView = (ListView) findViewById(R.id.dealer_listview);
		dealerList = new ArrayList<DealerEntity>();
		for (int i = 0; i < 20; i++) {
			DealerEntity dealerEntity = new DealerEntity();
			dealerList.add(dealerEntity);
		}
		dealerListAdapter = new SelectShopAdapter(this, R.layout.dealer_item,
				dealerList);
		dealerListView.setAdapter(dealerListAdapter);
		dealerListView.setOnItemClickListener(dealerListClickListener);

		bargainButton = (Button) findViewById(R.id.bargainBtn);
		bargainButton.setOnClickListener(barginBtnClickListener);

		cancleButton = (Button) findViewById(R.id.bargainCancleBtn);
		cancleButton.setOnClickListener(cancleBtnClickListener);
		getData();
	}

	protected OnClickListener cancleBtnClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(MyOrderStatusActivity.this,
					CancleReasonActivity.class);
			startActivity(intent);
		}
	};
	protected OnClickListener barginBtnClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(MyOrderStatusActivity.this, BargainActivity.class);
			startActivity(intent);
		}
	};
	protected OnItemClickListener dealerListClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(MyOrderStatusActivity.this,
					DealerDetailActivity.class);
			startActivity(intent);

		}
	};

	private void getData() {
		String url = GlobalData.getBaseUrl() + "/tenders/"+tenderId+".json";
		Gson gson = new Gson();
		StringEntity entity = null;
		// try {
		// System.out.println(gson.toJson(userEntity).toString());
		// entity = new StringEntity(gson.toJson(userEntity).toString());
		// } catch (UnsupportedEncodingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		String cookieStr = null;
		String cookieName = null;
		PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
		if (myCookieStore == null) {
			System.out.println("cookie store null");
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
			System.out.println("cookie null");
			return;
		}
		HttpConnection.getClient().addHeader("Cookie",
				cookieName + "=" + cookieStr);
		HttpConnection.get(url, new JsonHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
				// progressLayout.setVisibility(View.GONE);
				System.out.println("error");
				System.out.println("statusCode:" + statusCode);
				System.out.println("headers:" + headers);
				for (int i = 0; i < headers.length; i++) {
					System.out.println(headers[i]);
				}
				System.out.println("response:" + errorResponse);
				Message message = new Message();
				message.what = 2;
				mHandler.sendMessage(message);

			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				System.out.println("success");
				System.out.println("statusCode:" + statusCode);
				System.out.println("headers:" + headers);
				System.out.println("response:" + response);
				try {
					String state=response.getString("state");
					String[] stateArray = getResources().getStringArray(R.array.state);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// progressLayout.setVisibility(View.GONE);
				// UserUtil.login(SignInActivity.this);

			}

		});

	}

	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				// updateTitle();
				// adapter.notifyDataSetChanged();
				break;
			case 2:
				// Toast toast = Toast.makeText(getActivity(), "获取订单失败",
				// Toast.LENGTH_SHORT);
				// toast.show();
				break;
			}
		};
	};

	public void exitClick(View view) {
		this.finish();
	}

}

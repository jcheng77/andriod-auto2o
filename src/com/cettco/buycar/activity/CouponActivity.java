package com.cettco.buycar.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cettco.buycar.R;
import com.cettco.buycar.adapter.CouponAdapter;
import com.cettco.buycar.utils.GlobalData;
import com.cettco.buycar.utils.HttpConnection;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CouponActivity extends Activity {
	private static final int DATA_FAIL = 2;
	private static final int DATA_SUCCESS=1;
	private ListView listView;
	private CouponAdapter adapter;
	//private ArrayList<Integer> arrayList = new ArrayList<Integer>();
	private int tag;
	private Intent intent;
	
	private RelativeLayout progressLayout;
	private RelativeLayout nullDataLayout;
	private List<Integer> discountList;
	private boolean isPay = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coupon);
		//getActionBar().hide();
		progressLayout = (RelativeLayout) findViewById(R.id.opacity_progressbar_relativeLayout);
		progressLayout.setVisibility(View.VISIBLE);
		nullDataLayout = (RelativeLayout) findViewById(R.id.null_data_relativeLayout);
		TextView titleTextView = (TextView)findViewById(R.id.title_text);
		intent = getIntent();
		isPay = intent.getBooleanExtra("pay", false);
		titleTextView.setText("我的优惠券");
		listView = (ListView)findViewById(R.id.myBaseListview);
		adapter = new CouponAdapter(this,R.layout.item_coupon, discountList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(listItemClickListener);
		getPaymentData();
	}
	protected OnItemClickListener listItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			if(!isPay)return;
			intent.putExtra("result", discountList.get(position));
			setResult(RESULT_OK, intent);
			CouponActivity.this.finish();
		}
	};
	protected void getPaymentData() {
		// String url = GlobalData.getBaseUrl() + "/cars/list.json";
		// httpCache.clear();
		progressLayout.setVisibility(View.VISIBLE);
		String url = GlobalData.getBaseUrl() + "/deposits/amount_and_discount.json";
		HttpConnection.setCookie(getApplicationContext());
		HttpConnection.get(url, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				System.out.println("fail");
				progressLayout.setVisibility(View.GONE);
				Message message = new Message();
				message.what = DATA_FAIL;
				mHandler.sendMessage(message);
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				System.out.println("succuss");
				progressLayout.setVisibility(View.GONE);
				try {
					
					String result = new String(arg2, "UTF-8");
					System.out.println("result:"+result);
					JSONObject object = new JSONObject(result);
					//amount = object.getInt("amount");
					JSONArray array =object.getJSONArray("discount");
					String[] strArr = new String[array.length()];
					discountList = new ArrayList<Integer>();
					System.out.println("array lenght:"+array.length());
					for(int i=0;i<array.length();i++){
						discountList.add(array.getInt(i));
					}
					Message message = new Message();
					message.what = DATA_SUCCESS;
					mHandler.sendMessage(message);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
	}
	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DATA_SUCCESS:
				System.out.println("update");
				nullDataLayout.setVisibility(View.GONE);
				progressLayout.setVisibility(View.GONE);
				adapter.updateData(discountList);
				break;
			case DATA_FAIL:
				nullDataLayout.setVisibility(View.VISIBLE);
				progressLayout.setVisibility(View.GONE);
				Toast toast = Toast.makeText(CouponActivity.this,
						"获取数据失败", Toast.LENGTH_SHORT);
				toast.show();
				break;
			}
		};
	};
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		System.out.println("back pressed");
		setResult(RESULT_CANCELED, intent);
		//this.finish();
	}

	public void exitClick(View view){
		setResult(RESULT_CANCELED, intent);
		this.finish();
	}
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		}
		public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		}
}

package com.cettco.buycar.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.Header;

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
	private ListView listView;
	private CouponAdapter adapter;
	private ArrayList<String> arrayList = new ArrayList<String>();
	private int tag;
	private Intent intent;
	
	private RelativeLayout progressLayout;
	private RelativeLayout nullDataLayout;
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
		titleTextView.setText("我的优惠券");
		arrayList.add("1");
		arrayList.add("2");
		listView = (ListView)findViewById(R.id.myBaseListview);
		adapter = new CouponAdapter(this,R.layout.item_coupon, arrayList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(listItemClickListener);
		getData();
	}
	protected OnItemClickListener listItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			intent.putExtra("result", Integer.parseInt(arrayList.get(position)));
			setResult(RESULT_OK, intent);
			CouponActivity.this.finish();
		}
	};
	protected void getData() {
		String url = GlobalData.getBaseUrl() + "/cars/trims.json";
		RequestParams params = new RequestParams();
		
		HttpConnection.setCookie(getApplicationContext());
		HttpConnection.get(url,params,new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				System.out.println("fail");
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
					System.out.println("count:"+result);
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
				System.out.println("update");
				nullDataLayout.setVisibility(View.GONE);
				progressLayout.setVisibility(View.GONE);
				break;
			case 2:
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

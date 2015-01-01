package com.cettco.buycar.activity;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import cn.trinea.android.common.service.HttpCache;

import com.cettco.buycar.R;
import com.cettco.buycar.adapter.CarColorAdapter;
import com.cettco.buycar.adapter.SelectDealerAdapter;
import com.cettco.buycar.entity.CarBrandListEntity;
import com.cettco.buycar.entity.CarColorEntity;
import com.cettco.buycar.entity.CarColorListEntity;
import com.cettco.buycar.entity.DealerEntity;
import com.cettco.buycar.entity.OrderItemEntity;
import com.cettco.buycar.utils.GlobalData;
import com.cettco.buycar.utils.HttpConnection;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

public class SelectDealerActivity extends Activity{
	private ArrayList<DealerEntity> dealerList;
	private ListView listView;
	private SelectDealerAdapter dealerListAdapter;
	private Intent intent;
	private String name;
	private int tag;
	private String trim_id;
	private HttpCache httpCache;
	private ProgressBar progressBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selectshop);
		//getActionBar().hide();
		httpCache = new HttpCache(this);
		TextView titleTextView = (TextView)findViewById(R.id.title_text);
		progressBar = (ProgressBar)findViewById(R.id.activity_selectshop_progressbar);
		intent = getIntent();
		trim_id = intent.getStringExtra("trim_id");
		System.out.println("trim_id:"+trim_id);
		name= intent.getStringExtra("name");
		titleTextView.setText(name);
		listView = (ListView) findViewById(R.id.selectshop_listview);
		dealerList = new ArrayList<DealerEntity>();
		dealerListAdapter = new SelectDealerAdapter(this, R.layout.item_selectshop,
				dealerList);
		listView.setAdapter(dealerListAdapter);
		listView.setOnItemClickListener(dealerListClickListener);
		getData();
	}
	protected OnItemClickListener dealerListClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
//			CheckBox checkBox = (CheckBox) arg1.findViewById(R.id.selectshop_checkBox);
//			checkBox.toggle();
//			dealerListAdapter.getIsSelected().put(arg2, checkBox.isChecked());
//			Intent intent = new Intent();
//			intent.setClass(SelectDealerActivity.this,DealerDetailActivity.class);
//			startActivity(intent);
			CheckBox checkBox = (CheckBox) arg1.findViewById(R.id.selectshop_checkBox);
			checkBox.toggle();
			dealerListAdapter.getIsSelected().put(arg2, checkBox.isChecked());

		}
	};
	protected OnItemLongClickListener dealerlistLongClickListener = new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View view,
				int arg2, long arg3) {
			// TODO Auto-generated method stub
			CheckBox checkBox = (CheckBox) view.findViewById(R.id.selectshop_checkBox);
			checkBox.toggle();
			dealerListAdapter.getIsSelected().put(arg2, checkBox.isChecked());
			return true;
		}
	};
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		setResult(RESULT_CANCELED, intent);
		this.finish();
	}
	protected void getData() {
		// String url = GlobalData.getBaseUrl() + "/cars/list.json";
		// httpCache.clear();
		String url=GlobalData.getBaseUrl()+"/shops.json";
		RequestParams params = new RequestParams();
		params.put("trim_id", trim_id);
		System.out.println("trim_url:"+url);
		progressBar.setProgress(40);
		HttpConnection.get(url,params,new AsyncHttpResponseHandler(){

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
				progressBar.setProgress(80);
				try {
					String result= new String(arg2,"UTF-8");
					Type listType = new TypeToken<ArrayList<DealerEntity>>() {
					}.getType();
					dealerList = new Gson().fromJson(result, listType);
					System.out.println("size:"+dealerList.size());
					Message message = new Message();
					message.what = 1;
					mHandler.sendMessage(message);
					//System.out.println("result:"+result);
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
				progressBar.setProgress(100);
				progressBar.setVisibility(View.GONE);
				if(dealerList==null||dealerList.size()==0){
					Toast toast = Toast.makeText(SelectDealerActivity.this, "获取商家列表失败", Toast.LENGTH_SHORT);
					toast.show();
				}
				dealerListAdapter.updateList(dealerList);
				break;
			case 2:
				progressBar.setProgress(100);
				progressBar.setVisibility(View.GONE);
				Toast toast = Toast.makeText(SelectDealerActivity.this, "获取商家列表失败", Toast.LENGTH_SHORT);
				toast.show();
				break;
			}
		};
	};
	public void exitClick(View view){
		ArrayList<String>dealers = new ArrayList<String>();
		Iterator<Integer> iter = dealerListAdapter.getIsSelected().keySet().iterator();
		while (iter.hasNext()) {  
		    Integer key = iter.next();  
		    Boolean value = dealerListAdapter.getIsSelected().get(key);
		    if(value==true) dealers.add(dealerList.get(key).getId());
		}
		intent.putStringArrayListExtra("dealers", dealers);
		setResult(RESULT_OK, intent);
		setResult(RESULT_OK, intent);
		this.finish();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}
}

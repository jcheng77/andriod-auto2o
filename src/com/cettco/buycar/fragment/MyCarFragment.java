package com.cettco.buycar.fragment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import cn.trinea.android.common.entity.HttpResponse;
import cn.trinea.android.common.service.HttpCache;
import cn.trinea.android.common.service.HttpCache.HttpCacheListener;

import com.cettco.buycar.R;
import com.cettco.buycar.activity.CarListActivity;
import com.cettco.buycar.activity.MyOrderStatusActivity;
import com.cettco.buycar.activity.SignInActivity;
import com.cettco.buycar.adapter.MyOrderAdapter;
import com.cettco.buycar.entity.CarBrandListEntity;
import com.cettco.buycar.entity.MyOrderEntity;
import com.cettco.buycar.utils.HttpConnection;
import com.cettco.buycar.utils.UserUtil;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MyCarFragment extends Fragment {

	private View fragmentView;
	private ListView listView;
	private PullToRefreshListView pullToRefreshView;
	private MyOrderAdapter adapter;
	private ArrayList<MyOrderEntity> list = new ArrayList<MyOrderEntity>();
	private Button currentButton;
	private Button historyButton;
	private LinearLayout mycarBgLayout;
	
	//private array

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		fragmentView = inflater.inflate(R.layout.fragment_mycar, container,
				false);
		//System.out.println("oncreateview2");
		mycarBgLayout = (LinearLayout)fragmentView
				.findViewById(R.id.carlist_bg_layout);
		pullToRefreshView = (PullToRefreshListView) fragmentView
				.findViewById(R.id.pull_to_refresh_listview);
		pullToRefreshView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// Do work to refresh the list here.
						new GetDataTask().execute();
					}
					
				});
		listView = pullToRefreshView.getRefreshableView();
		listView.setOnItemClickListener(itemClickListener);
		for(int i = 0;i<0;i++){
			MyOrderEntity entity = new MyOrderEntity();
			list.add(entity);
		}
		adapter = new MyOrderAdapter(getActivity(), R.layout.my_order_item, list);
		listView.setAdapter(adapter);
		currentButton = (Button)fragmentView.findViewById(R.id.currentOrderBtn);
		historyButton = (Button)fragmentView.findViewById(R.id.cancledOrderBtn);
		currentButton.setOnClickListener(currentClickListener);
		historyButton.setOnClickListener(historyClickListener);
		return fragmentView;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//pullToRefreshView.setRefreshing();
	}

	protected OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			// System.out.println("size:");
			// Toast.makeText(this, carBrandList.size(), Toast.LENGTH_SHORT);
			int position = arg2 - 1;
			Intent intent = new Intent();
			intent.setClass(MyCarFragment.this.getActivity(), MyOrderStatusActivity.class);
			startActivity(intent);

		}
	};
	private OnClickListener currentClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			currentButton.setBackgroundResource(R.drawable.my_order_selected_btn_style);
			currentButton.setTextColor(getResources().getColor(R.color.white));
			
			historyButton.setBackgroundResource(R.drawable.my_order_btn_style);
			historyButton.setTextColor(getResources().getColor(R.color.blue));
			
			//pullToRefreshView.st
			pullToRefreshView.setRefreshing();
			
		}
	};
	private OnClickListener historyClickListener =  new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			historyButton.setBackgroundResource(R.drawable.my_order_selected_btn_style);
			historyButton.setTextColor(getResources().getColor(R.color.white));
			
			currentButton.setBackgroundResource(R.drawable.my_order_btn_style);
			currentButton.setTextColor(getResources().getColor(R.color.blue));
			
			pullToRefreshView.setRefreshing();
		}
	};
	private class GetDataTask extends AsyncTask<Void, Void, String[]> {
		@Override
		protected void onPostExecute(String[] result) {
			// Call onRefreshComplete when the list has been refreshed.
			pullToRefreshView.onRefreshComplete();
			super.onPostExecute(result);
		}

		@Override
		protected String[] doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try {
//				String url = "";
//				HttpConnection.get(url, new JsonHttpResponseHandler(){});
				Thread.sleep(4000);
				MyOrderEntity entity = new MyOrderEntity();
				list.add(entity);
				//adapter.notifyDataSetChanged();
				Message message = new Message();
				message.what = 1;
				mHandler.sendMessage(message);
			} catch (InterruptedException e) {
			}
			return null;
		}
	}
	private void getData(){
		String url="";
		Gson gson = new Gson();
        StringEntity entity = null;
//        try {
//        	System.out.println(gson.toJson(userEntity).toString());
//			entity = new StringEntity(gson.toJson(userEntity).toString());
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		HttpConnection.post(getActivity(), url, null, entity, "application/json", new JsonHttpResponseHandler(){

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
				//progressLayout.setVisibility(View.GONE);
				System.out.println("error");
				System.out.println("statusCode:"+statusCode);
				System.out.println("headers:"+headers);
				for(int i = 0;i<headers.length;i++){
					System.out.println(headers[i]);
				}
				System.out.println("response:"+errorResponse);
				Toast toast = Toast.makeText(getActivity(), "获取订单失败", Toast.LENGTH_SHORT);
				toast.show();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				System.out.println("success");
				System.out.println("statusCode:"+statusCode);
				System.out.println("headers:"+headers);
				System.out.println("response:"+response);
				//progressLayout.setVisibility(View.GONE);
				//UserUtil.login(SignInActivity.this);
				
			}
			
		});
	}
	private Handler mHandler = new Handler(){  
        
        public void handleMessage(Message msg) {  
            switch (msg.what) {  
            case 1:  
                //updateTitle(); 
            	adapter.notifyDataSetChanged();
                break;  
            }  
        };  
    };

}

package com.cettco.buycar.fragment;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import cn.trinea.android.common.entity.HttpResponse;
import cn.trinea.android.common.service.HttpCache;
import cn.trinea.android.common.service.HttpCache.HttpCacheListener;

import com.cettco.buycar.R;
import com.cettco.buycar.activity.CarDetailActivity;
import com.cettco.buycar.activity.CarListActivity;
import com.cettco.buycar.activity.MyOrderStatusActivity;
import com.cettco.buycar.activity.SignInActivity;
import com.cettco.buycar.adapter.MyOrderAdapter;
import com.cettco.buycar.entity.CarBrandListEntity;
import com.cettco.buycar.entity.MyOrderEntity;
import com.cettco.buycar.entity.OrderItemEntity;
import com.cettco.buycar.utils.DatabaseHelper;
import com.cettco.buycar.utils.GlobalData;
import com.cettco.buycar.utils.HttpConnection;
import com.cettco.buycar.utils.UserUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

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
//	private Button currentButton;
//	private Button historyButton;
	private LinearLayout mycarBgLayout;
	private List<OrderItemEntity> orderItems;
	//private List<E>
	
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
//		for(int i = 0;i<5;i++){
//			MyOrderEntity entity = new MyOrderEntity();
//			list.add(entity);
//		}
		adapter = new MyOrderAdapter(getActivity(), R.layout.my_order_item, orderItems);
		listView.setAdapter(adapter);
//		currentButton = (Button)fragmentView.findViewById(R.id.currentOrderBtn);
//		historyButton = (Button)fragmentView.findViewById(R.id.cancledOrderBtn);
//		currentButton.setOnClickListener(currentClickListener);
//		historyButton.setOnClickListener(historyClickListener);
		return fragmentView;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		System.out.println("onResume");
		//pullToRefreshView.setRefreshing();
		DatabaseHelper helper = DatabaseHelper.getHelper(getActivity());
		try {
			orderItems=helper.getOrderDao().queryForAll();
			adapter.updateList(orderItems);
			System.out.println("order size:"+orderItems.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
//	private OnClickListener currentClickListener = new OnClickListener() {
//		
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			currentButton.setBackgroundResource(R.drawable.my_order_selected_btn_style);
//			currentButton.setTextColor(getResources().getColor(R.color.white));
//			
//			historyButton.setBackgroundResource(R.drawable.my_order_btn_style);
//			historyButton.setTextColor(getResources().getColor(R.color.blue));
//			
//			//pullToRefreshView.st
//			pullToRefreshView.setRefreshing();
//			
//		}
//	};
//	private OnClickListener historyClickListener =  new OnClickListener() {
//		
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			historyButton.setBackgroundResource(R.drawable.my_order_selected_btn_style);
//			historyButton.setTextColor(getResources().getColor(R.color.white));
//			
//			currentButton.setBackgroundResource(R.drawable.my_order_btn_style);
//			currentButton.setTextColor(getResources().getColor(R.color.blue));
//			
//			pullToRefreshView.setRefreshing();
//		}
//	};
	private class GetDataTask extends AsyncTask<Void, Void, String[]> {
		@Override
		protected void onPostExecute(String[] result) {
			// Call onRefreshComplete when the list has been refreshed.
			pullToRefreshView.onRefreshComplete();
			super.onPostExecute(result);
		}

		@Override
		protected String[] doInBackground(Void... arg0) {
			getData();
			return null;
		}
	}
	private void getData(){
		String url=GlobalData.getBaseUrl()+"/deals.json";
		Gson gson = new Gson();
        StringEntity entity = null;
//        try {
//        	System.out.println(gson.toJson(userEntity).toString());
//			entity = new StringEntity(gson.toJson(userEntity).toString());
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        String cookieStr=null;
		String cookieName=null;
		PersistentCookieStore myCookieStore = new PersistentCookieStore(
				getActivity());
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
		if(cookieStr==null||cookieStr.equals("")){System.out.println("cookie null");return;}
//		SyncHttpConnection.getClient().addHeader("Cookie", cookieName+"="+cookieStr);
//		SyncHttpConnection.get(url,new JsonHttpResponseHandler(){
//
//			@Override
//			public void onFailure(int statusCode, Header[] headers,
//					Throwable throwable, JSONObject errorResponse) {
//				// TODO Auto-generated method stub
//				super.onFailure(statusCode, headers, throwable, errorResponse);
//				//progressLayout.setVisibility(View.GONE);
//				System.out.println("error");
//				System.out.println("statusCode:"+statusCode);
//				System.out.println("headers:"+headers);
//				for(int i = 0;i<headers.length;i++){
//					System.out.println(headers[i]);
//				}
//				System.out.println("response:"+errorResponse);
//				Message message = new Message();
//				message.what = 2;
//				mHandler.sendMessage(message);
//				
//			}
//
//			@Override
//			public void onSuccess(int statusCode, Header[] headers,
//					JSONObject response) {
//				// TODO Auto-generated method stub
//				super.onSuccess(statusCode, headers, response);
//				System.out.println("success");
//				System.out.println("statusCode:"+statusCode);
//				System.out.println("headers:"+headers);
//				System.out.println("response:"+response);
//				//progressLayout.setVisibility(View.GONE);
//				//UserUtil.login(SignInActivity.this);
//				
//			}
//			
//		});
	        HttpClient httpclient = new DefaultHttpClient();
	        String uri = GlobalData.getBaseUrl()+"/tenders.json";
	        HttpGet get = new HttpGet(uri);
	        //添加http头信息 
	        get.addHeader("Cookie", cookieName+"="+cookieStr);
	        get.addHeader("Content-Type", "application/json");
	        org.apache.http.HttpResponse response;
	        try {
				response = httpclient.execute(get);
				int code = response.getStatusLine().getStatusCode();
		        //检验状态码，如果成功接收数据
				System.out.println("code:"+code);
		        if (code == 200) {
		        	String result = EntityUtils.toString(response.getEntity());
		        	Type listType = new TypeToken<ArrayList<MyOrderEntity>>(){}.getType();
		        	list = new Gson().fromJson(result,listType);
		        	System.out.println(result);
		        	System.out.println(list.size());
		        	for(int i=0;i<list.size();i++){
		        		System.out.println(list.get(i).getModel());
		        		System.out.println(list.get(i).getDescription());
		        	}
		        	Message message = new Message();
					message.what = 1;
					mHandler.sendMessage(message);
		        }
		        else if(code==401){
					Message message = new Message();
					message.what = 2;
					mHandler.sendMessage(message);
		        }
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	}
	private Handler mHandler = new Handler(){  
        
        public void handleMessage(Message msg) {  
            switch (msg.what) {  
            case 1:  
                //updateTitle(); 
            	adapter.updateList(orderItems);
                break;  
            case 2:
            	Toast toast = Toast.makeText(getActivity(), "获取订单失败", Toast.LENGTH_SHORT);
				toast.show();
				break;
            }  
        };  
    };

}
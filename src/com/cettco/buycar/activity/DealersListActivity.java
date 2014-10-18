package com.cettco.buycar.activity;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import cn.trinea.android.common.view.DropDownListView;
import cn.trinea.android.common.view.DropDownListView.OnDropDownListener;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.nplatform.comapi.basestruct.GeoPoint;
import com.cettco.buycar.R;
import com.cettco.buycar.adapter.DealerCommentAdapter;
import com.cettco.buycar.adapter.DealerListAdapter;
import com.cettco.buycar.entity.DealerCommentEntity;
import com.cettco.buycar.entity.DealerEntity;
import com.cettco.buycar.entity.OrderItemEntity;
import com.cettco.buycar.utils.GlobalData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.PersistentCookieStore;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DealersListActivity extends Activity {

	private ArrayList<DealerEntity> listItems;
	private DropDownListView listView = null;
	private DealerListAdapter adapter;
	
	private LocationClient locationClient;
	private static final int UPDATE_TIME = 5000;  
    private static int LOCATION_COUTNS = 0; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_dealers);
		listView = (DropDownListView) findViewById(R.id.dealers_list_view);
		// set drop down listener
		listView.setOnDropDownListener(new OnDropDownListener() {

			@Override
			public void onDropDown() {
				// TODO Auto-generated method stub
				new GetDataTask(true).execute();
			}
		});
		listView.setOnBottomListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new GetDataTask(true).execute();

			}
		});
		listItems = new ArrayList<DealerEntity>();
		// listItems.addAll(Arrays.asList(mStrings));
		for (int i = 0; i < 10; i++) {
			DealerEntity entity = new DealerEntity();
			listItems.add(entity);
		}
		System.out.println("list item size:" + listItems.size());
		adapter = new DealerListAdapter(this, R.layout.item_dealer,
				listItems);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(listItemClickListener);
		initLocation();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		locationClient.stop();
		super.onStop();
	}
	
	private void initLocation(){
		locationClient = new LocationClient(getApplicationContext());
		//设置定位条件  
        LocationClientOption option = new LocationClientOption();
        //option.setl
        //option.setLocationMode(LocationMode.Hight_Accuracy);
        //option.setOpenGps(true);                                //是否打开GPS  
        option.setCoorType("bd09ll");                           //设置返回值的坐标类型。  
        //com.baidu.navi.location.LocationClientOption.n
        //option.setPriority(LocationClientOption.NetWorkFirst);  //设置定位优先级  
        option.setProdName("LocationDemo");                     //设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。  
        option.setScanSpan(UPDATE_TIME);   
        //设置定时定位的时间间隔。单位毫秒  
        locationClient.setLocOption(option);  
          
        //注册位置监听器  
        System.out.println("register");
        locationClient.registerLocationListener(new BDLocationListener() {  
              
            @Override  
            public void onReceiveLocation(BDLocation location) {  
                // TODO Auto-generated method stub  
            	System.out.println("location1");
                if (location == null) {  
                	System.out.println("location null");
                    return;  
                }  
                for(int i=0;i<listItems.size();i++){
                	DealerEntity tmp =listItems.get(i);
                	//GeoPoint g1 = new geo
                	LatLng pt1=new LatLng(location.getAltitude(), location.getLatitude());
                	LatLng pt2 = new LatLng(24.000, 34.000);
                	double distance = DistanceUtil.getDistance(pt1, pt2);
                	tmp.setDistance((int) distance);
                	System.out.println("distance:"+location.getAltitude());
                	
                }
                adapter.notifyDataSetChanged();
//                StringBuffer sb = new StringBuffer(256);  
//                sb.append("Time : ");  
//                sb.append(location.getTime());  
//                sb.append("\nError code : ");  
//                sb.append(location.getLocType());  
//                sb.append("\nLatitude : ");  
//                sb.append(location.getLatitude());  
//                sb.append("\nLontitude : ");  
//                sb.append(location.getLongitude());  
//                sb.append("\nRadius : ");  
//                sb.append(location.getRadius());  
                
                //distancetTextView.setText(distance+"km");
//                if (location.getLocType() == BDLocation.TypeGpsLocation){  
//                    sb.append("\nSpeed : ");  
//                    sb.append(location.getSpeed());  
//                    sb.append("\nSatellite : ");  
//                    sb.append(location.getSatelliteNumber());  
//                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){  
//                    sb.append("\nAddress : ");  
//                    sb.append(location.getAddrStr());  
//                }  
//                LOCATION_COUTNS ++;  
//                sb.append("\n检查位置更新次数：");  
//                sb.append(String.valueOf(LOCATION_COUTNS));  
//                locationInfoTextView.setText(sb.toString());  
            }  
              
//            @Override  
//            public void onReceivePoi(BDLocation location) {  
//            	System.out.println("location2");
//                if (location == null) {  
//                	System.out.println("location null");
//                    return;  
//                }  
//                for(int i=0;i<listItems.size();i++){
//                	DealerEntity tmp =listItems.get(i);
//                	LatLng pt1=new LatLng(location.getAltitude(), location.getLatitude());
//                	LatLng pt2 = new LatLng(24.000, 34.000);
//                	double distance = DistanceUtil.getDistance(pt1, pt2);
//                	tmp.setDistance((int) distance);
//                	System.out.println("distance:"+distance);
//                	
//                }
//                adapter.notifyDataSetChanged();
//            }  
//              
        });
        System.out.println("start");
        locationClient.start();
        System.out.println("start2");
	}
	private OnItemClickListener listItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			
		}
	};
	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		private boolean isDropDown;

		public GetDataTask(boolean isDropDown) {
			this.isDropDown = isDropDown;
		}

		@Override
		protected String[] doInBackground(Void... params) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				;
			}
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {

			if (isDropDown) {
				// listItems.addFirst("Added after drop down");
				adapter.notifyDataSetChanged();
				listView.onDropDownComplete();

				// should call onDropDownComplete function of DropDownListView
				// at end of drop down complete.
				// SimpleDateFormat dateFormat = new
				// SimpleDateFormat("MM-dd HH:mm:ss");
				// listView.onDropDownComplete(getString(R.string.update_at)
				// + dateFormat.format(new Date()));
			} else {
				// listItems.add("Added after on bottom");
				adapter.notifyDataSetChanged();

				// should call onBottomComplete function of DropDownListView at
				// end of on bottom complete.
				listView.onBottomComplete();
			}

			super.onPostExecute(result);
		}
	}

	private void getData() {
		// String url=GlobalData.getBaseUrl()+"/tenders.json";
		// Gson gson = new Gson();
		// StringEntity entity = null;
		// try {
		// System.out.println(gson.toJson(userEntity).toString());
		// entity = new StringEntity(gson.toJson(userEntity).toString());
		// } catch (UnsupportedEncodingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// SyncHttpConnection.getClient().addHeader("Cookie",
		// cookieName+"="+cookieStr);
		// SyncHttpConnection.get(url,new JsonHttpResponseHandler(){
		//
		// @Override
		// public void onFailure(int statusCode, Header[] headers,
		// Throwable throwable, JSONObject errorResponse) {
		// // TODO Auto-generated method stub
		// super.onFailure(statusCode, headers, throwable, errorResponse);
		// //progressLayout.setVisibility(View.GONE);
		// System.out.println("error");
		// System.out.println("statusCode:"+statusCode);
		// System.out.println("headers:"+headers);
		// for(int i = 0;i<headers.length;i++){
		// System.out.println(headers[i]);
		// }
		// System.out.println("response:"+errorResponse);
		// Message message = new Message();
		// message.what = 2;
		// mHandler.sendMessage(message);
		//
		// }
		//
		// @Override
		// public void onSuccess(int statusCode, Header[] headers,
		// JSONObject response) {
		// // TODO Auto-generated method stub
		// super.onSuccess(statusCode, headers, response);
		// System.out.println("success");
		// System.out.println("statusCode:"+statusCode);
		// System.out.println("headers:"+headers);
		// System.out.println("response:"+response);
		// //progressLayout.setVisibility(View.GONE);
		// //UserUtil.login(SignInActivity.this);
		//
		// }
		//
		// });
		String cookieStr = null;
		String cookieName = null;
		PersistentCookieStore myCookieStore = new PersistentCookieStore(
				this);
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
		HttpClient httpclient = new DefaultHttpClient();
		String uri = GlobalData.getBaseUrl() + "/tenders.json";
		HttpGet get = new HttpGet(uri);
		// 添加http头信息
		get.addHeader("Cookie", cookieName + "=" + cookieStr);
		get.addHeader("Content-Type", "application/json");
		org.apache.http.HttpResponse response;
		try {
			response = httpclient.execute(get);
			int code = response.getStatusLine().getStatusCode();
			// 检验状态码，如果成功接收数据
			System.out.println("code:" + code);
			if (code == 200) {
				String result = EntityUtils.toString(response.getEntity());
				Type listType = new TypeToken<ArrayList<OrderItemEntity>>() {
				}.getType();
//				list = new Gson().fromJson(result, listType);
//				System.out.println(result);
//				System.out.println(list.size());
//				for (int i = 0; i < list.size(); i++) {
//					System.out.println(list.get(i).getId());
//					System.out.println(list.get(i).getState());
//				}
				Message message = new Message();
				message.what = 1;
				mHandler.sendMessage(message);
			} else if (code == 401) {
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

	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				// updateTitle();
				//adapter.updateList(list);
				break;
			case 2:
				Toast toast = Toast.makeText(DealersListActivity.this, "获取商家列表失败",
						Toast.LENGTH_SHORT);
				toast.show();
				break;
			}
		};
	};
	public void exitClick(View view) {
		// intent.putExtra("result", position);
		// int size = mycarColorAdapter.getIsSelected().size();
		// for(int i = 0;i<size;i++){
		// mycarColorAdapter.getIsSelected().get(i);
		// }
		// setResult(RESULT_OK, intent);
		this.finish();
	}

}

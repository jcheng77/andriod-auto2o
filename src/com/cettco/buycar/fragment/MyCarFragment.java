package com.cettco.buycar.fragment;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.cettco.buycar.activity.AliPayActivity;
import com.cettco.buycar.activity.BargainActivity;
import com.cettco.buycar.activity.CarDetailActivity;
import com.cettco.buycar.activity.CarListActivity;
import com.cettco.buycar.activity.OrderDetailActivity;
import com.cettco.buycar.activity.SignInActivity;
import com.cettco.buycar.adapter.MyOrderAdapter;
import com.cettco.buycar.entity.CarBrandListEntity;
import com.cettco.buycar.entity.OrderItemEntity;
import com.cettco.buycar.minterface.RefreshInterface;
import com.cettco.buycar.utils.GlobalData;
import com.cettco.buycar.utils.HttpConnection;
import com.cettco.buycar.utils.UserUtil;
import com.cettco.buycar.utils.db.DatabaseHelperOrder;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.umeng.analytics.MobclickAgent;

import android.R.integer;
import android.app.Fragment;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MyCarFragment extends Fragment {

	private View fragmentView;
	private ListView listView;
	private PullToRefreshListView pullToRefreshView;
	private MyOrderAdapter adapter;
	// private ArrayList<OrderItemEntity> list = new
	// ArrayList<OrderItemEntity>();
	// private Button currentButton;
	// private Button historyButton;
	private LinearLayout mycarBgLayout;
	private List<OrderItemEntity> orderItems = new ArrayList<OrderItemEntity>();
	private List<OrderItemEntity> pageItems = new ArrayList<OrderItemEntity>();

	private int global_page = 1;
	
	private GetDataTask getDataTask;
	
	private HttpClient httpclient;
	private HttpGet httpget;
	
	//private ImageView guideImage;

	// private List<E>

	// private array

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		fragmentView = inflater.inflate(R.layout.fragment_mycar, container,
				false);
		mycarBgLayout = (LinearLayout) fragmentView
				.findViewById(R.id.carlist_bg_layout);
		pullToRefreshView = (PullToRefreshListView) fragmentView
				.findViewById(R.id.pull_to_refresh_listview);
		pullToRefreshView.setMode(Mode.BOTH);
		pullToRefreshView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {

					// 下拉Pulling Down
					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// 下拉的时候数据重置
						if (getDataTask != null && getDataTask.getStatus() != AsyncTask.Status.FINISHED)
							getDataTask.cancel(true);
						getDataTask = new GetDataTask(1); //every time create new object, as AsynTask will only be executed one time.
			            getDataTask.execute();
						//new GetDataTask(1).execute();
					}

					// 上拉Pulling Up
					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// 上拉的时候添加选项
						if (getDataTask != null && getDataTask.getStatus() != AsyncTask.Status.FINISHED)
							getDataTask.cancel(true);
						getDataTask = new GetDataTask(global_page+1); //every time create new object, as AsynTask will only be executed one time.
			            getDataTask.execute();
						//new GetDataTask(global_page + 1).execute();
					}

				});
		listView = pullToRefreshView.getRefreshableView();
		listView.setOnItemClickListener(itemClickListener);
		adapter = new MyOrderAdapter(getActivity(), R.layout.item_my_order,
				orderItems);
		adapter.setInterface(new RefreshInterface() {
			
			@Override
			public void refresh() {
				// TODO Auto-generated method stub
				onResume();
			}
		});
		listView.setAdapter(adapter);
		//guideImage = (ImageView)fragmentView.findViewById(R.id.mycar_guide_imageview);
		return fragmentView;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("Mycar");
		getCachedData();
		pullToRefreshView.setRefreshing(true);

	}

	private void getCachedData() {

		DatabaseHelperOrder helper = DatabaseHelperOrder
				.getHelper(getActivity());
		System.out.println("000");
		if (UserUtil.isLogin(getActivity())) {
			System.out.println("111");
			try {
				System.out.println("222");
				orderItems = helper.getDao().queryBuilder()
						.orderBy("time", false).query();
				System.out.println("333");
				adapter.updateList(orderItems);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				orderItems = helper.getDao().queryBuilder()
						.orderBy("time", false).where().eq("state", "viewed")
						.or().eq("state", "begain").query();
				adapter.updateList(orderItems);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(orderItems==null||orderItems.size()==0){
			mycarBgLayout.setVisibility(View.VISIBLE);
			//pullToRefreshView.setBackgroundColor(getResources().getColor(R.color.white));
		}else{
			mycarBgLayout.setVisibility(View.GONE);
			//pullToRefreshView.setBackgroundColor(getResources().getColor(R.color.light_gray));
		}

	}

	private void updateDB() {
		DatabaseHelperOrder helper = DatabaseHelperOrder
				.getHelper(getActivity());
		for (int i = 0; i < pageItems.size(); i++) {
			OrderItemEntity entity = pageItems.get(i);
			try {
				OrderItemEntity tmp = helper.getDao().queryBuilder().where()
						.eq("id", entity.getId()).queryForFirst();
				if (tmp != null) {
					tmp.setState(entity.getState());
					tmp.setPrice(entity.getPrice());
					if (entity.getUpdated_at() != null) {
						SimpleDateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
						try {
							// System.out.println("formate:"
							// + entity.getUpdated_at());
							Date date = format.parse(entity.getUpdated_at());
							// System.out.println("formate2");
							tmp.setTime(date);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					helper.getDao().update(tmp);

				} else {
					System.out.println("null");
					SimpleDateFormat format = new SimpleDateFormat(
							"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
					try {
						Date date = format.parse(entity.getUpdated_at());
						entity.setTime(date);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					helper.getDao().create(entity);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// getCachedData();
		// deleteLocalUselessData();
	}

	protected void deleteLocalUselessData() {
		DatabaseHelperOrder helper = DatabaseHelperOrder
				.getHelper(getActivity());
		List<OrderItemEntity> tmp = new ArrayList<OrderItemEntity>();
		try {
			tmp = helper.getDao().queryBuilder().orderBy("time", false).query();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("tmp size:" + tmp.size());
		for (int i = 0; i < tmp.size(); i++) {
			OrderItemEntity orderItemEntity = tmp.get(i);
			boolean notExist = true;
			System.out.println("id:" + orderItemEntity.getId());
			// System.out.println("l:"+orderItemEntity.getId());
			if (orderItemEntity.getId() != null) {
				for (int j = 0; j < pageItems.size();j++) {
					// pageItems.get(j).getId();
					System.out.println("page id:" + pageItems.get(j).getId());
					if (orderItemEntity.getId()
							.equals(pageItems.get(j).getId())) {
						notExist = false;
						break;
					}
				}
			}
			System.out.println("judeg:" + notExist);

			if (notExist) {
				try {
					System.out.println("delete:" + orderItemEntity.getId());
					helper.getDao().delete(orderItemEntity);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	protected OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			if (orderItems == null || orderItems.size() == 0)
				return;
			int position = arg2 - 1;
			OrderItemEntity orderItemEntity = orderItems.get(position);
			String state = orderItemEntity.getState();
			System.out.println("order:" + state);
			if (state.equals("viewed")) {
				Intent intent = new Intent();
				intent.setClass(MyCarFragment.this.getActivity(),
						CarDetailActivity.class);
				intent.putExtra("order_id", orderItemEntity.getOrder_id());
				startActivity(intent);
			}else if (state.equals("determined")) {
				Intent intent = new Intent();
				intent.setClass(MyCarFragment.this.getActivity(),
						AliPayActivity.class);
				intent.putExtra("tender_id", orderItemEntity.getId());
				startActivity(intent);

			} else if (state.equals("canceled")) {
				Intent intent = new Intent();
				intent.setClass(MyCarFragment.this.getActivity(),
						OrderDetailActivity.class);
				intent.putExtra("tender_id", orderItemEntity.getId());
				startActivity(intent);

			}
			else {
				Intent intent = new Intent();
				intent.setClass(MyCarFragment.this.getActivity(),
						OrderDetailActivity.class);
				intent.putExtra("tender_id", orderItemEntity.getId());
				startActivity(intent);
			}

		}
	};

	private class GetDataTask extends AsyncTask<Void, Void, String[]> {
		private int page;

		public GetDataTask(int page) {
			this.page = page;
		}

		@Override
		protected void onPostExecute(String[] result) {
			// Call onRefreshComplete when the list has been refreshed.
			pullToRefreshView.onRefreshComplete();
			super.onPostExecute(result);
		}

		@Override
		protected String[] doInBackground(Void... arg0) {
			getData(page);
			return null;
		}
		@Override
	    protected void onCancelled() {
			System.out.println("cancel");
	        if ( httpget != null )
	        {
	        	System.out.println("cancel2");
	          //httpClient.getConnectionManager().shutdown();
	          httpget.abort();
	        }  
	    } 
	}

	private void getData(int page) {
		System.out.println("getdata");
		String cookieStr = null;
		String cookieName = null;
		PersistentCookieStore myCookieStore = new PersistentCookieStore(
				getActivity());
		List<Cookie> cookies = myCookieStore.getCookies();
		for (Cookie cookie : cookies) {
			String name = cookie.getName();
			cookieName = name;
			if (name.equals("_JustBidIt_session")) {
				cookieStr = cookie.getValue();
				System.out.println("cookie");
				break;
			}
		}
		if (cookieStr == null || cookieStr.equals("")) {
			System.out.println("cookie null");
			return;
		}
//		if(httpclient!=null){
//			httpclient.getConnectionManager().shutdown();
//			
//		}
		httpclient = new DefaultHttpClient();
		//HttpClient httpclient = new DefaultHttpClient();
		String url = GlobalData.getBaseUrl() + "/tenders.json";
		Uri.Builder builder = Uri.parse(url).buildUpon();
		builder.appendQueryParameter("page", String.valueOf(page));
		String uri = builder.build().toString();
		HttpGet httpget = new HttpGet(uri);
		// 添加http头信息
		httpget.addHeader("Cookie", cookieName + "=" + cookieStr);
		httpget.addHeader("Content-Type", "application/json");
		org.apache.http.HttpResponse response;
		try {
			response = httpclient.execute(httpget);
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				String result = EntityUtils.toString(response.getEntity());
				System.out.println("page:" + result);
				Type listType = new TypeToken<ArrayList<OrderItemEntity>>() {
				}.getType();
				pageItems = new Gson().fromJson(result, listType);
				updateDB();
				// if (tmpEntities != null) {
				// global_page = global_page + 1;
				// orderItems.addAll(tmpEntities);
				// updateDB();
				// }
				Message message = new Message();
				message.what = 1;
				mHandler.sendMessage(message);
			} else {
				Message message = new Message();
				message.what = 2;
				mHandler.sendMessage(message);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				// updateTitle();
				// adapter.updateList(list);
				//deleteLocalUselessData();
				getCachedData();
				break;
			case 2:
				Toast toast = Toast.makeText(getActivity(), "获取订单失败",
						Toast.LENGTH_SHORT);
				toast.show();
				break;
			}
		};
	};

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("destroy");
//		HttpConnection.getClient().cancelRequests(getActivity(), true);
//		if (getDataTask != null && getDataTask.getStatus() != AsyncTask.Status.FINISHED)
//			getDataTask.cancel(true);
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		System.out.println("detach");
		//HttpConnection.getClient().cancelRequests(getActivity(), true);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		System.out.println("pause");
		MobclickAgent.onPageEnd("Mycar"); 
		//HttpConnection.getClient().cancelRequests(getActivity(), true);
//		if (getDataTask != null && getDataTask.getStatus() != AsyncTask.Status.FINISHED)
//			getDataTask.cancel(true);
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		System.out.println("destroyview");
		
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		System.out.println("stop");
		if(httpclient!=null){
			httpclient.getConnectionManager().shutdown();
			//httpclient = new DefaultHttpClient();
		}
		//HttpConnection.getClient().cancelRequests(getActivity(), true);
	}

}
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
import com.cettco.buycar.activity.AliPayActivity;
import com.cettco.buycar.activity.BargainActivity;
import com.cettco.buycar.activity.CarDetailActivity;
import com.cettco.buycar.activity.CarListActivity;
import com.cettco.buycar.activity.MyOrderStatusActivity;
import com.cettco.buycar.activity.OrderHasDealerActivity;
import com.cettco.buycar.activity.OrderWaitingActivity;
import com.cettco.buycar.activity.SignInActivity;
import com.cettco.buycar.adapter.MyOrderAdapter;
import com.cettco.buycar.entity.CarBrandListEntity;
import com.cettco.buycar.entity.OrderItemEntity;
import com.cettco.buycar.utils.GlobalData;
import com.cettco.buycar.utils.HttpConnection;
import com.cettco.buycar.utils.UserUtil;
import com.cettco.buycar.utils.db.DatabaseHelperOrder;
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
	private ArrayList<OrderItemEntity> list = new ArrayList<OrderItemEntity>();
	// private Button currentButton;
	// private Button historyButton;
	private LinearLayout mycarBgLayout;
	private List<OrderItemEntity> orderItems;

	// private List<E>

	// private array

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		fragmentView = inflater.inflate(R.layout.fragment_mycar, container,
				false);
		// System.out.println("oncreateview2");
		mycarBgLayout = (LinearLayout) fragmentView
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
		for (int i = 0; i < 5; i++) {
			OrderItemEntity entity = new OrderItemEntity();
			list.add(entity);
		}
		adapter = new MyOrderAdapter(getActivity(), R.layout.item_my_order,
				list);
		listView.setAdapter(adapter);
		adapter.updateList(list);
		// currentButton =
		// (Button)fragmentView.findViewById(R.id.currentOrderBtn);
		// historyButton =
		// (Button)fragmentView.findViewById(R.id.cancledOrderBtn);
		// currentButton.setOnClickListener(currentClickListener);
		// historyButton.setOnClickListener(historyClickListener);
		return fragmentView;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		System.out.println("onResume");
		pullToRefreshView.setRefreshing(true);
		getCachedData();

	}

	private void getCachedData() {

		DatabaseHelperOrder helper = DatabaseHelperOrder
				.getHelper(getActivity());
		if (UserUtil.isLogin(getActivity())) {
			try {
				orderItems = helper.getDao().queryForAll();
				adapter.updateList(orderItems);
				System.out.println("order size:" + orderItems.size());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				orderItems = helper.getDao().queryBuilder().where()
						.eq("state", "viewed").or().eq("state", "begain")
						.query();
				adapter.updateList(orderItems);
				System.out.println("order size:" + orderItems.size());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void updateDB() {
		DatabaseHelperOrder helper = DatabaseHelperOrder
				.getHelper(getActivity());
		for (int i = 0; i < list.size(); i++) {
			OrderItemEntity entity = list.get(i);
			try {
				OrderItemEntity tmp = helper.getDao().queryBuilder().where()
						.eq("id",entity.getId()).queryForFirst();
				if (tmp != null) {
					tmp.setState(entity.getState());
				} else {
					helper.getDao().create(entity);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//getCachedData();
	}

	protected OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			int position = arg2 - 1;
			OrderItemEntity orderItemEntity = orderItems.get(position);
			String state = orderItemEntity.getState();
			System.out.println("order:"+state);
			if (state.equals("viewed")) {
				Intent intent = new Intent();
				intent.setClass(MyCarFragment.this.getActivity(),
						CarDetailActivity.class);
				intent.putExtra("order_id", orderItemEntity.getOrder_id());
				startActivity(intent);
			} else if (state.equals("begain")) {
				Intent intent = new Intent();
				intent.setClass(MyCarFragment.this.getActivity(),
						BargainActivity.class);
				intent.putExtra("order_id", orderItemEntity.getOrder_id());
				startActivity(intent);

			} else if (state.equals("determined")) {
				Intent intent = new Intent();
				intent.setClass(MyCarFragment.this.getActivity(),
						AliPayActivity.class);
				startActivity(intent);

			} else if (state.equals("qualified")) {
				Intent intent = new Intent();
				intent.setClass(MyCarFragment.this.getActivity(),
						OrderWaitingActivity.class);
				startActivity(intent);

			} else if (state.equals("timeout")) {
				Intent intent = new Intent();
				intent.setClass(MyCarFragment.this.getActivity(),
						MyOrderStatusActivity.class);
				startActivity(intent);

			} else if (state.equals("sumbitted")) {
				Intent intent = new Intent();
				intent.setClass(MyCarFragment.this.getActivity(),
						OrderHasDealerActivity.class);
				startActivity(intent);
			} else if (state.equals("deal_made")) {
				Intent intent = new Intent();
				intent.setClass(MyCarFragment.this.getActivity(),
						OrderHasDealerActivity.class);
				intent.putExtra("id", orderItemEntity.getId());
				startActivity(intent);
			}

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
			getData();
			return null;
		}
	}
	private void getData() {
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
				break;
			}
		}
		if (cookieStr == null || cookieStr.equals("")) {
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
			if (code == 200) {
				String result = EntityUtils.toString(response.getEntity());
				System.out.println("result:"+result);
				Type listType = new TypeToken<ArrayList<OrderItemEntity>>() {
				}.getType();
				list = new Gson().fromJson(result, listType);
				updateDB();
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

}
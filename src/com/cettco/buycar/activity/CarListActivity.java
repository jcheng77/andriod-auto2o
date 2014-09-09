package com.cettco.buycar.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import cn.trinea.android.common.entity.HttpResponse;
import cn.trinea.android.common.service.HttpCache;
import cn.trinea.android.common.service.HttpCache.HttpCacheListener;

import com.cettco.buycar.R;
import com.cettco.buycar.adapter.CarBrandListAdapter;
import com.cettco.buycar.adapter.CarExpandableListAdapter;
import com.cettco.buycar.entity.CarBrandEntity;
import com.cettco.buycar.entity.CarBrandListEntity;
import com.cettco.buycar.entity.CarManufactorEntity;
import com.cettco.buycar.entity.CarTypeEntity;
import com.cettco.buycar.utils.GlobalData;
import com.cettco.buycar.utils.HttpConnection;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CarListActivity extends Activity {

	private PullToRefreshListView pullToRefreshView;
	private int toggle = 0;
	private CarBrandListAdapter carBrandListAdapter;
	// private ArrayList<CarBrandEntity> carBrandList;
	private ArrayList<CarBrandEntity> carBrandEntities;
	private ExpandableListView carExpandedView;
	private CarExpandableListAdapter carExpandableListAdapter;
	private ListView carBrandListView;
	private RelativeLayout currentBrandLayout;
	private LinearLayout nodataLayout;
	private ImageView closeImageView;
	private ProgressBar progressBar;
	private HttpCache httpCache;
	private String carListUrl = GlobalData.getBaseUrl() + "/cars/all/car_list.json";
	private TextView currentBrandNameTextView;
	private CarBrandListEntity carBrandListEntity = new CarBrandListEntity();
	private int brandPosition=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_carlist);
		// getActionBar().hide();
		progressBar = (ProgressBar) findViewById(R.id.progressbar_carlist);
		httpCache = new HttpCache(this);
		// carBrandListView = (ListView)findViewById(R.id.carBrandListView);
		// carBrandList = new ArrayList<CarBrandEntity>();
		// Test data
		carBrandEntities = new ArrayList<CarBrandEntity>();
		
		currentBrandLayout = (RelativeLayout) findViewById(R.id.current_car_brand_reletavielayout);
		closeImageView = (ImageView) findViewById(R.id.current_car_brand_cancle);
		closeImageView.setOnClickListener(closeClickListener);
		
		currentBrandNameTextView = (TextView)findViewById(R.id.curent_car_brand_name);
		nodataLayout = (LinearLayout)findViewById(R.id.carlist_nodata_layout);

//		CarTypeEntity carTypeEntity = new CarTypeEntity();
//		carTypeEntity.setName("111");
//		carTypeEntity.setPic_url("http://example.com/1.jpg");
//		ArrayList<CarTypeEntity> carTypeEntities = new ArrayList<CarTypeEntity>();
//		carTypeEntities.add(carTypeEntity);
//		carTypeEntities.add(carTypeEntity);
//
//		CarManufactorEntity carManufactorEntity = new CarManufactorEntity();
//		carManufactorEntity.setName("11");
//		carManufactorEntity.setModels(carTypeEntities);
//		ArrayList<CarManufactorEntity> carManufactorEntities = new ArrayList<CarManufactorEntity>();
//		carManufactorEntities.add(carManufactorEntity);
//		carManufactorEntities.add(carManufactorEntity);
//
//		CarBrandEntity carBrandEntity = new CarBrandEntity();
//		carBrandEntity.setName("1");
//		carBrandEntity.setMakers(carManufactorEntities);
//		
//		carBrandEntities.add(carBrandEntity);
//		carBrandEntities.add(carBrandEntity);
//		carBrandEntities.add(carBrandEntity);
//
//		//CarBrandListEntity carBrandListEntity = new CarBrandListEntity();
//		carBrandListEntity.setBrands(carBrandEntities);
//		Gson gson = new Gson();
//		String result = gson.toJson(carBrandListEntity);
//		System.out.println(result);
//		// String abc = ' ';
//		CarBrandListEntity tmp = gson
//				.fromJson(result, CarBrandListEntity.class);
//		System.out.println("list entity:" + tmp.getBrands().size());
//		System.out.println("list entity:"
//				+ tmp.getBrands().get(0).getMakers().get(0).getName());

		pullToRefreshView = (PullToRefreshListView) findViewById(R.id.car_list_pull_to_refresh_listview);
		pullToRefreshView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// Do work to refresh the list here.
						new GetDataTask().execute();
					}

				});
		carBrandListView = pullToRefreshView.getRefreshableView();
		carBrandListAdapter = new CarBrandListAdapter(this,
				R.layout.carbrandlist_item, carBrandEntities);
		carBrandListView.setAdapter(carBrandListAdapter);
		carBrandListView.setOnItemClickListener(carBrandListener);

		carExpandedView = (ExpandableListView) findViewById(R.id.carExpandedList);
		carExpandedView.setGroupIndicator(null);
		carExpandableListAdapter = new CarExpandableListAdapter(this, null);
		carExpandedView.setAdapter(carExpandableListAdapter);
		carExpandedView.setOnChildClickListener(carChildClickListener);

		// getCacheData();
	}

	@Override
	protected void onResume() {
		// carExpandedView.set
		//pullToRefreshView.
		super.onResume();
		getCacheData();
	}

//	protected void getData() {
//		String url = "";
//		HttpConnection.post(url, null, new JsonHttpResponseHandler() {
//
//		});
//	}

	

	protected OnClickListener closeClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			carExpandedView.setVisibility(View.GONE);
			currentBrandLayout.setVisibility(View.GONE);
			pullToRefreshView.setVisibility(View.VISIBLE);
			// pullToRefreshView.setr
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
			//String url = GlobalData.getBaseUrl() + "/cars/all/car_list.json";
			System.out.println("background");
			if (httpCache.containsKey(carListUrl)) {
				httpCache.clear();
				// httpCache.
			}
			getCacheData();
			return null;
		}
	}
	protected void getCacheData() {
		//String url = GlobalData.getBaseUrl() + "/cars/all/car_list.json";
		// httpCache.clear();
		httpCache.httpGet(carListUrl, new HttpCacheListener() {

			protected void onPreGet() {
				// do something like show progressBar before httpGet, runs on
				// the UI thread
				// progressBar.setVisibility(View.VISIBLE);
			}

			protected void onPostGet(HttpResponse httpResponse,
					boolean isInCache) {
				// do something like show data after httpGet, runs on the UI
				// thread
				// progressBar.setVisibility(View.GONE);
				System.out.println("isincache:"+isInCache);
				if (httpResponse != null) {
					// get data success
					// setText(httpResponse.getResponseBody());
					//System.out.println("code:" + httpResponse.getResponseCode());
					//System.out.println("body" + httpResponse.getResponseBody());
					nodataLayout.setVisibility(View.GONE);
					String result= httpResponse.getResponseBody();
					Gson gson = new Gson();
					carBrandListEntity = gson.fromJson(result, CarBrandListEntity.class);
					carBrandEntities= carBrandListEntity.getBrands();
					System.out.println("brand size:"+carBrandEntities.size());
					System.out.println("name:"+carBrandEntities.get(0).getName());
					Message message = new Message();
					message.what = 1;
					mHandler.sendMessage(message);

				} else {
					// get data fail
					Toast toast = Toast.makeText(CarListActivity.this, "获取车型列表失败", Toast.LENGTH_SHORT);
					toast.show();
					nodataLayout.setVisibility(View.VISIBLE);
				}
			}
		});
	}

	protected OnItemClickListener carBrandListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			// System.out.println("size:");
			// Toast.makeText(this, carBrandList.size(), Toast.LENGTH_SHORT);
			int position = arg2 - 1;
			// System.out.println("index:"+arg2);
			// if(toggle==0)
			// {
			// // ArrayList<CarBrandEntity> tmpArrayList = new
			// ArrayList<CarBrandEntity>();
			// // CarBrandEntity tmpBrandEntity =
			// carBrandEntities.get(position);
			// // tmpArrayList.add(tmpBrandEntity);
			// // carBrandListAdapter.updateList(tmpArrayList);
			// //pullToRefreshView.no
			// carExpandedView.setVisibility(View.VISIBLE);
			// currentBrandLayout.setVisibility(View.VISIBLE);
			// pullToRefreshView.setVisibility(View.GONE);
			//
			// //carExpandableListAdapter.updateList(carBrandEntities.get(position).getList());
			// toggle = 1;
			// }
			// else if(toggle==1)
			// {
			// //carBrandListAdapter.updateList(carBrandEntities);
			// carExpandedView.setVisibility(View.GONE);
			// currentBrandLayout.setVisibility(View.GONE);
			// pullToRefreshView.setVisibility(View.VISIBLE);
			//
			// toggle = 0;
			// }
			brandPosition = position;
			carExpandableListAdapter.updateList(carBrandEntities.get(position)
					.getMakers());
			carExpandedView.setVisibility(View.VISIBLE);
			currentBrandLayout.setVisibility(View.VISIBLE);
			currentBrandNameTextView.setText(carBrandEntities.get(position).getName());
			pullToRefreshView.setVisibility(View.GONE);

		}
	};
	protected OnChildClickListener carChildClickListener = new OnChildClickListener() {

		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			// TODO Auto-generated method stub
			CarTypeEntity carTypeEntity = carBrandEntities.get(brandPosition).getMakers().get(groupPosition).getModels().get(childPosition);
			Intent intent = new Intent();
			intent.setClass(CarListActivity.this, CarDetailActivity.class);
			intent.putExtra("model", new Gson().toJson(carTypeEntity));
			startActivity(intent);
			return false;
		}
	};
	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				// updateTitle();
				//carBrandListAdapter.notifyDataSetChanged();
				carBrandListAdapter.updateList(carBrandEntities);
				break;
			}
		};
	};

	public void exitClick(View view) {
		finish();
	}

}

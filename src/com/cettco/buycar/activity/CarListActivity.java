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
import com.cettco.buycar.utils.HttpConnection;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CarListActivity extends ActionBarActivity{

	private PullToRefreshListView pullToRefreshView;
	private int toggle = 0;
	private CarBrandListAdapter carBrandListAdapter;
	//private ArrayList<CarBrandEntity> carBrandList;
	private ArrayList<CarBrandEntity> carBrandEntities;
	private ExpandableListView carExpandedView;
	private CarExpandableListAdapter carExpandableListAdapter;
	private ListView carBrandListView;
	private RelativeLayout currentBrandLayout;
	private ImageView closeImageView;
	
	private HttpCache httpCache;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_carlist);
		getActionBar().hide();
		
		httpCache = new HttpCache(this);
		//carBrandListView = (ListView)findViewById(R.id.carBrandListView);
		//carBrandList = new ArrayList<CarBrandEntity>();
		//Test data
		currentBrandLayout = (RelativeLayout)findViewById(R.id.current_car_brand_reletavielayout);
		closeImageView = (ImageView)findViewById(R.id.current_car_brand_cancle);
		closeImageView.setOnClickListener(closeClickListener);
		
		CarTypeEntity carTypeEntity = new CarTypeEntity();
		carTypeEntity.setName("111");
		carTypeEntity.setUrl("http://example.com/1.jpg");
		ArrayList<CarTypeEntity> carTypeEntities = new ArrayList<CarTypeEntity>();
		carTypeEntities.add(carTypeEntity);
		carTypeEntities.add(carTypeEntity);
		
		CarManufactorEntity carManufactorEntity = new CarManufactorEntity();
		carManufactorEntity.setName("11");
		carManufactorEntity.setModel(carTypeEntities);
		ArrayList<CarManufactorEntity> carManufactorEntities = new ArrayList<CarManufactorEntity>();
		carManufactorEntities.add(carManufactorEntity);
		carManufactorEntities.add(carManufactorEntity);
		
		CarBrandEntity carBrandEntity = new CarBrandEntity();
		carBrandEntity.setName("1");
		carBrandEntity.setMaker(carManufactorEntities);
		carBrandEntities = new ArrayList<CarBrandEntity>();
		carBrandEntities.add(carBrandEntity);
		carBrandEntities.add(carBrandEntity);
		carBrandEntities.add(carBrandEntity);
		
		CarBrandListEntity carBrandListEntity = new CarBrandListEntity();
		carBrandListEntity.setBrand(carBrandEntities);
		Gson gson = new Gson();
		String result = gson.toJson(carBrandListEntity);
		System.out.println(result);
		
		CarBrandListEntity tmp = gson.fromJson(result,CarBrandListEntity.class);
		System.out.println("list entity:"+tmp.getBrand().size());
		System.out.println("list entity:"+tmp.getBrand().get(0).getMaker().get(0).getName());
		
		pullToRefreshView = (PullToRefreshListView)findViewById(R.id.car_list_pull_to_refresh_listview);
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
		carBrandListAdapter = new CarBrandListAdapter(this, R.layout.carbrandlist_item, carBrandEntities);
		carBrandListView.setAdapter(carBrandListAdapter);
		carBrandListView.setOnItemClickListener(carBrandListener);
		
		carExpandedView = (ExpandableListView)findViewById(R.id.carExpandedList);
		carExpandedView.setGroupIndicator(null);
		carExpandableListAdapter = new CarExpandableListAdapter(this, null);
		carExpandedView.setAdapter(carExpandableListAdapter);
		carExpandedView.setOnChildClickListener(carChildClickListener);
	}
	protected void getData(){
		String url ="";
		HttpConnection.post(url, null,new JsonHttpResponseHandler(){
			
		});
	}
	protected void getCacheData(){
		String url = "";
		httpCache.httpGet("http://www.trinea.cn/", new HttpCacheListener() {
			 
		    protected void onPreGet() {
		        // do something like show progressBar before httpGet, runs on the UI thread 
		    }
		 
		    protected void onPostGet(HttpResponse httpResponse, boolean isInCache) {
		        // do something like show data after httpGet, runs on the UI thread 
		        if (httpResponse != null) {
		            // get data success
		            //setText(httpResponse.getResponseBody());
		        } else {
		            // get data fail
		        }
		    }
		});
	}
	protected OnClickListener closeClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			carExpandedView.setVisibility(View.GONE);
			currentBrandLayout.setVisibility(View.GONE);
			pullToRefreshView.setVisibility(View.VISIBLE);
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
				Thread.sleep(4000);
			} catch (InterruptedException e) {
			}
			return null;
		}
	}
	protected OnItemClickListener carBrandListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			//System.out.println("size:");
			//Toast.makeText(this, carBrandList.size(), Toast.LENGTH_SHORT);
			int position = arg2-1;
//			System.out.println("index:"+arg2);
//			if(toggle==0)
//			{
////				ArrayList<CarBrandEntity> tmpArrayList = new ArrayList<CarBrandEntity>();
////				CarBrandEntity tmpBrandEntity = carBrandEntities.get(position);
////				tmpArrayList.add(tmpBrandEntity);
////				carBrandListAdapter.updateList(tmpArrayList);
//				//pullToRefreshView.no
//				carExpandedView.setVisibility(View.VISIBLE);
//				currentBrandLayout.setVisibility(View.VISIBLE);
//				pullToRefreshView.setVisibility(View.GONE);
//				
//				//carExpandableListAdapter.updateList(carBrandEntities.get(position).getList());
//				toggle = 1;
//			}
//			else if(toggle==1)
//			{
//				//carBrandListAdapter.updateList(carBrandEntities);
//				carExpandedView.setVisibility(View.GONE);
//				currentBrandLayout.setVisibility(View.GONE);
//				pullToRefreshView.setVisibility(View.VISIBLE);
//				
//				toggle = 0;
//			}
			carExpandableListAdapter.updateList(carBrandEntities.get(position).getMaker());
			carExpandedView.setVisibility(View.VISIBLE);
			currentBrandLayout.setVisibility(View.VISIBLE);
			pullToRefreshView.setVisibility(View.GONE);
			
			
		}
	};
	protected OnChildClickListener carChildClickListener = new OnChildClickListener() {
		
		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
                int groupPosition, int childPosition, long id) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(CarListActivity.this, CarDetailActivity.class);
			startActivity(intent);
			return false;
		}
	};
	public void exitClick(View view)
	{
		finish();
	}

}

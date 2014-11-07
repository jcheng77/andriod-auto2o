package com.cettco.buycar.activity;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.util.EncodingUtils;

import cn.trinea.android.common.entity.HttpResponse;
import cn.trinea.android.common.service.HttpCache;
import cn.trinea.android.common.service.HttpCache.HttpCacheListener;

import com.cettco.buycar.R;
import com.cettco.buycar.adapter.CarBrandListAdapter;
import com.cettco.buycar.adapter.CarExpandableListAdapter;
import com.cettco.buycar.entity.CarBrandEntity;
import com.cettco.buycar.entity.CarBrandListEntity;
import com.cettco.buycar.entity.CarColorEntity;
import com.cettco.buycar.entity.CarMakerEntity;
import com.cettco.buycar.entity.CarModelEntity;
import com.cettco.buycar.entity.CarTrimEntity;
import com.cettco.buycar.entity.OrderItemEntity;
import com.cettco.buycar.utils.GlobalData;
import com.cettco.buycar.utils.db.DatabaseHelperBrand;
import com.cettco.buycar.utils.db.DatabaseHelperColor;
import com.cettco.buycar.utils.db.DatabaseHelperMaker;
import com.cettco.buycar.utils.db.DatabaseHelperModel;
import com.cettco.buycar.utils.db.DatabaseHelperOrder;
import com.cettco.buycar.utils.db.DatabaseHelperTrim;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
	private List<CarBrandEntity> carBrandEntities;
	private ExpandableListView carExpandedView;
	private CarExpandableListAdapter carExpandableListAdapter;
	private ListView carBrandListView;
	private RelativeLayout currentBrandLayout;
	private LinearLayout nodataLayout;
	private ImageView closeImageView;
	private ProgressBar progressBar;
	private HttpCache httpCache;
	private String carListUrl = GlobalData.getBaseUrl() + "/cars/list.json";
	private TextView currentBrandNameTextView;
	private CarBrandListEntity carBrandListEntity = new CarBrandListEntity();
	private int brandPosition = 0;
	private List<CarMakerEntity> makerEntities;

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
		makerEntities = new ArrayList<CarMakerEntity>();

		currentBrandLayout = (RelativeLayout) findViewById(R.id.current_car_brand_reletavielayout);
		closeImageView = (ImageView) findViewById(R.id.current_car_brand_cancle);
		closeImageView.setOnClickListener(closeClickListener);

		currentBrandNameTextView = (TextView) findViewById(R.id.curent_car_brand_name);
		nodataLayout = (LinearLayout) findViewById(R.id.carlist_nodata_layout);

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
				R.layout.item_carbrandlist, carBrandEntities);
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
		// pullToRefreshView.
		super.onResume();
		getCachedData();
	}

	// protected void getData() {
	// String url = "";
	// HttpConnection.post(url, null, new JsonHttpResponseHandler() {
	//
	// });
	// }

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
			// String url = GlobalData.getBaseUrl() + "/cars/all/car_list.json";
			//System.out.println("background");
			if (httpCache.containsKey(carListUrl)) {
				httpCache.clear();
				// httpCache.
			}
			getData();
			return null;
		}
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
			try {
				getChildData(brandPosition);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("maker size:" + makerEntities.size());
			carExpandableListAdapter.updateList(makerEntities);
			carExpandedView.setVisibility(View.VISIBLE);
			currentBrandLayout.setVisibility(View.VISIBLE);
			currentBrandNameTextView.setText(carBrandEntities.get(position)
					.getName());
			pullToRefreshView.setVisibility(View.GONE);
			// carExpandableListAdapter.updateList(carBrandEntities.get(position)
			// .getMakers());
			// carExpandedView.setVisibility(View.VISIBLE);
			// currentBrandLayout.setVisibility(View.VISIBLE);
			// currentBrandNameTextView.setText(carBrandEntities.get(position)
			// .getName());
			// pullToRefreshView.setVisibility(View.GONE);

		}
	};
	protected OnChildClickListener carChildClickListener = new OnChildClickListener() {

		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			// TODO Auto-generated method stub
			// CarModelEntity carTypeEntity =
			// carBrandEntities.get(brandPosition)
			// .getMakers().get(groupPosition).getModels()
			// .get(childPosition);
			System.out.println("maker size:" + makerEntities.size());
			System.out.println("group position:" + groupPosition);
			System.out.println("model size:"
					+ makerEntities.get(groupPosition).getModels().size());
			System.out.println("child position:" + childPosition);
			CarModelEntity modelEntity = makerEntities.get(groupPosition)
					.getModels().get(childPosition);
			OrderItemEntity orderItemEntity = new OrderItemEntity();
			orderItemEntity.setPic_url(modelEntity.getPic_url());
			orderItemEntity.setState("viewed");
			String model_name = carBrandEntities.get(brandPosition).getName()
					+ " : " + makerEntities.get(groupPosition).getName()
					+ " : " + modelEntity.getName();
			orderItemEntity.setModel(model_name);
			orderItemEntity.setModel_id(modelEntity.getId());
			orderItemEntity.setTime(new Date());
			// orderItemEntity.setTrim(trim);
			DatabaseHelperOrder helper = DatabaseHelperOrder
					.getHelper(CarListActivity.this);
			try {
				int i = helper.getDao().create(orderItemEntity);
				System.out.println("i is:" + i);
				System.out.println("primary id:"
						+ orderItemEntity.getOrder_id());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Intent intent = new Intent();
			intent.setClass(CarListActivity.this, CarDetailActivity.class);
			intent.putExtra("order_id", orderItemEntity.getOrder_id());
			// intent.putExtra("model", new Gson().toJson(carTypeEntity));
			// intent.putExtra("name", car);
			// intent.putExtra("id", new Gson().toJson(carTypeEntity));
			startActivity(intent);
			return false;
		}
	};
	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				// updateTitle();
				// carBrandListAdapter.notifyDataSetChanged();
				carBrandListAdapter.updateList(carBrandEntities);
				try {
					writeCacheData();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 2:
				carBrandListAdapter.updateList(carBrandEntities);
			}
		};
	};

	private void getCachedData() {
		DatabaseHelperBrand helper = DatabaseHelperBrand.getHelper(this);
		try {
			List<CarBrandEntity> nums = helper.getDao().queryForAll();
			if (nums == null || nums.size() == 0) {
//				new Thread(new Runnable() {
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						String res = "";
//
//						try {
//
//							InputStream in = getResources().openRawResource(
//									R.raw.car_list);
//
//							// 在\Test\res\raw\bbi.txt,
//
//							int length = in.available();
//
//							byte[] buffer = new byte[length];
//
//							in.read(buffer);
//
//							// res = EncodingUtils.getString(buffer, "UTF-8");
//
//							// res = EncodingUtils.getString(buffer, "UNICODE");
//
//							res = EncodingUtils.getString(buffer, "UTF-8");
//
//							// 依bbi.txt的编码类型选择合适的编码，如果不调整会乱码
//
//							in.close();
//
//						} catch (Exception e) {
//
//							e.printStackTrace();
//
//						}
//						Gson gson = new Gson();
//						carBrandListEntity = gson.fromJson(res,
//								CarBrandListEntity.class);
//						carBrandEntities = carBrandListEntity.getBrands();
//						try {
//							writeCacheData();
//						} catch (SQLException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						Message message = new Message();
//						message.what = 2;
//						mHandler.sendMessage(message);
//					}
//				}).start();
			} else {
				//System.out.println("cached");
				carBrandEntities = nums;
				carBrandListAdapter.updateList(carBrandEntities);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void getData() {
		// String url = GlobalData.getBaseUrl() + "/cars/list.json";
		// httpCache.clear();
		//System.out.println(carListUrl);
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
				//System.out.println("isincache:" + isInCache);
				if (httpResponse != null) {
					// get data success
					// setText(httpResponse.getResponseBody());
					// System.out.println("code:" +
					// httpResponse.getResponseCode());
					// System.out.println("body" +
					// httpResponse.getResponseBody());
					nodataLayout.setVisibility(View.GONE);
					String result = httpResponse.getResponseBody();
					Gson gson = new Gson();
					carBrandListEntity = gson.fromJson(result,
							CarBrandListEntity.class);
					carBrandEntities = carBrandListEntity.getBrands();
					try {
						writeCacheData();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//System.out.println("brand size:" + carBrandEntities.size());
					//System.out.println("name:"
							//+ carBrandEntities.get(0).getName());
					Message message = new Message();
					message.what = 1;
					mHandler.sendMessage(message);

				} else {
					// get data fail
					Toast toast = Toast.makeText(CarListActivity.this,
							"获取车型列表失败", Toast.LENGTH_SHORT);
					toast.show();
					nodataLayout.setVisibility(View.VISIBLE);
				}
			}
		});
	}

	private void getChildData(int n) throws SQLException {
		DatabaseHelperMaker helperMaker = DatabaseHelperMaker.getHelper(this);
		CarBrandEntity brandEntity = carBrandEntities.get(n);
		//System.out.println("brand_id:" + brandEntity.getId());
		List<CarMakerEntity> tmp = helperMaker.getDao().queryBuilder().query();
		//System.out.println("tmp size:" + tmp.size());
		for (int m = 0; m < tmp.size(); m++) {
			//System.out.println("tmp " + tmp.get(m).getBrand_id() + ":"
					//+ tmp.get(m).getId());
		}
		makerEntities = helperMaker.getDao().queryBuilder().where()
				.eq("brand_id", brandEntity.getId()).query();
		for (int i = 0; i < makerEntities.size(); i++) {
			DatabaseHelperModel helperModel = DatabaseHelperModel
					.getHelper(this);
			List<CarModelEntity> modelEntities = helperModel.getDao()
					.queryBuilder().where()
					.eq("maker_id", makerEntities.get(i).getId()).query();
			//System.out
					//.println("modle pic:" + modelEntities.get(0).getPic_url());
			makerEntities.get(i).setModels(modelEntities);
		}

	}

	private void writeCacheData() throws SQLException {
		List<CarBrandEntity> tmpBrandEntities = carBrandListEntity.getBrands();
		// DatabaseHelperBrand
		DatabaseHelperBrand helperBrand = DatabaseHelperBrand.getHelper(this);
		for (int i = 0; i < tmpBrandEntities.size(); i++) {
			CarBrandEntity brandEntity = tmpBrandEntities.get(i);
			try {
				helperBrand.getDao().createOrUpdate(brandEntity);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<CarMakerEntity> tmpManufactorEntities = brandEntity
					.getMakers();
			//System.out.println("tmp maker size:" + tmpManufactorEntities);
			DatabaseHelperMaker helperMaker = DatabaseHelperMaker
					.getHelper(this);
			for (int j = 0; j < tmpManufactorEntities.size(); j++) {
				CarMakerEntity makerEntity = tmpManufactorEntities.get(j);
				makerEntity.setBrand_id(brandEntity.getId());
				//System.out.println("tmp maker :" + makerEntity.getId() + ":"
						//+ makerEntity.getName());
				try {
					helperMaker.getDao().createOrUpdate(makerEntity);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				List<CarModelEntity> tmpModelEntities = makerEntity.getModels();
				DatabaseHelperModel helperModel = DatabaseHelperModel
						.getHelper(this);
				for (int k = 0; k < tmpModelEntities.size(); k++) {
					CarModelEntity modelEntity = tmpModelEntities.get(k);
					modelEntity.setMaker_id(makerEntity.getId());
					try {
						helperModel.getDao().createOrUpdate(modelEntity);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					List<CarTrimEntity> tmpTrimEntities = modelEntity
							.getTrims();
					DatabaseHelperTrim helperTrim = DatabaseHelperTrim
							.getHelper(this);
					for (int m = 0; m < tmpTrimEntities.size(); m++) {
						CarTrimEntity trimEntity = tmpTrimEntities.get(m);
						trimEntity.setModel_id(modelEntity.getId());
						helperTrim.getDao().createOrUpdate(trimEntity);
					}
					List<CarColorEntity> tmpColorEntities = modelEntity
							.getColors();
					DatabaseHelperColor helperColor = DatabaseHelperColor
							.getHelper(this);
					for (int m = 0; m < tmpColorEntities.size(); m++) {
						CarColorEntity colorEntity = tmpColorEntities.get(m);
						colorEntity.setModel_id(modelEntity.getId());
						helperColor.getDao().createOrUpdate(colorEntity);
					}
				}
			}
		}
	}

	public void exitClick(View view) {
		finish();
	}

}

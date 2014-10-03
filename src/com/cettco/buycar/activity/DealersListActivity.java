package com.cettco.buycar.activity;

import java.util.ArrayList;

import cn.trinea.android.common.view.DropDownListView;
import cn.trinea.android.common.view.DropDownListView.OnDropDownListener;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.navi.location.BDLocation;
import com.baidu.navi.location.BDLocationListener;
import com.baidu.navi.location.LocationClient;
import com.baidu.navi.location.LocationClientOption;
import com.cettco.buycar.R;
import com.cettco.buycar.adapter.DealerCommentAdapter;
import com.cettco.buycar.adapter.DealerListAdapter;
import com.cettco.buycar.entity.DealerCommentEntity;
import com.cettco.buycar.entity.DealerEntity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
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
		adapter = new DealerListAdapter(this, R.layout.dealer_item,
				listItems);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(listItemClickListener);
	}

	private void initLocation(){
		locationClient = new LocationClient(this);
		//设置定位条件  
        LocationClientOption option = new LocationClientOption();  
        option.setOpenGps(true);                                //是否打开GPS  
        option.setCoorType("bd09ll");                           //设置返回值的坐标类型。  
        //com.baidu.navi.location.LocationClientOption.n
        option.setPriority(LocationClientOption.NetWorkFirst);  //设置定位优先级  
        option.setProdName("LocationDemo");                     //设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。  
        option.setScanSpan(UPDATE_TIME);                        //设置定时定位的时间间隔。单位毫秒  
        locationClient.setLocOption(option);  
          
        //注册位置监听器  
        locationClient.registerLocationListener(new BDLocationListener() {  
              
            @Override  
            public void onReceiveLocation(BDLocation location) {  
                // TODO Auto-generated method stub  
                if (location == null) {  
                    return;  
                }  
                for(int i=0;i<listItems.size();i++){
                	DealerEntity tmp =listItems.get(i);
                	LatLng pt1=null;
                	LatLng pt2 = null;
                	double distance = DistanceUtil.getDistance(pt1, pt2);
                	tmp.setDistance((int) distance);
                	
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
              
            @Override  
            public void onReceivePoi(BDLocation location) {  
            }  
              
        });
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

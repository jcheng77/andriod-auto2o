package com.cettco.buycar.activity;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.cettco.buycar.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DealerMapActivity extends Activity{

	private MapView mMapView = null;  
	private BaiduMap mBaiduMap;
	private Button button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());  
		setContentView(R.layout.activity_dealer_map);
		//getActionBar().hide();
		button = (Button)findViewById(R.id.localMapBtn);
		button.setOnClickListener(localMapBtnClickListener);
		mMapView = (MapView) findViewById(R.id.bmapView);  
		mBaiduMap = mMapView.getMap();  
		//普通地图  
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL); 
		//定义Maker坐标点  
		LatLng point = new LatLng(39.963175, 116.400244);  
		//构建Marker图标  
		BitmapDescriptor bitmap = BitmapDescriptorFactory  
		    .fromResource(R.drawable.icon_marka);  
		//构建MarkerOption，用于在地图上添加Marker  
		OverlayOptions option = new MarkerOptions()  
		    .position(point)  
		    .icon(bitmap);  
		//在地图上添加Marker，并显示  
		mBaiduMap.addOverlay(option);
	}
	protected OnClickListener localMapBtnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(Intent.ACTION_VIEW);
			Uri uri = Uri.parse("geo:39.922840,116.3543240,北京市西城区阜外大街2号万通大厦");
			intent.setData(uri);
			//intent.setPackage("com.baidu.BaiduMap");
			if (intent.resolveActivity(getPackageManager()) != null) {
		        startActivity(intent);
		    }
			else{
				 uri=Uri.parse("http://api.map.baidu.com/geocoder?address="+"上海虹桥机场"+"&output=html");
				 Intent intent2= new Intent(Intent.ACTION_VIEW, uri);
				 intent2.setData(uri); 
				 startActivity(intent2);
			}
		}
	};
	@Override  
    protected void onDestroy() {  
        super.onDestroy();  
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理  
        mMapView.onDestroy();  
    }  
    @Override  
    protected void onResume() {  
        super.onResume();  
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
        mMapView.onResume();  
        }  
    @Override  
    protected void onPause() {  
        super.onPause();  
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
        mMapView.onPause();  
        }  
	
}

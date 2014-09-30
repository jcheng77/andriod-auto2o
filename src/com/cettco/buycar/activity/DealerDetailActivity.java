package com.cettco.buycar.activity;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.navi.location.BDLocation;
import com.baidu.navi.location.BDLocationListener;
import com.baidu.navi.location.LocationClient;
import com.baidu.navi.location.LocationClientOption;
import com.cettco.buycar.R;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DealerDetailActivity extends Activity {

	private RelativeLayout locationLayout;
	private LocationClient locationClient;
	private TextView addressTextView;
	private TextView distancetTextView;
	private static final int UPDATE_TIME = 5000;  
    private static int LOCATION_COUTNS = 0; 
    private LatLng pt1; 
	private LatLng pt2;
	private RelativeLayout viewCommentLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dealer_detail);
		//getActionBar().hide();
		locationLayout = (RelativeLayout) findViewById(R.id.activity_dealer_detail_locationLayout);
		locationLayout.setOnClickListener(locationClickListener);
		
		viewCommentLayout = (RelativeLayout)findViewById(R.id.activity_dealer_detail_viewcomment);
		viewCommentLayout.setOnClickListener(commentClickListener);
		
		addressTextView = (TextView)findViewById(R.id.activity_dealer_detail_address_textView);
		distancetTextView = (TextView)findViewById(R.id.activity_dealer_detail_distance_textView);
		//
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
                pt1 = new LatLng(location.getLatitude(), location.getLongitude());
                double distance = DistanceUtil.getDistance(pt1, pt2);
                distancetTextView.setText(distance+"km");
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
        locationClient.start();
        locationClient.requestLocation();
	}
	private void stopLocation(){
		
	}
	private void startLocation(){
		if (locationClient == null) {  
            return;  
        }  
        if (locationClient.isStarted()) {  
            //startButton.setText("Start");  
            locationClient.stop();  
        }else {  
            //startButton.setText("Stop");  
            locationClient.start();  
            /* 
             *当所设的整数值大于等于1000（ms）时，定位SDK内部使用定时定位模式。 
             *调用requestLocation( )后，每隔设定的时间，定位SDK就会进行一次定位。 
             *如果定位SDK根据定位依据发现位置没有发生变化，就不会发起网络请求， 
             *返回上一次定位的结果；如果发现位置改变，就进行网络请求进行定位，得到新的定位结果。 
             *定时定位时，调用一次requestLocation，会定时监听到定位结果。 
             */  
            locationClient.requestLocation();  
        }  
	}
	
	private OnClickListener commentClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(DealerDetailActivity.this,DealerCommentActivity.class);
			startActivity(intent);
		}
	};
	private OnClickListener locationClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			 Intent intent = new Intent();
			 intent.setClass(DealerDetailActivity.this,DealerMapActivity.class);
			 startActivity(intent);

//			Intent intent = new Intent(Intent.ACTION_VIEW);
//			Uri uri = Uri.parse("geo:39.922840,116.3543240,北京市西城区阜外大街2号万通大厦");
//			intent.setData(uri);
//			if (intent.resolveActivity(getPackageManager()) != null) {
//		        startActivity(intent);
//		    }
//			else{
//				 uri=Uri.parse("http://api.map.baidu.com/geocoder?address="+"上海虹桥机场"+"&output=html");
//				 Intent intent2= new Intent(Intent.ACTION_VIEW, uri);
//				 intent2.setData(uri); 
//				 startActivity(intent2);
//			}
		}
	};

	@Override  
    protected void onDestroy() {  
        super.onDestroy();  
        if (locationClient != null && locationClient.isStarted()) {  
            locationClient.stop();  
            locationClient = null;  
        }  
    }
	public void exitClick(View view) {
		this.finish();
	}
}

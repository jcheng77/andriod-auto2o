package com.cettco.buycar.utils;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class GetLocation {
	private double latitude=0.0;
	private double longitude =0.0;
	public void getLocation(Context context){
		LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {    

            @Override  
            public void onProviderEnabled(String s) {  
                //To change body of implemented methods use File | Settings | File Templates.  
            }  

            @Override  
            public void onProviderDisabled(String s) {  
                //To change body of implemented methods use File | Settings | File Templates.  
            }

			@Override
			public void onLocationChanged(android.location.Location arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
				// TODO Auto-generated method stub
				
			}  
        });
		android.location.Location location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);   
        if(location != null){   
            latitude = location.getLatitude(); //经度   
            longitude = location.getLongitude(); //纬度
            System.out.println("latitude:"+latitude+" longitude:"+longitude);
        } 
        else {
			System.out.println("It's null");
		}
    }  
}

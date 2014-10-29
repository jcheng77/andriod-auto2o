package com.cettco.buycar.activity;

import com.baidu.mapapi.SDKInitializer;
import com.cettco.buycar.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class OrderDetailActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());  
		setContentView(R.layout.activity_order_detail);
	}
	public void exitClick(View view){
		this.finish();
	}
	
}

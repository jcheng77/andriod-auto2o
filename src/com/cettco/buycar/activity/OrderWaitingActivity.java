package com.cettco.buycar.activity;

import com.cettco.buycar.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class OrderWaitingActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_waiting);
	}
	public void exitClick(View view){
		this.finish();
	}

}

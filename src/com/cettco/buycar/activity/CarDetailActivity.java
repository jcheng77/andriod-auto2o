package com.cettco.buycar.activity;

import com.cettco.buycar.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class CarDetailActivity extends ActionBarActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cardetail);
		getActionBar().hide();
		LinearLayout selectCarTypeLayout = (LinearLayout)findViewById(R.id.selectCarType_layout);
		selectCarTypeLayout.setOnClickListener(selectCartypeClickListener);
	}
	protected OnClickListener selectCartypeClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(CarDetailActivity.this, SelectCarTypeActivity.class);
			startActivity(intent);
		}
	};
	public void exitClick(View view)
	{
		finish();
	}
}

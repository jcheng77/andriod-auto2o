package com.cettco.buycar.activity;

import com.cettco.buycar.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class BargainActivity extends ActionBarActivity{

	private RelativeLayout colorLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bargain);
		getActionBar().hide();
		colorLayout = (RelativeLayout)findViewById(R.id.activityBargainColorRelativeLayout);
		colorLayout.setOnClickListener(colorLayoutClickListener);
	}
	protected OnClickListener colorLayoutClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(BargainActivity.this, BargainColorActivity.class);
			startActivity(intent);
		}
	};
	public void exitClick(View view){
		this.finish();
	}
	
}

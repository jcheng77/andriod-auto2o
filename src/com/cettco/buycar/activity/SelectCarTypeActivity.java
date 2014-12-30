package com.cettco.buycar.activity;

import com.cettco.buycar.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class SelectCarTypeActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_cartype);
		//getActionBar().hide();
	}
	public void exitClick(View view)
	{
		this.finish();
	}
	
}

package com.cettco.buycar.activity;

import com.cettco.buycar.R;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class FindPwdActivity extends ActionBarActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_findpwd);
		getActionBar().hide();
	}
	public void exitClick(View view)
	{
		this.finish();
	}

}

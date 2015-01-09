package com.cettco.buycar.activity;

import com.cettco.buycar.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MyCreditActivity extends Activity{

	private TextView titleTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_credit);
		titleTextView = (TextView) findViewById(R.id.title_text);
		titleTextView.setText("我的积分");
	}
	
}

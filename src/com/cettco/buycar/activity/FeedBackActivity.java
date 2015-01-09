package com.cettco.buycar.activity;

import com.cettco.buycar.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FeedBackActivity extends Activity{


	private TextView titleTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		titleTextView = (TextView) findViewById(R.id.title_text);
		titleTextView.setText("对话CEO");
	}
	public void exitClick(View view){
		this.finish();
	}

}

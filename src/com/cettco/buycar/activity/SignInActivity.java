package com.cettco.buycar.activity;

import com.cettco.buycar.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SignInActivity extends ActionBarActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin);
		getActionBar().hide();
		TextView signUpTextView = (TextView)findViewById(R.id.signUpText);
		signUpTextView.setOnClickListener(signUpClickListener);
		TextView findpwdteTextView = (TextView)findViewById(R.id.findpwd_text);
		findpwdteTextView.setOnClickListener(findpwdclClickListener);
		
	}
	protected OnClickListener signUpClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(SignInActivity.this, SignUpActivity.class);
			startActivity(intent);
		}
	};
	protected OnClickListener findpwdclClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(SignInActivity.this, FindPwdActivity.class);
			startActivity(intent);
		}
	};
	public void exitClick(View view)
	{
		this.finish();
	}
	
}

package com.cettco.buycar.activity;

import org.apache.http.Header;

import com.cettco.buycar.R;
import com.cettco.buycar.utils.GetLocation;
import com.cettco.buycar.utils.HttpConnection;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignInActivity extends ActionBarActivity{

	private Button signinButton;
	private EditText signinPhoneEditText;
	private EditText signinPasswordeEditText;
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
		
		signinButton = (Button)findViewById(R.id.signinBtn);
		signinButton.setOnClickListener(signInClickListener);
		
		signinPasswordeEditText = (EditText)findViewById(R.id.signinPasswordEditText);
		signinPhoneEditText = (EditText)findViewById(R.id.signinPhoneEditText);
		GetLocation location = new GetLocation();
		location.getLocation(this);
	}
	protected OnClickListener signInClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			String url = "";
			String phone = signinPhoneEditText.getText().toString();
			String password = signinPasswordeEditText.getText().toString();
			RequestParams params = new RequestParams();
			params.put("phone", phone);
			params.put("password", password);
			HttpConnection.post(url, params, new AsyncHttpResponseHandler() {
				
				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
					// TODO Auto-generated method stub
					
				}
			});
		}
	};
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

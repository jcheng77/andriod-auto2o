package com.cettco.buycar.activity;

import org.apache.http.Header;

import com.cettco.buycar.R;
import com.cettco.buycar.utils.HttpConnection;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends ActionBarActivity{

	private EditText signupPhoneEditText;
	private EditText signupPasswordEditText;
	private EditText signupRePasswordeEditText;
	private Button signupButton;
	private Button checkcodeButton;
	private EditText checkcodeEditText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		getActionBar().hide();
		
		signupButton = (Button)findViewById(R.id.signupBtn);
		signupButton.setOnClickListener(signupBtnClickListener);
		
		signupPhoneEditText = (EditText)findViewById(R.id.signupPhoneEditText);
		signupPasswordEditText = (EditText)findViewById(R.id.signupPasswordEdit);
		signupRePasswordeEditText = (EditText)findViewById(R.id.signupRePasswordEditText);
		
		checkcodeButton = (Button)findViewById(R.id.signupCheckcodeBtn);
		checkcodeButton.setOnClickListener(checkcodeBtnClickListener);
		
		checkcodeEditText = (EditText)findViewById(R.id.signupCheckcodeEditText);
		
	}
	protected OnClickListener checkcodeBtnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			String url = "";
			String phone = signupPhoneEditText.getText().toString();
			RequestParams params = new RequestParams();
			params.put("phone", phone);
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
	protected OnClickListener signupBtnClickListener =new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			String url = "";
			String phone = signupPhoneEditText.getText().toString();
			String password = signupPasswordEditText.getText().toString();
			String rePassword = signupRePasswordeEditText.getText().toString();
			String checkcode = checkcodeEditText.getText().toString();
			RequestParams params = new RequestParams();
			params.put("phone", phone);
			params.put("password", password);
			params.put("repassowrd", rePassword);
			params.put("checkcode", checkcode);
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
	public void exitClick(View view)
	{
		this.finish();
	}
	
}

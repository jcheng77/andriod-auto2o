	package com.cettco.buycar.activity;

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import com.cettco.buycar.R;
import com.cettco.buycar.entity.User;
import com.cettco.buycar.entity.UserEntity;
import com.cettco.buycar.utils.GlobalData;
import com.cettco.buycar.utils.HttpConnection;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class SignUpActivity extends Activity{

	private EditText signupPhoneEditText;
	private EditText signupPasswordEditText;
	private EditText signupRePasswordeEditText;
	private Button signupButton;
	private Button checkcodeButton;
	private EditText checkcodeEditText;
	private RelativeLayout progressLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		//getActionBar().hide();
		
		signupButton = (Button)findViewById(R.id.signupBtn);
		signupButton.setOnClickListener(signupBtnClickListener);
		
		signupPhoneEditText = (EditText)findViewById(R.id.signupPhoneEditText);
//		signupPasswordEditText = (EditText)findViewById(R.id.signupPasswordEdit);
//		signupRePasswordeEditText = (EditText)findViewById(R.id.signupRePasswordEditText);
//		
//		checkcodeButton = (Button)findViewById(R.id.signupCheckcodeBtn);
//		checkcodeButton.setOnClickListener(checkcodeBtnClickListener);
//		
//		checkcodeEditText = (EditText)findViewById(R.id.signupCheckcodeEditText);
		progressLayout = (RelativeLayout)findViewById(R.id.progressbar_relativeLayout);
		
	}
//	protected OnClickListener checkcodeBtnClickListener = new OnClickListener() {
//		
//		@Override
//		public void onClick(View arg0) {
//			// TODO Auto-generated method stub
//			String url = "";
//			String phone = signupPhoneEditText.getText().toString();
//			RequestParams params = new RequestParams();
//			params.put("dealer[phone]", phone);
//			HttpConnection.post(url, params, new JsonHttpResponseHandler(){
//
//				@Override
//				public void onFailure(int statusCode, Header[] headers,
//						Throwable throwable, JSONObject errorResponse) {
//					// TODO Auto-generated method stub
//					super.onFailure(statusCode, headers, throwable, errorResponse);
//					progressLayout.setVisibility(View.GONE);
//				}
//
//				@Override
//				public void onSuccess(int statusCode, Header[] headers,
//						JSONObject response) {
//					// TODO Auto-generated method stub
//					super.onSuccess(statusCode, headers, response);
//					progressLayout.setVisibility(View.GONE);
//				}
//				
//			});
//			progressLayout.setVisibility(View.VISIBLE);
//		}
//	};
	protected OnClickListener signupBtnClickListener =new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			String url = GlobalData.getBaseUrl()+"/users/register.json";
			String phone = signupPhoneEditText.getText().toString();
			User user = new User();
			user.setPhone(phone);
			UserEntity userEntity = new UserEntity();
			userEntity.setUser(user);
			Gson gson = new Gson();
	        StringEntity entity = null;
	        try {
	        	System.out.println(gson.toJson(userEntity).toString());
				entity = new StringEntity(gson.toJson(userEntity).toString());
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			HttpConnection.post(SignUpActivity.this, url, null, entity, "application/json", new JsonHttpResponseHandler(){

				@Override
				public void onFailure(int statusCode, Header[] headers,
						Throwable throwable, JSONObject errorResponse) {
					// TODO Auto-generated method stub
					super.onFailure(statusCode, headers, throwable, errorResponse);
					progressLayout.setVisibility(View.GONE);
					System.out.println("error");
					System.out.println("statusCode:"+statusCode);
					System.out.println("headers:"+headers);
					for(int i = 0;i<headers.length;i++){
						System.out.println(headers[i]);
					}
					System.out.println("response:"+errorResponse);
				}

				@Override
				public void onSuccess(int statusCode, Header[] headers,
						JSONObject response) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, response);
					progressLayout.setVisibility(View.GONE);
					System.out.println("success");
					System.out.println("statusCode:"+statusCode);
					System.out.println("headers:"+headers);
					for(int i = 0;i<headers.length;i++){
						System.out.println(headers[i]);
					}
					System.out.println("response:"+response);
				}
				
			});
			progressLayout.setVisibility(View.VISIBLE);
		}
	};
	public void exitClick(View view)
	{
		this.finish();
	}
	
}

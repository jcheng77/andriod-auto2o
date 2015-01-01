package com.cettco.buycar.activity;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.cettco.buycar.R;
import com.cettco.buycar.entity.User;
import com.cettco.buycar.entity.UserEntity;
import com.cettco.buycar.utils.MyApplication;
import com.cettco.buycar.utils.GetLocation;
import com.cettco.buycar.utils.GlobalData;
import com.cettco.buycar.utils.HttpConnection;
import com.cettco.buycar.utils.UserUtil;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends Activity {

	private Button signinButton;
	private EditText signinPhoneEditText;
	private EditText signinPasswordeEditText;
	private RelativeLayout progressLayout;
	private TextView titleTextView;
	private Button signUpBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin);
		// getActionBar().hide();
		titleTextView = (TextView) findViewById(R.id.title_text);
		titleTextView.setText("登录");
		signUpBtn = (Button) findViewById(R.id.signUpBtn);
		signUpBtn.setOnClickListener(signUpClickListener);
		TextView findpwdteTextView = (TextView) findViewById(R.id.findpwd_text);
		findpwdteTextView.setOnClickListener(findpwdclClickListener);

		signinButton = (Button) findViewById(R.id.signinBtn);
		signinButton.setOnClickListener(signInClickListener);

		signinPasswordeEditText = (EditText) findViewById(R.id.signinPasswordEditText);
		signinPhoneEditText = (EditText) findViewById(R.id.signinPhoneEditText);

		progressLayout = (RelativeLayout) findViewById(R.id.progressbar_relativeLayout);
		// GetLocation location = new GetLocation();
		// location.getLocation(this);
	}

	protected OnClickListener signInClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			String url = GlobalData.getBaseUrl() + "/users/sign_in.json";
			String phone = signinPhoneEditText.getText().toString();
			String password = signinPasswordeEditText.getText().toString();
			User user = new User();
			user.setPhone(phone);
			user.setPassword(password);
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
			HttpConnection.setCookie(getApplicationContext());
			HttpConnection.post(SignInActivity.this, url, null, entity,
					"application/json;charset=utf-8",
					new JsonHttpResponseHandler() {

						@Override
						public void onFailure(int statusCode, Header[] headers,
								Throwable throwable, JSONObject errorResponse) {
							// TODO Auto-generated method stub
							super.onFailure(statusCode, headers, throwable,
									errorResponse);
							progressLayout.setVisibility(View.GONE);
							System.out.println("error");
							System.out.println("statusCode:" + statusCode);
							System.out.println("headers:" + headers);
							// for(int i = 0;i<headers.length;i++){
							// System.out.println(headers[i]);
							// }
							System.out.println("response:" + errorResponse);
							Toast toast = Toast.makeText(SignInActivity.this,
									"登录失败，重新登录", Toast.LENGTH_SHORT);
							toast.show();
						}

						@Override
						public void onSuccess(int statusCode, Header[] headers,
								JSONObject response) {
							// TODO Auto-generated method stub
							super.onSuccess(statusCode, headers, response);
							System.out.println("success");
							System.out.println("statusCode:" + statusCode);

							for (int i = 0; i < headers.length; i++) {
								System.out.println(headers[0]);
							}
							System.out.println("response:" + response);
							progressLayout.setVisibility(View.GONE);
							String id = null;
							String phone = null;
							try {
								id = response.getString("id");
								phone = response.getString("phone");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							UserUtil.setPhone(SignInActivity.this, phone);
							UserUtil.login(SignInActivity.this);
							UserUtil.setUserId(SignInActivity.this, id);
							SignInActivity.this.finish();

						}

					});
			progressLayout.setVisibility(View.VISIBLE);
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

	public void exitClick(View view) {
		this.finish();
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

}

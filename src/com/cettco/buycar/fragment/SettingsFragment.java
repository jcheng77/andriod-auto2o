package com.cettco.buycar.fragment;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.cettco.buycar.R;
import com.cettco.buycar.activity.FeedBackActivity;
import com.cettco.buycar.activity.MainActivity;
import com.cettco.buycar.activity.MyCreditActivity;
import com.cettco.buycar.activity.SignInActivity;
import com.cettco.buycar.activity.WebActivity;
import com.cettco.buycar.entity.User;
import com.cettco.buycar.entity.UserEntity;
import com.cettco.buycar.utils.GlobalData;
import com.cettco.buycar.utils.HttpConnection;
import com.cettco.buycar.utils.UpdateManager;
import com.cettco.buycar.utils.UserUtil;
import com.cettco.buycar.view.SettingsItemView;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsFragment extends Fragment {

	// private RelativeLayout loginLayout;
	// private TextView loginTextView;
	//
	// private LinearLayout logoutLayout;
	//
	// private ImageView loginArrowImageView;
	// // private RelativeLayout progressLayout;
	// private RelativeLayout checkUpdateLayout;
	// private LinearLayout phonelLayout;

	// new style
	private View fragmentView;
	private LinearLayout isLoginLly;
	private LinearLayout notLoginLly;
	private TextView loginNameTxv;
	private TextView loginPhoneTxv;
	private Button logouButton;
	private SettingsItemView checkUpdateLayout;
	//private SettingsItemView creditLy;
	//private SettingsItemView agreementLy;
	private SettingsItemView talkToCEOLy;
	private SettingsItemView aboutPailixingLy;
	private LinearLayout phonelLayout;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// return super.onCreateView(inflater, container, savedInstanceState);
		fragmentView = inflater.inflate(R.layout.fragment_settings, container,
				false);
		// progressLayout = (RelativeLayout) fragmentView
		// .findViewById(R.id.progressbar_relativeLayout);
		isLoginLly = (LinearLayout) fragmentView
				.findViewById(R.id.fragment_settings_islogin_lly);
		notLoginLly = (LinearLayout) fragmentView
				.findViewById(R.id.fragment_settings_notlogin_lly);
		notLoginLly.setOnClickListener(loginClickListener);
		loginNameTxv = (TextView) fragmentView
				.findViewById(R.id.fragment_settings_login_name_textview);
		loginPhoneTxv = (TextView) fragmentView
				.findViewById(R.id.fragment_settings_login_phone_textview);
		logouButton = (Button) fragmentView.findViewById(R.id.logout_button);
		logouButton.setOnClickListener(logoutClickListener);
		checkUpdateLayout = (SettingsItemView) fragmentView
				.findViewById(R.id.fragment_settings_checkupdate_ly);
		checkUpdateLayout.setOnClickListener(checkUpdateClickListener);

//		creditLy = (SettingsItemView) fragmentView
//				.findViewById(R.id.fragment_settings_credit_ly);
//		creditLy.setOnClickListener(creditClickListner);
//
//		agreementLy = (SettingsItemView) fragmentView
//				.findViewById(R.id.fragment_settings_agreement_ly);
//		agreementLy.setOnClickListener(agreementClickListner);

		talkToCEOLy = (SettingsItemView) fragmentView
				.findViewById(R.id.fragment_settings_talktoceo_ly);
		talkToCEOLy.setOnClickListener(talkToCEOClickListner);

		aboutPailixingLy = (SettingsItemView) fragmentView
				.findViewById(R.id.fragment_settings_about_pailixing_ly);
		aboutPailixingLy.setOnClickListener(aboutPailixingClickListner);

		phonelLayout = (LinearLayout) fragmentView
				.findViewById(R.id.settings_phone_linearlayout);
		phonelLayout.setOnClickListener(phoneClickListener);

		// loginArrowImageView = (ImageView) fragmentView
		// .findViewById(R.id.settings_login_arrow_imageview);
		// loginLayout = (RelativeLayout) fragmentView
		// .findViewById(R.id.settings_login_layout);
		// loginLayout.setOnClickListener(loginClickListener);
		// loginTextView = (TextView) fragmentView
		// .findViewById(R.id.settings_login_textView);
		//
		// logoutLayout = (LinearLayout) fragmentView
		// .findViewById(R.id.logout_layout);
		// logouButton = (Button) fragmentView.findViewById(R.id.logout_button);
		// logouButton.setOnClickListener(logoutClickListener);
		//
		// checkUpdateLayout = (RelativeLayout) fragmentView
		// .findViewById(R.id.settings_checkupdate_linearlayout);
		// checkUpdateLayout.setOnClickListener(checkUpdateClickListener);
		// phonelLayout = (LinearLayout) fragmentView
		// .findViewById(R.id.settings_phone_linearlayout);
		// phonelLayout.setOnClickListener(phoneClickListener);
		return fragmentView;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (UserUtil.isLogin(getActivity())) {
			isLoginLly.setVisibility(View.VISIBLE);
			notLoginLly.setVisibility(View.GONE);
			loginPhoneTxv.setText(UserUtil.getPhone(getActivity()));
			logouButton.setVisibility(View.VISIBLE);
			// loginTextView.setText(UserUtil.getPhone(getActivity()));
			// logoutLayout.setVisibility(View.VISIBLE);
			// loginArrowImageView.setVisibility(View.GONE);
		} else {
			isLoginLly.setVisibility(View.GONE);
			notLoginLly.setVisibility(View.VISIBLE);
			logouButton.setVisibility(View.GONE);
		}
	}

	private OnClickListener creditClickListner = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(getActivity(), MyCreditActivity.class);
			startActivity(intent);
		}
	};
	private OnClickListener agreementClickListner = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			System.out.println("aggrenment click");
			Intent intent = new Intent();
			intent.setClass(getActivity(), WebActivity.class);
			intent.putExtra("name", "商家协议");
			startActivity(intent);

		}
	};
	private OnClickListener talkToCEOClickListner = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(getActivity(), FeedBackActivity.class);
			//intent.putExtra("name", "对话CEO");
			startActivity(intent);

		}
	};
	private OnClickListener aboutPailixingClickListner = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(getActivity(), WebActivity.class);
			intent.putExtra("name", "关于拍立行");
			startActivity(intent);

		}
	};

	private OnClickListener phoneClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			String num = "4000320092";
			Pattern p = Pattern.compile("\\d+?");
			Matcher match = p.matcher(num);
			// 正则验证输入的是否为数字
			if (match.matches()) {
				Intent intent = new Intent("android.intent.action.CALL",
						Uri.parse("tel:" + num));
				startActivity(intent);
			} else {
				Toast.makeText(getActivity(), "号码不对", Toast.LENGTH_LONG).show();
			}
		}
	};
	private OnClickListener checkUpdateClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			UpdateManager manager = new UpdateManager(getActivity(), 1);
			// 检查软件更新
			manager.checkUpdate();
		}
	};
	private OnClickListener logoutClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			// progressLayout.setVisibility(View.VISIBLE);
			HttpConnection.getClient().removeHeader("Cookie");
			UserUtil.logout(getActivity());
			PersistentCookieStore myCookieStore = new PersistentCookieStore(
					getActivity());
			if (myCookieStore != null)
				myCookieStore.clear();
			Toast toast = Toast.makeText(getActivity(), "注销成功",
					Toast.LENGTH_SHORT);
			toast.show();
			onResume();
		}
	};
	private OnClickListener loginClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (UserUtil.isLogin(getActivity())) {
				return;
			} else {
				Intent intent = new Intent();
				intent.setClass(getActivity(), SignInActivity.class);
				startActivity(intent);
			}
		}
	};

	// private void signOutData(){
	// String url = GlobalData.getBaseUrl()+"/dealers/sign_in.json";
	// String phone = signinPhoneEditText.getText().toString();
	// String password = signinPasswordeEditText.getText().toString();
	// User user = new User();
	// user.setPhone(phone);
	// user.setPassword(password);
	// UserEntity userEntity = new UserEntity();
	// userEntity.setDealer(user);
	// Gson gson = new Gson();
	// StringEntity entity = null;
	// try {
	// System.out.println(gson.toJson(userEntity).toString());
	// entity = new StringEntity(gson.toJson(userEntity).toString());
	// } catch (UnsupportedEncodingException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// HttpConnection.setCookie(getApplicationContext());
	// HttpConnection.post(SignInActivity.this, url, null, entity,
	// "application/json;charset=utf-8", new JsonHttpResponseHandler(){
	//
	// @Override
	// public void onFailure(int statusCode, Header[] headers,
	// Throwable throwable, JSONObject errorResponse) {
	// // TODO Auto-generated method stub
	// super.onFailure(statusCode, headers, throwable, errorResponse);
	// progressLayout.setVisibility(View.GONE);
	// System.out.println("error");
	// System.out.println("statusCode:"+statusCode);
	// System.out.println("headers:"+headers);
	// // for(int i = 0;i<headers.length;i++){
	// // System.out.println(headers[i]);
	// // }
	// System.out.println("response:"+errorResponse);
	// Toast toast = Toast.makeText(SignInActivity.this, "登录失败，重新登录",
	// Toast.LENGTH_SHORT);
	// toast.show();
	// }
	//
	// @Override
	// public void onSuccess(int statusCode, Header[] headers,
	// JSONObject response) {
	// // TODO Auto-generated method stub
	// super.onSuccess(statusCode, headers, response);
	// System.out.println("success");
	// System.out.println("statusCode:"+statusCode);
	//
	// for(int i=0;i<headers.length;i++){
	// System.out.println(headers[0]);
	// }
	// System.out.println("response:"+response);
	// progressLayout.setVisibility(View.GONE);
	// String id=null;
	// try {
	// id = response.getString("id");
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// UserUtil.login(SignInActivity.this);
	// UserUtil.setUserId(SignInActivity.this, id);
	// Toast toast = Toast.makeText(SignInActivity.this, "登录成功",
	// Toast.LENGTH_SHORT);
	// toast.show();
	// getActivity().finish();
	//
	// }
	//
	// });
	// }

}

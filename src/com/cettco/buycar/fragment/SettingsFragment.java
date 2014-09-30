package com.cettco.buycar.fragment;

import com.cettco.buycar.R;
import com.cettco.buycar.activity.MainActivity;
import com.cettco.buycar.activity.SignInActivity;
import com.cettco.buycar.utils.UserUtil;
import com.loopj.android.http.PersistentCookieStore;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsFragment extends Fragment{

	private RelativeLayout loginLayout;
	private TextView loginTextView;
	private View fragmentView;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//return super.onCreateView(inflater, container, savedInstanceState);
		fragmentView = inflater.inflate(R.layout.fragment_settings, container,false);
		loginLayout = (RelativeLayout)fragmentView.findViewById(R.id.settings_login_layout);
		loginLayout.setOnClickListener(loginClickListener);
		loginTextView = (TextView)fragmentView.findViewById(R.id.settings_login_textView);
		return fragmentView;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(UserUtil.isLogin(getActivity())){
			loginTextView.setText("188*****");
		}else {
			loginTextView.setText("请登录");
		}
	}

	private OnClickListener loginClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(UserUtil.isLogin(getActivity())){
				UserUtil.logout(getActivity());
				PersistentCookieStore myCookieStore = new PersistentCookieStore(
						getActivity());
				if(myCookieStore==null)return;
				myCookieStore.clear();
				loginTextView.setText("请登录");
				Toast toast = Toast.makeText(getActivity(), "注销成功", Toast.LENGTH_SHORT);
				toast.show();
			}
			else{
				Intent intent = new Intent();
				intent.setClass(getActivity(), SignInActivity.class);
				startActivity(intent);
			}
		}
	};
	
}

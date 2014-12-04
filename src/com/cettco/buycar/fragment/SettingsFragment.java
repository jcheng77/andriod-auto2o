package com.cettco.buycar.fragment;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cettco.buycar.R;
import com.cettco.buycar.activity.CarListActivity;
import com.cettco.buycar.activity.MainActivity;
import com.cettco.buycar.activity.SignInActivity;
import com.cettco.buycar.entity.OrderItemEntity;
import com.cettco.buycar.utils.HttpConnection;
import com.cettco.buycar.utils.UpdateManager;
import com.cettco.buycar.utils.UserUtil;
import com.cettco.buycar.utils.db.DatabaseHelperOrder;
import com.loopj.android.http.PersistentCookieStore;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
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

public class SettingsFragment extends Fragment{

	private RelativeLayout loginLayout;
	private TextView loginTextView;
	private View fragmentView;
	private LinearLayout logoutLayout;
	private Button logouButton;
	private ImageView loginArrow;
	
	private RelativeLayout checkUpdateLayout;
	private LinearLayout phonelLayout;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//return super.onCreateView(inflater, container, savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		fragmentView = inflater.inflate(R.layout.fragment_settings, container,false);
		loginArrow = (ImageView)fragmentView.findViewById(R.id.settings_login_arrow);
		loginLayout = (RelativeLayout)fragmentView.findViewById(R.id.settings_login_layout);
		loginLayout.setOnClickListener(loginClickListener);
		loginTextView = (TextView)fragmentView.findViewById(R.id.settings_login_textView);
		
		logoutLayout = (LinearLayout)fragmentView.findViewById(R.id.logout_layout);		
		logouButton = (Button)fragmentView.findViewById(R.id.logout_button);
		logouButton.setOnClickListener(logoutClickListener);
		
		checkUpdateLayout = (RelativeLayout)fragmentView.findViewById(R.id.settings_check_update_layout);
		checkUpdateLayout.setOnClickListener(checkUpdateClickListener);
		
		phonelLayout = (LinearLayout)fragmentView.findViewById(R.id.settings_phone_linearlayout);
		phonelLayout.setOnClickListener(phoneClickListener);
		return fragmentView;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(UserUtil.isLogin(getActivity())){
			loginTextView.setText(UserUtil.getPhone(getActivity()));
			logoutLayout.setVisibility(View.VISIBLE);
			loginArrow.setVisibility(View.GONE);
		}else {
			loginTextView.setText("请登录");
			logoutLayout.setVisibility(View.GONE);
		}
	}
	private OnClickListener phoneClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			String num="4000320092";
			Pattern p = Pattern.compile("\\d+?");
			Matcher match = p.matcher(num);
			//正则验证输入的是否为数字
			if(match.matches()){
				Intent intent=new Intent("android.intent.action.CALL",Uri.parse("tel:"+num));
				startActivity(intent);
			}else{
				Toast.makeText(getActivity(), "号码不对",Toast.LENGTH_LONG).show();
			}
		}
	};
	private OnClickListener checkUpdateClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			UpdateManager manager = new UpdateManager(getActivity());  
			// 检查软件更新  
			manager.checkUpdate();
		}
	};
	private OnClickListener logoutClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			HttpConnection.getClient().removeHeader("Cookie");
			DatabaseHelperOrder helper = DatabaseHelperOrder.getHelper(getActivity());
			try {
				List<OrderItemEntity> list = helper.getDao().queryForAll();
				helper.getDao().delete(list);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			//HttpConnection.getClient().removeHeader("Cookie");
			UserUtil.logout(getActivity());
			PersistentCookieStore myCookieStore = new PersistentCookieStore(
					getActivity());
			if(myCookieStore==null)return;
			myCookieStore.clear();
			Toast toast = Toast.makeText(getActivity(), "注销成功", Toast.LENGTH_SHORT);
			toast.show();
			onResume();
//			JPushInterface.setAlias(getActivity(), null, new TagAliasCallback() {
//				
//				@Override
//				public void gotResult(int arg0, String arg1, Set<String> arg2) {
//					// TODO Auto-generated method stub
//					System.out.print("set success:"+arg1);
//					if(arg0==0){
//						Toast toast = Toast.makeText(getActivity(), "注销成功", Toast.LENGTH_SHORT);
//						toast.show();
//						onResume();
//					}else{
//						Toast toast = Toast.makeText(getActivity(), "注销失败", Toast.LENGTH_SHORT);
//						toast.show();
//						onResume();
//					}
//					
//				}
//			});
			
		}
	};
	private OnClickListener loginClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(UserUtil.isLogin(getActivity())){
//				UserUtil.logout(getActivity());
//				PersistentCookieStore myCookieStore = new PersistentCookieStore(
//						getActivity());
//				if(myCookieStore==null)return;
//				myCookieStore.clear();
//				loginTextView.setText("请登录");
//				Toast toast = Toast.makeText(getActivity(), "注销成功", Toast.LENGTH_SHORT);
//				toast.show();
				return;
			}
			else{
				Intent intent = new Intent();
				intent.setClass(getActivity(), SignInActivity.class);
				startActivity(intent);
			}
		}
	};
	
}

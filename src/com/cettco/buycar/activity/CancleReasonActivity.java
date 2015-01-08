package com.cettco.buycar.activity;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.cettco.buycar.R;
import com.cettco.buycar.adapter.CancleReasonAdapter;
import com.cettco.buycar.entity.OrderDetailEntity;
import com.cettco.buycar.entity.OrderItemEntity;
import com.cettco.buycar.utils.GlobalData;
import com.cettco.buycar.utils.HttpConnection;
import com.cettco.buycar.utils.db.DatabaseHelperOrder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CancleReasonActivity extends Activity {

	private CancleReasonAdapter adapter;
	private ArrayList<String> list;
	private ListView listView;
	private TextView textView;
	private TextView doneTextView;
	private String tender_id;
	private RelativeLayout progressLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cancle_reason);
		// getActionBar().hide();
		progressLayout = (RelativeLayout)findViewById(R.id.progressbar_relativeLayout);
		textView = (TextView) findViewById(R.id.title_text);
		textView.setText("取消原因");
		list = new ArrayList<String>();
		list.add("4s店出价不给力");
		list.add("4s店离我这里太远");
		list.add("信息提交错误");
		list.add("选择其他车型");
		list.add("推迟购买计划");
		tender_id = getIntent().getStringExtra("tender_id");
		adapter = new CancleReasonAdapter(this, R.layout.item_cancle_reason,
				list);

		listView = (ListView) findViewById(R.id.cancleReason_listview);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(listener);

		doneTextView = (TextView) findViewById(R.id.actionbar_done_btn);
		doneTextView.setVisibility(View.VISIBLE);
		doneTextView.setOnClickListener(doneBtnClickListener);
	}

	protected OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			CheckBox checkBox = (CheckBox) arg1
					.findViewById(R.id.cancleReason_checkBox);
			checkBox.toggle();
			adapter.getIsSelected().put(arg2, checkBox.isChecked());
		}
	};
	protected OnClickListener doneBtnClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			getData();
		}
	};

	protected void getData() {
		// String url = GlobalData.getBaseUrl() + "/cars/list.json";
		// httpCache.clear();
		String cookieStr = null;
		String cookieName = null;
		PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
		if (myCookieStore == null) {
			//System.out.println("cookie store null");
			return;
		}
		List<Cookie> cookies = myCookieStore.getCookies();
		for (Cookie cookie : cookies) {
			String name = cookie.getName();
			cookieName = name;
			//System.out.println(name);
			if (name.equals("_JustBidIt_session")) {
				cookieStr = cookie.getValue();
				//System.out.println("value:" + cookieStr);
				break;
			}
		}
		if (cookieStr == null || cookieStr.equals("")) {
			//System.out.println("cookie null");
			return;
		}
		String url = GlobalData.getBaseUrl() + "/tenders/" + tender_id+ "/cancel.json";
		StringBuffer reasonBuffer = new StringBuffer();
		Iterator<Integer> iter = adapter.getIsSelected().keySet()
				.iterator();
		while (iter.hasNext()) {
			Integer key = iter.next();
			Boolean value = adapter.getIsSelected().get(key);
			if (value == true) {
				//colors.add(colorList.get(key).getId());
				reasonBuffer.append(list.get(key)+" ");
			}
		}
		//System.out.println("reson:"+reasonBuffer.toString());
		if(reasonBuffer.toString().equals("")){
			Toast toast = Toast.makeText(CancleReasonActivity.this,
					"请至少选择一个取消原因", Toast.LENGTH_SHORT);
			toast.show();
			return;
		}
		JSONObject cancelJsonObject = new JSONObject();
		JSONObject json = new JSONObject();
		try {
			cancelJsonObject.put("cancel_reason", reasonBuffer.toString());
			json.put("tender", cancelJsonObject);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}		
		
		StringEntity entity = null;
		try {
			//System.out.println("patch:"+json.toString());
			entity = new StringEntity(json.toString(),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpConnection.getClient().addHeader("x-http-method-override", "PATCH");
		HttpConnection.getClient().addHeader("Cookie",
				cookieName + "=" + cookieStr);
		progressLayout.setVisibility(View.VISIBLE);
		HttpConnection.post(this, url, null, entity,  "application/json;charset=utf-8",new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				//System.out.println("fail");
				Message message = new Message();
				message.what = 2;
				mHandler.sendMessage(message);
				HttpConnection.getClient().removeHeader(
						"x-http-method-override");
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub

				// Type listType = new TypeToken<ArrayList<DealerEntity>>()
				// {
				// }.getType();
				// dealerList = new Gson().fromJson(result, listType);
				// System.out.println("size:"+dealerList.size());
				HttpConnection.getClient().removeHeader("x-http-method-override");
				Message message = new Message();
				message.what = 1;
				mHandler.sendMessage(message);
			}

		});
	}

	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				progressLayout.setVisibility(View.GONE);
				//deleteDB();
				Toast toast = Toast.makeText(CancleReasonActivity.this,
						"删除成功", Toast.LENGTH_SHORT);
				toast.show();
				CancleReasonActivity.this.finish();
				break;
			case 2:
				progressLayout.setVisibility(View.GONE);
				Toast toast2 = Toast.makeText(CancleReasonActivity.this,
						"提交数据失败，请重试", Toast.LENGTH_SHORT);
				toast2.show();
				break;
			}
		};
	};
//	private void deleteDB(){
//		DatabaseHelperOrder helper = DatabaseHelperOrder
//				.getHelper(CancleReasonActivity.this);
//		try {
//			OrderItemEntity entity = helper.getDao().queryBuilder().where().eq("id", tender_id).queryForFirst();
//			if(entity!=null){System.out.println("not null");}
//				helper.getDao().delete(entity);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	public void exitClick(View view) {
		this.finish();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}

}

package com.cettco.buycar.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.cettco.buycar.R;
import com.cettco.buycar.entity.CarColorEntity;
import com.cettco.buycar.entity.CarColorListEntity;
import com.cettco.buycar.entity.Tender;
import com.cettco.buycar.entity.TenderEntity;
import com.cettco.buycar.utils.GlobalData;
import com.cettco.buycar.utils.HttpConnection;
import com.cettco.buycar.utils.UserUtil;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SubmitOrderActivity extends Activity{

	public final int RESULT_COLOR = 0;
	public final int RESULT_TIME = 1;
	public final int RESULT_LOAN = 2;
	public final int RESULT_LOCATION = 3;
	public final int RESULT_PLATE = 4;

	private TextView titleTextView;
	private TextView agreementTextView;

	private Button submitButton;

	private TextView colorTextView;
	private RelativeLayout colorLayout;
	private String colorString;

	private TextView getCarTimeTextView;
	private RelativeLayout getcarTimeLayout;
	private ArrayList<String> getcarTimeList;
	private int getcarTimeSelection=0;

	private TextView loantTextView;
	private RelativeLayout loanLayout;
	private ArrayList<String> loanList;
	private int loanSelection=0;

	private TextView locationTextView;
	private RelativeLayout locationLayout;
	private ArrayList<String> locationList;
	private int locationSelection=0;

	private TextView plateTextView;
	private RelativeLayout plateLayout;
	private ArrayList<String> plateList;
	private int plateSelection=0;
	
	private ImageView carImageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submitorder);
		//getActionBar().hide();
		getArray();
		titleTextView = (TextView)findViewById(R.id.title_text);
		titleTextView.setText(R.string.title_car_demand);
		
		carImageView = (ImageView)findViewById(R.id.submitOrder_img);
//		LinearLayout chooseCarColorLayout = (LinearLayout)findViewById(R.id.selectCarColor_layout);
//		LinearLayout chooseCarLicenseLayout = (LinearLayout)findViewById(R.id.selectCarLicense_layout);
//		LinearLayout chooseCarLocationLayout = (LinearLayout)findViewById(R.id.selectLicenseLocation_layout);
//		LinearLayout chooseCarPaymentLayout = (LinearLayout)findViewById(R.id.selectPayment_layout);
//		
//		chooseCarColorLayout.setOnClickListener(chooseCarColorClickListener);
//		chooseCarLicenseLayout.setOnClickListener(chooseCarLicenseClickListener);
//		chooseCarLocationLayout.setOnClickListener(chooseCarLocationClickListener);
//		chooseCarPaymentLayout.setOnClickListener(chooseCarPaymentClickListener);
		
		colorLayout = (RelativeLayout) findViewById(R.id.activity_submitorder_color_layout);
		colorLayout.setOnClickListener(colorLayoutClickListener);
		colorTextView = (TextView) findViewById(R.id.activity_submitorder_color_textview);
		colorString = getIntent().getStringExtra("color");
		
		//CarColorListEntity carColorListEntity = new Gson().fromJson(getIntent().getStringExtra("color"), CarColorListEntity.class);

		getcarTimeLayout = (RelativeLayout) findViewById(R.id.activity_submitorder_getcarTime_layout);
		getcarTimeLayout.setOnClickListener(getCarTimeClickListener);
		getCarTimeTextView = (TextView) findViewById(R.id.activity_submitorder_getcarTime_textview);

		loanLayout = (RelativeLayout) findViewById(R.id.activity_submitorder_loan_layout);
		loanLayout.setOnClickListener(loanClickListener);
		loantTextView = (TextView) findViewById(R.id.activity_submitorder_loan_textview);

		locationLayout = (RelativeLayout) findViewById(R.id.activity_submitorder_platelocation_layout);
		locationLayout.setOnClickListener(locationClickListener);
		locationTextView = (TextView) findViewById(R.id.activity_submitorder_platelocation_textview);

		plateLayout = (RelativeLayout) findViewById(R.id.activity_submitorder_haveplate_layout);
		plateLayout.setOnClickListener(plateClickListener);
		plateTextView = (TextView) findViewById(R.id.activity_submitorder_haveplate_textview);
		
		Button submitBtn = (Button)findViewById(R.id.submitorderBtn);
		submitBtn.setOnClickListener(submitBtnClickListener);
	}
	private void getArray(){
		Resources res = getResources();
		String[] tmp = res.getStringArray(R.array.getcarTime_array);
		getcarTimeList =new ArrayList<String>(Arrays.asList(tmp)); 
		//ArrayList<String> aa= (ArrayList<String>) Arrays.asList(tmp);
		tmp = res.getStringArray(R.array.loan_array);
		loanList=new ArrayList<String>(Arrays.asList(tmp));
		tmp = res.getStringArray(R.array.location_array);
		locationList = new ArrayList<String>(Arrays.asList(tmp));
		tmp = res.getStringArray(R.array.plate_array);
		plateList =new ArrayList<String>(Arrays.asList(tmp));
	}
	private void updateUI(){
		getCarTimeTextView.setText(getcarTimeList.get(getcarTimeSelection));
		loantTextView.setText(loanList.get(loanSelection));
		locationTextView.setText(locationList.get(locationSelection));
		plateTextView.setText(plateList.get(plateSelection));
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data==null) return;
		System.out.println("resultcode :" + resultCode + " requestcode:"
				+ requestCode);
		Bundle b = data.getExtras();
		switch (resultCode) { // resultCode为回传的标记，我在B中回传的是RESULT_OK
		case RESULT_OK:
			// data为B中回传的Intent
			int position = b.getInt("result");
			if (requestCode == RESULT_COLOR) {

			} else if (requestCode == RESULT_TIME) {
				getcarTimeSelection = position;
			} else if (requestCode == RESULT_LOAN) {
				loanSelection = position;
			} else if (requestCode == RESULT_LOCATION) {
				locationSelection = position;
			} else if (requestCode == RESULT_PLATE) {
				plateSelection = position;
			}
			break;
		default:
			break;
		}
	}
	protected OnClickListener colorLayoutClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(SubmitOrderActivity.this, ChooseCarColorActivity.class);
			intent.putExtra("name", "选择颜色");
			intent.putExtra("tag", 1);
			intent.putExtra("color", colorString);
			startActivityForResult(intent, 0);
		}
	};
	protected OnClickListener getCarTimeClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(SubmitOrderActivity.this, MyBaseListActivity.class);
			intent.putExtra("name", "提车时间");
			intent.putExtra("tag", 1);
			intent.putStringArrayListExtra("list",getcarTimeList);
			startActivityForResult(intent, 1);
		}
	};
	protected OnClickListener loanClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(SubmitOrderActivity.this, MyBaseListActivity.class);
			intent.putExtra("name", "是否贷款");
			intent.putExtra("tag", 2);
			intent.putStringArrayListExtra("list",loanList);
			startActivityForResult(intent, 2);
		}
	};
	protected OnClickListener locationClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(SubmitOrderActivity.this, MyBaseListActivity.class);
			intent.putExtra("name", "车牌地点");
			intent.putExtra("tag", 3);
			intent.putStringArrayListExtra("list",locationList);
			startActivityForResult(intent, 3);
		}
	};
	protected OnClickListener plateClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(SubmitOrderActivity.this, MyBaseListActivity.class);
			intent.putExtra("name", "有无牌照");
			intent.putExtra("tag", 4);
			intent.putStringArrayListExtra("list",plateList);
			startActivityForResult(intent, 4);
		}
	};
	private OnClickListener submitBtnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
//			String tenderUrl=GlobalData.getBaseUrl()+"/tenders.json";
//			Gson gson = new Gson();
//			Tender tender = new Tender();
//			tender.setDescription("test1");
//			tender.setModel("宝马");
//			tender.setColor_id(color_id);
//			tender.setTrim_id(trim_id);
//			TenderEntity tenderEntity = new TenderEntity();
//			tenderEntity.setTender(tender);
//	        StringEntity entity = null;
//	        try {
//	        	System.out.println(gson.toJson(tenderEntity).toString());
//				entity = new StringEntity(gson.toJson(tenderEntity).toString());
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	        String cookieStr=null;
//			String cookieName=null;
//			PersistentCookieStore myCookieStore = new PersistentCookieStore(
//					SubmitOrderActivity.this);
//			if(myCookieStore==null){System.out.println("cookie store null");return;}
//			List<Cookie> cookies = myCookieStore.getCookies();
//			for (Cookie cookie : cookies) {
//				String name =cookie.getName();
//				cookieName=name;
//				System.out.println(name);
//				if(name.equals("_JustBidIt_session")){
//					cookieStr=cookie.getValue();
//					System.out.println("value:"+cookieStr);
//					break;
//				}
//			}
//			if(cookieStr==null||cookieStr.equals("")){System.out.println("cookie null");return;}
//			HttpConnection.getClient().addHeader("Cookie", cookieName+"="+cookieStr);
//			HttpConnection.post(SubmitOrderActivity.this, tenderUrl, null, entity, "application/json;charset=utf-8", new JsonHttpResponseHandler(){
//
//				@Override
//				public void onFailure(int statusCode, Header[] headers,
//						Throwable throwable, JSONObject errorResponse) {
//					// TODO Auto-generated method stub
//					super.onFailure(statusCode, headers, throwable, errorResponse);
//					System.out.println("error");
//					System.out.println("statusCode:"+statusCode);
//					System.out.println("headers:"+headers);
//				}
//
//				@Override
//				public void onSuccess(int statusCode, Header[] headers,
//						JSONObject response) {
//					// TODO Auto-generated method stub
//					super.onSuccess(statusCode, headers, response);
//					
//					System.out.println("success");
//					System.out.println("statusCode:"+statusCode);
//					
////					for(int i=0;i<headers.length;i++){
////						System.out.println(headers[0]);
////					}
//					System.out.println("response:"+response);
//					if(statusCode==201){
//						try {
//							int id = response.getInt("id");
//							Intent intent = new Intent();
//							intent.putExtra("tenderId", id);
//							intent.setClass(SubmitOrderActivity.this, MyOrderStatusActivity.class);
//							startActivity(intent);
//						} catch (JSONException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						
//					}
//					
//				}
//				
//			});
			Intent intent = new Intent();
			intent.setClass(SubmitOrderActivity.this, MyOrderStatusActivity.class);
			startActivity(intent);
			
		}
	};
	private OnClickListener chooseCarLicenseClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			ArrayList<String> arrayList = new ArrayList<String>();
			arrayList.add("有牌照）");
			arrayList.add("无牌照");
			Intent intent = new Intent();
			intent.setClass(SubmitOrderActivity.this, MyBaseListActivity.class);
			intent.putExtra("name", "有无牌照");
			intent.putStringArrayListExtra("list", arrayList);
			startActivity(intent);
			
		}
	};
	private OnClickListener chooseCarLocationClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			ArrayList<String> arrayList = new ArrayList<String>();
			arrayList.add("本地牌照（上海）");
			arrayList.add("外地牌照");
			Intent intent = new Intent();
			intent.setClass(SubmitOrderActivity.this, MyBaseListActivity.class);
			intent.putExtra("name", "牌照地点");
			intent.putStringArrayListExtra("list", arrayList);
			startActivity(intent);
			
		}
	};
	private OnClickListener chooseCarPaymentClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			ArrayList<String> arrayList = new ArrayList<String>();
			arrayList.add("贷款");
			arrayList.add("全额付款");
			Intent intent = new Intent();
			intent.setClass(SubmitOrderActivity.this, MyBaseListActivity.class);
			intent.putExtra("name", "付款方式");
			intent.putStringArrayListExtra("list", arrayList);
			startActivity(intent);
			
		}
	};
	private OnClickListener chooseCarColorClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(SubmitOrderActivity.this, ChooseCarColorActivity.class);
			startActivity(intent);
		}
	};
	@Override
	protected void onResume() {
	    super.onResume();
	    updateUI();
	    // Normal case behavior follows
	}
	public void exitClick(View view){
		this.finish();
	}
	
}

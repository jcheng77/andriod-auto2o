package com.cettco.buycar.activity;

import java.util.ArrayList;

import com.cettco.buycar.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SubmitOrderActivity extends ActionBarActivity{

	public final int RESULT_COLOR = 0;
	public final int RESULT_TIME = 1;
	public final int RESULT_LOAN = 2;
	public final int RESULT_LOCATION = 3;
	public final int RESULT_PLATE = 4;

	private TextView agreementTextView;

	private Button submitButton;

	private TextView colorTextView;
	private RelativeLayout colorLayout;

	private TextView getCarTimeTextView;
	private RelativeLayout getcarTimeLayout;

	private TextView loantTextView;
	private RelativeLayout loanLayout;

	private TextView locationTextView;
	private RelativeLayout locationLayout;

	private TextView plateTextView;
	private RelativeLayout plateLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submitorder);
		getActionBar().hide();
		
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		System.out.println("resultcode :" + resultCode + " requestcode:"
				+ requestCode);
		Bundle b = data.getExtras();
		switch (resultCode) { // resultCode为回传的标记，我在B中回传的是RESULT_OK
		case RESULT_OK:
			// data为B中回传的Intent
			if (requestCode == RESULT_COLOR) {

			} else if (requestCode == RESULT_TIME) {

			} else if (requestCode == RESULT_LOAN) {

			} else if (requestCode == RESULT_LOCATION) {

			} else if (requestCode == RESULT_PLATE) {

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
			startActivityForResult(intent, 0);
		}
	};
	protected OnClickListener getCarTimeClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			ArrayList<String> arrayList = new ArrayList<String>();
			arrayList.add("有牌照）");
			arrayList.add("无牌照");
			Intent intent = new Intent();
			intent.setClass(SubmitOrderActivity.this, MyBaseListActivity.class);
			intent.putExtra("name", "有无牌照");
			intent.putExtra("tag", 1);
			intent.putStringArrayListExtra("list", arrayList);
			startActivityForResult(intent, 1);
		}
	};
	protected OnClickListener loanClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(SubmitOrderActivity.this, BargainColorActivity.class);
			startActivityForResult(intent, 0);
		}
	};
	protected OnClickListener locationClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(SubmitOrderActivity.this, BargainColorActivity.class);
			startActivityForResult(intent, 0);
		}
	};
	protected OnClickListener plateClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(SubmitOrderActivity.this, BargainColorActivity.class);
			startActivityForResult(intent, 0);
		}
	};
	private OnClickListener submitBtnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
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
	public void exitClick(View view){
		this.finish();
	}
	
}

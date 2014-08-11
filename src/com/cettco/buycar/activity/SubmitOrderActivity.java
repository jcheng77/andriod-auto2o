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

public class SubmitOrderActivity extends ActionBarActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submitorder);
		getActionBar().hide();
		
		LinearLayout chooseCarColorLayout = (LinearLayout)findViewById(R.id.selectCarColor_layout);
		LinearLayout chooseCarLicenseLayout = (LinearLayout)findViewById(R.id.selectCarLicense_layout);
		LinearLayout chooseCarLocationLayout = (LinearLayout)findViewById(R.id.selectLicenseLocation_layout);
		LinearLayout chooseCarPaymentLayout = (LinearLayout)findViewById(R.id.selectPayment_layout);
		
		chooseCarColorLayout.setOnClickListener(chooseCarColorClickListener);
		chooseCarLicenseLayout.setOnClickListener(chooseCarLicenseClickListener);
		chooseCarLocationLayout.setOnClickListener(chooseCarLocationClickListener);
		chooseCarPaymentLayout.setOnClickListener(chooseCarPaymentClickListener);
		
		Button submitBtn = (Button)findViewById(R.id.submitorderBtn);
		submitBtn.setOnClickListener(submitBtnClickListener);
	}
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
	
}

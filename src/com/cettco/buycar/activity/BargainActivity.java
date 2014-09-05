package com.cettco.buycar.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cettco.buycar.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BargainActivity extends Activity {

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bargain);
		//getActionBar().hide();
		getArray();

		agreementTextView = (TextView) findViewById(R.id.simple_agreement);
		String text = "<font color='black'>一口价购买方为我公司与4s经销商协议的特价购买通道，价格优势明显但是如果购车订单成功后任何一方违约则可能会涉及到违约金支付</font> <font color='red'>点击查看详情</font>";

		agreementTextView.setText(Html.fromHtml(text));
		agreementTextView.setOnClickListener(agreementClickListener);

		submitButton = (Button) findViewById(R.id.submit_bargain_price_btn);
		submitButton.setOnClickListener(submitBtnClickListener);

		colorLayout = (RelativeLayout) findViewById(R.id.activityBargainColorRelativeLayout);
		colorLayout.setOnClickListener(colorLayoutClickListener);
		colorTextView = (TextView) findViewById(R.id.activity_bargain_color_textview);

		getcarTimeLayout = (RelativeLayout) findViewById(R.id.activity_bargain_getcarTime_layout);
		getcarTimeLayout.setOnClickListener(getCarTimeClickListener);
		getCarTimeTextView = (TextView) findViewById(R.id.activity_bargain_getcarTime_textview);

		loanLayout = (RelativeLayout) findViewById(R.id.activity_bargain_loan_layout);
		loanLayout.setOnClickListener(loanClickListener);
		loantTextView = (TextView) findViewById(R.id.activity_bargain_loan_textview);

		locationLayout = (RelativeLayout) findViewById(R.id.activity_bargain_platelocation_layout);
		locationLayout.setOnClickListener(locationClickListener);
		locationTextView = (TextView) findViewById(R.id.activity_bargain_platelocation_textview);

		plateLayout = (RelativeLayout) findViewById(R.id.activity_bargain_haveplate_layout);
		plateLayout.setOnClickListener(plateClickListener);
		plateTextView = (TextView) findViewById(R.id.activity_bargain_haveplate_textview);

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
		if(data==null)return;
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

	protected OnClickListener submitBtnClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub

			AlertDialog.Builder builder = new AlertDialog.Builder(
					BargainActivity.this);
			builder.setTitle(R.string.alerttitle);
			builder.setMessage(R.string.alertmsg)
					.setPositiveButton("同意并继续",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// FIRE ZE MISSILES!
									dialog.dismiss();
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// User cancelled the dialog
									dialog.dismiss();
								}
							});
			// Create the AlertDialog object and return it
			builder.create().show();
		}
	};
	protected OnClickListener agreementClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(BargainActivity.this, AgreementActivity.class);
			startActivity(intent);
		}
	};
	protected OnClickListener colorLayoutClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(BargainActivity.this, ChooseCarColorActivity.class);
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
			intent.setClass(BargainActivity.this, MyBaseListActivity.class);
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
			intent.setClass(BargainActivity.this, MyBaseListActivity.class);
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
			intent.setClass(BargainActivity.this, MyBaseListActivity.class);
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
			intent.setClass(BargainActivity.this, MyBaseListActivity.class);
			intent.putExtra("name", "有无牌照");
			intent.putExtra("tag", 4);
			intent.putStringArrayListExtra("list",plateList);
			startActivityForResult(intent, 4);
		}
	};

	@Override
	protected void onResume() {
	    super.onResume();
	    updateUI();
	    // Normal case behavior follows
	}
	public void exitClick(View view) {
		this.finish();
	}

}

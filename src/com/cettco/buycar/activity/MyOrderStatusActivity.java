package com.cettco.buycar.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.cettco.buycar.R;
import com.cettco.buycar.adapter.DealerListAdapter;
import com.cettco.buycar.entity.DealerEntity;

public class MyOrderStatusActivity extends ActionBarActivity {
	private ArrayList<DealerEntity> dealerList;
	private ListView dealerListView;
	private DealerListAdapter dealerListAdapter;
	private Button bargainButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myorder_status);
		getActionBar().hide();
		dealerListView = (ListView)findViewById(R.id.dealer_listview);
		dealerList = new ArrayList<DealerEntity>();
		for(int i = 0;i<20;i++){
			DealerEntity dealerEntity = new DealerEntity();
			dealerList.add(dealerEntity);
		}
		dealerListAdapter = new DealerListAdapter(this, R.layout.dealer_item, dealerList);
		dealerListView.setAdapter(dealerListAdapter);
		dealerListView.setOnItemClickListener(dealerListClickListener);
		
		bargainButton = (Button)findViewById(R.id.bargainBtn);
		bargainButton.setOnClickListener(barginBtnClickListener);
		
	}
	protected OnClickListener barginBtnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(MyOrderStatusActivity.this, BargainActivity.class);
			startActivity(intent);
		}
	};
	protected OnItemClickListener dealerListClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(MyOrderStatusActivity.this, DealerDetailActivity.class);
			startActivity(intent);
			
		}
	};
	public void exitClick(View view){
		this.finish();
	}
	
}

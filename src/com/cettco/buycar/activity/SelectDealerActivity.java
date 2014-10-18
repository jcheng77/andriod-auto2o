package com.cettco.buycar.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.cettco.buycar.R;
import com.cettco.buycar.adapter.CarColorAdapter;
import com.cettco.buycar.adapter.SelectShopAdapter;
import com.cettco.buycar.entity.CarColorEntity;
import com.cettco.buycar.entity.CarColorListEntity;
import com.cettco.buycar.entity.DealerEntity;
import com.google.gson.Gson;

public class SelectDealerActivity extends Activity{
	private ArrayList<DealerEntity> dealerList;
	private ListView listView;
	private SelectShopAdapter dealerListAdapter;
	private Intent intent;
	private String name;
	private int tag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selectshop);
		//getActionBar().hide();
		TextView titleTextView = (TextView)findViewById(R.id.title_text);
		intent = getIntent();
//		name = intent.getStringExtra("name");
//		tag = intent.getIntExtra("tag",0);
//		titleTextView.setText(name);
		listView = (ListView) findViewById(R.id.selectshop_listview);
		dealerList = new ArrayList<DealerEntity>();
		for (int i = 0; i < 20; i++) {
			DealerEntity dealerEntity = new DealerEntity();
			dealerList.add(dealerEntity);
		}
		dealerListAdapter = new SelectShopAdapter(this, R.layout.item_selectshop,
				dealerList);
		listView.setAdapter(dealerListAdapter);
		listView.setOnItemClickListener(dealerListClickListener);
		//listView.setOnItemLongClickListener(dealerlistLongClickListener);
	}
	protected OnItemClickListener dealerListClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
//			CheckBox checkBox = (CheckBox) arg1.findViewById(R.id.selectshop_checkBox);
//			checkBox.toggle();
//			dealerListAdapter.getIsSelected().put(arg2, checkBox.isChecked());
//			Intent intent = new Intent();
//			intent.setClass(SelectDealerActivity.this,DealerDetailActivity.class);
//			startActivity(intent);
			CheckBox checkBox = (CheckBox) arg1.findViewById(R.id.selectshop_checkBox);
			checkBox.toggle();
			dealerListAdapter.getIsSelected().put(arg2, checkBox.isChecked());

		}
	};
	protected OnItemLongClickListener dealerlistLongClickListener = new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View view,
				int arg2, long arg3) {
			// TODO Auto-generated method stub
			CheckBox checkBox = (CheckBox) view.findViewById(R.id.selectshop_checkBox);
			checkBox.toggle();
			dealerListAdapter.getIsSelected().put(arg2, checkBox.isChecked());
			return true;
		}
	};
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		setResult(RESULT_CANCELED, intent);
		this.finish();
	}
	public void exitClick(View view){
		//intent.putExtra("result", position);
//		int size = mycarColorAdapter.getIsSelected().size();
//		for(int i = 0;i<size;i++){
//			mycarColorAdapter.getIsSelected().get(i);
//		}
		setResult(RESULT_OK, intent);
		this.finish();
	}
}

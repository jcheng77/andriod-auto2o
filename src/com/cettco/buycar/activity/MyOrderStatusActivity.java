package com.cettco.buycar.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ListView;

import com.cettco.buycar.R;
import com.cettco.buycar.adapter.DealerListAdapter;
import com.cettco.buycar.entity.DealerEntity;

public class MyOrderStatusActivity extends ActionBarActivity {
	private ArrayList<DealerEntity> dealerList;
	private ListView dealerListView;
	private DealerListAdapter dealerListAdapter;
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
		
	}
	public void exitClick(View view){
		this.finish();
	}
	
}

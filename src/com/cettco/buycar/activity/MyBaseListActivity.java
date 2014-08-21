package com.cettco.buycar.activity;

import java.util.ArrayList;

import com.cettco.buycar.R;
import com.cettco.buycar.adapter.MyBaseListAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class MyBaseListActivity extends ActionBarActivity{

	private ListView listView;
	private MyBaseListAdapter adapter;
	private String name;
	private ArrayList<String> arrayList = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_baselist);
		getActionBar().hide();
		TextView titleTextView = (TextView)findViewById(R.id.title_text);
		Intent intent = getIntent();
		name = intent.getStringExtra("name");
		titleTextView.setText(name);
		arrayList = intent.getStringArrayListExtra("list");
		listView = (ListView)findViewById(R.id.myBaseListview);
		adapter = new MyBaseListAdapter(this,android.R.layout.simple_list_item_1, arrayList);
		listView.setAdapter(adapter);
	}
	public void exitClick(View view){
		this.finish();
	}
	

}

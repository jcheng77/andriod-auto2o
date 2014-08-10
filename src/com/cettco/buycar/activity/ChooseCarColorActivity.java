package com.cettco.buycar.activity;

import java.util.ArrayList;

import com.cettco.buycar.R;
import com.cettco.buycar.adapter.CarColorAdapter;
import com.cettco.buycar.entity.CarColorEntity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ListView;

public class ChooseCarColorActivity extends ActionBarActivity{
	
	private ArrayList<CarColorEntity> colorList;
	private ListView listView;
	private CarColorAdapter mycarColorAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_carcolor);
		getActionBar().hide();
		listView = (ListView)findViewById(R.id.color_listview);	
		colorList = new ArrayList<CarColorEntity>();
		for(int i= 0;i<4;i++)
		{
			CarColorEntity entity = new CarColorEntity();
			entity.setColor("#444444");
			entity.setName("黄色");
			colorList.add(entity);
		}
		mycarColorAdapter = new CarColorAdapter(this,R.layout.carcolor_item,colorList);
		listView.setAdapter(mycarColorAdapter);
	}
	
	public void exitClick(View view){
		this.finish();
	}
	
	
	
	
	
}

package com.cettco.buycar.activity;

import java.util.ArrayList;
import java.util.List;

import com.cettco.buycar.R;
import com.cettco.buycar.adapter.CarBrandListAdapter;
import com.cettco.buycar.adapter.CarExpandableListAdapter;
import com.cettco.buycar.entity.CarBrandEntity;
import com.cettco.buycar.entity.CarManufactorEntity;
import com.cettco.buycar.entity.CarTypeEntity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CarListActivity extends ActionBarActivity{

	private int toggle = 0;
	private CarBrandListAdapter carBrandListAdapter;
	private ArrayList<CarBrandEntity> carBrandList;
	private ArrayList<CarBrandEntity> carBrandEntities;
	private ExpandableListView carExpandedView;
	private CarExpandableListAdapter carExpandableListAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_carlist);
		getActionBar().hide();
		ListView carBrandListView = (ListView)findViewById(R.id.carBrandListView);
		carBrandList = new ArrayList<CarBrandEntity>();
		//Test data
		CarTypeEntity carTypeEntity = new CarTypeEntity();
		carTypeEntity.setName("111");
		ArrayList<CarTypeEntity> carTypeEntities = new ArrayList<CarTypeEntity>();
		carTypeEntities.add(carTypeEntity);
		
		CarManufactorEntity carManufactorEntity = new CarManufactorEntity();
		carManufactorEntity.setName("11");
		carManufactorEntity.setList(carTypeEntities);
		ArrayList<CarManufactorEntity> carManufactorEntities = new ArrayList<CarManufactorEntity>();
		carManufactorEntities.add(carManufactorEntity);
		
		CarBrandEntity carBrandEntity = new CarBrandEntity();
		carBrandEntity.setName("1");
		carBrandEntity.setList(carManufactorEntities);
		carBrandEntities = new ArrayList<CarBrandEntity>();
		carBrandEntities.add(carBrandEntity);
		carBrandEntities.add(carBrandEntity);
		carBrandEntities.add(carBrandEntity);
		
		carBrandListAdapter = new CarBrandListAdapter(this, R.layout.carbrandlist_item, carBrandEntities);
		carBrandListView.setAdapter(carBrandListAdapter);
		carBrandListView.setOnItemClickListener(carBrandListener);
		
		carExpandedView = (ExpandableListView)findViewById(R.id.carExpandedList);
		carExpandableListAdapter = new CarExpandableListAdapter(this, null);
		carExpandedView.setAdapter(carExpandableListAdapter);
		carExpandedView.setOnChildClickListener(carChildClickListener);
	}
	protected OnItemClickListener carBrandListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			//System.out.println("size:");
			//Toast.makeText(this, carBrandList.size(), Toast.LENGTH_SHORT);
			if(toggle==0)
			{
				ArrayList<CarBrandEntity> tmpArrayList = new ArrayList<CarBrandEntity>();
				CarBrandEntity tmpBrandEntity = carBrandEntities.get(arg2);
				tmpArrayList.add(tmpBrandEntity);
				carBrandListAdapter.updateList(tmpArrayList);
				carExpandedView.setVisibility(View.VISIBLE);
				carExpandableListAdapter.updateList(carBrandEntities.get(arg2).getList());
				toggle = 1;
			}
			else if(toggle==1)
			{
				carBrandListAdapter.updateList(carBrandEntities);
				carExpandedView.setVisibility(View.GONE);
				toggle = 0;
			}
			
			
		}
	};
	protected OnChildClickListener carChildClickListener = new OnChildClickListener() {
		
		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
                int groupPosition, int childPosition, long id) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(CarListActivity.this, CarDetailActivity.class);
			startActivity(intent);
			return false;
		}
	};
	public void exitClick(View view)
	{
		finish();
	}

}

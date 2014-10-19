package com.cettco.buycar.activity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cettco.buycar.R;
import com.cettco.buycar.adapter.CarColorAdapter;
import com.cettco.buycar.entity.CarColorEntity;
import com.cettco.buycar.entity.CarColorListEntity;
import com.cettco.buycar.utils.db.DatabaseHelperColor;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class SelectCarColorActivity extends Activity{
	
	private List<CarColorEntity> colorList;
	private ListView listView;
	private CarColorAdapter mycarColorAdapter;
	private Intent intent;
	private String name;
	private String model_id;
	//private int tag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selectshop);
		//getActionBar().hide();
		TextView titleTextView = (TextView)findViewById(R.id.title_text);
		intent = getIntent();
		name = intent.getStringExtra("name");
		model_id=intent.getStringExtra("model_id");
		//System.out.println("model_id:"+model_id);
		DatabaseHelperColor helperColor = DatabaseHelperColor
				.getHelper(this);
		try {
			colorList = helperColor.getDao().queryBuilder().where().eq("model_id", model_id).query();
			//System.out.println("color list size:"+colorList.size());
			//System.out.println("color list 0:"+colorList.get(0).getCode());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//tag = intent.getIntExtra("tag",0);
		titleTextView.setText(name);
		listView = (ListView)findViewById(R.id.selectshop_listview);	
		//colorList = new Gson().fromJson(intent.getStringExtra("color"), CarColorListEntity.class).getColors();
//		for(int i= 0;i<colorList.size();i++)
//		{
//			CarColorEntity entity = new CarColorEntity();
//			entity.setCode("#444444");
//			entity.setName("黄色");
//			colorList.add(entity);
//		}
		mycarColorAdapter = new CarColorAdapter(this,R.layout.item_color,colorList);
		listView.setAdapter(mycarColorAdapter);
		listView.setOnItemClickListener(listener);
	}
	protected OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			//System.out.println("position:"+arg2);
			CheckBox checkBox = (CheckBox) arg1.findViewById(R.id.car_color_checkBox);
			checkBox.toggle();
			mycarColorAdapter.getIsSelected().put(arg2, checkBox.isChecked());
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
		int size = mycarColorAdapter.getIsSelected().size();
		for(int i = 0;i<size;i++){
			mycarColorAdapter.getIsSelected().get(i);
		}
		setResult(RESULT_OK, intent);
		this.finish();
	}
	
	
	
	
	
}

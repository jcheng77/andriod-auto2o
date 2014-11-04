package com.cettco.buycar.activity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
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

public class SelectCarColorActivity extends Activity {

	private List<CarColorEntity> colorList;
	private ListView listView;
	private CarColorAdapter mycarColorAdapter;
	private Intent intent;
	private String name;
	private String model_id;
	//private ArrayList<String> selected_colors;

	// private int tag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_color);
		TextView titleTextView = (TextView) findViewById(R.id.title_text);
		intent = getIntent();
		name = intent.getStringExtra("name");
		model_id = intent.getStringExtra("model_id");
		//selected_colors = intent.getStringArrayListExtra("selected_colors");
		DatabaseHelperColor helperColor = DatabaseHelperColor.getHelper(this);
		try {
			colorList = helperColor.getDao().queryBuilder().where()
					.eq("model_id", model_id).query();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		titleTextView.setText(name);
		listView = (ListView) findViewById(R.id.select_color_listview);
		mycarColorAdapter = new CarColorAdapter(this, R.layout.item_color,
				colorList);
		listView.setAdapter(mycarColorAdapter);
		listView.setOnItemClickListener(listener);
	}

	protected OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			// System.out.println("position:"+arg2);
			CheckBox checkBox = (CheckBox) arg1
					.findViewById(R.id.car_color_checkBox);
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

	public void exitClick(View view) {
		// intent.putExtra("result", position);
		ArrayList<String> colors = new ArrayList<String>();
		Iterator<Integer> iter = mycarColorAdapter.getIsSelected().keySet()
				.iterator();
		while (iter.hasNext()) {
			Integer key = iter.next();
			Boolean value = mycarColorAdapter.getIsSelected().get(key);
			if (value == true) {
				colors.add(colorList.get(key).getId());
			}
		}
		intent.putStringArrayListExtra("colors", colors);
		setResult(RESULT_OK, intent);
		this.finish();
	}

}

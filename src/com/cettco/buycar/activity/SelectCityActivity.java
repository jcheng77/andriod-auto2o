package com.cettco.buycar.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cettco.buycar.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SelectCityActivity extends ActionBarActivity{

	private ArrayList<String> arrayList;
	private ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selectcity);
		getActionBar().hide();
		listView = (ListView)findViewById(R.id.city_listview);
		arrayList = new ArrayList<String>();
		arrayList.add("Shanghai");
		arrayList.add("beijing");
		final StableArrayAdapter adapter = new StableArrayAdapter(this,
		        android.R.layout.simple_list_item_1, arrayList);
		listView.setAdapter(adapter);
	}
	private class StableArrayAdapter extends ArrayAdapter<String> {

	    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

	    public StableArrayAdapter(Context context, int textViewResourceId,
	        List<String> objects) {
	      super(context, textViewResourceId, objects);
	      for (int i = 0; i < objects.size(); ++i) {
	        mIdMap.put(objects.get(i), i);
	      }
	    }

	    @Override
	    public long getItemId(int position) {
	      String item = getItem(position);
	      return mIdMap.get(item);
	    }

	    @Override
	    public boolean hasStableIds() {
	      return true;
	    }

	  }
	public void exitClick(View view)
	{
		finish();
	}
	
}

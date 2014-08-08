package com.cettco.buycar.adapter;

import java.util.ArrayList;
import java.util.List;

import com.cettco.buycar.entity.CarBrandEntity;
import com.cettco.buycar.R;

import android.R.raw;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CarBrandListAdapter extends ArrayAdapter<CarBrandEntity>{

	private Context context;
	private List<CarBrandEntity> carBrandList;
	public CarBrandListAdapter(Context context, int resource,
			ArrayList<CarBrandEntity> carBrandList) {
		super(context, resource, carBrandList);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.carBrandList = carBrandList;
	}
	public void updateList(List<CarBrandEntity> list){
		this.carBrandList = list;
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return carBrandList.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.carbrandlist_item, parent,
				false);
		CarBrandEntity entity = carBrandList.get(position);
		TextView textView = (TextView)rowView.findViewById(R.id.carBrandText);
		textView.setText(entity.getName());
		return rowView;
	}
	
}
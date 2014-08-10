package com.cettco.buycar.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cettco.buycar.R;
import com.cettco.buycar.entity.CarColorEntity;

public class CarColorAdapter extends ArrayAdapter<CarColorEntity>{

	private Context context;
	private List<CarColorEntity> list;
	
	public CarColorAdapter(Context context, int resource,
			 List<CarColorEntity> list) {
		super(context, resource, list);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.carcolor_item, parent,
				false);
		CarColorEntity carColorEntity = list.get(position);
		ImageView imageView = (ImageView)rowView.findViewById(R.id.car_color_imageview);
		TextView textView = (TextView)rowView.findViewById(R.id.car_color_text);
		textView.setText(carColorEntity.getName());
		return rowView;
	}
	
	
	
}
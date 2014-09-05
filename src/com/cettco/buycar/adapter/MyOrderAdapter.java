package com.cettco.buycar.adapter;

import java.util.ArrayList;
import java.util.List;

import com.cettco.buycar.R;
import com.cettco.buycar.entity.MyOrderEntity;
import com.cettco.buycar.fragment.MyCarFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyOrderAdapter extends ArrayAdapter<MyOrderEntity>{

	private Context context;
	private List<MyOrderEntity> list;
	public MyOrderAdapter(Context context, int resource,
			List<MyOrderEntity> list) {
		super(context, resource, list);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
	}
//	public void updateList(List<CarBrandEntity> list){
//		this.carBrandList = list;
//		notifyDataSetChanged();
//	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.list.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.my_order_item, parent,
				false);
		MyOrderEntity entity = list.get(position);
//		TextView textView = (TextView)rowView.findViewById(R.id.carBrandText);
//		textView.setText(entity.getName());
		return rowView;
	}

}
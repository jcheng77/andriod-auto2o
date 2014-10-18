package com.cettco.buycar.adapter;

import java.util.ArrayList;
import java.util.List;
import com.cettco.buycar.entity.CarBrandEntity;
import com.cettco.buycar.R;
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
			List<CarBrandEntity> carBrandList) {
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
		//System.out.println("adpater size:"+carBrandList.size());
		return carBrandList==null?0:this.carBrandList.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if(convertView==null){
			holder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_carbrandlist, parent,
					false);
			holder.textView = (TextView)convertView.findViewById(R.id.carBrandText);
			convertView.setTag(holder);
;
		}else {
			holder = (ViewHolder) convertView.getTag(); 
		}
		CarBrandEntity entity = carBrandList.get(position);
		holder.textView.setText(entity.getName());
		return convertView;
//		LayoutInflater inflater = (LayoutInflater) context
//				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		View rowView = inflater.inflate(R.layout.carbrandlist_item, parent,
//				false);
//		CarBrandEntity entity = carBrandList.get(position);
//		TextView textView = (TextView)rowView.findViewById(R.id.carBrandText);
//		textView.setText(entity.getName());
//		//ImageView imageView = (ImageView)rowView.findViewById(R.id.car)
//		return rowView;
	}
	private static class ViewHolder
    {
		TextView textView;
    }
	
}

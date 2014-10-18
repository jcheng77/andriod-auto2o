package com.cettco.buycar.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.cettco.buycar.R;
import com.cettco.buycar.entity.CarColorEntity;

public class CarColorAdapter extends ArrayAdapter<CarColorEntity>{

	private HashMap<Integer, Boolean> isSelected; 
	private Context context;
	private List<CarColorEntity> list;
	
	public CarColorAdapter(Context context, int resource,
			 List<CarColorEntity> list) {
		super(context, resource, list);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		isSelected = new HashMap<Integer, Boolean>();  
	        // 初始化数据  
	    initDate();
	}
	private void initDate() {  
        for (int i = 0; i < list.size(); i++) {  
            getIsSelected().put(i, false);  
        }  
    }  
	public  HashMap<Integer, Boolean> getIsSelected() {  
        return isSelected;  
    }  

    public  void setIsSelected(HashMap<Integer, Boolean> isSelected) {  
        this.isSelected = isSelected;  
    }  

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if(convertView==null){
			holder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_color, parent,
					false);
			holder.imageView = (ImageView)convertView.findViewById(R.id.car_color_imageview);
			holder.textView = (TextView)convertView.findViewById(R.id.car_color_text);
			
			holder.checkBox = (CheckBox)convertView.findViewById(R.id.car_color_checkBox);
			convertView.setTag(holder);
;
		}else {
			holder = (ViewHolder) convertView.getTag(); 
		}
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.item_color, parent,
				false);
		CarColorEntity carColorEntity = list.get(position);
		holder.imageView.setBackgroundColor(Color.parseColor(carColorEntity.getCode()));
		holder.textView.setText(carColorEntity.getName());
		return rowView;
	}
	private static class ViewHolder
    {
		ImageView imageView;
		TextView textView;
		
		CheckBox checkBox;
    }
	
	
}
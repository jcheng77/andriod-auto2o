package com.cettco.buycar.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.cettco.buycar.R;
import com.cettco.buycar.entity.CarColorEntity;

public class CarColorAdapter extends ArrayAdapter<CarColorEntity> {

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

	public HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		this.isSelected = isSelected;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list == null ? 0 : list.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_color, parent, false);
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.car_color_imageview);
			holder.textView = (TextView) convertView
					.findViewById(R.id.car_color_text);

			holder.checkBox = (CheckBox) convertView
					.findViewById(R.id.car_color_checkBox);
			convertView.setTag(holder);
			;
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// LayoutInflater inflater = (LayoutInflater) context
		// .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// View rowView = inflater.inflate(R.layout.item_color, parent,
		// false);
		CarColorEntity carColorEntity = list.get(position);
		try {
			if (carColorEntity.getCode().startsWith(" ")){
//				Drawable myIcon = context.getResources().getDrawable( R.drawable.ic_launcher); 
//			    ColorFilter filter = new LightingColorFilter( Color.BLACK, Color.BLACK);
//			    myIcon.setColorFilter(filter);
//			    holder.imageView.setImageDrawable(myIcon);
//				holder.imageView.setBackgroundColor(Color
//						.parseColor(carColorEntity.getCode().substring(1)));
//				holder.imageView.invalidate();
				//holder.imageView.changeColor(Color.parseColor(carColorEntity.getCode().substring(1)));
//				ShapeDrawable shapeDrawable = (ShapeDrawable)holder.imageView.getBackground();
//				shapeDrawable.getPaint().setColor(Color.parseColor(carColorEntity.getCode().substring(1)));
				GradientDrawable shapeDrawable = (GradientDrawable)holder.imageView.getBackground();
				//shapeDrawable.getPaint().setColor(Color.parseColor(carColorEntity.getCode()));
				//shapeDrawable.setColor(Color.parseColor(Color.parseColor(carColorEntity.getCode().substring(1)));
				shapeDrawable.setColor(Color.parseColor(carColorEntity.getCode().substring(1)));
			}
				
			else{
//				Drawable myIcon = context.getResources().getDrawable( R.drawable.ic_launcher); 
//			    ColorFilter filter = new LightingColorFilter( Color.BLACK, Color.BLACK);
//			    myIcon.setColorFilter(filter);
//			    holder.imageView.setImageDrawable(myIcon);
//				holder.imageView.setBackgroundColor(Color
//						.parseColor(carColorEntity.getCode()));
//				holder.imageView.invalidate();
				//holder.imageView.changeColor(Color.parseColor(carColorEntity.getCode()));
				GradientDrawable shapeDrawable = (GradientDrawable)holder.imageView.getBackground();
				//shapeDrawable.getPaint().setColor(Color.parseColor(carColorEntity.getCode()));
				shapeDrawable.setColor(Color.parseColor(carColorEntity.getCode()));
			}
				
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("error reason:" + e);
			System.out.println("error color:" + carColorEntity.getCode());
		}
		holder.textView.setText(carColorEntity.getName());
		holder.checkBox.setChecked(isSelected.get(position));
		return convertView;
	}

	private static class ViewHolder {
		ImageView imageView;
		TextView textView;

		CheckBox checkBox;
	}

}
package com.cettco.buycar.adapter;

import java.util.HashMap;
import java.util.List;

import com.cettco.buycar.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CancleReasonAdapter extends ArrayAdapter<String> {

	private HashMap<Integer, Boolean> isSelected;
	private Context context;
	private List<String> list;

	public CancleReasonAdapter(Context context, int resource,
			List<String> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = objects;
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
			convertView = inflater.inflate(R.layout.cancle_reason_item, parent,
					false);
			holder.textView = (TextView)convertView.findViewById(R.id.cancleReason_text);
			convertView.setTag(holder);
;
		}else {
			holder = (ViewHolder) convertView.getTag(); 
		}
//		LayoutInflater inflater = (LayoutInflater) context
//				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		View rowView = inflater.inflate(R.layout.cancle_reason_item, parent,
//				false);
		//TextView textView = (TextView)rowView.findViewById(R.id.cancleReason_text);
		holder.textView.setText(list.get(position));
		
		//CheckBox checkBox = (CheckBox)rowView.findViewById(R.id.cancleReason_checkBox);
		//return rowView;
		return convertView;
	}
	private static class ViewHolder
    {
		TextView textView;
    }

}

package com.cettco.buycar.adapter;

import java.util.HashMap;
import java.util.List;

import com.cettco.buycar.R;
import com.cettco.buycar.entity.DealerEntity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class MyBaseListAdapter extends ArrayAdapter<String>{
	private Context context;
	private List<String> list;
	@SuppressLint("UseSparseArrays")
	public MyBaseListAdapter(Context context, int resource,
			List<String> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = objects;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.list==null?0:this.list.size();
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if(convertView==null){
			holder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_my_base_list, parent,
					false);
			
			holder.nameTextView = (TextView)convertView.findViewById(R.id.item_base_list_textview);
			//holder.nameTextView.setTypeface(null, Typeface.BOLD);
			convertView.setTag(holder);
;
		}else {
			holder = (ViewHolder) convertView.getTag(); 
		}
		holder.nameTextView.setText(list.get(position));
		return convertView;
		//return super.getView(position, convertView, parent);
	}
	private static class ViewHolder
    {
		TextView nameTextView;
    }

}

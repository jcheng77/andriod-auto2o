package com.cettco.buycar.adapter;

import java.util.List;

import com.cettco.buycar.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CouponAdapter extends ArrayAdapter<String>{
	private Context context;
	private List<String> list;

	public CouponAdapter(Context context, int resource,
			List<String> objects) {
		super(context, resource,objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = objects;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if(convertView==null){
			holder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_coupon, parent,
					false);
			holder.textView = (TextView)convertView.findViewById(R.id.item_coupon_date_txtv);
			convertView.setTag(holder);
;
		}else {
			holder = (ViewHolder) convertView.getTag(); 
		}
		holder.textView.setText(list.get(position));
		return convertView;
	}
	private static class ViewHolder
    {
		TextView textView;
    }

}

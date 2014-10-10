package com.cettco.buycar.adapter;

import java.util.ArrayList;
import java.util.List;

import com.cettco.buycar.R;
import com.cettco.buycar.entity.MyOrderEntity;
import com.cettco.buycar.entity.OrderItemEntity;
import com.cettco.buycar.fragment.MyCarFragment;
import com.cettco.buycar.utils.Data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyOrderAdapter extends ArrayAdapter<OrderItemEntity>{

	private Context context;
	private List<OrderItemEntity> list;
	public MyOrderAdapter(Context context, int resource,
			List<OrderItemEntity> list) {
		super(context, resource, list);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
	}
	public void updateList(List<OrderItemEntity> list){
		this.list = list;
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list==null?0:this.list.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.my_order_item, parent,
				false);
		OrderItemEntity entity = list.get(position);
		TextView modeltextView = (TextView)rowView.findViewById(R.id.my_order_model_textview);
		modeltextView.setText(entity.getName());
		TextView pricetextView = (TextView)rowView.findViewById(R.id.my_order_price_textview);
		pricetextView.setText(entity.getPrice());
		ImageView imageView= (ImageView)rowView.findViewById(R.id.my_order_imageview);
		Data.IMAGE_CACHE.get(entity.getPic_url(), imageView);
//		TextView statetextView = (TextView)rowView.findViewById(R.id.my_order_state_textview);
//	    statetextView.setText(entity.getState());
//		TextView numtextView = (TextView)rowView.findViewById(R.id.my_order_num_textview);
//		numtextView.setText(entity.getSecond_round_bids());
		return rowView;
	}

}

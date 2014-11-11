
package com.cettco.buycar.adapter;

import java.util.List;

import com.cettco.buycar.R;
import com.cettco.buycar.entity.OrderItemEntity;
import com.cettco.buycar.utils.Data;

import android.R.bool;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyOrderAdapter extends ArrayAdapter<OrderItemEntity> {

	private Context context;
	private List<OrderItemEntity> list;

	public MyOrderAdapter(Context context, int resource,
			List<OrderItemEntity> list) {
		super(context, resource, list);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
	}

	public void updateList(List<OrderItemEntity> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(list==null||list.size()==0)return 1;
		return list.size();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_my_order, parent,
					false);
			holder.stateTextView = (TextView) convertView
					.findViewById(R.id.my_order_status_textview);
			holder.brandMakerModelTextView = (TextView) convertView
					.findViewById(R.id.my_order_brandmakermodel_textview);
			holder.trimTextView=  (TextView) convertView
					.findViewById(R.id.my_order_trim_textview);
			holder.pricetextView = (TextView) convertView
					.findViewById(R.id.my_order_price_textview);
			holder.stateLayout = (LinearLayout) convertView
					.findViewById(R.id.my_order_status_layout);
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.my_order_imageview);
			holder.hasdataLayout = (LinearLayout)convertView
					.findViewById(R.id.my_order_has_data_layout);
			holder.nodataLayout = (LinearLayout)convertView
					.findViewById(R.id.my_order_no_data_layout);
			convertView.setTag(holder);
			;
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if(list==null||list.size()==0){
			holder.hasdataLayout.setVisibility(View.GONE);
			holder.nodataLayout.setVisibility(View.VISIBLE);
			return convertView;
		}
		holder.hasdataLayout.setVisibility(View.VISIBLE);
		holder.nodataLayout.setVisibility(View.GONE);
		OrderItemEntity entity = list.get(position);
		//System.out.println(entity.getPic_url());
		String[] name_array = entity.getModel().split(" : ");
		//System.out.println(name_array);
		if(name_array!=null&&name_array.length>=4){
			holder.brandMakerModelTextView.setText(name_array[0]+" "+name_array[1]+" "+name_array[2]);
			holder.trimTextView.setText(name_array[3]);
		}if(name_array!=null&&name_array.length==3){
			holder.brandMakerModelTextView.setText(name_array[0]+" "+name_array[1]+" "+name_array[2]);
			//holder.trimTextView.setText(name_array[3]);
		}
		holder.pricetextView.setText(entity.getPrice());
		Data.IMAGE_CACHE.get(entity.getPic_url(), holder.imageView);
		//System.out.println("state:"+entity.getState());
		if (entity.getState().equals("viewed")) {
			holder.stateTextView.setText("以看车型");
			holder.stateLayout.setBackgroundColor(Color.parseColor("#FF6600"));
		} else if (entity.getState().equals("begain")) {
			holder.stateTextView.setText("决定购买车型");
			holder.stateLayout.setBackgroundColor(Color.parseColor("#66cc66"));
		} else if (entity.getState().equals("determined")) {
			holder.stateTextView.setText("已提交订单,待支付");
			holder.stateLayout.setBackgroundColor(Color.parseColor("#ccff00"));
		} else if (entity.getState().equals("qualified")) {
			holder.stateTextView.setText("已支付");
			holder.stateLayout.setBackgroundColor(Color.parseColor("#99ccff"));
		} else if (entity.getState().equals("timeout")) {
			holder.stateTextView.setText("超时");
			holder.stateLayout.setBackgroundColor(Color.parseColor("#FF6666"));
		} else if (entity.getState().equals("deal_made")) {
			holder.stateTextView.setText("已有4s店接单");
			holder.stateLayout.setBackgroundColor(Color.parseColor("#0099cc"));
		} else if (entity.getState().equals("final_deal_closed")) {
			holder.stateTextView.setText("最终成交");
			holder.stateLayout.setBackgroundColor(Color.parseColor("#FF0033"));
		}
		return convertView;
	}

	private static class ViewHolder {
		TextView stateTextView;
		TextView brandMakerModelTextView;
		TextView trimTextView;
		TextView pricetextView;
		LinearLayout stateLayout;
		ImageView imageView;
		LinearLayout nodataLayout;
		LinearLayout hasdataLayout;
	}

}

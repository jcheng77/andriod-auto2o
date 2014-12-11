
package com.cettco.buycar.adapter;

import java.sql.SQLException;
import java.util.List;

import com.baidu.mapapi.search.poi.PoiBoundSearchOption;
import com.cettco.buycar.R;
import com.cettco.buycar.activity.CancleReasonActivity;
import com.cettco.buycar.entity.OrderItemEntity;
import com.cettco.buycar.utils.MyApplication;
import com.cettco.buycar.utils.db.DatabaseHelperOrder;

import android.R.bool;
import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
			holder.cancellaLayout = (LinearLayout)convertView.findViewById(R.id.cancel_linearlayout);
			convertView.setTag(holder);
			holder.cancelButton = (Button)convertView
					.findViewById(R.id.my_order_cancel_btn);
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
		final OrderItemEntity entity = list.get(position);
		final int mPosition = position;
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
		if(entity.getPrice()==null)
			holder.pricetextView.setText("");
		else holder.pricetextView.setText(entity.getPrice()+"元");
		MyApplication.IMAGE_CACHE.get(entity.getPic_url(), holder.imageView);
		//System.out.println("state:"+entity.getState());
		if (entity.getState().equals("viewed")) {
			holder.cancellaLayout.setVisibility(View.GONE);
			holder.stateTextView.setText("已看车型");
			holder.stateLayout.setBackgroundColor(Color.parseColor("#FF6201"));
		} else if (entity.getState().equals("begain")) {
			holder.cancellaLayout.setVisibility(View.GONE);
			holder.stateTextView.setText("决定购买车型");
			holder.stateLayout.setBackgroundColor(Color.parseColor("#FF6201"));
		} else if (entity.getState().equals("determined")) {
			holder.cancellaLayout.setVisibility(View.GONE);
			holder.stateTextView.setText("已提交订单,待支付");
			holder.stateLayout.setBackgroundColor(Color.parseColor("#FF6201"));
		} else if (entity.getState().equals("qualified")) {
			holder.cancellaLayout.setVisibility(View.GONE);
			holder.stateTextView.setText("已支付,等待4s店接受订单");
			holder.stateLayout.setBackgroundColor(Color.parseColor("#0C8398"));
		} else if (entity.getState().equals("timeout")) {
			holder.cancellaLayout.setVisibility(View.GONE);
			holder.stateTextView.setText("超时");
			holder.stateLayout.setBackgroundColor(Color.parseColor("#939393"));
		} else if (entity.getState().equals("deal_made")) {
			holder.cancellaLayout.setVisibility(View.GONE);
			holder.stateTextView.setText("已有4s店接单");
			holder.stateLayout.setBackgroundColor(Color.parseColor("#939393"));
		} else if (entity.getState().equals("final_deal_closed")) {
			holder.cancellaLayout.setVisibility(View.GONE);
			holder.stateTextView.setText("最终成交");
			holder.stateLayout.setBackgroundColor(Color.parseColor("#939393"));
		}else if (entity.getState().equals("canceled")) {
			holder.stateTextView.setText("取消交易");
			holder.cancellaLayout.setVisibility(View.GONE);
			holder.stateLayout.setBackgroundColor(Color.parseColor("#939393"));
		}
//		holder.cancelButton.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
////				Toast toast = Toast.makeText(context, "cancel click", Toast.LENGTH_SHORT);
////				toast.show();
//				if(entity.getState().equals("viewed")||entity.getState().equals("begain")){
//					DatabaseHelperOrder helper = DatabaseHelperOrder.getHelper(context);
//					try {
//						helper.getDao().delete(entity);
//						list.remove(mPosition);
//						notifyDataSetChanged();
//					} catch (SQLException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}else {
//					Intent intent = new Intent();
//					intent.setClass(context, CancleReasonActivity.class);
//					intent.putExtra("tender_id", entity.getId());
//					context.startActivity(intent);
//				}
//			}
//		});
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
		Button cancelButton;
		LinearLayout cancellaLayout;
	}

}

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
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
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
		if (list == null || list.size() == 0)
			return 1;
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
			holder.trimTextView = (TextView) convertView
					.findViewById(R.id.my_order_trim_textview);
			holder.pricetextView = (TextView) convertView
					.findViewById(R.id.my_order_price_textview);
			holder.stateLayout = (LinearLayout) convertView
					.findViewById(R.id.my_order_status_layout);
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.my_order_imageview);
			holder.hasdataLayout = (LinearLayout) convertView
					.findViewById(R.id.my_order_has_data_layout);
			holder.nodataLayout = (LinearLayout) convertView
					.findViewById(R.id.my_order_no_data_layout);
			holder.cancellaLayout = (RelativeLayout) convertView
					.findViewById(R.id.cancel_linearlayout);
			convertView.setTag(holder);
			holder.cancelButton = (Button) convertView
					.findViewById(R.id.my_order_cancel_btn);
			holder.priceLayout = (LinearLayout)convertView
					.findViewById(R.id.my_order_price_linearlayout);
			;
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (list == null || list.size() == 0) {
			holder.hasdataLayout.setVisibility(View.GONE);
			holder.nodataLayout.setVisibility(View.VISIBLE);
			return convertView;
		}
		holder.hasdataLayout.setVisibility(View.VISIBLE);
		holder.nodataLayout.setVisibility(View.GONE);
		final OrderItemEntity entity = list.get(position);
		final int mPosition = position;
		// System.out.println(entity.getPic_url());
		String[] name_array = entity.getModel().split(" : ");
		// System.out.println(name_array);
		if (name_array != null && name_array.length >= 4) {
//			holder.brandMakerModelTextView.setText(name_array[0] + " "
//					+ name_array[1] + " " + name_array[2]);
			holder.brandMakerModelTextView.setText(name_array[2]);
			holder.trimTextView.setText(name_array[3]);
		}
		if (name_array != null && name_array.length == 3) {
//			holder.brandMakerModelTextView.setText(name_array[0] + " "
//					+ name_array[1] + " " + name_array[2]);
			// holder.trimTextView.setText(name_array[3]);
			holder.brandMakerModelTextView.setText(name_array[2]);
		}
		if (entity.getPrice() == null)
			holder.pricetextView.setText("");
		else
			holder.pricetextView.setText(entity.getPrice() + "万");
		MyApplication.IMAGE_CACHE.get(entity.getPic_url(), holder.imageView);
		// System.out.println("state:"+entity.getState());
		if (entity.getState().equals("viewed")) {
			holder.cancellaLayout.setVisibility(View.VISIBLE);
			holder.stateTextView.setText("已看车型");
			holder.stateLayout.setBackgroundColor(context.getResources().getColor(R.color.order_color_viewed));
			holder.priceLayout.setVisibility(View.GONE);
		}else if (entity.getState().equals("determined")) {
			holder.priceLayout.setVisibility(View.VISIBLE);
			holder.cancellaLayout.setVisibility(View.GONE);
			holder.stateTextView.setText("已提交订单,等待支付");
			holder.stateLayout.setBackgroundColor(context.getResources().getColor(R.color.order_color_determined));
		} else if (entity.getState().equals("qualified")) {
			holder.priceLayout.setVisibility(View.VISIBLE);
			holder.cancellaLayout.setVisibility(View.GONE);
			holder.stateTextView.setText("已支付,等待4s店接受订单");
			holder.stateLayout.setBackgroundColor(context.getResources().getColor(R.color.order_color_qualified));
		} else if (entity.getState().equals("timeout")) {
			holder.priceLayout.setVisibility(View.VISIBLE);
			holder.cancellaLayout.setVisibility(View.GONE);
			holder.stateTextView.setText("超时");
			holder.stateLayout.setBackgroundColor(context.getResources().getColor(R.color.order_color_timeout));
		} else if (entity.getState().equals("deal_made")) {
			holder.priceLayout.setVisibility(View.VISIBLE);
			holder.cancellaLayout.setVisibility(View.GONE);
			holder.stateTextView.setText("已有4s店接单,请到店提车");
			holder.stateLayout.setBackgroundColor(context.getResources().getColor(R.color.order_color_deal_made));
		} else if (entity.getState().equals("final_deal_closed")) {
			holder.priceLayout.setVisibility(View.VISIBLE);
			holder.cancellaLayout.setVisibility(View.GONE);
			holder.stateTextView.setText("最终成交");
			holder.stateLayout.setBackgroundColor(context.getResources().getColor(R.color.order_color_final_deal_closed));
		} else if (entity.getState().equals("canceled")) {
			holder.priceLayout.setVisibility(View.VISIBLE);
			holder.stateTextView.setText("取消交易");
			holder.cancellaLayout.setVisibility(View.GONE);
			holder.stateLayout.setBackgroundColor(context.getResources().getColor(R.color.order_color_canceled));
		}
		holder.cancelButton.setTag(R.id.popup_menu_myorder_position, position);
		holder.cancelButton.setOnClickListener(optionClickListener);
		return convertView;
	}

	private OnClickListener optionClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			final int pos = (int) v.getTag(R.id.popup_menu_myorder_position);
			Context wrapper = new ContextThemeWrapper(context, R.style.PopupMenu);
			PopupMenu popup = new PopupMenu(wrapper, v);
			//PopupMenu popup = new PopupMenu(context, v);
			// Inflating the Popup using xml file
			popup.getMenuInflater().inflate(R.menu.popup_menu_myorder,
					popup.getMenu());

			// registering popup with OnMenuItemClickListener
			popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

				@Override
				public boolean onMenuItemClick(MenuItem item) {
					// TODO Auto-generated method stub
					if(item.getItemId()==R.id.popup_menu_my_order_delete){
						OrderItemEntity entity = list.get(pos);
						if(entity.getState().equals("viewed")||entity.getState().equals("begain")){
							DatabaseHelperOrder helper = DatabaseHelperOrder.getHelper(context);
							try {
								helper.getDao().delete(entity);
								list.remove(pos);
								notifyDataSetChanged();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else {
							Intent intent = new Intent();
							intent.setClass(context, CancleReasonActivity.class);
							intent.putExtra("tender_id", entity.getId());
							context.startActivity(intent);
						}
					}
					return false;
				}
			});

			popup.show();
		}
	};

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
		RelativeLayout cancellaLayout;
		LinearLayout priceLayout;
	}

}

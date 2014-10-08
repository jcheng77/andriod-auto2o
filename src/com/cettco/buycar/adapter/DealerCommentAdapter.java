package com.cettco.buycar.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cettco.buycar.R;
import com.cettco.buycar.entity.DealerCommentEntity;
import com.cettco.buycar.utils.MarkView;

public class DealerCommentAdapter extends ArrayAdapter<DealerCommentEntity>{
	private Context context;
	private List<DealerCommentEntity> list;
	
	public DealerCommentAdapter(Context context, int resource,
			ArrayList<DealerCommentEntity> list) {
		super(context, resource, list);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
	}
	public void updateList(List<DealerCommentEntity> list){
		this.list = list;
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//System.out.println("adpater size:"+carBrandList.size());
		return list==null?0:this.list.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if(convertView==null){
			holder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.dealer_comment_item, parent,
					false);
			holder.priceMarkView = (MarkView)convertView.findViewById(R.id.dealer_comment_price_markview);
			holder.timeMarkView = (MarkView)convertView.findViewById(R.id.dealer_comment_time_markview);
			holder.qualityMarkView = (MarkView)convertView.findViewById(R.id.dealer_comment_quality_markview);
//			qualityMarkView.setLevel(3);
//			qualityMarkView.setClick(false);
//			DealerCommentEntity entity = carBrandList.get(position);
			holder.textView = (TextView)convertView.findViewById(R.id.carBrandText);
//			textView.setText(entity.getName());
			convertView.setTag(holder);
;
		}else {
			holder = (ViewHolder) convertView.getTag(); 
		}
		holder.priceMarkView.setLevel(3);
		holder.priceMarkView.setClick(false);
		holder.timeMarkView.setLevel(3);
		holder.timeMarkView.setClick(false);
		holder.qualityMarkView.setLevel(3);
		holder.qualityMarkView.setClick(false);
		//holder.textView.setText(entity.getName());
		return convertView;
//		LayoutInflater inflater = (LayoutInflater) context
//				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		View rowView = inflater.inflate(R.layout.dealer_comment_item, parent,
//				false);
//		MarkView priceMarkView = (MarkView)rowView.findViewById(R.id.dealer_comment_price_markview);
//		priceMarkView.setLevel(3);
//		priceMarkView.setClick(false);
//		MarkView timeMarkView = (MarkView)rowView.findViewById(R.id.dealer_comment_time_markview);
//		timeMarkView.setLevel(3);
//		priceMarkView.setClick(false);
//		MarkView qualityMarkView = (MarkView)rowView.findViewById(R.id.dealer_comment_quality_markview);
//		qualityMarkView.setLevel(3);
//		qualityMarkView.setClick(false);
//		DealerCommentEntity entity = carBrandList.get(position);
//		TextView textView = (TextView)rowView.findViewById(R.id.carBrandText);
//		textView.setText(entity.getName());
		//ImageView imageView = (ImageView)rowView.findViewById(R.id.car)
		//return rowView;
	}
	private static class ViewHolder
    {
		MarkView priceMarkView;
		MarkView timeMarkView;
		MarkView qualityMarkView;
		TextView textView;
    }
}

package com.cettco.buycar.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.cettco.buycar.R;
import com.cettco.buycar.entity.DealerCommentEntity;

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
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.dealer_comment_item, parent,
				false);
//		DealerCommentEntity entity = carBrandList.get(position);
//		TextView textView = (TextView)rowView.findViewById(R.id.carBrandText);
//		textView.setText(entity.getName());
		//ImageView imageView = (ImageView)rowView.findViewById(R.id.car)
		return rowView;
	}
}

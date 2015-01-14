package com.cettco.buycar.adapter;

import java.net.ContentHandler;
import java.util.HashMap;
import java.util.List;

//import com.baidu.navi.location.LocationClient;
import com.cettco.buycar.R;
import com.cettco.buycar.activity.DealerCommentActivity;
import com.cettco.buycar.entity.CarBrandEntity;
import com.cettco.buycar.entity.DealerEntity;
import com.cettco.buycar.utils.MarkView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DealerListAdapter extends ArrayAdapter<DealerEntity>{

	private HashMap<Integer, Boolean> isSelected; 
	private Context context;
	private List<DealerEntity> list;
	
	//Location private memebers
//	private LocationClient locationClient;
//	private static final int UPDATE_TIME = 5000;  
//    private static int LOCATION_COUTNS = 0;
	public DealerListAdapter(Context context, int resource,
			List<DealerEntity> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = objects;
		isSelected = new HashMap<Integer, Boolean>();  
		initDate();
	}
	public void updateList(List<DealerEntity> list){
		this.list = list;
		notifyDataSetChanged();
	}
	private void initDate() {  
        for (int i = 0; i < list.size(); i++) {  
            getIsSelected().put(i, false);  
        }  
    } 
	public  HashMap<Integer, Boolean> getIsSelected() {  
        return isSelected;  
    }  

    public  void setIsSelected(HashMap<Integer, Boolean> isSelected) {  
        this.isSelected = isSelected;  
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
			convertView = inflater.inflate(R.layout.item_dealer, parent,
					false);
			holder.viewcommentLayout = (RelativeLayout)convertView.findViewById(R.id.dealer_view_comment_layout);
			
			holder.distanceTextView = (TextView)convertView.findViewById(R.id.dealer_item_distance);
			//
			holder.priceMarkView = (MarkView)convertView.findViewById(R.id.dealer_price_markview);
			holder.timeMarkView = (MarkView)convertView.findViewById(R.id.dealer_time_markview);

			holder.qualityMarkView = (MarkView)convertView.findViewById(R.id.dealer_quality_markview);
			convertView.setTag(holder);
;
		}else {
			holder = (ViewHolder) convertView.getTag(); 
		}
		DealerEntity entity = list.get(position);
		holder.viewcommentLayout.setOnClickListener(viewCommentClickListener);
		holder.distanceTextView.setText(Integer.toString(entity.getDistance()));
		
		holder.priceMarkView.setLevel(3);
		holder.priceMarkView.setClick(false);
		
		holder.timeMarkView.setLevel(3);
		holder.timeMarkView.setClick(false);
		
		holder.qualityMarkView.setLevel(3);
		holder.qualityMarkView.setClick(false);
//		LayoutInflater inflater = (LayoutInflater) context
//				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		View rowView = inflater.inflate(R.layout.dealer_item, parent,
//				false);
//		DealerEntity entity = list.get(position);
//		RelativeLayout viewcommentLayout = (RelativeLayout)rowView.findViewById(R.id.dealer_view_comment_layout);
//		viewcommentLayout.setOnClickListener(viewCommentClickListener);
//		TextView textView = (TextView)rowView.findViewById(R.id.dealer_item_distance);
//		textView.setText(Integer.toString(entity.getDistance()));
//		//
//		MarkView priceMarkView = (MarkView)rowView.findViewById(R.id.dealer_price_markview);
//		priceMarkView.setLevel(3);
//		priceMarkView.setClick(false);
//		MarkView timeMarkView = (MarkView)rowView.findViewById(R.id.dealer_time_markview);
//		timeMarkView.setLevel(3);
//		timeMarkView.setClick(false);
//		MarkView qualityMarkView = (MarkView)rowView.findViewById(R.id.dealer_quality_markview);
//		qualityMarkView.setLevel(3);
//		qualityMarkView.setClick(false);
//		return rowView;
		return convertView;
		//return super.getView(position, convertView, parent);
	}
	private OnClickListener viewCommentClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(context, DealerCommentActivity.class);
			context.startActivity(intent);
		}
	};
	private static class ViewHolder
    {
		RelativeLayout viewcommentLayout;
		TextView distanceTextView;
		//
		MarkView priceMarkView;
		MarkView timeMarkView ;
		MarkView qualityMarkView ;
    }

}

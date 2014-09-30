package com.cettco.buycar.adapter;

import java.net.ContentHandler;
import java.util.HashMap;
import java.util.List;

import com.baidu.navi.location.LocationClient;
import com.cettco.buycar.R;
import com.cettco.buycar.activity.DealerCommentActivity;
import com.cettco.buycar.entity.DealerEntity;

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
		return this.list.size();
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.dealer_item, parent,
				false);
		RelativeLayout viewcommentLayout = (RelativeLayout)rowView.findViewById(R.id.dealer_view_comment_layout);
		viewcommentLayout.setOnClickListener(viewCommentClickListener);
		TextView textView = (TextView)rowView.findViewById(R.id.dealer_item_distance);
		return rowView;
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

}

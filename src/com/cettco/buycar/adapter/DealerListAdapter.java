package com.cettco.buycar.adapter;

import java.net.ContentHandler;
import java.util.List;

import com.cettco.buycar.R;
import com.cettco.buycar.entity.DealerEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DealerListAdapter extends ArrayAdapter<DealerEntity>{

	private Context context;
	private List<DealerEntity> list;
	public DealerListAdapter(Context context, int resource,
			List<DealerEntity> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = objects;
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
		return rowView;
		//return super.getView(position, convertView, parent);
	}

}

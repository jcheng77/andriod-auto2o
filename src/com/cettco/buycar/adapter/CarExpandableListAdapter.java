package com.cettco.buycar.adapter;

import java.util.List;

import com.cettco.buycar.R;
import com.cettco.buycar.entity.CarMakerEntity;
import com.cettco.buycar.entity.CarModelEntity;
import com.cettco.buycar.utils.MyApplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CarExpandableListAdapter extends BaseExpandableListAdapter {

	private Context context;
	List<CarMakerEntity> list;

	public CarExpandableListAdapter(Context context,
			List<CarMakerEntity> list) {
		this.context = context;
		this.list = list;
	}

	public void updateList(List<CarMakerEntity> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public Object getChild(int arg0, int arg1) {
		// TODO Auto-generated method stub
		if(this.list==null)return null;
		if(this.list.get(arg0).getModels()==null)return 0;
		return this.list.get(arg0).getModels().get(arg1);
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return arg1;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		CarModelEntity carTypeEntity = (CarModelEntity) getChild(groupPosition, childPosition);
		if(convertView==null){
			LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_car_type, null);
		}
		TextView carTypetTextView = (TextView) convertView.findViewById(R.id.list_car_type_text);
		carTypetTextView.setText(carTypeEntity.getName());
		ImageView imageView = (ImageView)convertView.findViewById(R.id.list_car_type_img);
		//System.out.println("img url:"+carTypeEntity.getPic_url());
		MyApplication.IMAGE_CACHE.get(carTypeEntity.getPic_url(), imageView);
		return convertView;
	}

	@Override
	public int getChildrenCount(int arg0) {
		// TODO Auto-generated method stub
		if(this.list==null)return 0;
		if(this.list.get(arg0).getModels()==null) return 0;
		return this.list.get(arg0).getModels().size();
	}

	@Override
	public Object getGroup(int arg0) {
		// TODO Auto-generated method stub
		if(this.list==null) return null;
		return this.list.get(arg0);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return this.list==null?0:this.list.size();
	}

	@Override
	public long getGroupId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getGroupView(int arg0, boolean arg1, View arg2, ViewGroup arg3) {
		// TODO Auto-generated method stub
		CarMakerEntity carManufactorEntity = (CarMakerEntity) getGroup(arg0);
		if (arg2 == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			arg2 = infalInflater.inflate(R.layout.list_car_manufactor, null);
		}

		TextView carManufactorText = (TextView) arg2
				.findViewById(R.id.list_car_manufactor_text);
		carManufactorText.setText(carManufactorEntity.getName());
		return arg2;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}

}

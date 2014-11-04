package com.cettco.buycar.adapter;

import java.util.HashMap;
import java.util.List;

import com.cettco.buycar.R;
import com.cettco.buycar.entity.DealerEntity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class SelectDealerAdapter extends ArrayAdapter<DealerEntity>{

	private HashMap<Integer, Boolean> isSelected; 
	private Context context;
	private List<DealerEntity> list;
	@SuppressLint("UseSparseArrays")
	public SelectDealerAdapter(Context context, int resource,
			List<DealerEntity> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = objects;
		isSelected = new HashMap<Integer, Boolean>();  
		initDate();
	}
	private void initDate() {  
        for (int i = 0; i < this.list.size(); i++) {  
            getIsSelected().put(i, false);  
        }  
    } 
	public void updateList(List<DealerEntity> list){
		this.list = list;
		notifyDataSetChanged();
		initDate();
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
			convertView = inflater.inflate(R.layout.item_selectshop, parent,
					false);
			
			holder.nameTextView = (TextView)convertView.findViewById(R.id.selectshop_name_text);
			//
			holder.addressTextView = (TextView)convertView.findViewById(R.id.selectshop_address_text);
			holder.checkBox = (CheckBox)convertView.findViewById(R.id.selectshop_checkBox);
			convertView.setTag(holder);
;
		}else {
			holder = (ViewHolder) convertView.getTag(); 
		}
		DealerEntity entity = list.get(position);
		holder.nameTextView.setText(entity.getName());
		holder.addressTextView.setText(entity.getAddress());
		holder.checkBox.setChecked(isSelected.get(position));
		return convertView;
		//return super.getView(position, convertView, parent);
	}
	private static class ViewHolder
    {
		TextView nameTextView;
		TextView addressTextView;
		CheckBox checkBox;
    }

}

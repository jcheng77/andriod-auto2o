package com.cettco.buycar.activity;

import java.util.ArrayList;

import com.cettco.buycar.R;
import com.cettco.buycar.adapter.CancleReasonAdapter;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CancleReasonActivity extends ActionBarActivity {

	private CancleReasonAdapter adapter;
	private ArrayList<String> list;
	private ListView listView;
	private TextView textView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cancle_reason);
		getActionBar().hide();
		textView = (TextView)findViewById(R.id.title_text);
		textView.setText("取消原因");
		list = new ArrayList<String>();
		list.add("4s店出价不给力");
		list.add("4s店离我这里太远");
		list.add("信息提交错误");
		list.add("选择其他车型");
		list.add("推迟购买计划");
		adapter = new CancleReasonAdapter(this, R.layout.cancle_reason_item, list);
		
		listView = (ListView)findViewById(R.id.cancleReason_listview);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(listener);
	}
	protected OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			CheckBox checkBox = (CheckBox) arg1.findViewById(R.id.cancleReason_checkBox);
			checkBox.toggle();
			adapter.getIsSelected().put(arg2, checkBox.isChecked());
		}
	};
	protected OnClickListener doneBtnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	public void exitClick(View view){
		this.finish();
	}

}

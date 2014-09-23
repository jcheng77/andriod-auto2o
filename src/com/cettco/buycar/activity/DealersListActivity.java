package com.cettco.buycar.activity;

import java.util.ArrayList;

import cn.trinea.android.common.view.DropDownListView;
import cn.trinea.android.common.view.DropDownListView.OnDropDownListener;

import com.cettco.buycar.R;
import com.cettco.buycar.adapter.DealerCommentAdapter;
import com.cettco.buycar.adapter.DealerListAdapter;
import com.cettco.buycar.entity.DealerCommentEntity;
import com.cettco.buycar.entity.DealerEntity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class DealersListActivity extends Activity {

	private ArrayList<DealerEntity> listItems;
	private DropDownListView listView = null;
	private DealerListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dealers);
		listView = (DropDownListView) findViewById(R.id.dealers_list_view);
		// set drop down listener
		listView.setOnDropDownListener(new OnDropDownListener() {

			@Override
			public void onDropDown() {
				// TODO Auto-generated method stub
				new GetDataTask(true).execute();
			}
		});
		listView.setOnBottomListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new GetDataTask(true).execute();

			}
		});
		listItems = new ArrayList<DealerEntity>();
		// listItems.addAll(Arrays.asList(mStrings));
		for (int i = 0; i < 10; i++) {
			DealerEntity entity = new DealerEntity();
			listItems.add(entity);
		}
		System.out.println("list item size:" + listItems.size());
		adapter = new DealerListAdapter(this, R.layout.dealer_item,
				listItems);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(listItemClickListener);
	}

	private OnItemClickListener listItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			
		}
	};
	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		private boolean isDropDown;

		public GetDataTask(boolean isDropDown) {
			this.isDropDown = isDropDown;
		}

		@Override
		protected String[] doInBackground(Void... params) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				;
			}
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {

			if (isDropDown) {
				// listItems.addFirst("Added after drop down");
				adapter.notifyDataSetChanged();
				listView.onDropDownComplete();

				// should call onDropDownComplete function of DropDownListView
				// at end of drop down complete.
				// SimpleDateFormat dateFormat = new
				// SimpleDateFormat("MM-dd HH:mm:ss");
				// listView.onDropDownComplete(getString(R.string.update_at)
				// + dateFormat.format(new Date()));
			} else {
				// listItems.add("Added after on bottom");
				adapter.notifyDataSetChanged();

				// should call onBottomComplete function of DropDownListView at
				// end of on bottom complete.
				listView.onBottomComplete();
			}

			super.onPostExecute(result);
		}
	}

	public void exitClick(View view) {
		// intent.putExtra("result", position);
		// int size = mycarColorAdapter.getIsSelected().size();
		// for(int i = 0;i<size;i++){
		// mycarColorAdapter.getIsSelected().get(i);
		// }
		// setResult(RESULT_OK, intent);
		this.finish();
	}

}

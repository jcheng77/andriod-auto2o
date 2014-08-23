package com.cettco.buycar.fragment;

import java.util.ArrayList;

import com.cettco.buycar.R;
import com.cettco.buycar.adapter.MyOrderAdapter;
import com.cettco.buycar.entity.MyOrderEntity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MyCarFragment extends Fragment {

	private View fragmentView;
	private ListView listView;
	private PullToRefreshListView pullToRefreshView;
	private MyOrderAdapter adapter;
	private ArrayList<MyOrderEntity> list = new ArrayList<MyOrderEntity>();

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		System.out.println("oncreateview");
		fragmentView = inflater.inflate(R.layout.fragment_mycar, container,
				false);
		//System.out.println("oncreateview2");
		pullToRefreshView = (PullToRefreshListView) fragmentView
				.findViewById(R.id.pull_to_refresh_listview);
		pullToRefreshView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// Do work to refresh the list here.
						new GetDataTask().execute();
					}
					
				});
		listView = pullToRefreshView.getRefreshableView();
		for(int i = 0;i<5;i++){
			MyOrderEntity entity = new MyOrderEntity();
			list.add(entity);
		}
		adapter = new MyOrderAdapter(getActivity(), R.layout.my_order_item, list);
		listView.setAdapter(adapter);
		return fragmentView;
	}

	private class GetDataTask extends AsyncTask<Void, Void, String[]> {
		@Override
		protected void onPostExecute(String[] result) {
			// Call onRefreshComplete when the list has been refreshed.
			pullToRefreshView.onRefreshComplete();
			super.onPostExecute(result);
		}

		@Override
		protected String[] doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
			}
			return null;
		}
	}

}

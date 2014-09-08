package com.cettco.buycar.fragment;

import java.util.ArrayList;

import com.cettco.buycar.R;
import com.cettco.buycar.adapter.MyOrderAdapter;
import com.cettco.buycar.entity.MyOrderEntity;
import com.cettco.buycar.utils.HttpConnection;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class MyCarFragment extends Fragment {

	private View fragmentView;
	private ListView listView;
	private PullToRefreshListView pullToRefreshView;
	private MyOrderAdapter adapter;
	private ArrayList<MyOrderEntity> list = new ArrayList<MyOrderEntity>();
	private Button currentButton;
	private Button historyButton;
	
	//private array

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//System.out.println("oncreateview");
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
		for(int i = 0;i<0;i++){
			MyOrderEntity entity = new MyOrderEntity();
			list.add(entity);
		}
		adapter = new MyOrderAdapter(getActivity(), R.layout.my_order_item, list);
		listView.setAdapter(adapter);
		currentButton = (Button)fragmentView.findViewById(R.id.currentOrderBtn);
		historyButton = (Button)fragmentView.findViewById(R.id.cancledOrderBtn);
		currentButton.setOnClickListener(currentClickListener);
		historyButton.setOnClickListener(historyClickListener);
		return fragmentView;
	}
	
	private OnClickListener currentClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			currentButton.setBackgroundResource(R.drawable.my_order_selected_btn_style);
			currentButton.setTextColor(getResources().getColor(R.color.white));
			
			historyButton.setBackgroundResource(R.drawable.my_order_btn_style);
			historyButton.setTextColor(getResources().getColor(R.color.blue));
			
			//pullToRefreshView.st
			pullToRefreshView.setRefreshing();
			
		}
	};
	private OnClickListener historyClickListener =  new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			historyButton.setBackgroundResource(R.drawable.my_order_selected_btn_style);
			historyButton.setTextColor(getResources().getColor(R.color.white));
			
			currentButton.setBackgroundResource(R.drawable.my_order_btn_style);
			currentButton.setTextColor(getResources().getColor(R.color.blue));
			
			pullToRefreshView.setRefreshing();
		}
	};
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
//				String url = "";
//				HttpConnection.get(url, new JsonHttpResponseHandler(){});
				Thread.sleep(4000);
				MyOrderEntity entity = new MyOrderEntity();
				list.add(entity);
				//adapter.notifyDataSetChanged();
				Message message = new Message();
				message.what = 1;
				mHandler.sendMessage(message);
			} catch (InterruptedException e) {
			}
			return null;
		}
	}
	private Handler mHandler = new Handler(){  
        
        public void handleMessage(Message msg) {  
            switch (msg.what) {  
            case 1:  
                //updateTitle(); 
            	adapter.notifyDataSetChanged();
                break;  
            }  
        };  
    };

}

package com.cettco.buycar.activity;

import java.util.ArrayList;
import java.util.LinkedList;

import cn.trinea.android.common.view.DropDownListView;
import cn.trinea.android.common.view.DropDownListView.OnDropDownListener;

import com.cettco.buycar.R;
import com.cettco.buycar.adapter.DealerCommentAdapter;
import com.cettco.buycar.entity.DealerCommentEntity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DealerCommentActivity extends Activity{

	private ArrayList<DealerCommentEntity>   listItems;
    private DropDownListView     listView  = null;
    private DealerCommentAdapter adapter;
    private TextView addCommentTextView;
    private TextView titleTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dealer_comment);
		titleTextView = (TextView)findViewById(R.id.title_text);
		titleTextView.setText("评论");
		addCommentTextView = (TextView)findViewById(R.id.comment_add_textview);
		addCommentTextView.setOnClickListener(addCommentClick);
		listView = (DropDownListView)findViewById(R.id.comment_list_view);
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
        listItems = new ArrayList<DealerCommentEntity>();
        //listItems.addAll(Arrays.asList(mStrings));
        for(int i =0;i<10;i++){
        	DealerCommentEntity entity = new DealerCommentEntity();
        	listItems.add(entity);
        }
        System.out.println("list item size:"+listItems.size());
        adapter = new DealerCommentAdapter(this, R.layout.item_dealer_comment, listItems);
        listView.setAdapter(adapter);
	}
	 private class GetDataTask extends AsyncTask<Void, Void, String[]> {
		 
	        private boolean isDropDown;
	 
	        public GetDataTask(boolean isDropDown){
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
	                //listItems.addFirst("Added after drop down");
	                adapter.notifyDataSetChanged();
	                listView.onDropDownComplete();
	 
	                // should call onDropDownComplete function of DropDownListView at end of drop down complete.
//	                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
//	                listView.onDropDownComplete(getString(R.string.update_at)
//	                                            + dateFormat.format(new Date()));
	            } else {
	                //listItems.add("Added after on bottom");
	                adapter.notifyDataSetChanged();
	 
	                // should call onBottomComplete function of DropDownListView at end of on bottom complete.
	                listView.onBottomComplete();
	            }
	 
	            super.onPostExecute(result);
	        }
	    }
	 private OnClickListener addCommentClick = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(DealerCommentActivity.this, AddCommentActivity.class);
			startActivity(intent);
		}
	};
	public void exitClick(View view){
		//intent.putExtra("result", position);
//		int size = mycarColorAdapter.getIsSelected().size();
//		for(int i = 0;i<size;i++){
//			mycarColorAdapter.getIsSelected().get(i);
//		}
		//setResult(RESULT_OK, intent);
		this.finish();
	}
}

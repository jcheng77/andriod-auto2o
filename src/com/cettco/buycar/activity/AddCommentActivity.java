package com.cettco.buycar.activity;

import com.cettco.buycar.R;
import com.cettco.buycar.utils.MarkView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddCommentActivity extends Activity {

	private MarkView priceMarkView;
	private MarkView timeMarkView;
	private MarkView qualityMarkView;
	private Button submitButton;
	private EditText editText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_comment);
		priceMarkView = (MarkView)findViewById(R.id.add_comment_price_markview);
		priceMarkView.setLevel(0);

		timeMarkView = (MarkView)findViewById(R.id.add_comment_time_markview);
		timeMarkView.setLevel(0);

		qualityMarkView = (MarkView)findViewById(R.id.add_comment_quality_markview);
		qualityMarkView.setLevel(0);
		
		submitButton = (Button)findViewById(R.id.add_comment_submit_btn);
		submitButton.setOnClickListener(onClickListener);
		editText = (EditText)findViewById(R.id.add_comment_editText);

	}
	private OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			priceMarkView.getLevel();
		}
	};
	public void exitClick(View view){
		this.finish();
	}

}

package com.cettco.buycar.view;

import com.cettco.buycar.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.view.View.OnTouchListener;

public class SettingsItemView extends FrameLayout {

	private TextView textTextView;

	public SettingsItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.item_settings, this);
		textTextView = (TextView) findViewById(R.id.item_settings_text_textview);
		TypedArray array = context.obtainStyledAttributes(attrs,
				R.styleable.SettingsItemView);
		String myText = array.getString(R.styleable.SettingsItemView_myText)
				.toString();
		textTextView.setText(myText);
		array.recycle();
		setBackgroundColor(getResources().getColor(R.color.white));
		//setOnTouchListener(this);
	}
//
//	@Override
//	public boolean onTouch(View v, MotionEvent event) {
//		// TODO Auto-generated method stub
//		switch (event.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//			setBackgroundColor(getResources().getColor(R.color.gray));
//			break;
//		case MotionEvent.ACTION_UP:
//
//			// set color back to default
//			setBackgroundColor(getResources().getColor(R.color.white));
//			break;
//		case MotionEvent.ACTION_CANCEL:
//
//			// set color back to default
//			setBackgroundColor(getResources().getColor(R.color.white));
//			break;
//		case MotionEvent.ACTION_OUTSIDE:
//
//			// set color back to default
//			setBackgroundColor(getResources().getColor(R.color.white));
//			break;
//		}
//		return false;
//	}

}

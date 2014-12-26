package com.cettco.buycar.activity;

import java.sql.SQLException;
import java.util.Date;

import com.cettco.buycar.R;
import com.cettco.buycar.entity.CarTrimEntity;
import com.cettco.buycar.entity.OrderItemEntity;
import com.cettco.buycar.utils.db.DatabaseHelperOrder;
import com.cettco.buycar.utils.db.DatabaseHelperTrim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BargainPriceActivity extends Activity{
	private OrderItemEntity orderItemEntity = new OrderItemEntity();
	private TextView titleTextView;
	private Button nextButton;
	private EditText userNameEditText;
	private EditText priceEditText;
	private TextView guidePirceTextView;
	private TextView benefitPriceTextView;
	private LinearLayout invalidPriceLayout;
	private int order_id;
	private String trim_id;
	private double price;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bargain_price);
		order_id = getIntent().getIntExtra("order_id", -1);
		titleTextView = (TextView)findViewById(R.id.title_text);
		titleTextView.setText("设置期望成交价");
		nextButton = (Button)findViewById(R.id.bargain_price_nextstepbtn);
		nextButton.setOnClickListener(nextClickListener);
		priceEditText = (EditText) findViewById(R.id.activity_bargain_price_textview);
		priceEditText.setOnFocusChangeListener(priceOnFocusChangeListener);
		userNameEditText = (EditText) findViewById(R.id.activity_bargain_name_textview);
		invalidPriceLayout = (LinearLayout)findViewById(R.id.invalid_price_linearlayout);
		guidePirceTextView = (TextView)findViewById(R.id.activity_bargain_price_guide_price_textview);
		benefitPriceTextView = (TextView)findViewById(R.id.activity_bargain_price_benefit_price_textview);
		DatabaseHelperOrder orderHelper = DatabaseHelperOrder.getHelper(this);
		try {
			orderItemEntity = orderHelper.getDao().queryBuilder().where()
					.eq("order_id", order_id).queryForFirst();
			
			trim_id = orderItemEntity.getTrim_id();
			System.out.println("trim id:"+trim_id);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//String trim_id =
		DatabaseHelperTrim helperTrim = DatabaseHelperTrim
				.getHelper(this);
		try {
			CarTrimEntity trimEntity = helperTrim.getDao().queryBuilder().where()
					.eq("id",trim_id).queryForFirst();
			price = Double.parseDouble(trimEntity.getGuide_price());
			priceEditText.setText(String.format("%.1f", price));
			guidePirceTextView.setText(String.format("%.1f", price));
			//priceEditText.setText(String.valueOf(price));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private OnClickListener nextClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			double now = Double.parseDouble(priceEditText.getText().toString());
			if((price-now)/price>0.5){
				invalidPriceLayout.setVisibility(View.VISIBLE);
				Toast toast = Toast.makeText(BargainPriceActivity.this,
						"请填写合理裸车价", Toast.LENGTH_SHORT);
				toast.show();
				return;
			}
			String name =userNameEditText.getText().toString();
			if(name==null||name.equals("")){
				Toast toast = Toast.makeText(BargainPriceActivity.this,
						"请填写购车人姓名", Toast.LENGTH_SHORT);
				toast.show();
				return;
			}
			orderItemEntity.setTime(new Date());
			orderItemEntity.setName(userNameEditText.getText().toString());
			orderItemEntity.setPrice(priceEditText.getText().toString());
			DatabaseHelperOrder helper = DatabaseHelperOrder.getHelper(BargainPriceActivity.this);
			try {
				helper.getDao().update(orderItemEntity);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Intent intent = new Intent();
			intent.setClass(BargainPriceActivity.this, BargainActivity.class);
			intent.putExtra("order_id", order_id);
			startActivity(intent);
			
		}
	};
	private OnFocusChangeListener priceOnFocusChangeListener = new OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(View arg0, boolean arg1) {
			// TODO Auto-generated method stub
			
			double now = Double.parseDouble(priceEditText.getText().toString());
			benefitPriceTextView.setText(String.format("%.1f", price-now));
			if((price-now)/price>0.5){
				invalidPriceLayout.setVisibility(View.VISIBLE);
				return;
			}
			//if(userNameEditText.gettext)
		}
	};
	public void exitClick(View view) {
		this.finish();
	}
}

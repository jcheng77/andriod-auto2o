package com.cettco.buycar.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alipay.android.app.sdk.AliPay;
import com.alipay.android.msp.ExternalPartner;
import com.alipay.android.msp.Keys;
import com.alipay.android.msp.Result;
import com.alipay.android.msp.Rsa;
import com.cettco.buycar.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class AliPayActivity extends ActionBarActivity{

	public static final String TAG = "alipay-sdk";

	private static final int RQF_PAY = 1;

	private static final int RQF_LOGIN = 2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alipay);
	}
	private OnClickListener payClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			try {
				Log.i("ExternalPartner", "onItemClick");
				String info = getNewOrderInfo(0);
				String sign = Rsa.sign(info, Keys.PRIVATE);
				sign = URLEncoder.encode(sign,"UTF-8");
				info += "&sign=\"" + sign + "\"&" + getSignType();
				Log.i("ExternalPartner", "start pay");
				// start the pay.
				Log.i(TAG, "info = " + info);

				final String orderInfo = info;
				new Thread() {
					public void run() {
						AliPay alipay = new AliPay(AliPayActivity.this, mHandler);
						
						//设置为沙箱模式，不设置默认为线上环境
						//alipay.setSandBox(true);

						String result = alipay.pay(orderInfo);

						Log.i(TAG, "result = " + result);
						Message msg = new Message();
						msg.what = RQF_PAY;
						msg.obj = result;
						mHandler.sendMessage(msg);
					}
				}.start();

			} catch (Exception ex) {
				ex.printStackTrace();
				//Toast.makeText(ExternalPartner.this, R.string.remote_call_failed,
						//Toast.LENGTH_SHORT).show();
			}
		}
	};
	private String getNewOrderInfo(int position) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		sb.append("partner=\"");
		sb.append(Keys.DEFAULT_PARTNER);
		sb.append("\"&out_trade_no=\"");
		sb.append(getOutTradeNo());
		sb.append("\"&subject=\"");
		//sb.append(sProducts[position].subject);
		sb.append("\"&body=\"");
		//sb.append(sProducts[position].body);
		sb.append("\"&total_fee=\"");
		//sb.append(sProducts[position].price.replace("一口价:", ""));
		sb.append("\"&notify_url=\"");

		// 网址需要做URL编码
		sb.append(URLEncoder.encode("http://notify.java.jpxx.org/index.jsp","UTF-8"));
		sb.append("\"&service=\"mobile.securitypay.pay");
		sb.append("\"&_input_charset=\"UTF-8");
		sb.append("\"&return_url=\"");
		sb.append(URLEncoder.encode("http://m.alipay.com","UTF-8"));
		sb.append("\"&payment_type=\"1");
		sb.append("\"&seller_id=\"");
		sb.append(Keys.DEFAULT_SELLER);

		// 如果show_url值为空，可不传
		// sb.append("\"&show_url=\"");
		sb.append("\"&it_b_pay=\"1m");
		sb.append("\"");

		return new String(sb);
	}

	private String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
		Date date = new Date();
		String key = format.format(date);

		java.util.Random r = new java.util.Random();
		key += r.nextInt();
		key = key.substring(0, 15);
		Log.d(TAG, "outTradeNo: " + key);
		return key;
	}

	private String getSignType() {
		return "sign_type=\"RSA\"";
	}
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Result result = new Result((String) msg.obj);

			switch (msg.what) {
			case RQF_PAY:
			case RQF_LOGIN: {
				Toast.makeText(AliPayActivity.this, result.getResult(),
						Toast.LENGTH_SHORT).show();

			}
				break;
			default:
				break;
			}
		};
	};

	public static class Product {
		public String subject;
		public String body;
		public String price;
	}
}

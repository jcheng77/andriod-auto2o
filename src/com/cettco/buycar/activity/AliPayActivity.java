package com.cettco.buycar.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import org.apache.http.Header;

import com.alipay.android.msp.Keys;
import com.alipay.android.msp.Result;
import com.alipay.android.msp.Rsa;
import com.alipay.android.msp.SignUtils;
import com.alipay.sdk.app.PayTask;
import com.cettco.buycar.R;
import com.cettco.buycar.entity.OrderDetailEntity;
import com.cettco.buycar.entity.OrderItemEntity;
import com.cettco.buycar.utils.GlobalData;
import com.cettco.buycar.utils.HttpConnection;
import com.cettco.buycar.utils.MyApplication;
import com.cettco.buycar.utils.UserUtil;
import com.cettco.buycar.utils.db.DatabaseHelperOrder;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AliPayActivity extends Activity {

	public static final String TAG = "alipay-sdk";
	private static final int DATA_SUCCESS = 5;
	private static final int DATA_FAIL = 6;
	private Button submitButton;

	private int selection = 0;

	//private RelativeLayout webLayout;
	private RelativeLayout clientLayout;
	private CheckBox webCheckBox;
	private CheckBox clientCheckBox;
	
	private String tender_id;
	
	private TextView titleTextView;
	private OrderDetailEntity detailEntity;
	
	private TextView brandTextView;
	private TextView trimTextView;
	private ImageView carImageView;
	private OrderItemEntity orderItemEntity = new OrderItemEntity();
	
	private RelativeLayout progressLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alipay);
		progressLayout = (RelativeLayout)findViewById(R.id.progressbar_relativeLayout);
		//progressLayout.setVisibility(View.VISIBLE);
		brandTextView = (TextView)findViewById(R.id.alipay_car_brand_textview);
		trimTextView = (TextView)findViewById(R.id.alipay_car_trim_textview);
		carImageView = (ImageView)findViewById(R.id.alipay_car_img_imageview);
		titleTextView = (TextView)findViewById(R.id.title_text);
		titleTextView.setText("支付");
		//webCheckBox = (CheckBox) findViewById(R.id.alipay_web_checkbox);
		clientCheckBox = (CheckBox) findViewById(R.id.alipay_client_checkbox);
		clientLayout = (RelativeLayout) findViewById(R.id.alipay_client_layout);
		clientLayout.setOnClickListener(clientClickListener);

		//webLayout = (RelativeLayout) findViewById(R.id.alipay_web_layout);
		//webLayout.setOnClickListener(webClickListener);
		submitButton = (Button) findViewById(R.id.alipay_submit_btn);
		submitButton.setOnClickListener(payClickListener);
		submitButton.setVisibility(View.GONE);
		
		tender_id = getIntent().getStringExtra("tender_id");
		DatabaseHelperOrder orderHelper = DatabaseHelperOrder.getHelper(this);
		try {
			orderItemEntity = orderHelper.getDao().queryBuilder().where()
					.eq("id", tender_id).queryForFirst();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		getData();
	}

	private OnClickListener webClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			clientCheckBox.setChecked(false);
			webCheckBox.setChecked(true);
			selection=1;
		}
	};
	private OnClickListener clientClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			selection = 0;
			clientCheckBox.setChecked(true);
			webCheckBox.setChecked(false);
		}
	};

	private OnClickListener payClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			String bodyString= detailEntity.getBrand().getName()+" "+detailEntity.getMaker().getName()+" "+detailEntity.getModel().getName();
			pay("定金支付",bodyString,"99");
		}
	};

	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				Result resultObj = new Result((String) msg.obj);
				String resultStatus = resultObj.resultStatus;

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(AliPayActivity.this, "支付成功",
							Toast.LENGTH_SHORT).show();
					orderItemEntity.setState("qualified");
					DatabaseHelperOrder orderHelper = DatabaseHelperOrder
							.getHelper(AliPayActivity.this);
					try {
						orderHelper.getDao().update(orderItemEntity);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Intent intent = new Intent();
					// intent.putExtra("tenderId", id);
					intent.setClass(AliPayActivity.this,
							MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000” 代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(AliPayActivity.this, "支付结果确认中",
								Toast.LENGTH_SHORT).show();

					} else {
						Toast.makeText(AliPayActivity.this, "支付失败",
								Toast.LENGTH_SHORT).show();

					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				Toast.makeText(AliPayActivity.this, "检查结果为：" + msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			}
			case DATA_SUCCESS:{
				updateUI();
			}
			case DATA_FAIL:{
//				System.out.println("44444");
//				Toast toast = Toast.makeText(AliPayActivity.this, "获取详情失败", Toast.LENGTH_SHORT);
//				toast.show();
			}
			default:
				break;
			}
		};
	};
	private void updateUI(){
		brandTextView.setText(detailEntity.getBrand().getName()+" "+detailEntity.getMaker().getName()+" "+detailEntity.getModel().getName());
		trimTextView.setText(detailEntity.getTrim().getName());
		System.out.println("pic:"+detailEntity.getPic_url());
		MyApplication.IMAGE_CACHE.get(detailEntity.getPic_url(),carImageView);
		submitButton.setVisibility(View.VISIBLE);
	}

	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay(String subject,String body,String price) {
		String orderInfo = getOrderInfo(subject, body, price);
		String sign = sign(orderInfo);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();
		System.out.println("payInfo:"+payInfo);
		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(AliPayActivity.this);
				// 调用支付接口
				String result = alipay.pay(payInfo);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * check whether the device has authentication alipay account.
	 * 查询终端设备是否存在支付宝认证账户
	 * 
	 */
	public void check(View v) {
		Runnable checkRunnable = new Runnable() {

			@Override
			public void run() {
				PayTask payTask = new PayTask(AliPayActivity.this);
				boolean isExist = payTask.checkAccountIfExist();

				Message msg = new Message();
				msg.what = SDK_CHECK_FLAG;
				msg.obj = isExist;
				mHandler.sendMessage(msg);
			}
		};

		Thread checkThread = new Thread(checkRunnable);
		checkThread.start();

	}

	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(this);
		String version = payTask.getVersion();
		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	public String getOrderInfo(String subject, String body, String price) {
		// 合作者身份ID
		String orderInfo = "partner=" + "\"" + Keys.DEFAULT_PARTNER + "\"";

		// 卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + Keys.DEFAULT_SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + GlobalData.getBaseUrl()+"/deposits/alipay_app_notify"
				+ "\"";

		// 接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. 获取外部订单号
	 * 
	 */
	public String getOutTradeNo() {
//		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
//				Locale.getDefault());
//		Date date = new Date();
//		String key = format.format(date);
//
//		Random r = new Random();
//		key = key + r.nextInt();
//		key = key.substring(0, 15);
		//String key = UserUtil.getUserId(AliPayActivity.this)+" "+tender_id;
		return tender_id;
		//return key;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		return SignUtils.sign(content, Keys.PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}

	protected void getData() {
		// String url = GlobalData.getBaseUrl() + "/cars/list.json";
		// httpCache.clear();
		progressLayout.setVisibility(View.VISIBLE);
		String url = GlobalData.getBaseUrl() + "/tenders/" + tender_id + ".json";
		HttpConnection.setCookie(getApplicationContext());
		HttpConnection.get(url, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				System.out.println("fail");
				progressLayout.setVisibility(View.GONE);
				Message message = new Message();
				message.what = DATA_FAIL;
				mHandler.sendMessage(message);
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				System.out.println("seccuss");
				progressLayout.setVisibility(View.GONE);
				try {
					String result = new String(arg2, "UTF-8");
					System.out.println("result:" + result);
					Gson gson = new Gson();
					detailEntity = gson.fromJson(result,
							OrderDetailEntity.class);
					Message message = new Message();
					message.what = DATA_SUCCESS;
					mHandler.sendMessage(message);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
	}

	public void exitClick(View view) {
		this.finish();
	}
}

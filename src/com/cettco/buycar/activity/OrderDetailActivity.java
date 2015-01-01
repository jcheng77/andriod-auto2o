package com.cettco.buycar.activity;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import org.apache.http.Header;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.cettco.buycar.R;
import com.cettco.buycar.entity.CarTrimEntity;
import com.cettco.buycar.entity.OrderDetailEntity;
import com.cettco.buycar.utils.MyApplication;
import com.cettco.buycar.utils.GlobalData;
import com.cettco.buycar.utils.HttpConnection;
import com.cettco.buycar.utils.db.DatabaseHelperTrim;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OrderDetailActivity extends Activity {

	private MapView mMapView = null;
	private BaiduMap mBaiduMap;
	private ImageView qrImageView;
	private String tender_id;
	private OrderDetailEntity detailEntity;

	private LinearLayout carInfoLayout;
	private LinearLayout dealerInfoLayout;
	private LinearLayout dealerIntentionLayout;
	private LinearLayout intentionLayout;
	private LinearLayout qRcodeLayout;
	private TextView stateTextView;
	private ImageView carImageView;
	private TextView modelTextView;
	private TextView priceTextView;
	private TextView benefitTextView;
	private TextView pickupTimeTextView;
	private TextView licenseLocationTextView;
	private TextView gotLicenseTextView;
	private TextView loanTextView;
	private TextView trimTextView;
	private TextView colorTextView;

	private TextView dealerPhoneTextView;
	private TextView shopNameTextView;
	private TextView shopAddressTextView;

	private TextView titleTextView;

	private RelativeLayout progressLayout;
	private RelativeLayout nullDataLayout;

	// bid info
	private TextView insuranceTextView;
	private TextView purTaxTextView;
	private TextView licFeeTextView;
	private TextView miscFeeTextView;
	private TextView desTextView;
	
	private Button cancelButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_order_detail);
		titleTextView = (TextView) findViewById(R.id.title_text);
		titleTextView.setText("订单详情");
		cancelButton = (Button)findViewById(R.id.order_detail_cancel_btn);
		cancelButton.setOnClickListener(cancelClickListener);
		progressLayout = (RelativeLayout) findViewById(R.id.opacity_progressbar_relativeLayout);
		progressLayout.setVisibility(View.VISIBLE);
		nullDataLayout = (RelativeLayout) findViewById(R.id.null_data_relativeLayout);
		dealerPhoneTextView = (TextView) findViewById(R.id.order_detail_dealer_info_phone_textview);
		shopAddressTextView = (TextView) findViewById(R.id.order_detail_shop_info_address_textview);
		shopNameTextView = (TextView) findViewById(R.id.order_detail_shop_info_name_textview);
		carInfoLayout = (LinearLayout) findViewById(R.id.activity_order_detail_car_info);
		dealerInfoLayout = (LinearLayout) findViewById(R.id.activity_order_detail_dealer_info);
		dealerIntentionLayout = (LinearLayout) findViewById(R.id.activity_order_detail_dealer_intention);
		intentionLayout = (LinearLayout) findViewById(R.id.activity_order_detail_customer_intention);
		qRcodeLayout = (LinearLayout) findViewById(R.id.activity_order_detail_qrcode);
		stateTextView = (TextView) findViewById(R.id.order_detail_state);
		carImageView = (ImageView) findViewById(R.id.order_detail_car_imageview);
		modelTextView = (TextView) findViewById(R.id.order_detail_brandmakermodel_textview);
		priceTextView = (TextView) findViewById(R.id.order_detail_price_textview);
		benefitTextView= (TextView) findViewById(R.id.order_detail_benefit_textview);
		pickupTimeTextView = (TextView) findViewById(R.id.order_detail_pickup_time_textview);
		licenseLocationTextView = (TextView) findViewById(R.id.order_detail_license_location_textview);
		gotLicenseTextView = (TextView) findViewById(R.id.order_detail_got_licence_textview);
		loanTextView = (TextView) findViewById(R.id.order_detail_loan_option_textview);
		trimTextView = (TextView) findViewById(R.id.order_detail_trim_textview);
		colorTextView = (TextView) findViewById(R.id.order_detail_color_textview);
		// bid info
		insuranceTextView = (TextView) findViewById(R.id.order_detail_bid_insurance_textview);
		purTaxTextView = (TextView) findViewById(R.id.order_detail_bid_purchase_tax_textview);
		licFeeTextView = (TextView) findViewById(R.id.order_detail_bid_license_fee_textview);
		miscFeeTextView = (TextView) findViewById(R.id.order_detail_bid_misc_fee_textview);
		desTextView = (TextView) findViewById(R.id.order_detail_bid_description_textview);
		//
		tender_id = getIntent().getStringExtra("tender_id");
		mMapView = (MapView) findViewById(R.id.order_has_dealer_bmapView);
		mBaiduMap = mMapView.getMap();
		// 普通地图
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		// 定义Maker坐标点
		LatLng point = new LatLng(39.963175, 116.400244);
		// 构建Marker图标
		BitmapDescriptor bitmap = BitmapDescriptorFactory
				.fromResource(R.drawable.icon_marka);
		// 构建MarkerOption，用于在地图上添加Marker
		OverlayOptions option = new MarkerOptions().position(point)
				.icon(bitmap);
		// 在地图上添加Marker，并显示
		mBaiduMap.addOverlay(option);

		qrImageView = (ImageView) findViewById(R.id.order_has_dealer_qr_image);
		//getData();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
		MobclickAgent.onResume(this);
		getData();
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}
	
	private OnClickListener cancelClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(OrderDetailActivity.this, CancleReasonActivity.class);
			intent.putExtra("tender_id", tender_id);
			startActivity(intent);
		}
	};

	protected void getData() {
		// String url = GlobalData.getBaseUrl() + "/cars/list.json";
		// httpCache.clear();
		String url = GlobalData.getBaseUrl() + "/tenders/" + tender_id + ".json";
		HttpConnection.setCookie(getApplicationContext());
		HttpConnection.get(url, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				System.out.println("fail");
				Message message = new Message();
				message.what = 2;
				mHandler.sendMessage(message);
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				System.out.println("seccuss");
				try {
					String result = new String(arg2, "UTF-8");
					System.out.println("result:" + result);
					Gson gson = new Gson();
					detailEntity = gson.fromJson(result,
							OrderDetailEntity.class);
					// Type listType = new TypeToken<ArrayList<DealerEntity>>()
					// {
					// }.getType();
					// dealerList = new Gson().fromJson(result, listType);
					// System.out.println("size:"+dealerList.size());
					Message message = new Message();
					message.what = 1;
					mHandler.sendMessage(message);
					// System.out.println("result:"+result);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
	}

	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				System.out.println("update");
				nullDataLayout.setVisibility(View.GONE);
				progressLayout.setVisibility(View.GONE);
				// dealerListAdapter.updateList(dealerList);
				updateUI();
				break;
			case 2:
				nullDataLayout.setVisibility(View.VISIBLE);
				progressLayout.setVisibility(View.GONE);
				Toast toast = Toast.makeText(OrderDetailActivity.this,
						"获取订单详情失败", Toast.LENGTH_SHORT);
				toast.show();
				break;
			}
		};
	};

	private void updateUI() {
		String state = detailEntity.getState();
		if (state.equals("qualified")) {
			stateTextView.setText("已支付,等待4s店接受报价");
			cancelButton.setVisibility(View.VISIBLE);
			qRcodeLayout.setVisibility(View.GONE);
		} else if (state.equals("deal_made")) {
			stateTextView.setText("已有4s店接受报价");
			cancelButton.setVisibility(View.VISIBLE);
			dealerInfoLayout.setVisibility(View.VISIBLE);
			dealerIntentionLayout.setVisibility(View.VISIBLE);
			dealerPhoneTextView.setText(detailEntity.getDealer().getPhone());
			shopAddressTextView.setText(detailEntity.getShop().getAddress());
			shopNameTextView.setText(detailEntity.getShop().getName());
			//bid info
			insuranceTextView.setText(detailEntity.getBid().getInsurance()+" 元");
			purTaxTextView.setText(detailEntity.getBid().getPurchase_tax()+" 元");
			licFeeTextView.setText(detailEntity.getBid().getLicense_fee()+" 元");
			miscFeeTextView.setText(detailEntity.getBid().getMisc_fee()+" 元");
			desTextView.setText(detailEntity.getBid().getDescription()+" 元");
			qRcodeLayout.setVisibility(View.VISIBLE);
			try {
				qrImageView.setImageBitmap(Create2DCode(detailEntity
						.getVerfiy_code()));
			} catch (WriterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (state.equals("final_deal_closed")) {
			cancelButton.setVisibility(View.GONE);
			stateTextView.setText("最终成交");
			dealerInfoLayout.setVisibility(View.VISIBLE);
			dealerIntentionLayout.setVisibility(View.VISIBLE);
			dealerPhoneTextView.setText(detailEntity.getDealer().getPhone());
			shopAddressTextView.setText(detailEntity.getShop().getAddress());
			shopNameTextView.setText(detailEntity.getShop().getName());
			//bid info
			insuranceTextView.setText(detailEntity.getBid().getInsurance()+" 元");
			purTaxTextView.setText(detailEntity.getBid().getPurchase_tax()+" 元");
			licFeeTextView.setText(detailEntity.getBid().getLicense_fee()+" 元");
			miscFeeTextView.setText(detailEntity.getBid().getMisc_fee()+" 元");
			desTextView.setText(detailEntity.getBid().getDescription()+" 元");
			qRcodeLayout.setVisibility(View.GONE);
			// try {
			// qrImageView.setImageBitmap(Create2DCode(detailEntity
			// .getVerfiy_code()));
			// } catch (WriterException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

		}else if(state.equals("canceled")){
			cancelButton.setVisibility(View.GONE);
			stateTextView.setText("订单已取消");
			qRcodeLayout.setVisibility(View.GONE);
		}
		MyApplication.IMAGE_CACHE.get(detailEntity.getPic_url(), carImageView);
		String brandName = detailEntity.getBrand().getName();
		String makerName = detailEntity.getMaker().getName();
		String modelName = detailEntity.getModel().getName();
		String trimName = detailEntity.getTrim().getName();
		StringBuffer colorBuffer = new StringBuffer("");
		for(int i=0;i<detailEntity.getColors().size();i++){
			colorBuffer.append(detailEntity.getColors().get(i).getName()+",");
		}
		colorBuffer.deleteCharAt(colorBuffer.length()-1);
		// detailEntity.
		colorTextView.setText(colorBuffer.toString());
		modelTextView.setText(brandName + "(" + makerName + ") " + modelName);
		trimTextView.setText(trimName);
		priceTextView.setText(detailEntity.getPrice()+"万");
		DatabaseHelperTrim helperTrim = DatabaseHelperTrim
				.getHelper(this);
		try {
			CarTrimEntity trimEntity= helperTrim.getDao().queryBuilder().where()
					.eq("id",detailEntity.getTrim_id()).queryForFirst();
			double guide = Double.valueOf(trimEntity.getGuide_price());
			double myPrice = Double.valueOf(detailEntity.getPrice());
			benefitTextView.setText(String.format("%.1f", guide-myPrice)+"万");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// pickupTimeTextView.setText(detailEntity.getPickup_time());
		if (detailEntity.getPickup_time().equals("0")) {
			pickupTimeTextView.setText("7天");
		} else if (detailEntity.getPickup_time().equals("1")) {
			pickupTimeTextView.setText("14天");
		} else if (detailEntity.getPickup_time().equals("2")) {
			pickupTimeTextView.setText("21天后");
		}
		licenseLocationTextView.setText(detailEntity.getLicense_location());
		if (detailEntity.getGot_licence().equals("0")) {
			gotLicenseTextView.setText("否");
		} else if (detailEntity.getGot_licence().equals("1")) {
			gotLicenseTextView.setText("是");
		}
		if (detailEntity.getLoan_option().equals("0")) {
			loanTextView.setText("贷款");
		} else if (detailEntity.getLoan_option().equals("1")) {
			loanTextView.setText("全款");
		} else if (detailEntity.getLoan_option().equals("2")) {
			loanTextView.setText("均可");
		}

	}

	public Bitmap Create2DCode(String str) throws WriterException {
		// 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
		BitMatrix matrix = new MultiFormatWriter().encode(str,
				BarcodeFormat.QR_CODE, 300, 300);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		// 二维矩阵转为一维像素数组,也就是一直横着排了
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (matrix.get(x, y)) {
					pixels[y * width + x] = 0xff000000;
				}

			}
		}

		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		// 通过像素数组生成bitmap,具体参考api
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}

	protected OnClickListener localMapBtnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(Intent.ACTION_VIEW);
			Uri uri = Uri.parse("geo:39.922840,116.3543240,北京市西城区阜外大街2号万通大厦");
			intent.setData(uri);
			// intent.setPackage("com.baidu.BaiduMap");
			if (intent.resolveActivity(getPackageManager()) != null) {
				startActivity(intent);
			} else {
				uri = Uri.parse("http://api.map.baidu.com/geocoder?address="
						+ "上海虹桥机场" + "&output=html");
				Intent intent2 = new Intent(Intent.ACTION_VIEW, uri);
				intent2.setData(uri);
				startActivity(intent2);
			}
		}
	};

	public void exitClick(View view) {
		this.finish();
	}

}

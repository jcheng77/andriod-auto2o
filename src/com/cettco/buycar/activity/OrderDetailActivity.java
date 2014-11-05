package com.cettco.buycar.activity;

import java.io.UnsupportedEncodingException;

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
import com.cettco.buycar.entity.OrderDetailEntity;
import com.cettco.buycar.utils.Data;
import com.cettco.buycar.utils.GlobalData;
import com.cettco.buycar.utils.HttpConnection;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OrderDetailActivity extends Activity {

	private MapView mMapView = null;
	private BaiduMap mBaiduMap;
	private ImageView qrImageView;
	private String id;
	private OrderDetailEntity detailEntity;

	private LinearLayout carInfoLayout;
	private LinearLayout dealerInfoLayout;
	private LinearLayout intentionLayout;
	private LinearLayout qRcodeLayout;
	private TextView stateTextView;
	private ImageView carImageView;
	private TextView modelTextView;
	private TextView priceTextView;
	private TextView pickupTimeTextView;
	private TextView licenseLocationTextView;
	private TextView gotLicenseTextView;
	private TextView loanTextView;
	private TextView trimTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_order_detail);
		carInfoLayout = (LinearLayout) findViewById(R.id.activity_order_detail_car_info);
		dealerInfoLayout = (LinearLayout) findViewById(R.id.activity_order_detail_dealer_info);
		intentionLayout = (LinearLayout) findViewById(R.id.activity_order_detail_customer_intention);
		qRcodeLayout = (LinearLayout) findViewById(R.id.activity_order_detail_qrcode);
		stateTextView = (TextView)findViewById(R.id.order_detail_state);
		carImageView = (ImageView)findViewById(R.id.order_detail_car_imageview);
		modelTextView = (TextView)findViewById(R.id.order_detail_brandmakermodel_textview);
		priceTextView = (TextView)findViewById(R.id.order_detail_price_textview);
		pickupTimeTextView = (TextView)findViewById(R.id.order_detail_pickup_time_textview);
		licenseLocationTextView = (TextView)findViewById(R.id.order_detail_license_location_textview);
		gotLicenseTextView = (TextView)findViewById(R.id.order_detail_got_licence_textview);
		loanTextView = (TextView)findViewById(R.id.order_detail_loan_option_textview);
		trimTextView = (TextView)findViewById(R.id.order_detail_trim_textview);
		id = getIntent().getStringExtra("id");
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
		getData();
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
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}

	protected void getData() {
		// String url = GlobalData.getBaseUrl() + "/cars/list.json";
		// httpCache.clear();
		String url = GlobalData.getBaseUrl() + "/tenders/" + id + ".json";
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
				// dealerListAdapter.updateList(dealerList);
				updateUI();
				break;
			case 2:
				Toast toast = Toast.makeText(OrderDetailActivity.this, "获取订单详情失败", Toast.LENGTH_SHORT);
				toast.show();
				break;
			}
		};
	};

	private void updateUI() {
		String state = detailEntity.getState();
		if (state.equals("qualified")) {
			stateTextView.setText("已支付,等待4s店接受报价");
		} else if (state.equals("deal_made")) {
			stateTextView.setText("已有4s店接受报价");
			dealerInfoLayout.setVisibility(View.VISIBLE);

		} else if (state.equals("final_deal_closed")) {
			stateTextView.setText("最终成交");
			dealerInfoLayout.setVisibility(View.VISIBLE);
			qRcodeLayout.setVisibility(View.VISIBLE);
			try {
				qrImageView.setImageBitmap(Create2DCode(detailEntity
						.getVerfiy_code()));
			} catch (WriterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		System.out.println("pic:"+detailEntity.getPic_url());
		Data.IMAGE_CACHE.get(detailEntity.getPic_url(),carImageView);
		String brandName= detailEntity.getBrand().getName();
		String makerName = detailEntity.getMaker().getName();
		String modelName = detailEntity.getModel().getName();
		String trimName = detailEntity.getTrim().getName();
		modelTextView.setText(brandName+"("+makerName+") "+modelName);
		trimTextView.setText(trimName);
		priceTextView.setText(detailEntity.getPrice());
		pickupTimeTextView.setText(detailEntity.getPickup_time());
		licenseLocationTextView.setText(detailEntity.getLicense_location());
		gotLicenseTextView.setText(detailEntity.getGot_licence());
		loanTextView.setText(detailEntity.getLoan_option());
		
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

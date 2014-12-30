package com.cettco.buycar.service;

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.cettco.buycar.activity.SignInActivity;
import com.cettco.buycar.entity.User;
import com.cettco.buycar.entity.UserEntity;
import com.cettco.buycar.utils.GlobalData;
import com.cettco.buycar.utils.HttpConnection;
import com.cettco.buycar.utils.UserUtil;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

public class RegisterBaiduId extends IntentService{

	public RegisterBaiduId(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		String userId = intent.getStringExtra("userId");
		String channelId = intent.getStringExtra("channelId");
		postData(userId, channelId);
	}
	private void postData(String userId,String channelId){
		String url = GlobalData.getBaseUrl()+"/devices.json";
		JSONObject deviceJsonObject = new JSONObject();
		JSONObject json = new JSONObject();
		try {
			deviceJsonObject.put("baidu_user_id", userId);
			deviceJsonObject.put("baidu_channel_id", channelId);		
			json.put("type", "baidu_push");
			json.put("device", deviceJsonObject);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		Gson gson = new Gson();
        StringEntity entity = null;
        try {
			entity = new StringEntity(json.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        HttpConnection.setCookie(getApplicationContext());
		HttpConnection.post(this, url, null, entity, "application/json;charset=utf-8", new JsonHttpResponseHandler(){

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
				System.out.println("error");
				System.out.println("statusCode:"+statusCode);
				System.out.println("headers:"+headers);
//				for(int i = 0;i<headers.length;i++){
//					System.out.println(headers[i]);
//				}
				System.out.println("response:"+errorResponse);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				System.out.println("success");
				System.out.println("statusCode:"+statusCode);
				
				for(int i=0;i<headers.length;i++){
					System.out.println(headers[0]);
				}
				System.out.println("response:"+response);					
				
			}
			
		});
	}
	
}

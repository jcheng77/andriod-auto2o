package com.cettco.buycar.utils;
import android.content.Context;

import com.loopj.android.http.*;
public class HttpConnection {
	private static AsyncHttpClient client = new AsyncHttpClient();

	static{
		client.setTimeout(2000);
		client.addHeader("Content-Type", "application/json");
	}
	  public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
	      client.get(url, params, responseHandler);
	      //client.po
	  }

	  public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
	      client.post(url, params, responseHandler);
	  }
	  
	  public static void get(String url, AsyncHttpResponseHandler responseHandler){
		  client.get(url, responseHandler);
	  }
	  
	  public static void post(String url, AsyncHttpResponseHandler responseHandler) {
	      client.post(url, responseHandler);
	  }
	  public static void post(Context context,String url,org.apache.http.Header[] headers,org.apache.http.HttpEntity entity,String contentType,AsyncHttpResponseHandler responseHandler){
		  
		  client.post(context, url, headers, entity, contentType, responseHandler);
	  }
}
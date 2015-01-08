package com.cettco.buycar.utils;
import java.util.List;

import android.content.Context;

import com.loopj.android.http.*;
public class HttpConnection {
	private static AsyncHttpClient client = new AsyncHttpClient();

	static{
		client.setTimeout(8000);
		client.addHeader("Content-Type", "application/json;charset=utf-8");
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
	  public static void get(android.content.Context context,
                java.lang.String url,
                org.apache.http.Header[] headers,
                RequestParams params,
                ResponseHandlerInterface responseHandler){
		  client.get(context, url, headers, params, responseHandler);
	  }
	  public static void post(String url, AsyncHttpResponseHandler responseHandler) {
	      client.post(url, responseHandler);
	  }
	  public static void post(Context context,String url,org.apache.http.Header[] headers,org.apache.http.HttpEntity entity,String contentType,AsyncHttpResponseHandler responseHandler){
		  
		  client.post(context, url, headers, entity, contentType, responseHandler);
	  }
	  public static void setCookie(Context context){
		  PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
		  client.setCookieStore(myCookieStore);
	  }
	  public static AsyncHttpClient getClient(){
		  return client;
	  }
}
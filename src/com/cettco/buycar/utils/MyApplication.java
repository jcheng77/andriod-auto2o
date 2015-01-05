package com.cettco.buycar.utils;

import cn.trinea.android.common.service.impl.ImageCache;
import cn.trinea.android.common.util.CacheManager;

import com.baidu.frontia.FrontiaApplication;

import android.app.Application;

public class MyApplication extends Application{
	public static final ImageCache IMAGE_CACHE = CacheManager.getImageCache();
	//public static CookieStore myCookieStore = new PersistentCookieStore(getac);
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		//baseUrl="http://baidu.com/";
		super.onCreate();
		FrontiaApplication.initFrontiaApplication(this);
	}
}

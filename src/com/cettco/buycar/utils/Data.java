package com.cettco.buycar.utils;

import cn.jpush.android.api.JPushInterface;
import cn.trinea.android.common.service.impl.ImageCache;
import cn.trinea.android.common.util.CacheManager;
import android.app.Application;

public class Data extends Application{
	public static final ImageCache IMAGE_CACHE = CacheManager.getImageCache();
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		//baseUrl="http://baidu.com/";
		super.onCreate();
		JPushInterface.init(this);
	}
}
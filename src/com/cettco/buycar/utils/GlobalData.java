package com.cettco.buycar.utils;

public class GlobalData {
	private static String baseUrl = "http://54.68.52.232";

	public static String getBaseUrl() {
		return baseUrl;
	}

	public static void setBaseUrl(String baseUrl) {
		GlobalData.baseUrl = baseUrl;
	}
}

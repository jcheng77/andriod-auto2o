package com.cettco.buycar.utils;

public class GlobalData {
	private static String baseUrl = "http://staging.pailixing.com";

	public static String getBaseUrl() {
		return baseUrl;
	}

	public static void setBaseUrl(String baseUrl) {
		GlobalData.baseUrl = baseUrl;
	}
}

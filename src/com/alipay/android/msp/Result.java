package com.alipay.android.msp;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.util.Log;

public class Result {
	
	private static final Map<String, String> sResultStatus;

	private String mResult;
	
	String resultStatus = null;
	String memo = null;
	String result = null;
	boolean isSignOk = false;

	public Result(String result) {
		this.mResult = result;
	}

	static {
		sResultStatus = new HashMap<String, String>();
		sResultStatus.put("9000", "鎿?浣滄�鍔�");
		sResultStatus.put("4000", "绯荤粺寮傚父");
		sResultStatus.put("4001", "鏁版?牸寮?涓?姝ｇ‘");
		sResultStatus.put("4003", "璇ョ敤鎴风粦瀹氱殑鏀粯瀹?璐︽埛琚喕缁撴垨涓?鍏?璁告敮浠�");
		sResultStatus.put("4004", "璇ョ敤鎴峰凡瑙ｉ櫎缁戝畾");
		sResultStatus.put("4005", "缁戝畾澶辫触鎴栨病鏈夌粦瀹�");
		sResultStatus.put("4006", "璁㈠?曟敮浠樺け璐�");
		sResultStatus.put("4010", "閲?鏂扮粦瀹氳处鎴�");
		sResultStatus.put("6000", "鏀粯鏈?鍔℃鍦ㄨ繘琛屽?囩骇鎿?浣�");
		sResultStatus.put("6001", "鐢ㄦ埛涓��栨秷鏀粯鎿?浣�");
		sResultStatus.put("7001", "缃戦〉鏀粯澶辫触");
	}

	public  String getResult() {
		String src = mResult.replace("{", "");
		src = src.replace("}", "");
		return getContent(src, "memo=", ";result");
	}

	public  void parseResult() {
		
		try {
			String src = mResult.replace("{", "");
			src = src.replace("}", "");
			String rs = getContent(src, "resultStatus=", ";memo");
			if (sResultStatus.containsKey(rs)) {
				resultStatus = sResultStatus.get(rs);
			} else {
				resultStatus = "鍏朵粬閿欒";
			}
			resultStatus += "(" + rs + ")";

			memo = getContent(src, "memo=", ";result");
			result = getContent(src, "result=", null);
			isSignOk = checkSign(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private  boolean checkSign(String result) {
		boolean retVal = false;
		try {
			JSONObject json = string2JSON(result, "&");

			int pos = result.indexOf("&sign_type=");
			String signContent = result.substring(0, pos);

			String signType = json.getString("sign_type");
			signType = signType.replace("\"", "");

			String sign = json.getString("sign");
			sign = sign.replace("\"", "");

			if (signType.equalsIgnoreCase("RSA")) {
				retVal = Rsa.doCheck(signContent, sign, Keys.PUBLIC);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("Result", "Exception =" + e);
		}
		Log.i("Result", "checkSign =" + retVal);
		return retVal;
	}

	public  JSONObject string2JSON(String src, String split) {
		JSONObject json = new JSONObject();

		try {
			String[] arr = src.split(split);
			for (int i = 0; i < arr.length; i++) {
				String[] arrKey = arr[i].split("=");
				json.put(arrKey[0], arr[i].substring(arrKey[0].length() + 1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return json;
	}

	private  String getContent(String src, String startTag, String endTag) {
		String content = src;
		int start = src.indexOf(startTag);
		start += startTag.length();

		try {
			if (endTag != null) {
				int end = src.indexOf(endTag);
				content = src.substring(start, end);
			} else {
				content = src.substring(start);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return content;
	}
}

package com.cettco.buycar.activity;

import com.cettco.buycar.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

public class WebActivity extends Activity {

	private TextView titleTextView;
	private WebView myWebView;
	private ProgressBar progressbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);
		titleTextView = (TextView) findViewById(R.id.title_text);
		Intent intent = getIntent();
		String name = intent.getStringExtra("name");
		titleTextView.setText(name);
		progressbar = (ProgressBar) findViewById(R.id.activity_mywebview_progressbar);
		myWebView = (WebView) findViewById(R.id.activity_web_webview);
		WebSettings settings = myWebView.getSettings();
		settings.setLoadWithOverviewMode(true);
		settings.setJavaScriptEnabled(true);
		myWebView.setWebChromeClient(new MyWebChromeClient());
		myWebView.setWebViewClient(new MyWebViewClient());
		myWebView.loadUrl("http://www.baidu.com");
	}

	private class MyWebChromeClient extends WebChromeClient {

		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			// TODO Auto-generated method stub
			super.onProgressChanged(view, newProgress);
			System.out.println("projgress:"+newProgress);
			progressbar.setProgress(newProgress);
			if (newProgress == 1) {
				progressbar.setVisibility(View.GONE);
			} else {
				progressbar.setVisibility(View.VISIBLE);
			}
		}
		// @Override
		// public boolean shouldOverrideUrlLoading(WebView view, String url) {
		// view.loadUrl(url);
		// return true;
		// }
		//
		// @Override
		// public void onPageStarted(WebView view, String url, Bitmap favicon) {
		// // TODO Auto-generated method stub
		// super.onPageStarted(view, url, favicon);
		// }
		//
		// @Override
		// public void onPageFinished(WebView view, String url) {
		// // TODO Auto-generated method stub
		// super.onPageFinished(view, url);
		// }
	}

	private class MyWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			// TODO Auto-generated method stub
			super.onPageStarted(view, url, favicon);
			progressbar.setVisibility(View.VISIBLE);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onPageFinished(view, url);
			progressbar.setVisibility(View.GONE);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
			myWebView.goBack();
			return true;
		} else if ((keyCode == KeyEvent.KEYCODE_BACK) && !myWebView.canGoBack()) {
			this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void exitClick(View v) {
		this.finish();
	}

}

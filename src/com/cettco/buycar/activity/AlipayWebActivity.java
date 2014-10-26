package com.cettco.buycar.activity;

import com.cettco.buycar.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AlipayWebActivity extends Activity{

	private WebView mWebView;
	private ProgressDialog mProgressDialog;
    private static final String TAG = "AlipayWebActivity";
    protected static final int PAY_SUCCESS = 20;
    protected static final int LOAD_FINISH_CODE = 40;
    private static final int DEFAULT_CODE = 50;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alipay_web);
		mWebView = (WebView) findViewById(R.id.alipay_web_webview);
        //mWebView.setBackgroundColor(0);
        initial();
	}
	@SuppressLint({ "SetJavaScriptEnabled", "HandlerLeak" })
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void initial() {
        String url = getIntent().getStringExtra("url");
        //LogUtil.i(TAG, url);
        WebSettings webSettings = mWebView.getSettings();
        mWebView.requestFocus();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setFocusable(true);
        webSettings.setBuiltInZoomControls(true);

        mWebView.setScrollBarStyle(0);
//        if (MobileUtil.getMobileVersion()>11) {        
//            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        }
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int newProgress) {
                //LogUtil.i(TAG, "newProgress: " + newProgress);
                if (newProgress == 100 && mProgressDialog!=null) {
                    mProgressDialog.dismiss();
                }
                super.onProgressChanged(view, newProgress);
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //LogUtil.i(TAG, "URL : " + url);
                //LogUtil.i(TAG, "URL : " + url.contains("&cmd=success&"));
            	System.out.println("override:"+url);
                loadUrl(view, url);
                return true;//停止在当前界面
            }
            public void onPageFinished(WebView view, String url) {
                //LogUtil.i(TAG, "onPageFinished" + url);
                //trade_status ： TRADE_FINISHED，成功之后处理
            	System.out.println("finish:"+url);

                super.onPageFinished(view, url);

            }
            @Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				System.out.println("start:"+url);
			}
        });

        loadUrl(mWebView, url);
    }

    @SuppressLint("HandlerLeak")
    private Handler mUihandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case PAY_SUCCESS:
                clearCache();
                //FactoryUtil.getInstance().exit();            
                break;
            case LOAD_FINISH_CODE:
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                break;
            case DEFAULT_CODE:
                showProgressDialog(AlipayWebActivity.this,
                        R.string.dialog_loading);
            }
            super.handleMessage(msg);
        }
    };

    private void loadUrl(final WebView webView, final String url) {

        mUihandler.sendEmptyMessage(DEFAULT_CODE);
        System.out.println("laod start");
        webView.loadUrl(url);
        System.out.println("laod end");

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //LogUtil.i(TAG, "can goback:" + mWebView.canGoBack());
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showProgressDialog(Context context, int message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage(context.getText(message));
            mProgressDialog.show();
        }
    }

    private void clearCache() {
        mWebView.clearCache(true);
        mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        finish();
    }

    private class MyOnClickListener implements OnClickListener{
        @Override
        public void onClick(View v) {
            int id = v.getId();
//            if (id==R.id.iv_quit) {
//                finish();
//            }
        }        
    }

	public void exitClick(View view){
		this.finish();
	}
}

package com.smartwatch.demo.activity;

import com.smartwatch.demo.entity.ParaType;
import com.smartwatch.demo.utils.SmartWatchJSObject;
import com.smartwatch.demo.widget.LoadDialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

@SuppressLint("SetJavaScriptEnabled") 
public class WebViewActivity extends Activity {

	private LoadDialog loadDialog;
	private SmartWatchJSObject smartWatchJSObject;
	private String url = ParaType.SmartWatchUrl;
	public static final int SCAN_TWO_DIMENSION_CODE  = 0x01;
	private WebView webView; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_web_view);
		if (getIntent().hasExtra("url")){
			url = getIntent().getStringExtra("url");
		}
			

		 webView = (WebView) findViewById(R.id.webView_smart_watch);

		webViewSetting(webView);
		ImageButton back = (ImageButton) findViewById(R.id.imageButton_titleBarLeft);
		TextView tvTitle = (TextView) findViewById(R.id.textView_titleBarTitle);
		tvTitle.setText("手环调试页面");
		back.setOnClickListener(new MyOnClickListener());
		try {
			if(!url.equals("") && (url.contains("http://") || url.contains("https://"))){
				webView.loadUrl(url);// 接入Url
			}else {
				url = ParaType.SmartWatchUrl;
				webView.loadUrl(url);
			}
		} catch (Exception e) {
			url = ParaType.SmartWatchUrl;
			webView.loadUrl(url);
		}
		
		

		smartWatchJSObject = new SmartWatchJSObject(this);
		webView.addJavascriptInterface(smartWatchJSObject,
				SmartWatchJSObject.JS_NAME);
	}

	private void webViewSetting(final WebView wv) {
		WebSettings ws = wv.getSettings();
		ws.setJavaScriptEnabled(true);// 可用JS
		ws.setBlockNetworkImage(true);
		// ws.setGeolocationEnabled(true);
		wv.setScrollBarStyle(0);
		wv.setBackgroundColor(0xffeeeeee);
		wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		wv.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(final WebView view,
					final String url) {
				loadurl(view, url);// 载入网页
				return true;
			}

		});

		wv.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				if (loadDialog == null) {
					loadDialog = new LoadDialog(WebViewActivity.this);
				}
				loadDialog.show();
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				loadDialog.dismiss();
			}

		});
	}

	public void loadurl(final WebView view, final String surl) {
		new Thread() {
			public void run() {
				try {
					view.loadUrl(surl);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			WebViewActivity.this.finish();
		}

	}
	 
 
	 @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == SCAN_TWO_DIMENSION_CODE && resultCode == Activity.RESULT_OK
				&& null != data) {
			String  reStr=data.getStringExtra("RESULT");
			String url ="javascript:getWatchNO('"+reStr+"')";
			webView.loadUrl(url);
		}
	}
	
}


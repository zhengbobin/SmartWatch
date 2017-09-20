package com.smartwatch.demo.activity;

import com.smartwatch.demo.entity.ParaType;
import com.smartwatch.demo.utils.SmartWatchJSObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

public class WebViewUrlActivity extends Activity implements
		View.OnClickListener {
	private EditText editTextUrl;
	private String url;
	private String murl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_web_view_url);
		editTextUrl = (EditText) findViewById(R.id.editText_url);
		findViewById(R.id.button_webView).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_webView:
			Intent intent = getIntent();
			String name = intent.getStringExtra("button");
			if (name.equals("button_scanTwoDimensionCode")) {
				url = editTextUrl.getText().toString().trim();
				if (!url.equals("")
						&& (url.contains("http://") || url.contains("https://"))) {
					SmartWatchJSObject smartWatchJSObject = new SmartWatchJSObject(
							this);
					System.out.println("url======>(1)" + url);
					smartWatchJSObject.scanTwoDimensionCode();
					
				} else {
					murl = ParaType.SmartWatchUrl;
					url = murl;
					SmartWatchJSObject smartWatchJSObject = new SmartWatchJSObject(
							this);
					smartWatchJSObject.scanTwoDimensionCode();
					System.out.println("murl===>" + url);

				}
				break;
			} else if (name.equals("button_webView")) {
				url = editTextUrl.getText().toString().trim();
				Intent intent3 = new Intent(this, WebViewActivity.class);
				if (!url.equals("")
						&& (url.contains("http://") || url.contains("https://"))) {

					intent3.putExtra("url", url);
					System.out.println("url======>(2)" + url);
					startActivity(intent3);

				} else {
					murl = ParaType.SmartWatchUrl;
					url = murl;

					intent3.putExtra("url", url);
					System.out.println("url======>(two)" + url);
					startActivity(intent3);
				}

				break;
			}

		}

	}

}

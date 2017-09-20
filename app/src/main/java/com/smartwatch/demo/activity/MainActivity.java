package com.smartwatch.demo.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class MainActivity extends Activity implements View.OnClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		findViewById(R.id.button_client).setOnClickListener(this);
		findViewById(R.id.button_webView).setOnClickListener(this);
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.button_client:
				startActivity(new Intent(this, ClientActivity.class));
				break;
				
			case R.id.button_webView:
				Intent intent=new Intent(this, WebViewUrlActivity.class);
				intent.putExtra("button", "button_webView");
				startActivity(intent);
				break;

		}

	}

}

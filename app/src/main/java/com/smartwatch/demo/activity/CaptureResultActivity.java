package com.smartwatch.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class CaptureResultActivity extends Activity {
	
	private ImageButton topBackBtn;
	private TextView topTitleTv;
	private TextView contentTv;
	private String content = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_capture_result);
		
		content = getIntent().getStringExtra("SCAN_RESULT");
		
		topBackBtn=(ImageButton)findViewById(R.id.imageButton_titleBarLeft);
		topTitleTv=(TextView)findViewById(R.id.textView_titleBarTitle);
		topTitleTv.setText("扫描结果");
		contentTv = (TextView) findViewById(R.id.capture_result_content);
		if (content != null) {
			contentTv.setText(content);
			if (content.startsWith("http") || content.startsWith("www")) {
				Intent viewIntent = new Intent(Intent.ACTION_VIEW,
						Uri.parse(content));
				startActivity(viewIntent);

			}
		}
		topBackBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}


}

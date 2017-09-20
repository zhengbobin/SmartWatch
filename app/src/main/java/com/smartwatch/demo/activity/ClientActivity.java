package com.smartwatch.demo.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.smartwatch.demo.entity.ParaType;
import com.smartwatch.demo.utils.SmartWatchJSObject;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class ClientActivity extends Activity implements View.OnClickListener {

	private Button buttonRecorder;
	// 录音的路径
	private String savePath;
	// 语音动弹文件名
	private String fileName;
	// 是否正在录音中
	private boolean isRecording = false;
	// 是否超时
	private boolean IS_OVERTIME = false;
	private File amrFile;// 语音文件
	private DisplayMetrics dm;

	private SmartWatchJSObject smartWatchJSObject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_client);
		initView();
	}

	private void initView() {
		findViewById(R.id.button_callPhone).setOnClickListener(this);
		findViewById(R.id.button_sendSms).setOnClickListener(this);
		buttonRecorder = (Button) findViewById(R.id.button_recorder);
		buttonRecorder.setOnTouchListener(recorderTouchListener);
		findViewById(R.id.button_scanTwoDimensionCode).setOnClickListener(this);
		smartWatchJSObject = new SmartWatchJSObject(this);

		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
	}

	// 语音录制按钮触摸事件
	private View.OnTouchListener recorderTouchListener = new View.OnTouchListener() {
		long startVoiceT = 0;// 开始时间
		long endVoiceT = 0;// 结束世间
		int startY = 0;// 开始的Y
		byte state = ParaType.RECORDER_STATE_RECARDING;

		public boolean onTouch(View v, MotionEvent event) {
			try {
				// 超时
				if (IS_OVERTIME) {
					smartWatchJSObject.endRecord();
					buttonRecorder
							.setBackgroundResource(R.drawable.chat_voice_center_btn_normal);
					buttonRecorder.setText("按住  说话");
					// 状态为取消
					if (state == ParaType.RECORDER_STATE_CANALE
							|| state == ParaType.TWEET_PUBING) {
						smartWatchJSObject.deleteVoiceFile();
						amrFile = null;
						if (state == ParaType.RECORDER_STATE_CANALE)
							IS_OVERTIME = false;
						return false;
					}
					if (state != ParaType.TWEET_PUBING) {
						state = ParaType.TWEET_PUBING;

						// 更新界面，发送语音

					}
					return false;
				}
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// isRecording = true;
					savePath = ParaType.AUDIO_SAVE_PATH;
					MediaPlayer md = MediaPlayer.create(ClientActivity.this,
							R.raw.notificationsound);
					md.start();
					// 提示音播放完开始录音
					md.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
						public void onCompletion(MediaPlayer mp) {
							smartWatchJSObject.startRecord(fileName);
						}
					});
					// startRecorder(fileName);
					IS_OVERTIME = false;
					buttonRecorder
							.setBackgroundResource(R.drawable.chat_voice_center_btn_pressed);
					buttonRecorder.setText("松开  结束");
					// 按下时记录录音文件名
					String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
							.format(new Date());
					fileName = timeStamp + ".amr";// 语音动弹命名
					startY = (int) event.getY();
					startVoiceT = System.currentTimeMillis();
					break;
				case MotionEvent.ACTION_MOVE:
					int tempY = (int) event.getY();
					if (Math.abs(startY - tempY) > dm.heightPixels / 3) {
						// 取消
						state = ParaType.RECORDER_STATE_CANALE;
						// showRecarderStatus(ParaType.RECORDER_STATE_CANALE);
					} else {
						// 录音
						state = ParaType.RECORDER_STATE_RECARDING;
						// showRecarderStatus(ParaType.RECORDER_STATE_RECARDING);
					}
					break;
				case MotionEvent.ACTION_UP:
					// isRecording = false;
					buttonRecorder
							.setBackgroundResource(R.drawable.chat_voice_center_btn_normal);
					buttonRecorder.setText("按住  说话");
					endVoiceT = System.currentTimeMillis();
					long voiceT = endVoiceT - startVoiceT;
					// 停止录音
					if (isRecording) {
						smartWatchJSObject.endRecord();
						isRecording = false;
					}
					// 录音小于最小时间
					if (voiceT < ParaType.RECORDER_TIME_MINTIME * 1000
							|| state == ParaType.RECORDER_STATE_CANALE) {
						smartWatchJSObject.deleteVoiceFile();
						amrFile = null;
						if (voiceT < ParaType.RECORDER_TIME_MINTIME * 1000)
							// showRecarderStatus(ParaType.RECORDER_STATE_SHORT);
							if (state == ParaType.RECORDER_STATE_CANALE)
								// mvTimeMess.setVisibility(View.INVISIBLE);
								return false;
					}
					// 更新界面，发送语音

					break;
				default:
					break;
				}

			} catch (Exception e) {
				// TODO: handle exception
			}
			return false;
		}

	};

	@Override
	public void onClick(View v) {
		String phone = "15918716549";
		String maxRecordTime = "15";
		String smsContent = "第一!绝对不意气用事!" + "第二!绝对不漏判任何一件坏事!" + "第三!绝对裁判的公正漂亮!"
				+ "裁判机器人蜻蜓队长前来进见!" + "这场争夺战由我来做裁判!" + "蜻蜓队长跑马灯!" + "跑马灯!我的灯呢？"
				+ "卧槽我的灯呢？" + "有没有谁看见我的灯？" + "草泥马的武器大师你别跑！！！！";
		switch (v.getId()) {
		case R.id.button_callPhone:
			smartWatchJSObject.callPhone(phone);
			break;

		case R.id.button_sendSms:
			smartWatchJSObject.sendSms(phone, smsContent);
			break;

		case R.id.button_scanTwoDimensionCode:
			 Intent intent=new Intent(this,WebViewUrlActivity.class);
			 intent.putExtra("button","button_scanTwoDimensionCode" );
			 startActivity(intent);
			// smartWatchJSObject.scanTwoDimensionCode();
			//SmartWatchJSObject smartWatchJSObject = new SmartWatchJSObject(this);
			//smartWatchJSObject.scanTwoDimensionCode();
			break;

		}

	}

}

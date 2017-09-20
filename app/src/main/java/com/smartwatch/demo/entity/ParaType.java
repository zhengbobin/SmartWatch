package com.smartwatch.demo.entity;

import java.io.File;

import android.os.Environment;


/**
 * 参数类型定义
 */
public class ParaType {
		
	public static final String SmartWatchUrl = "http://www.liangfeilong.win:9001/guanjia/main.html?name=18520891739&password=123456";
		
		public static final String FilePath = Environment.getExternalStorageDirectory().getAbsolutePath()
			+ File.separator;
		public final static String AUDIO_SAVE_PATH = FilePath + "Voice/";
		// 录制
		public final static byte RECORDER_STATE_RECARDING = 0x0;
		// 录制时间太短
		public final static byte RECORDER_STATE_SHORT = 0x01;
		// 发布中
		public final static byte TWEET_PUBING = 0X02;
		// 取消发布
		public final static byte RECORDER_STATE_CANALE = 0x03;
		// 普通动弹
		public final static byte TWEET_TYPE_CONTENT = 0X04;
		// 语音动弹
		public final static byte TWEET_TYPE_VOICE = 0X05;

		// 语音最短时间(秒)
		public final static int RECORDER_TIME_MINTIME = 1;
		// 语音最长时间(秒)
		public final static int RECORDER_TIME_MAXTIME = 60;
}

package com.smartwatch.demo.utils;


import java.io.File;
import java.io.Serializable;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.smartwatch.demo.activity.CaptureActivity;
import com.smartwatch.demo.entity.ParaType;

/**
 * 智能手环网页交互对象
 *
 * @author android_xi
 * @date 2015/8/17.
 */

@SuppressLint("SetJavaScriptEnabled")
public class SmartWatchJSObject implements Serializable {
	
	private Activity activity;
	private AudioRecordUtils recordUtils = new AudioRecordUtils();// 录音工具类
	// 录音的路径
	private String savePath = ParaType.AUDIO_SAVE_PATH;
	// 语音动弹文件名
	private String fileName;
	// 是否正在录音中
	private boolean isRecording = false;
	// 是否超时
	private boolean IS_OVERTIME = false;
	private Handler mHandler = new Handler();
	
	private File amrFile;// 语音文件
	public static final String JS_NAME = "smartWatchJSObject";
	//发送短信使用
	SmsManager smsManager = SmsManager.getDefault();
	
	
	public static final int SCAN_TWO_DIMENSION_CODE  = 0x01; 
	 
	
	public SmartWatchJSObject(Activity activity) {
        this.activity = activity;
    }
	
    /**
     * 拨打电话
     * @param phone 手机号码
     */
    @JavascriptInterface
    public void callPhone(String phone) {
    	if(!phone.equals("")) {
    		Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + StringUtils.deleteNotNum(phone)));
            activity.startActivity(intent);
        }else {
            Toast.makeText(activity, "手机号码不存在", Toast.LENGTH_SHORT).show();
        }
    }

    
  
    
    
    /**
     * 发送短信
     *
     * @param phone 手机号码
     */
    /*@JavascriptInterface
    public void sendSms(String phone, String smsContent) {
    	Uri smsToUri = Uri.parse("smsto:" + phone);
    	Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
    	intent.putExtra("sms_body", smsContent);
    	activity.startActivity(intent);
    }*/
    @JavascriptInterface
    public void sendSms(String phone, String smsContent) {
    	
    	try {
    		if(smsContent.length() > 70) {
                List<String> contents = smsManager.divideMessage(smsContent);
                for(String sms : contents) {
                    smsManager.sendTextMessage(phone, null, sms, null, null);
                }
            } else {
             smsManager.sendTextMessage(phone, null, smsContent, null, null);
            }
    		Toast.makeText(activity, "短信已发送", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Toast.makeText(activity, "短信未发送", Toast.LENGTH_SHORT).show();
		}
    }
    

    /**
     * 开始录音
     *
     * @param fileName 录音文件名
     */
    @JavascriptInterface
    public void startRecord(String fileName) {
    	Log.e("-savePath-", savePath);
    	this.fileName = fileName;
    	if (StringUtils.isEmpty(savePath))
			return;
		File savedir = new File(savePath);
		if (!savedir.exists()) {
			savedir.mkdirs();
		}
		recordUtils.start(savePath, fileName);
		isRecording = true;
		Toast.makeText(activity, "开始录音", Toast.LENGTH_SHORT).show();
    }
    
    /**
     * 停止录音
     */
    @JavascriptInterface
    public void endRecord() {
    	recordUtils.stop();
		amrFile = new File(savePath, this.fileName);
		Toast.makeText(activity, "停止录音", Toast.LENGTH_SHORT).show();
    }

    /**
     * 删除录音文件
     */
    @JavascriptInterface
    public void deleteVoiceFile() {
    	File newPath = new File(savePath + this.fileName);
		boolean res = newPath.delete();
		Toast.makeText(activity, "删除录音文件", Toast.LENGTH_SHORT).show();
    }

    /**
     * 扫二维码
     *
     */
    @JavascriptInterface
    public void scanTwoDimensionCode() {
    	Intent intent = new Intent(activity, CaptureActivity.class);
        intent.putExtra(CaptureActivity.SCAN_INTENT_TYPE, CaptureActivity.MAIN_SCAN_TYPE);
        activity. startActivityForResult(intent,SCAN_TWO_DIMENSION_CODE);
    } 
    
    
    
}

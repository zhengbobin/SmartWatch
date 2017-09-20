package com.smartwatch.demo.widget;

import com.smartwatch.demo.activity.R;

import android.app.Activity;
import android.app.Dialog;

/**
 * 自定义进度对话框
 */
public class LoadDialog extends Dialog {

	public LoadDialog(Activity context) {
		super(context, R.style.ProgressDialog);
		this.setContentView(R.layout.progress_dialog);

		// 禁止点击activity空白处就dismiss对话框
		this.setCanceledOnTouchOutside(false);
	}
}

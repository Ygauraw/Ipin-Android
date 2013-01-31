package com.ipin.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

public class Tips {

	public static void showToastTips(Context context, String tips) {
		Toast.makeText(context, tips, 500).show();
	}

	public static void exitAlertTips(final Context context) {
		new AlertDialog.Builder(context).setTitle("系统提示").setMessage("确定要退出吗")
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				}).setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								System.exit(0);
							}
						}).show();
		return;
	}
}

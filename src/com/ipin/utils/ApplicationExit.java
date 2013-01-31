package com.ipin.utils;

import java.util.Iterator;

import com.ipin.application.IpinApplication;

import android.app.Activity;

public class ApplicationExit {
	
	public static void exit(IpinApplication application) {
		Iterator<Activity> it = application.getActivityList().iterator();
		while (it.hasNext()) {
			it.next().finish();
		}
	}
}

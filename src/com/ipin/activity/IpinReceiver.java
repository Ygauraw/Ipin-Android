package com.ipin.activity;

import com.ipin.utils.IpinMessageConst;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class IpinReceiver extends BroadcastReceiver implements IpinMessageConst{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
//		if (intent.getAction().toString().equals(IPIN_MESSAGE)) {
//			Intent returnActivityIntent = new Intent(context, Index.class);
//			returnActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			context.startActivity(returnActivityIntent);
//		}
	}

}

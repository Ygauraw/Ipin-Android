package com.ipin.activity;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class IpinService extends Service {

	public static final String IPIN_USER_INFO_ID = "IPIN_USER_INFO";
	
	private Handler objHandler = new Handler() {
	};
	private int intCounter = 0;

	private Runnable mTasks = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			intCounter++;
			Log.i("HIPPO", "Counter:" + Integer.toString(intCounter));
//			white(true){
//				
//			}
			objHandler.postDelayed(mTasks, 1000);
		}
		
	};

	@Override
	public void onStart(Intent intent, int startId){
		objHandler.postDelayed(mTasks, 1000);
		super.onStart(intent, startId);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate(){
		super.onCreate();
	}

	@Override
	public void onDestroy(){
		objHandler.removeCallbacks(mTasks);
		super.onDestroy();
	}
	
}

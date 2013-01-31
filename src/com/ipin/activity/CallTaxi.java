package com.ipin.activity;

import com.ipin.application.IpinApplication;
import com.ipin.softwaremenu.SoftwareMenu;
import com.ipin.utils.ApplicationExit;
import com.ipin.utils.IpinNotification;
import com.ipin.utils.PostActivityConst;
import com.ipin.utils.PostActivitySwitcher;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.TabHost;
import android.widget.AdapterView.OnItemClickListener;

public class CallTaxi extends Activity implements OnItemClickListener,
		PostActivityConst {

	private static final String TAG = "CallTaxi";

	// ͼ�������ť
	private NotificationManager notificationManager = null;
	// �����˵�ѡ��
	private SoftwareMenu softwareMenu = null;
	// ҳ��ת����
	private Intent intent = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.calltaxi);
		IpinApplication application = (IpinApplication) this.getApplication();
		application.getActivityList().add(this);
		notificationManager = (NotificationManager) this
				.getSystemService(android.content.Context.NOTIFICATION_SERVICE);

		// ��ǩ�ؼ�����
		TabHost mTabHost = (TabHost) findViewById(R.id.calltaxi_tabhost);
		mTabHost.setup();
		mTabHost.addTab(mTabHost.newTabSpec("urgentcalltaxi").setIndicator(
				"�����ٵ�").setContent(R.id.caltaxi_urgentcalltext));
		mTabHost.addTab(mTabHost.newTabSpec("booktaxi").setIndicator("ԤԼ��ʿ")
				.setContent(R.id.caltaxi_booktaxi));
		mTabHost.addTab(mTabHost.newTabSpec("calltaxitrad")
				.setIndicator("ʹ��˵��").setContent(R.id.calltaxi_trad));

		softwareMenu = new SoftwareMenu(CallTaxi.this);
		softwareMenu.getMenuGrid().setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("menu");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (softwareMenu.isInitialDialog()) {
			softwareMenu.showDialog();
		} else {
			softwareMenu.initialAndShowDialog();
		}
		return false;
	}

	// �˵��������
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int itemNumber, long longNumber) {
		int tag;
		tag = itemNumber;
		switch (itemNumber) {
		case ITEM_CALLTAXI:
			tag = itemNumber + 100;
			break;
		case ITEM_SETTING:

			break;
		case ITEM_BACKLOGIN:
			((IpinApplication) CallTaxi.this.getApplication()).logoutUser();
			break;
		case ITEM_EXIT:
			ApplicationExit.exit((IpinApplication) CallTaxi.this
					.getApplication());
			return;
		}
		intent = PostActivitySwitcher.getSoftwareSwitchIntent(this, tag);
		if (intent == null) {
			return;
		} else {
			startActivity(intent);
			finish();
		}
	}

	// ���ؼ��������
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			IpinApplication application = (IpinApplication) CallTaxi.this
					.getApplication();
			startActivity(IpinNotification.setNotification(application.getUser().getUserAccount(), this,
					notificationManager));
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	// ��ֹ��Ļ�л�ˢ��
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// land
		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			// port
		}
	}
}

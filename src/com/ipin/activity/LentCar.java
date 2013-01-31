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

public class LentCar extends Activity implements OnItemClickListener,
		PostActivityConst {

	private static final String TAG = "LentCar";
	
	// 图标监听按钮
	private NotificationManager notificationManager = null;
	// 创建菜单选项
	private SoftwareMenu softwareMenu = null;
	// 页面转换器
	private Intent intent = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lentcar);
		IpinApplication application = (IpinApplication)this.getApplication();
		application.getActivityList().add(this);
		notificationManager = (NotificationManager) this
		.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		
		// 标签控件控制
		TabHost mTabHost = (TabHost) findViewById(R.id.lentcar_tabhost);
		mTabHost.setup();
		mTabHost.addTab(mTabHost.newTabSpec("lentcarapply")
				.setIndicator("包车申请").setContent(R.id.lentcar_apply));
		mTabHost.addTab(mTabHost.newTabSpec("lentcartrad").setIndicator("使用说明")
				.setContent(R.id.lentcar_introduce));

		softwareMenu = new SoftwareMenu(LentCar.this);
		softwareMenu.getMenuGrid().setOnItemClickListener(this);
	}

	// 创建MENU选项
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("menu");
		return super.onCreateOptionsMenu(menu);
	}

	// 创建MENU监听
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (softwareMenu.isInitialDialog()) {
			softwareMenu.showDialog();
		} else {
			softwareMenu.initialAndShowDialog();
		}
		return false;
	}

	// 菜单点击监听
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int itemNumber, long longNumber) {
		int tag;
		tag = itemNumber;
		switch (itemNumber) {
		case ITEM_LENTCAR:
			tag = itemNumber + 100;
			break;
		case ITEM_SETTING:

			break;
		case ITEM_BACKLOGIN:
			((IpinApplication)LentCar.this.getApplication()).logoutUser();
			break;
		case ITEM_EXIT:
			ApplicationExit.exit((IpinApplication)LentCar.this.getApplication());
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

	// 返回键点击监听
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			IpinApplication application = (IpinApplication) LentCar.this
					.getApplication();
			startActivity(IpinNotification.setNotification(application.getUser().getUserAccount(), this, notificationManager));
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	// 防止屏幕切换刷新
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

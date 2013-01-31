package com.ipin.activity;

import com.ipin.application.IpinApplication;
import com.ipin.softwaremenu.SoftwareMenu;
import com.ipin.utils.ApplicationExit;
import com.ipin.utils.IpinNotification;
import com.ipin.utils.PostActivityConst;
import com.ipin.utils.PostActivitySwitcher;
import com.ipin.utils.InitTools;
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
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.AdapterView.OnItemClickListener;

public class Travel extends Activity implements OnItemClickListener,
		PostActivityConst {

	private static final String TAG = "Travel";
	
	// 图标监听按钮
	private NotificationManager notificationManager = null;
	// 创建菜单选项
	private SoftwareMenu softwareMenu = null;
	// 页面转换器
	private Intent intent = null;
	
	// 列表页
	private ListView travelView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.travel);
		IpinApplication application = (IpinApplication)this.getApplication();
		application.getActivityList().add(this);
		notificationManager = (NotificationManager) this
		.getSystemService(android.content.Context.NOTIFICATION_SERVICE);

		// 标签控件控制
		TabHost mTabHost = (TabHost) findViewById(R.id.screen_chat_tabhost);
		mTabHost.setup();
		mTabHost.addTab(mTabHost.newTabSpec("travelview").setIndicator("旅游景点")
				.setContent(R.id.travel_view));
		mTabHost.addTab(mTabHost.newTabSpec("travelapply").setIndicator("旅游申请")
				.setContent(R.id.travel_apply));
		mTabHost.addTab(mTabHost.newTabSpec("travelintroduce").setIndicator(
				"旅游功能介绍").setContent(R.id.travel_introduce));

		// 创建菜单选项
		softwareMenu = new SoftwareMenu(Travel.this);
		softwareMenu.getMenuGrid().setOnItemClickListener(this);

		// 初始化旅游景点
		travelView = (ListView) findViewById(R.id.travel_view);
		travelView
				.addFooterView(InitTools.initUpdateButton(this, "TravelView"));
		travelView.setAdapter(InitTools.initAdapter(this, "travelinfo"));
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
		// TODO Auto-generated method stub
		intent = new Intent();
		int tag;
		tag = itemNumber;
		switch (itemNumber) {
		case ITEM_TRAVEL:
			tag = itemNumber + 100;
			break;
		case ITEM_SETTING:

			break;
		case ITEM_BACKLOGIN:
			((IpinApplication)Travel.this.getApplication()).logoutUser();
			break;
		case ITEM_EXIT:
			ApplicationExit.exit((IpinApplication)Travel.this.getApplication());
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
			IpinApplication application = (IpinApplication) Travel.this
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

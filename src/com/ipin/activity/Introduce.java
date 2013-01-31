package com.ipin.activity;

import com.ipin.application.IpinApplication;
import com.ipin.logindialogmenu.LoginDialogMenu;
import com.ipin.utils.ApplicationExit;
import com.ipin.utils.InitTools;
import com.ipin.utils.PreActivityConst;
import com.ipin.utils.PreActivitySwitcher;
import com.ipin.utils.Tips;

import android.app.Activity;
import android.content.Intent;
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

public class Introduce extends Activity implements OnItemClickListener,PreActivityConst{
	// 日志标记
	private static final String TAG = "Introduce";

	// 创建菜单选项
	public LoginDialogMenu loginDialogMenu = null;
	// 页面转换器
	private Intent intent = null;
	// 团队成员介绍
	private ListView teamIntroduce = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.introduce);
		IpinApplication application = (IpinApplication)this.getApplication();
		application.getActivityList().add(this);

		// 标签控件控制
		TabHost mTabHost = (TabHost) findViewById(R.id.introduce_tabhost);
		mTabHost.setup();
		mTabHost.addTab(mTabHost.newTabSpec("introducesoftware").setIndicator(
				"软件介绍").setContent(R.id.introduce_software));
		mTabHost.addTab(mTabHost.newTabSpec("introducecompany").setIndicator(
				"公司介绍").setContent(R.id.introduce_company));
		mTabHost.addTab(mTabHost.newTabSpec("introduceteam").setIndicator(
				"团队介绍").setContent(R.id.introduce_team_listview));

		loginDialogMenu = new LoginDialogMenu(Introduce.this);
		loginDialogMenu.getMenuGrid().setOnItemClickListener(this);

		teamIntroduce = (ListView) findViewById(R.id.introduce_team_listview);
		teamIntroduce.setAdapter(InitTools.initAdapter(this, "teammember"));
	}

	// 创建MENU选项
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.i(TAG, "onCreateOptionsMenu");
		menu.add("menu");
		return super.onCreateOptionsMenu(menu);
	}

	// 创建MENU监听
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		Log.i(TAG, "onMenuOpened");
		if (loginDialogMenu.isInitialDialog()) {
			loginDialogMenu.showDialog();
		} else {
			loginDialogMenu.initialAndShowDialog();
		}
		return false;
	}

	// 菜单点击监听
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int itemNumber, long longNumber) {
		int tag;
		tag = itemNumber;
		switch (tag) {
		case ITEM_INTRODUCE:
			tag = tag + 100;
			break;
		case ITEM_SETTING: 
			break;
		
		case ITEM_EXIT:
			ApplicationExit.exit((IpinApplication)Introduce.this.getApplication());
			return;
		}
		intent = PreActivitySwitcher.getSoftwareSwitchIntent(this, tag);
		if (intent == null) {
			return;
		} else {
			startActivity(intent);
			finish();
		}
	}

	// 返回按钮监听器
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.i(TAG, "onKeyDown " + event.getCharacters());
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Tips.exitAlertTips(this);
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}
}

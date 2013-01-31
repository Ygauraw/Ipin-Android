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
	
	// ͼ�������ť
	private NotificationManager notificationManager = null;
	// �����˵�ѡ��
	private SoftwareMenu softwareMenu = null;
	// ҳ��ת����
	private Intent intent = null;
	
	// �б�ҳ
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

		// ��ǩ�ؼ�����
		TabHost mTabHost = (TabHost) findViewById(R.id.screen_chat_tabhost);
		mTabHost.setup();
		mTabHost.addTab(mTabHost.newTabSpec("travelview").setIndicator("���ξ���")
				.setContent(R.id.travel_view));
		mTabHost.addTab(mTabHost.newTabSpec("travelapply").setIndicator("��������")
				.setContent(R.id.travel_apply));
		mTabHost.addTab(mTabHost.newTabSpec("travelintroduce").setIndicator(
				"���ι��ܽ���").setContent(R.id.travel_introduce));

		// �����˵�ѡ��
		softwareMenu = new SoftwareMenu(Travel.this);
		softwareMenu.getMenuGrid().setOnItemClickListener(this);

		// ��ʼ�����ξ���
		travelView = (ListView) findViewById(R.id.travel_view);
		travelView
				.addFooterView(InitTools.initUpdateButton(this, "TravelView"));
		travelView.setAdapter(InitTools.initAdapter(this, "travelinfo"));
	}

	// ����MENUѡ��
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("menu");
		return super.onCreateOptionsMenu(menu);
	}

	// ����MENU����
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

	// ���ؼ��������
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

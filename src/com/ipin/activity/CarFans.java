package com.ipin.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.ipin.application.IpinApplication;
import com.ipin.softwaremenu.SoftwareMenu;
import com.ipin.utils.ApplicationExit;
import com.ipin.utils.InitTools;
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
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.AdapterView.OnItemClickListener;

public class CarFans extends Activity implements OnItemClickListener,
		PostActivityConst {

	private static final String TAG = "CarFans";
	
	// ͼ�������ť
	private NotificationManager notificationManager = null;
	// �����˵�ѡ��
	private SoftwareMenu softwareMenu = null;
	// ҳ��ת����
	private Intent intent = null;

//	private ListView carFansGroup = null;
	private ListView carFans = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.carfans);
		IpinApplication application = (IpinApplication)this.getApplication();
		application.getActivityList().add(this);
		notificationManager = (NotificationManager) this
		.getSystemService(android.content.Context.NOTIFICATION_SERVICE);

		// ��ǩ�ؼ�����
		TabHost mTabHost = (TabHost) findViewById(R.id.carfans_tabhost);
		mTabHost.setup();
		mTabHost.addTab(mTabHost.newTabSpec("main").setIndicator("�������")
				.setContent(R.id.carfans_recentcarfans));
		mTabHost.addTab(mTabHost.newTabSpec("information").setIndicator("����")
				.setContent(R.id.carfans_carfans));
		mTabHost.addTab(mTabHost.newTabSpec("tips").setIndicator("��Ⱥ")
				.setContent(R.id.carfans_cargroups));
		mTabHost.addTab(mTabHost.newTabSpec("sharecarinfo")
				.setIndicator("ƴ����Ϣ").setContent(R.id.carfans_sharecarinfo));

		// �����˵�ѡ��
		softwareMenu = new SoftwareMenu(CarFans.this);
		softwareMenu.getMenuGrid().setOnItemClickListener(this);
		
		carFans = (ListView)findViewById(R.id.carfans_carfans);
		carFans.setAdapter(InitTools.initAdapter(this, "carfans"));
		carFans.setOnItemClickListener(new CarFansListener());
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
		int tag;
		tag = itemNumber;
		switch (itemNumber) {
		case ITEM_CARFANS:
			tag = itemNumber + 100;
			break;
		case ITEM_SETTING:

			break;
		case ITEM_BACKLOGIN:
			((IpinApplication)CarFans.this.getApplication()).logoutUser();
			break;
		case ITEM_EXIT:
			ApplicationExit.exit((IpinApplication)CarFans.this.getApplication());
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
	
	class CarFansListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			new Thread() {
				public void run() {
					Socket socket = null;
					BufferedReader in = null;
					PrintWriter out = null;
					try {
						socket = new Socket("192.168.202.1", 9080);
						in = new BufferedReader(new InputStreamReader(socket
								.getInputStream()));
						out = new PrintWriter(socket.getOutputStream(), true);
						out.println("DOW");
//						System.out.println("Today is: " + in.readLine());
						Log.i(TAG, "Today is: " + in.readLine());
						out.println("DOY");
//						System.out.println("This year is: " + in.readLine());
						Log.i(TAG, "This year is: " + in.readLine());
//						byte[] b = new byte[2048];
//						String msg = new String(b, 0, System.in.read(b));
//						out.println("FREE " + msg);
//						System.out.println("The response is: " + in.readLine());
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						Log.i(TAG, e.getMessage());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						Log.i(TAG, e.getMessage());
					} finally {
						try {
							if (in != null) {
								in.close();
							}
							if (out != null) {
								out.close();
							}
							if (socket != null) {
								socket.close();
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							Log.i(TAG, e.getMessage());
						}
					}
				}
			}.start();
		}
		
	}

	// ���ؼ��������
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			IpinApplication application = (IpinApplication) CarFans.this
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

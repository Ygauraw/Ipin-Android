package com.ipin.activity;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.ipin.application.IpinApplication;
import com.ipin.beans.User;
import com.ipin.softwaremenu.SoftwareMenu;
import com.ipin.utils.ApplicationExit;
import com.ipin.utils.HttpOperator;
import com.ipin.utils.IpinMessageConst;
import com.ipin.utils.IpinNotification;
import com.ipin.utils.NetworkConst;
import com.ipin.utils.PostActivityConst;
import com.ipin.utils.PostActivitySwitcher;
import com.ipin.utils.InitTools;
import com.ipin.utils.HandlerConst;
import com.ipin.utils.ServerAddressParams;
import com.ipin.utils.Tips;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.AdapterView.OnItemClickListener;

public class Index extends Activity implements OnItemClickListener,
		PostActivityConst, HandlerConst, IpinMessageConst {

	private static final String TAG = "Index";

	// 拼车tips数据数据获取URL
	private static final String PHONESHARECARTIPSP = "http://"
			+ ServerAddressParams.IP
			+ ":8080/IpinServer/getPhoneSharecartip.do";
	// 拼车消息数据数据获取URL
	// private static final String PHONESHARECARTIPSP = "http://" +
	// ServerAddressParams.IP +":8080/IpinServer/getPhoneSharecartip.do";

	// 全局保存变量
	private IpinApplication application = null;
	// 图标监听按钮
	private NotificationManager notificationManager = null;
	// 创建菜单选项
	private SoftwareMenu softwareMenu = null;
	// 页面转换器
	private Intent intent = null;

	// 列表页
	private ListView sharecarInfo = null;
	private ListView sharecarTips = null;

	// 页面组件
	private RotateAnimation tireAnimation = null;
	private ImageView tire = null;
	private LinearLayout userLocation = null;
	private LinearLayout userFeedBack = null;
	private LinearLayout userPublicSCI = null;
	private Button sharecarTipsFooterView = null;
	private ProgressDialog progressDialog = null;

	Handler indexHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle date = new Bundle();
			switch (msg.what) {
			case NetworkConst.SCT_UPDATE_START:
				progressDialog = ProgressDialog.show(Index.this, "", "正在更新数据系统",
						true);
				Log.i(TAG, "loading data...");
				break;
			case NetworkConst.SCT_UPDATE_ERROR:
				progressDialog.dismiss();
				date = msg.getData();
				Tips.showToastTips(Index.this, "错误代码:"
						+ String.valueOf(msg.getData().getString("ErrorMessage")));
//				Log.e(TAG, date.getString("ErrorMessage"));
				break;
			case NetworkConst.SCT_UPDATE_SUCCESS:
				progressDialog.dismiss();
				date = msg.getData();
				Tips.showToastTips(Index.this, "成功");
				Log.i(TAG, "Tips update success...");
				break;
			}

		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.index);
		IpinApplication application = (IpinApplication) this.getApplication();
		application.getActivityList().add(this);
		notificationManager = (NotificationManager) this
				.getSystemService(android.content.Context.NOTIFICATION_SERVICE);

		// tempInit();

		// 标签控件控制
		TabHost mTabHost = (TabHost) findViewById(R.id.index_tabhost);
		mTabHost.setup();
		mTabHost.addTab(mTabHost.newTabSpec("main").setIndicator("首页")
				.setContent(R.id.index_index));
		mTabHost.addTab(mTabHost.newTabSpec("information").setIndicator("拼车消息")
				.setContent(R.id.index_sharecarinfo));
		mTabHost.addTab(mTabHost.newTabSpec("tips").setIndicator("拼车tips")
				.setContent(R.id.index_sharecartips));

		// 创建菜单选项
		softwareMenu = new SoftwareMenu(Index.this);
		softwareMenu.getMenuGrid().setOnItemClickListener(this);

		// 初始化首页组件
		tire = (ImageView) findViewById(R.id.index_index_appcontain_imgview);
		tireAnimation = new RotateAnimation(0.0f, +36000000000000.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		tireAnimation.setDuration(500000);
		tire.startAnimation(tireAnimation);

		userLocation = (LinearLayout) findViewById(R.id.index_index_appcontain_showlocal);
		userLocation.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

				// 判断GPS模块是否开启，如果没有则开启
				if (locationManager
						.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
					Intent intent = new Intent();
					intent.setClass(v.getContext(), UserLocation.class);
					startActivityForResult(intent, 0);
				} else {
					Tips.showToastTips(Index.this, "发现你没有设置GPS模块,现正进行设置");
					Intent intent = new Intent(
							Settings.ACTION_SECURITY_SETTINGS);
					startActivityForResult(intent, 0);
				}
			}
		});

		userFeedBack = (LinearLayout) findViewById(R.id.index_index_appcontain_problem);
		userFeedBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}

		});

		userPublicSCI = (LinearLayout) findViewById(R.id.index_index_appcontain_sentshareinfo);
		userPublicSCI.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}

		});

		// 创建Tab显示分选项的列表
		sharecarInfo = (ListView) findViewById(R.id.index_sharecarinfo);
		sharecarTips = (ListView) findViewById(R.id.index_sharecartips);
		// 初始化列表更新键
		sharecarInfo.addFooterView(InitTools.initUpdateButton(this,
				"ShareCarInfo"));
		sharecarTipsFooterView = InitTools.initUpdateButton(this,
				"ShareCarTips");
		sharecarTipsFooterView
				.setOnClickListener(new SharecarTipsFooterListener(application.getIndex_SharecarTips_start(), application.getIndex_SharecarTips_size()));
		
		sharecarTips.addFooterView(sharecarTipsFooterView);
		// 初始化列表内容
		sharecarInfo.setAdapter(InitTools.initAdapter(this, "sharecarinfo"));
		sharecarTips.setAdapter(InitTools.initAdapter(this, "sharecartips"));
		// 初始化列表监听器
		sharecarInfo.setOnItemClickListener(new InfoItemClickListener());
		sharecarTips.setOnItemClickListener(new TipsItemClickListener());
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
		case ITEM_INDEX:
			tag = itemNumber + 100;
			break;
		case ITEM_SETTING:

			break;
		case ITEM_BACKLOGIN:
			((IpinApplication) Index.this.getApplication()).logoutUser();
			break;
		case ITEM_EXIT:
			ApplicationExit.exit((IpinApplication) Index.this.getApplication());
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
			IpinApplication application = (IpinApplication) Index.this
					.getApplication();
			startActivity(IpinNotification.setNotification(application
					.getUser().getUserAccount(), this, notificationManager));
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

	// 拼车tips列表点击监听
	class TipsItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub

		}

	}

	// 拼车消息列表点击监听
	class InfoItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub

		}
	}

	// 测试用的临时初始化模块
	public void tempInit() {
		IpinApplication application = (IpinApplication) Index.this
				.getApplication();
		User user = new User();
		user.setUserAccount("lok");
		user.setPassword("123456");
		user.setUserId(10);
		user.setUserNickName("lok");
		application.setUser(user);
	}

	// 拼车tip列表更新按钮监听
	class SharecarTipsFooterListener implements OnClickListener {

		private int starts;
		private int size;
		
		public SharecarTipsFooterListener(int starts, int size){
			this.starts = starts;
			this.size = size;
		}
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.i(TAG, "UpdateSharecartips_OnClickListener");
			// if (!InitTools.isConnectNetwork(v.getContext())) {
			// Tips.showToastTips(v.getContext(), "网络连接错误, 请检查网络连接");
			// return;
			// }
			new Thread() {
				@Override
				public void run() {
					Message startMsg = new Message();
					String result = null;
					startMsg.what = NetworkConst.SCT_UPDATE_START;
					indexHandler.sendMessage(startMsg);
					// 设置参数
					final List<NameValuePair> tipsListValuePairList = new ArrayList<NameValuePair>();
					tipsListValuePairList.add(new BasicNameValuePair("start",
							Integer.toString(starts)));
					tipsListValuePairList.add(new BasicNameValuePair("size",
							Integer.toString(size)));
					// 错误返回设置
					Bundle data = new Bundle();
					Message errorMsg = new Message();
					try {
						result = HttpOperator.httpPost(PHONESHARECARTIPSP,
								tipsListValuePairList, application
										.getHttpClient());
					} catch (SocketTimeoutException e) {
						data.putString("ErrorMessage", e.getMessage());
						errorMsg.setData(data);
						errorMsg.what = NetworkConst.SCT_UPDATE_ERROR;
						indexHandler.sendMessage(errorMsg);
					} catch (ClientProtocolException e) {
						data.putString("ErrorMessage", e.getMessage());
						errorMsg.setData(data);
						errorMsg.what = NetworkConst.SCT_UPDATE_ERROR;
						indexHandler.sendMessage(errorMsg);
					} catch (IOException e) {
						data.putString("ErrorMessage", e.getMessage());
						errorMsg.setData(data);
						errorMsg.what = NetworkConst.SCT_UPDATE_ERROR;
						indexHandler.sendMessage(errorMsg);
					} catch (Exception e) {
						data.putString("ErrorMessage", e.getMessage());
						errorMsg.setData(data);
						errorMsg.what = NetworkConst.SCT_UPDATE_ERROR;
						indexHandler.sendMessage(errorMsg);
					}
					try {
						if (result != null) {
							JSONObject object = new JSONObject(result);
							Bundle response = new Bundle();
							Message responseMsg = new Message();
							response.putString("Answer", object.toString());
							responseMsg.setData(data);
							responseMsg.what = NetworkConst.SCT_UPDATE_SUCCESS;
							indexHandler.sendMessage(responseMsg);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						data.putString("ErrorMessage", e.getMessage());
						errorMsg.setData(data);
						errorMsg.what = NetworkConst.SCT_UPDATE_ERROR;
						indexHandler.sendMessage(errorMsg);
					}

				}
			}.start();
		}
	}

}

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
import com.ipin.logindialogmenu.LoginSettingMenu;
import com.ipin.loginmenu.LoginMenu;
import com.ipin.utils.ApplicationExit;
import com.ipin.utils.HttpOperator;
import com.ipin.utils.InitTools;
import com.ipin.utils.PreActivityConst;
import com.ipin.utils.ServerAddressParams;
import com.ipin.utils.Tips;
import com.ipin.beans.User;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;

public class Login extends Activity implements PreActivityConst {

	// 日志标记
	private static final String TAG = "Login";

	// 数据获取URL
	private static final String PHONELOGINACTION = "http://"
			+ ServerAddressParams.IP + ":8080/IpinServer/phonelogin.do";

	// 全局保存变量
	private IpinApplication application = null;
	// 页面转换类
	private Intent intent = null;
	// 用户名输入栏
	private EditText usernameEditText;
	// 密码输入栏
	private EditText passwordEditText;
	// 保存账号选项
	private ImageButton rememberAccount;
	// 保存账号标记
	private boolean isRememberAccount;
	// 登录按钮
	private ImageButton login;
	// 菜单内容
	private LoginMenu.MenuAdapter menuAdapter;
	// 菜单
	private LoginMenu loginMenu;
	// 保存已选择的菜单项
	private int selectMenu = 1;
	// 测试按钮
	private LinearLayout guessLogin = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		IpinApplication application = (IpinApplication) this.getApplication();
		application.getActivityList().add(this);

		// 检查网络是否可用
		InitTools.checkNetState(this);
		// 菜单初始化
		menuAdapter = new LoginMenu.MenuAdapter(this, new String[] { "主页",
				"注册", "介绍", "设置", "退出" }, 16, 0xFF222222, Color.LTGRAY,
				Color.WHITE);
		loginMenu = new LoginMenu(this, new MenuClickListener(), menuAdapter,
				0x55123456, R.style.loginMenuAnimation);
		loginMenu.update();
		loginMenu.SetTitleSelect(0);

		// 控件初始化板块
		usernameEditText = (EditText) findViewById(R.id.username);
		passwordEditText = (EditText) findViewById(R.id.password);
		login = (ImageButton) findViewById(R.id.login);
		rememberAccount = (ImageButton) findViewById(R.id.rememberaccount);
		isRememberAccount = false;
		rememberAccount.setOnClickListener(new RememberAccountListener());
		login.setOnClickListener(new LoginListener());
		guessLogin = (LinearLayout) findViewById(R.id.login_guess_login);
		guessLogin.setOnClickListener(new GuessLoginListener());

		// 控件初始设置
		usernameEditText.setHint("请输入用户名");
		usernameEditText.setBackgroundColor(Color.TRANSPARENT);
		passwordEditText.setBackgroundColor(Color.TRANSPARENT);
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
		if (loginMenu != null) {
			if (loginMenu.isShowing()) {
				loginMenu.dismiss();
			} else {
				loginMenu.showAtLocation(findViewById(R.id.loginmenubar),
						Gravity.BOTTOM, 0, 0);
			}
		}
		return false;
	}

	// 菜单点击监听
	class MenuClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view,
				int itemNumber, long longNumber) {
			Log.i(TAG, "MenuClick_OnItemClickListener");
			intent = new Intent();
			selectMenu = itemNumber;
			loginMenu.SetTitleSelect(selectMenu);
			switch (itemNumber) {
			case 0:
				Tips.showToastTips(view.getContext(), "将会打开网站主页");
				Uri uri = Uri.parse(WEB_INDEX);
				intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
				break;
			case 1:
				Tips.showToastTips(view.getContext(), "将会打开注册界面");
				intent.setClass(Login.this, Regist.class);
				startActivity(intent);
				Login.this.finish();
				break;
			case 2:
				Tips.showToastTips(view.getContext(), "将会打开介绍界面");
				intent.setClass(Login.this, Introduce.class);
				startActivity(intent);
				Login.this.finish();
				break;
			case 3:
				LoginSettingMenu loginSettingMenu = new LoginSettingMenu(view
						.getContext());
				if (loginSettingMenu.isInitialDialog()) {
					loginSettingMenu.showDialog();
				} else {
					loginSettingMenu.initialAndShowDialog();
				}
				break;
			case 4:
				ApplicationExit.exit((IpinApplication) Login.this
						.getApplication());
				break;
			}
		}
	}

	// 记住密码选择器监听器
	class RememberAccountListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Log.i(TAG, "RememberAccount_OnClickListener");
			if (isRememberAccount == false) {
				v.setBackgroundResource(R.drawable.rememberaccount);
				isRememberAccount = true;
			} else {
				v.setBackgroundResource(R.drawable.orgrememberaccount);
				isRememberAccount = false;
			}
		}
	}

	// 登录按钮监听器
	class LoginListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Log.i(TAG, "Login_OnClickListener");
			if (!InitTools.isConnectNetwork(v.getContext())) {
				Tips.showToastTips(v.getContext(), "网络连接错误, 请检查网络连接");
				return;
			}
			v.setBackgroundResource(R.drawable.loginbutton2);
			String username = usernameEditText.getText().toString().trim();
			String password = passwordEditText.getText().toString().trim();
			if (username.length() < 6 || password.length() < 6) {
				if (username.length() < 6) {
					Tips.showToastTips(v.getContext(), "账号错误");
				}
				if (password.length() < 6) {
					Tips.showToastTips(v.getContext(), "密码错误");
				}
				v.setBackgroundResource(R.drawable.loginbutton1);
				return;
			} else {
				// 用户验证成功则转入软件首页
				String result = null;
				application = (IpinApplication) getApplication();
				final ProgressDialog progressDialog = ProgressDialog.show(v
						.getContext(), "", "正在登陆系统", true);
				final List<NameValuePair> userValuePairList = new ArrayList<NameValuePair>();
				// 设置参数
				userValuePairList.add(new BasicNameValuePair("username",
						username));
				userValuePairList.add(new BasicNameValuePair("password",
						password));
				try {
					progressDialog.show();
					result = HttpOperator.httpPost(PHONELOGINACTION,
							userValuePairList, application.getHttpClient());
				} catch (SocketTimeoutException e) {
					// TODO Auto-generated catch block
					Tips.showToastTips(Login.this, "网络连接超时\n错误代码:"
							+ e.getMessage());
					Log.i(TAG, e.getMessage());
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					Tips.showToastTips(Login.this, "用户传输协议错误\n错误代码:"
							+ e.getMessage());
					Log.i(TAG, e.getMessage());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Tips.showToastTips(Login.this, "数据流传输错误\n错误代码:"
							+ e.getMessage());
					Log.i(TAG, e.getMessage());
				} catch (Exception e) {
					Tips.showToastTips(Login.this, "错误代码:" + e.getMessage());
					Log.i(TAG, e.getMessage());
				} finally {
					progressDialog.dismiss();
					v.setBackgroundResource(R.drawable.loginbutton1);
					// application.shutdownHttpClient();
				}
				try {
					if (result != null) {
						JSONObject object = new JSONObject(result);
						User user = new User();
						user.setUserId(object.getInt("userId"));
						user.setUserAccount(object.getString("userAccount"));
						user.setPassword(object.getString("password"));
						user.setUserCredit(object.getInt("userCredit"));
						user.setUserEmail(object.getString("userEmail"));
						user.setUserImage(object.getString("userImage"));
						user.setUserTel(object.getString("userTel"));
						user.setUserNickName(object.getString("userNickName"));
						user.setUserTrueName(object.getString("userTrueName"));
						application.setUser(user);
						Tips.showToastTips(Login.this, "欢迎"
								+ user.getUserAccount());
						Intent guessIntent = new Intent();
						guessIntent.setClass(Login.this, Index.class);
						startActivity(guessIntent);
						finish();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Log.i(TAG, e.getMessage());
				}

			}
		}
	}

	// 测试按钮监听器
	class GuessLoginListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			Log.i(TAG, "GuessLogin_OnClickListener");
			Intent guessIntent = new Intent();
			guessIntent.setClass(arg0.getContext(), Index.class);
			startActivity(guessIntent);
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

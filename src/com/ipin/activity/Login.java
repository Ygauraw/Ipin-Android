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

	// ��־���
	private static final String TAG = "Login";

	// ���ݻ�ȡURL
	private static final String PHONELOGINACTION = "http://"
			+ ServerAddressParams.IP + ":8080/IpinServer/phonelogin.do";

	// ȫ�ֱ������
	private IpinApplication application = null;
	// ҳ��ת����
	private Intent intent = null;
	// �û���������
	private EditText usernameEditText;
	// ����������
	private EditText passwordEditText;
	// �����˺�ѡ��
	private ImageButton rememberAccount;
	// �����˺ű��
	private boolean isRememberAccount;
	// ��¼��ť
	private ImageButton login;
	// �˵�����
	private LoginMenu.MenuAdapter menuAdapter;
	// �˵�
	private LoginMenu loginMenu;
	// ������ѡ��Ĳ˵���
	private int selectMenu = 1;
	// ���԰�ť
	private LinearLayout guessLogin = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		IpinApplication application = (IpinApplication) this.getApplication();
		application.getActivityList().add(this);

		// ��������Ƿ����
		InitTools.checkNetState(this);
		// �˵���ʼ��
		menuAdapter = new LoginMenu.MenuAdapter(this, new String[] { "��ҳ",
				"ע��", "����", "����", "�˳�" }, 16, 0xFF222222, Color.LTGRAY,
				Color.WHITE);
		loginMenu = new LoginMenu(this, new MenuClickListener(), menuAdapter,
				0x55123456, R.style.loginMenuAnimation);
		loginMenu.update();
		loginMenu.SetTitleSelect(0);

		// �ؼ���ʼ�����
		usernameEditText = (EditText) findViewById(R.id.username);
		passwordEditText = (EditText) findViewById(R.id.password);
		login = (ImageButton) findViewById(R.id.login);
		rememberAccount = (ImageButton) findViewById(R.id.rememberaccount);
		isRememberAccount = false;
		rememberAccount.setOnClickListener(new RememberAccountListener());
		login.setOnClickListener(new LoginListener());
		guessLogin = (LinearLayout) findViewById(R.id.login_guess_login);
		guessLogin.setOnClickListener(new GuessLoginListener());

		// �ؼ���ʼ����
		usernameEditText.setHint("�������û���");
		usernameEditText.setBackgroundColor(Color.TRANSPARENT);
		passwordEditText.setBackgroundColor(Color.TRANSPARENT);
	}

	// ����MENUѡ��
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.i(TAG, "onCreateOptionsMenu");
		menu.add("menu");
		return super.onCreateOptionsMenu(menu);
	}

	// ����MENU����
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

	// �˵��������
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
				Tips.showToastTips(view.getContext(), "�������վ��ҳ");
				Uri uri = Uri.parse(WEB_INDEX);
				intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
				break;
			case 1:
				Tips.showToastTips(view.getContext(), "�����ע�����");
				intent.setClass(Login.this, Regist.class);
				startActivity(intent);
				Login.this.finish();
				break;
			case 2:
				Tips.showToastTips(view.getContext(), "����򿪽��ܽ���");
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

	// ��ס����ѡ����������
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

	// ��¼��ť������
	class LoginListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Log.i(TAG, "Login_OnClickListener");
			if (!InitTools.isConnectNetwork(v.getContext())) {
				Tips.showToastTips(v.getContext(), "�������Ӵ���, ������������");
				return;
			}
			v.setBackgroundResource(R.drawable.loginbutton2);
			String username = usernameEditText.getText().toString().trim();
			String password = passwordEditText.getText().toString().trim();
			if (username.length() < 6 || password.length() < 6) {
				if (username.length() < 6) {
					Tips.showToastTips(v.getContext(), "�˺Ŵ���");
				}
				if (password.length() < 6) {
					Tips.showToastTips(v.getContext(), "�������");
				}
				v.setBackgroundResource(R.drawable.loginbutton1);
				return;
			} else {
				// �û���֤�ɹ���ת�������ҳ
				String result = null;
				application = (IpinApplication) getApplication();
				final ProgressDialog progressDialog = ProgressDialog.show(v
						.getContext(), "", "���ڵ�½ϵͳ", true);
				final List<NameValuePair> userValuePairList = new ArrayList<NameValuePair>();
				// ���ò���
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
					Tips.showToastTips(Login.this, "�������ӳ�ʱ\n�������:"
							+ e.getMessage());
					Log.i(TAG, e.getMessage());
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					Tips.showToastTips(Login.this, "�û�����Э�����\n�������:"
							+ e.getMessage());
					Log.i(TAG, e.getMessage());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Tips.showToastTips(Login.this, "�������������\n�������:"
							+ e.getMessage());
					Log.i(TAG, e.getMessage());
				} catch (Exception e) {
					Tips.showToastTips(Login.this, "�������:" + e.getMessage());
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
						Tips.showToastTips(Login.this, "��ӭ"
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

	// ���԰�ť������
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

	// ���ذ�ť������
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

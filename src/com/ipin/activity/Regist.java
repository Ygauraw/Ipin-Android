package com.ipin.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.ipin.application.IpinApplication;
import com.ipin.logindialogmenu.LoginDialogMenu;
import com.ipin.utils.ApplicationExit;
import com.ipin.utils.PreActivityConst;
import com.ipin.utils.PreActivitySwitcher;
import com.ipin.utils.Tips;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Regist extends Activity implements OnItemClickListener,
		PreActivityConst {

	private static final String TAG = "Regist";

	// 创建菜单选项
	public LoginDialogMenu loginDialogMenu = null;
	// 页面转换器
	private Intent intent = null;
	// 初始化菜单控件
	private ButtonListener buttonListener = null;
	// 同意用户协议
	private CheckBox agreeContract = null;
	// 不同意用协议
	private CheckBox refuseContract = null;
	// 用户账号
	private EditText userAccountET = null;
	// 用户密码
	private EditText passwordET = null;
	// 用户密码确认
	private EditText passowrdConfirmET = null;
	// 用户真实姓名
	private EditText trueNameET = null;
	// 用户电话号码
	private EditText telET = null;
	// 用户昵称
	private EditText nickNameET = null;
	// 用户电邮
	private EditText emailET = null;
	// 用户账号确认
	private TextView userAccountTV = null;
	// 用户密码确认
	private TextView passwordTV = null;
	// 用户真实姓名确认
	private TextView trueNameTV = null;
	// 用户电话号码确认
	private TextView telTV = null;
	// 用户昵称确认
	private TextView nickNameTV = null;
	// 用户电邮确认
	private TextView emailTV = null;
	// 用户信息重置
	private Button infoRest = null;
	// 用户信息确认
	private Button infoOk = null;
	// 用户信息确认重设
	private Button confirmRest = null;
	// 用户信息确认提交
	private Button confirmSubmit = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.regist);
		IpinApplication application = (IpinApplication)this.getApplication();
		application.getActivityList().add(this);

		// 标签控件控制
		TabHost mTabHost = (TabHost) findViewById(R.id.regist_tabhost);
		mTabHost.setup();
		mTabHost.addTab(mTabHost.newTabSpec("registinfo").setIndicator("用户协议")
				.setContent(R.id.regist_contract));
		mTabHost.addTab(mTabHost.newTabSpec("information").setIndicator("注册信息")
				.setContent(R.id.regist_info));
		mTabHost.addTab(mTabHost.newTabSpec("tips").setIndicator("确认注册信息")
				.setContent(R.id.regist_confirm));

		loginDialogMenu = new LoginDialogMenu(Regist.this);
		loginDialogMenu.getMenuGrid().setOnItemClickListener(this);

		initRegistWidget();
		buttonListener = new ButtonListener();

		infoRest.setOnClickListener(buttonListener);
		infoOk.setOnClickListener(buttonListener);
		confirmRest.setOnClickListener(buttonListener);
		confirmSubmit.setOnClickListener(buttonListener);
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
		case ITEM_REGIST:
			tag = tag + 100;
			break;
		case ITEM_SETTING:
			break;
		
		case ITEM_EXIT:
			ApplicationExit.exit((IpinApplication)Regist.this.getApplication());
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

	// 返回键点击监听
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

	// 按钮初始化组件
	public void initRegistWidget() {
		Log.i(TAG, "initRegistWidget");
		agreeContract = (CheckBox) findViewById(R.id.regist_contract_agree);
		refuseContract = (CheckBox) findViewById(R.id.regist_contract_rejest);
		userAccountET = (EditText) findViewById(R.id.regist_info_useraccount);
		passwordET = (EditText) findViewById(R.id.regist_info_userpassword1);
		passowrdConfirmET = (EditText) findViewById(R.id.regist_info_userpassword2);
		trueNameET = (EditText) findViewById(R.id.regist_info_trueusername);
		telET = (EditText) findViewById(R.id.regist_info_usertel);
		nickNameET = (EditText) findViewById(R.id.regist_info_usernickname);
		emailET = (EditText) findViewById(R.id.regist_info_useremail);
		userAccountTV = (TextView) findViewById(R.id.regist_confirm_useaccount);
		passwordTV = (TextView) findViewById(R.id.regist_confirm_password);
		trueNameTV = (TextView) findViewById(R.id.regist_confirm_truename);
		telTV = (TextView) findViewById(R.id.regist_confirm_tel);
		nickNameTV = (TextView) findViewById(R.id.regist_confirm_nickname);
		emailTV = (TextView) findViewById(R.id.regist_confirm_email);
		infoRest = (Button) findViewById(R.id.regist_info_reset);
		infoOk = (Button) findViewById(R.id.regist_info_ok);
		confirmRest = (Button) findViewById(R.id.regist_confirm_reset);
		confirmSubmit = (Button) findViewById(R.id.regist_confirm_submit);
	}

	// 提交及重置按钮监听器
	class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.i(TAG, "Button_OnClickListener");
			switch (v.getId()) {
			case R.id.regist_info_reset:
				userAccountET.setText("");
				passwordET.setText("");
				passowrdConfirmET.setText("");
				trueNameET.setText("");
				telET.setText("");
				nickNameET.setText("");
				emailET.setText("");
				break;
			case R.id.regist_info_ok:
				if (userAccountET.getText().toString().equals("")
						|| passwordET.getText().toString().equals("")
						|| passowrdConfirmET.getText().toString().equals("")
						|| telET.getText().toString().equals("")) {
					Tips.showToastTips(v.getContext(), "请填写星号符号的信息");
				} else {
					if (userAccountET.getText().toString().length() < 6) {
						Tips.showToastTips(v.getContext(), "账号长度应大于6");
					} else {
						if (userAccountET.getText().toString().length() > 16) {
							Tips.showToastTips(v.getContext(), "账号长度应小于16");
						}
					}
					if (passwordET.getText().toString().length() < 6) {
						Tips.showToastTips(v.getContext(), "密码长度应大于6");
					} else {
						if (passwordET.getText().toString().length() > 16) {
							Tips.showToastTips(v.getContext(), "密码长度应小于16");
						}
					}
				}
				if (!passwordET.getText().toString().equals(
						passowrdConfirmET.getText().toString())) {
					Tips.showToastTips(v.getContext(), "确认密码与输入密码不符");
					return;
				}
				userAccountTV.setText(userAccountET.getText().toString());
				passwordTV.setText(passowrdConfirmET.getText().toString());
				trueNameTV.setText(trueNameET.getText().toString());
				telTV.setText(telET.getText().toString());
				nickNameTV.setText(nickNameET.getText().toString());
				emailTV.setText(emailET.getText().toString());
				if (nickNameET.getText().toString().equals("")) {
					nickNameTV.setText(userAccountET.getText().toString());
				}
				break;
			case R.id.regist_confirm_reset:

				break;
			case R.id.regist_confirm_submit:
				if (agreeContract.isChecked() && !refuseContract.isChecked()) {
					// 调用服务器并对返回值进行注册
					final ProgressDialog progressDialog = ProgressDialog.show(v
							.getContext(), "", "正在注册账号", true);
					final List<NameValuePair> userValuePairList = new ArrayList<NameValuePair>();
//					userValuePairList.add(new BasicNameValuePair("userAccount",
//							userAccountET.toString()));
//					userValuePairList.add(new BasicNameValuePair("password",
//							password));
				} else if (agreeContract.isChecked()
						&& refuseContract.isChecked()) {
					Tips.showToastTips(v.getContext(), "同意与拒绝只能选其一");
					return;
				} else if (!agreeContract.isChecked()
						&& !refuseContract.isChecked()) {
					Tips.showToastTips(v.getContext(), "若要注册用户必须同意用户协议");
					return;
				} else if (!agreeContract.isChecked()
						&& refuseContract.isChecked()) {
					Tips.showToastTips(v.getContext(), "若要注册用户必须同意用户协议");
					return;
				}
				break;
			default:
				break;
			}
		}
	}
}
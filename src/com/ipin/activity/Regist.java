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

	// �����˵�ѡ��
	public LoginDialogMenu loginDialogMenu = null;
	// ҳ��ת����
	private Intent intent = null;
	// ��ʼ���˵��ؼ�
	private ButtonListener buttonListener = null;
	// ͬ���û�Э��
	private CheckBox agreeContract = null;
	// ��ͬ����Э��
	private CheckBox refuseContract = null;
	// �û��˺�
	private EditText userAccountET = null;
	// �û�����
	private EditText passwordET = null;
	// �û�����ȷ��
	private EditText passowrdConfirmET = null;
	// �û���ʵ����
	private EditText trueNameET = null;
	// �û��绰����
	private EditText telET = null;
	// �û��ǳ�
	private EditText nickNameET = null;
	// �û�����
	private EditText emailET = null;
	// �û��˺�ȷ��
	private TextView userAccountTV = null;
	// �û�����ȷ��
	private TextView passwordTV = null;
	// �û���ʵ����ȷ��
	private TextView trueNameTV = null;
	// �û��绰����ȷ��
	private TextView telTV = null;
	// �û��ǳ�ȷ��
	private TextView nickNameTV = null;
	// �û�����ȷ��
	private TextView emailTV = null;
	// �û���Ϣ����
	private Button infoRest = null;
	// �û���Ϣȷ��
	private Button infoOk = null;
	// �û���Ϣȷ������
	private Button confirmRest = null;
	// �û���Ϣȷ���ύ
	private Button confirmSubmit = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.regist);
		IpinApplication application = (IpinApplication)this.getApplication();
		application.getActivityList().add(this);

		// ��ǩ�ؼ�����
		TabHost mTabHost = (TabHost) findViewById(R.id.regist_tabhost);
		mTabHost.setup();
		mTabHost.addTab(mTabHost.newTabSpec("registinfo").setIndicator("�û�Э��")
				.setContent(R.id.regist_contract));
		mTabHost.addTab(mTabHost.newTabSpec("information").setIndicator("ע����Ϣ")
				.setContent(R.id.regist_info));
		mTabHost.addTab(mTabHost.newTabSpec("tips").setIndicator("ȷ��ע����Ϣ")
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
		if (loginDialogMenu.isInitialDialog()) {
			loginDialogMenu.showDialog();
		} else {
			loginDialogMenu.initialAndShowDialog();
		}
		return false;
	}

	// �˵��������
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

	// ���ؼ��������
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

	// ��ť��ʼ�����
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

	// �ύ�����ð�ť������
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
					Tips.showToastTips(v.getContext(), "����д�Ǻŷ��ŵ���Ϣ");
				} else {
					if (userAccountET.getText().toString().length() < 6) {
						Tips.showToastTips(v.getContext(), "�˺ų���Ӧ����6");
					} else {
						if (userAccountET.getText().toString().length() > 16) {
							Tips.showToastTips(v.getContext(), "�˺ų���ӦС��16");
						}
					}
					if (passwordET.getText().toString().length() < 6) {
						Tips.showToastTips(v.getContext(), "���볤��Ӧ����6");
					} else {
						if (passwordET.getText().toString().length() > 16) {
							Tips.showToastTips(v.getContext(), "���볤��ӦС��16");
						}
					}
				}
				if (!passwordET.getText().toString().equals(
						passowrdConfirmET.getText().toString())) {
					Tips.showToastTips(v.getContext(), "ȷ���������������벻��");
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
					// ���÷��������Է���ֵ����ע��
					final ProgressDialog progressDialog = ProgressDialog.show(v
							.getContext(), "", "����ע���˺�", true);
					final List<NameValuePair> userValuePairList = new ArrayList<NameValuePair>();
//					userValuePairList.add(new BasicNameValuePair("userAccount",
//							userAccountET.toString()));
//					userValuePairList.add(new BasicNameValuePair("password",
//							password));
				} else if (agreeContract.isChecked()
						&& refuseContract.isChecked()) {
					Tips.showToastTips(v.getContext(), "ͬ����ܾ�ֻ��ѡ��һ");
					return;
				} else if (!agreeContract.isChecked()
						&& !refuseContract.isChecked()) {
					Tips.showToastTips(v.getContext(), "��Ҫע���û�����ͬ���û�Э��");
					return;
				} else if (!agreeContract.isChecked()
						&& refuseContract.isChecked()) {
					Tips.showToastTips(v.getContext(), "��Ҫע���û�����ͬ���û�Э��");
					return;
				}
				break;
			default:
				break;
			}
		}
	}
}
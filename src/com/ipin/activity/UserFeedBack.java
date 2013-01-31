package com.ipin.activity;

import com.ipin.application.IpinApplication;
import com.ipin.softwaremenu.SoftwareMenu;
import com.ipin.utils.ApplicationExit;
import com.ipin.utils.PostActivityConst;
import com.ipin.utils.PostActivitySwitcher;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class UserFeedBack extends Activity implements OnItemClickListener, PostActivityConst{
	private static final String TAG = "UserFeedBack";
	
	// �����˵�ѡ��
	private SoftwareMenu softwareMenu = null;
	// ҳ��ת����
	private Intent intent = null;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.i(TAG, "onCreate");
    	super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.userfeedback);
		IpinApplication application = (IpinApplication)this.getApplication();
		application.getActivityList().add(this);
		
		// �����˵�ѡ��
		softwareMenu = new SoftwareMenu(this);
		softwareMenu.getMenuGrid().setOnItemClickListener(this);
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
		int tag;
		tag = itemNumber;
		switch (itemNumber) {
		case ITEM_SETTING:

			break;
		case ITEM_BACKLOGIN:
			((IpinApplication)UserFeedBack.this.getApplication()).logoutUser();
			break;
		case ITEM_EXIT:
			ApplicationExit.exit((IpinApplication)UserFeedBack.this.getApplication());
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
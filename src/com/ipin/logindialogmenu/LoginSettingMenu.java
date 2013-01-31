package com.ipin.logindialogmenu;

import com.ipin.activity.R;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class LoginSettingMenu {

	// 菜单所在页面环境
	private Context context = null;
	
	// 菜单显示板
	private AlertDialog menuDialog = null;
	private View menuView = null;
	private ListView menuListView = null;
	
	// 响应器
	private AssessStyle asListener = null;

	public LoginSettingMenu(Context context) {
		this.setContext(context);
		menuView = View.inflate(context, R.layout.loginsettingmenu, null);
		menuDialog = new AlertDialog.Builder(context).create();
		menuDialog.setView(menuView);
		menuListView = (ListView) menuView
				.findViewById(R.id.loginsettingmenu_listview);
		String[] raw = new String[] { "设置主题", "访问设置" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1, raw);
		menuListView.setAdapter(adapter);
		asListener = new AssessStyle();
		this.menuListView.setOnItemClickListener(asListener);
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public Context getContext() {
		return context;
	}

	public void setMenuDialog(AlertDialog menuDialog) {
		this.menuDialog = menuDialog;
	}

	public AlertDialog getMenuDialog() {
		return menuDialog;
	}
	
	public boolean isInitialDialog(){
		if(this.menuDialog != null){
			return true;
		}else{
			return false;
		}
	}
	
	public void initialAndShowDialog(){
		this.menuDialog = new AlertDialog.Builder(context).setView(menuView).show();
	}
	
	public void showDialog(){
		this.menuDialog.show();
	}
	
	class AssessStyle implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			if(arg2 == 1){
				
			}
		}
		
	}
}

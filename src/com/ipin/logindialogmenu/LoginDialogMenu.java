package com.ipin.logindialogmenu;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.ipin.activity.R;

public class LoginDialogMenu {
	
	// �˵�����ҳ�滷��
	private Context context = null;
	// �˵���ʾ��
	private AlertDialog menuDialog = null;
	// �˵���ʾ��ʽ
	private GridView menuGrid = null;
	// �˵���ʾ��
	private View menuView = null;
	
	// �˵�ͼƬ 
	int[] menu_image_array = { R.drawable.indexmenu_index,
			R.drawable.indexmenu_travel, R.drawable.indexmenu_lentcar,
			R.drawable.indexmenu_calltaxi, R.drawable.indexmenu_setting,
			R.drawable.indexmenu_exit };
	// �˵�����
	String[] menu_name_array = { "��վ��ҳ", "��¼", "ע��", "ʹ�ý���", "����", "�˳�"};
	
	public LoginDialogMenu(Context page){
		this.context = page;
		// �����˵�ѡ��
		menuView = View.inflate(context, R.layout.logindialogmenu_view, null);
		// ����AlertDialog
		menuDialog = new AlertDialog.Builder(context).create();
		menuDialog.setView(menuView);
		menuDialog.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_MENU)// ��������
					dialog.dismiss();
				return false;
			}

		});
		menuGrid = (GridView) menuView.findViewById(R.id.logindialogmenu_gridview);
		menuGrid.setAdapter(getMenuAdapter(menu_name_array, menu_image_array));
	}
	
	private SimpleAdapter getMenuAdapter(String[] menuNameArray,
			int[] imageResourceArray) {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < menuNameArray.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", imageResourceArray[i]);
			map.put("itemText", menuNameArray[i]);
			data.add(map);
		}
		SimpleAdapter simperAdapter = new SimpleAdapter(context, data,
				R.layout.item_menu, new String[] { "itemImage", "itemText" },
				new int[] { R.id.item_image, R.id.item_text });
		return simperAdapter;
	}
	
	public AlertDialog getMenuDialog() {
		return menuDialog;
	}

	public void setMenuDialog(AlertDialog menuDialog) {
		this.menuDialog = menuDialog;
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
	
	public GridView getMenuGrid() {
		return menuGrid;
	}

	public void setMenuGrid(GridView menuGrid) {
		this.menuGrid = menuGrid;
	}
}

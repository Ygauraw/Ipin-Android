package com.ipin.softwaremenu;

import java.util.ArrayList;
import java.util.HashMap;

import com.ipin.activity.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.SimpleAdapter;

public class SoftwareMenu {

	// 菜单所在页面环境
	private Context context = null;
	// 菜单显示板
	private AlertDialog menuDialog = null;
	// 菜单显示格式
	private GridView menuGrid = null;
	// 菜单显示类
	private View menuView = null;

	// 菜单图片
	private int[] menu_image_array = { R.drawable.indexmenu_index,
			R.drawable.indexmenu_travel, R.drawable.indexmenu_lentcar,
			R.drawable.indexmenu_calltaxi, R.drawable.indexmenu_carfans,
			R.drawable.indexmenu_setting, R.drawable.indexmenu_backlogin,
			R.drawable.indexmenu_exit };

	// 菜单文字
	private String[] menu_name_array = { "主页", "旅游", "商务包车", "电召的士", "车友",
			"设置", "退出登录", "退出" };

	public SoftwareMenu(Context page) {
		this.context = page;
		// 创建菜单选项
		menuView = View.inflate(context, R.layout.softwaremenu_gridview, null);
		// 创建AlertDialog
		menuDialog = new AlertDialog.Builder(context).create();
		menuDialog.setView(menuView);
		menuDialog.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_MENU)// 监听按键
					dialog.dismiss();
				return false;
			}

		});
		menuGrid = (GridView) menuView.findViewById(R.id.gridview);
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

	public boolean isInitialDialog() {
		if (this.menuDialog != null) {
			return true;
		} else {
			return false;
		}
	}

	public void initialAndShowDialog() {
		this.menuDialog = new AlertDialog.Builder(context).setView(menuView)
				.show();
	}

	public void showDialog() {
		this.menuDialog.show();
	}

	public GridView getMenuGrid() {
		return menuGrid;
	}

	public void setMenuGrid(GridView menuGrid) {
		this.menuGrid = menuGrid;
	}
}

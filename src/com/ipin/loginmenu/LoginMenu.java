package com.ipin.loginmenu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class LoginMenu extends PopupWindow {
	GridView menuView;
	LinearLayout menuLayout;
	private MenuAdapter menuAdapter;

	public LoginMenu(Context context, OnItemClickListener menuItemClick,
			MenuAdapter menuAdapter, int menuBgColor, int aniLoginMenu) {
		super(context);

		menuLayout = new LinearLayout(context);
		menuLayout.setOrientation(LinearLayout.VERTICAL);

		menuView = new GridView(context);
		menuView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		menuView.setNumColumns(menuAdapter.getCount());
		menuView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		menuView.setVerticalSpacing(1);
		menuView.setHorizontalSpacing(1);
		menuView.setGravity(Gravity.CENTER);
		menuView.setOnItemClickListener(menuItemClick);
		menuView.setAdapter(menuAdapter);
		
		// 选中的时候为透明色
		menuView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		this.menuAdapter = menuAdapter;
		menuLayout.addView(menuView);

		this.setContentView(menuLayout);
		this.setWidth(LayoutParams.FILL_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		
		// 设置TabMenu菜单背景
        this.setBackgroundDrawable(new ColorDrawable(menuBgColor));  
        this.setAnimationStyle(aniLoginMenu);
        
        // menu菜单获得焦点 如果没有获得焦点menu菜单中的控件事件无法响应
        this.setFocusable(true);  
	}
	
	public void SetTitleSelect(int index){
		menuView.setSelection(index);
		this.menuAdapter.SetFocus(index);
	}

	static public class MenuAdapter extends BaseAdapter {

		// 上下文
		private Context menuAdapterContext;
		// 分页标签的字符串数组
		@SuppressWarnings("unused")
		private int fontColor, unselColor, selColor;
		// 字符串数组
		private TextView[] menuNames;

		public MenuAdapter(Context context, String[] menuNamesText,
				int fontSize, int fontColor, int unselColor, int selColor) {
			this.menuAdapterContext = context;
			this.fontColor = fontColor;
			this.unselColor = unselColor;
			this.selColor = selColor;
			this.menuNames = new TextView[menuNamesText.length];
			for (int i = 0; i < menuNamesText.length; i++) {
				menuNames[i] = new TextView(menuAdapterContext);
				menuNames[i].setText(menuNamesText[i]);
				menuNames[i].setTextSize(fontSize);
				menuNames[i].setTextColor(fontColor);
				menuNames[i].setGravity(Gravity.CENTER);
				menuNames[i].setPadding(2, 2, 2, 2);
			}
		}

		@Override
		public int getCount() {
			return menuNames.length;
		}

		@Override
		public Object getItem(int position) {
			return menuNames[position];
		}

		@Override
		public long getItemId(int position) {
			return menuNames[position].getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v;
			if (convertView == null) {
				v = menuNames[position];
			} else {
				v = convertView;
			}
			return v;
		}

		private void SetFocus(int index) {
			for (int i = 0; i < menuNames.length; i++) {
				if (i != index) {
					menuNames[i].setBackgroundDrawable(new ColorDrawable(
							unselColor));
					menuNames[i].setTextColor(selColor);
				}
			}			
			// 设置选中项的颜色
			menuNames[index].setBackgroundColor(0x000000);
			// 设置选中项的字体颜色
			menuNames[index].setTextColor(selColor);
		}

	}
}
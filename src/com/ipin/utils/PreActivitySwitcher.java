package com.ipin.utils;

import android.content.Context;
import android.content.Intent;

public class PreActivitySwitcher implements PreActivityConst{
	// 包名
	public static final String PACKAGENAME = "com.ipin.activity";
	
	// 登录
	public static final String CLASSNAME_LOGIN = PACKAGENAME + ".Login";
	// 注册
	public static final String CLASSNAME_REGIST = PACKAGENAME + ".Regist";
	// 介绍
	public static final String CLASSNAME_INTRODUCE = PACKAGENAME + ".Introduce";
	
	public static Intent getSoftwareSwitchIntent(Context context, int tag) {
		Intent result = new Intent();
		switch (tag) {
		case ITEM_WEBINDEX:
			break;
		case ITEM_LOGIN + 100:
			Tips.showToastTips(context, "你已经在登录页面");
			result = null;
			break;
		case ITEM_LOGIN:
			Tips.showToastTips(context, "将会打开登录页面");
			result.setClassName(PACKAGENAME, CLASSNAME_LOGIN);
			break;
		case ITEM_REGIST + 100:
			Tips.showToastTips(context, "你已经在注册页面");
			result = null;
			break;
		case ITEM_REGIST:
			Tips.showToastTips(context, "将会打开注册页面");
			result.setClassName(PACKAGENAME, CLASSNAME_REGIST);
			break;
		case ITEM_INTRODUCE + 100:
			Tips.showToastTips(context, "你已经在软件介绍页面");
			result = null;
			break;
		case ITEM_INTRODUCE:
			Tips.showToastTips(context, "将会打软件介绍页面");
			result.setClassName(PACKAGENAME, CLASSNAME_INTRODUCE);
			break;
		default:
			result = null;
			break;
		}
		return result;
	}
}

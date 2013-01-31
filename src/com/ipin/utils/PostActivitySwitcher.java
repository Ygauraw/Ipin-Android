package com.ipin.utils;

import android.content.Context;
import android.content.Intent;

public class PostActivitySwitcher implements PostActivityConst{
	// 包名
	public static final String PACKAGENAME = "com.ipin.activity";

	// 主页
	public static final String CLASSNAME_INDEX = PACKAGENAME + ".Index";
	// 旅游
	public static final String CLASSNAME_TRAVEL = PACKAGENAME + ".Travel";
	// 商务包车
	public static final String CLASSNAME_LENTCAR = PACKAGENAME + ".LentCar";
	// 电召的士
	public static final String CLASSNAME_CALLTAXI = PACKAGENAME + ".CallTaxi";
	// 车友
	public static final String CLASSNAME_CARFANS = PACKAGENAME + ".CarFans";
	// 返回登陆页
	public static final String CLASSNAME_LOGIN = PACKAGENAME + ".Login";

	public static Intent getSoftwareSwitchIntent(Context context, int tag) {
		Intent result = new Intent();
		switch (tag) {
		case ITEM_INDEX:
			Tips.showToastTips(context, "将会转到软件主页");
			result.setClassName(PACKAGENAME, CLASSNAME_INDEX);
			break;
		case ITEM_INDEX + 100:
			Tips.showToastTips(context, "你已经在软件主页");
			result = null;
			break;
		case ITEM_TRAVEL:
			Tips.showToastTips(context, "将会打开旅游页面");
			result.setClassName(PACKAGENAME, CLASSNAME_TRAVEL);
			break;
		case ITEM_TRAVEL + 100:
			Tips.showToastTips(context, "你已经在旅游页面");
			result = null;
			break;
		case ITEM_LENTCAR:
			Tips.showToastTips(context, "将会打开包车页面");
			result.setClassName(PACKAGENAME, CLASSNAME_LENTCAR);
			break;
		case ITEM_LENTCAR + 100:
			Tips.showToastTips(context, "你已经在包车页面");
			result = null;
			break;
		case ITEM_CALLTAXI:
			Tips.showToastTips(context, "将会打电召的士页面");
			result.setClassName(PACKAGENAME, CLASSNAME_CALLTAXI);
			break;
		case ITEM_CALLTAXI + 100:
			Tips.showToastTips(context, "你已经在电召的士页面");
			result = null;
			break;
		case ITEM_CARFANS:
			Tips.showToastTips(context, "将会打开车友页面");
			result.setClassName(PACKAGENAME, CLASSNAME_CARFANS);
			break;
		case ITEM_CARFANS + 100:
			Tips.showToastTips(context, "你已经在车友页面");
			result = null;
			break;
		case ITEM_BACKLOGIN:
			Tips.showToastTips(context, "正在返回登录页");
			result.setClassName(PACKAGENAME, CLASSNAME_LOGIN);
			break;
		default:
			result = null;
			break;
		}
		return result;
	}
}

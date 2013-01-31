package com.ipin.utils;

import android.content.Context;
import android.content.Intent;

public class PreActivitySwitcher implements PreActivityConst{
	// ����
	public static final String PACKAGENAME = "com.ipin.activity";
	
	// ��¼
	public static final String CLASSNAME_LOGIN = PACKAGENAME + ".Login";
	// ע��
	public static final String CLASSNAME_REGIST = PACKAGENAME + ".Regist";
	// ����
	public static final String CLASSNAME_INTRODUCE = PACKAGENAME + ".Introduce";
	
	public static Intent getSoftwareSwitchIntent(Context context, int tag) {
		Intent result = new Intent();
		switch (tag) {
		case ITEM_WEBINDEX:
			break;
		case ITEM_LOGIN + 100:
			Tips.showToastTips(context, "���Ѿ��ڵ�¼ҳ��");
			result = null;
			break;
		case ITEM_LOGIN:
			Tips.showToastTips(context, "����򿪵�¼ҳ��");
			result.setClassName(PACKAGENAME, CLASSNAME_LOGIN);
			break;
		case ITEM_REGIST + 100:
			Tips.showToastTips(context, "���Ѿ���ע��ҳ��");
			result = null;
			break;
		case ITEM_REGIST:
			Tips.showToastTips(context, "�����ע��ҳ��");
			result.setClassName(PACKAGENAME, CLASSNAME_REGIST);
			break;
		case ITEM_INTRODUCE + 100:
			Tips.showToastTips(context, "���Ѿ����������ҳ��");
			result = null;
			break;
		case ITEM_INTRODUCE:
			Tips.showToastTips(context, "������������ҳ��");
			result.setClassName(PACKAGENAME, CLASSNAME_INTRODUCE);
			break;
		default:
			result = null;
			break;
		}
		return result;
	}
}

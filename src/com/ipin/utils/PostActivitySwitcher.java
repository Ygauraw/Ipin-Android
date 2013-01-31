package com.ipin.utils;

import android.content.Context;
import android.content.Intent;

public class PostActivitySwitcher implements PostActivityConst{
	// ����
	public static final String PACKAGENAME = "com.ipin.activity";

	// ��ҳ
	public static final String CLASSNAME_INDEX = PACKAGENAME + ".Index";
	// ����
	public static final String CLASSNAME_TRAVEL = PACKAGENAME + ".Travel";
	// �������
	public static final String CLASSNAME_LENTCAR = PACKAGENAME + ".LentCar";
	// ���ٵ�ʿ
	public static final String CLASSNAME_CALLTAXI = PACKAGENAME + ".CallTaxi";
	// ����
	public static final String CLASSNAME_CARFANS = PACKAGENAME + ".CarFans";
	// ���ص�½ҳ
	public static final String CLASSNAME_LOGIN = PACKAGENAME + ".Login";

	public static Intent getSoftwareSwitchIntent(Context context, int tag) {
		Intent result = new Intent();
		switch (tag) {
		case ITEM_INDEX:
			Tips.showToastTips(context, "����ת�������ҳ");
			result.setClassName(PACKAGENAME, CLASSNAME_INDEX);
			break;
		case ITEM_INDEX + 100:
			Tips.showToastTips(context, "���Ѿ��������ҳ");
			result = null;
			break;
		case ITEM_TRAVEL:
			Tips.showToastTips(context, "���������ҳ��");
			result.setClassName(PACKAGENAME, CLASSNAME_TRAVEL);
			break;
		case ITEM_TRAVEL + 100:
			Tips.showToastTips(context, "���Ѿ�������ҳ��");
			result = null;
			break;
		case ITEM_LENTCAR:
			Tips.showToastTips(context, "����򿪰���ҳ��");
			result.setClassName(PACKAGENAME, CLASSNAME_LENTCAR);
			break;
		case ITEM_LENTCAR + 100:
			Tips.showToastTips(context, "���Ѿ��ڰ���ҳ��");
			result = null;
			break;
		case ITEM_CALLTAXI:
			Tips.showToastTips(context, "�������ٵ�ʿҳ��");
			result.setClassName(PACKAGENAME, CLASSNAME_CALLTAXI);
			break;
		case ITEM_CALLTAXI + 100:
			Tips.showToastTips(context, "���Ѿ��ڵ��ٵ�ʿҳ��");
			result = null;
			break;
		case ITEM_CARFANS:
			Tips.showToastTips(context, "����򿪳���ҳ��");
			result.setClassName(PACKAGENAME, CLASSNAME_CARFANS);
			break;
		case ITEM_CARFANS + 100:
			Tips.showToastTips(context, "���Ѿ��ڳ���ҳ��");
			result = null;
			break;
		case ITEM_BACKLOGIN:
			Tips.showToastTips(context, "���ڷ��ص�¼ҳ");
			result.setClassName(PACKAGENAME, CLASSNAME_LOGIN);
			break;
		default:
			result = null;
			break;
		}
		return result;
	}
}

package com.ipin.utils;

import java.util.ArrayList;
import java.util.HashMap;

import com.ipin.activity.Login;
import com.ipin.activity.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleAdapter;

/**
 * @author WhiteLok
 * 
 */
public class InitTools {

	public static void checkNetState(final Context context) {
		if (!isConnectNetwork(context)) {
			Builder b = new AlertDialog.Builder(context)
					.setTitle("û�п��õ�����").setMessage("�Ƿ������������ã�");
			b.setPositiveButton("��", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					Intent mIntent = new Intent("/");
					ComponentName comp = new ComponentName(
							"com.android.settings",
							"com.android.settings.WirelessSettings");
					mIntent.setComponent(comp);
					mIntent.setAction("android.intent.action.VIEW");
					((Login) context).startActivityForResult(mIntent, 0);
				}
			}).setNeutralButton("��", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.cancel();
				}
			}).show();
		}
		return;
	}

	public static boolean isConnectNetwork(Context context) {
		boolean netSataus = false;
		ConnectivityManager cwjManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		cwjManager.getActiveNetworkInfo();
		if (cwjManager.getActiveNetworkInfo() != null) {
			netSataus = cwjManager.getActiveNetworkInfo().isAvailable();
		}
		return netSataus;
	}

	public static SimpleAdapter initAdapter(Context context, String tag) {
		SimpleAdapter adapter = null;
		if (tag.equals("teammember")) {
			ArrayList<HashMap<String, Object>> tempList = new ArrayList<HashMap<String, Object>>();

			HashMap<String, Object> tempMap1 = new HashMap<String, Object>();
			tempMap1.put("Item_image", R.drawable.usera);
			tempMap1.put("Item_title", "09�Ű�1�����׿�");
			tempList.add(tempMap1);

			HashMap<String, Object> tempMap2 = new HashMap<String, Object>();
			tempMap2.put("Item_image", R.drawable.userb);
			tempMap2.put("Item_title", "ׯ��");
			tempList.add(tempMap2);

			HashMap<String, Object> tempMap3 = new HashMap<String, Object>();
			tempMap3.put("Item_image", R.drawable.userc);
			tempMap3.put("Item_title", "�⿭��");
			tempList.add(tempMap3);

			HashMap<String, Object> tempMap4 = new HashMap<String, Object>();
			tempMap4.put("Item_image", R.drawable.userd);
			tempMap4.put("Item_title", "������");
			tempList.add(tempMap4);

			HashMap<String, Object> tempMap5 = new HashMap<String, Object>();
			tempMap5.put("Item_image", R.drawable.usere);
			tempMap5.put("Item_title", "��÷��");
			tempList.add(tempMap5);

			HashMap<String, Object> tempMap6 = new HashMap<String, Object>();
			tempMap6.put("Item_image", R.drawable.userf);
			tempMap6.put("Item_title", "κ��");
			tempList.add(tempMap6);

			HashMap<String, Object> tempMap7 = new HashMap<String, Object>();
			tempMap7.put("Item_image", R.drawable.userg);
			tempMap7.put("Item_title", "�μҺ�");
			tempList.add(tempMap7);

			HashMap<String, Object> tempMap8 = new HashMap<String, Object>();
			tempMap8.put("Item_image", R.drawable.userh);
			tempMap8.put("Item_title", "�齡��");
			tempList.add(tempMap8);

			HashMap<String, Object> tempMap9 = new HashMap<String, Object>();
			tempMap9.put("Item_image", R.drawable.useri);
			tempMap9.put("Item_title", "�ϱ���");
			tempList.add(tempMap9);

			HashMap<String, Object> tempMap10 = new HashMap<String, Object>();
			tempMap10.put("Item_image", R.drawable.userj);
			tempMap10.put("Item_title", "��Զ��");
			tempList.add(tempMap10);

			adapter = new SimpleAdapter(context, tempList,
					R.layout.introduce_team_listview, new String[] {
							"Item_image", "Item_title" }, new int[] {
							R.id.team_item_image, R.id.team_item_name });
		}
		if (tag.equals("sharecartips")) {
			ArrayList<HashMap<String, Object>> tempList = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> tempMap1 = new HashMap<String, Object>();
			tempMap1.put("Item_title", "ƴ����ȫС�ٿ�");
			tempMap1.put("Item_digest", "ƴ���İ�ȫ�����Ǹ�λ��������ĵ�����...");
			tempList.add(tempMap1);

			HashMap<String, Object> tempMap2 = new HashMap<String, Object>();
			tempMap2.put("Item_title", "�̶��ĳ��ѣ����ϵİ�ȫ");
			tempMap2.put("Item_digest", "�����ȶ���ƴ�����������������...");
			tempList.add(tempMap2);

			HashMap<String, Object> tempMap3 = new HashMap<String, Object>();
			tempMap3.put("Item_title", "����ƶ�����ȫ��ƴ��Э��");
			tempMap3.put("Item_digest", "ƴ���ĳɰܴܺ�̶���ȡ����ƴ��Э����ƶ�...");
			tempList.add(tempMap3);

			HashMap<String, Object> tempMap4 = new HashMap<String, Object>();
			tempMap4.put("Item_title", "ƴ��Q&A");
			tempMap4.put("Item_digest", "ƴ����ʶ�ʴ�...");
			tempList.add(tempMap4);

			HashMap<String, Object> tempMap5 = new HashMap<String, Object>();
			tempMap5.put("Item_title", "�����Ч��ʹ�õ��ٵ�ʿ");
			tempMap5.put("Item_digest", "���ٵ�ʿ�Ǳ����������һ������...");
			tempList.add(tempMap5);
			adapter = new SimpleAdapter(context, tempList,
					R.layout.index_sharecartips_listview, new String[] {
							"Item_title", "Item_digest" }, new int[] {
							R.id.tips_item_title, R.id.tips_item_digest });
		}
		if (tag.equals("sharecarinfo")) {
			ArrayList<HashMap<String, Object>> tempList = new ArrayList<HashMap<String, Object>>();

			HashMap<String, Object> tempMap1 = new HashMap<String, Object>();
			tempMap1.put("Item_image", R.drawable.userc);
			tempMap1.put("Item_title", "������°ൽ��خ�����ȶ����");
			tempList.add(tempMap1);

			HashMap<String, Object> tempMap2 = new HashMap<String, Object>();
			tempMap2.put("Item_image", R.drawable.userh);
			tempMap2.put("Item_title", "ִ���Ͻ�ʦ������԰ǰ����ȱ2��");
			tempList.add(tempMap2);

			HashMap<String, Object> tempMap3 = new HashMap<String, Object>();
			tempMap3.put("Item_image", R.drawable.userb);
			tempMap3.put("Item_title", "����ƴ��С�ֶӣ��г������Ա̯���ͷ�");
			tempList.add(tempMap3);

			HashMap<String, Object> tempMap4 = new HashMap<String, Object>();
			tempMap4.put("Item_image", R.drawable.usere);
			tempMap4.put("Item_title", "����ϰ��壬�г���ȱһ��");
			tempList.add(tempMap4);

			HashMap<String, Object> tempMap5 = new HashMap<String, Object>();
			tempMap5.put("Item_image", R.drawable.userj);
			tempMap5.put("Item_title", "5:00pm���ݴ�ѧ���ƻ���");
			tempList.add(tempMap5);
			adapter = new SimpleAdapter(context, tempList,
					R.layout.index_sharecarinfo_listview, new String[] {
							"Item_image", "Item_title" }, new int[] {
							R.id.info_item_image, R.id.info_item_title });
		}
		if (tag.equals("travelinfo")) {
			ArrayList<HashMap<String, Object>> tempList = new ArrayList<HashMap<String, Object>>();

			HashMap<String, Object> tempMap1 = new HashMap<String, Object>();
			tempMap1.put("Item_image", R.drawable.view1);
			tempMap1.put("Item_title", "�ͼ�Χ��");
			tempMap1.put("Item_abstract", "�ͼҷ羰��������380Ԫ");
			tempList.add(tempMap1);

			HashMap<String, Object> tempMap2 = new HashMap<String, Object>();
			tempMap2.put("Item_image", R.drawable.view2);
			tempMap2.put("Item_title", "������ʯ");
			tempMap2.put("Item_abstract", "1����40Ԫ�������");
			tempList.add(tempMap2);

			HashMap<String, Object> tempMap3 = new HashMap<String, Object>();
			tempMap3.put("Item_image", R.drawable.view3);
			tempMap3.put("Item_title", "����ɽ�ʾ�");
			tempMap3.put("Item_abstract", "�Ź��ۣ���������");
			tempList.add(tempMap3);

			HashMap<String, Object> tempMap4 = new HashMap<String, Object>();
			tempMap4.put("Item_image", R.drawable.view4);
			tempMap4.put("Item_title", "���ܹ�ͤ");
			tempMap4.put("Item_abstract", "����Ż�5�����ϰ��");
			tempList.add(tempMap4);

			HashMap<String, Object> tempMap5 = new HashMap<String, Object>();
			tempMap5.put("Item_image", R.drawable.view5);
			tempMap5.put("Item_title", "��¡ˮ������");
			tempMap5.put("Item_abstract", "�ڲ���Ʊ���㶮��");
			tempList.add(tempMap5);

			HashMap<String, Object> tempMap6 = new HashMap<String, Object>();
			tempMap6.put("Item_image", R.drawable.view6);
			tempMap6.put("Item_title", "������¥");
			tempMap6.put("Item_abstract", "������1���Σ���Խ��ɽ������");
			tempList.add(tempMap6);

			HashMap<String, Object> tempMap7 = new HashMap<String, Object>();
			tempMap7.put("Item_image", R.drawable.view7);
			tempMap7.put("Item_title", "�麣ʮ�ﳤ̲");
			tempMap7.put("Item_abstract", "������⣬�����۵�ֻҪ38��");
			tempList.add(tempMap7);

			HashMap<String, Object> tempMap8 = new HashMap<String, Object>();
			tempMap8.put("Item_image", R.drawable.view8);
			tempMap8.put("Item_title", "�ӻ�������");
			tempMap8.put("Item_abstract", "����С�������Ծ�ɽ��Ұζ");
			tempList.add(tempMap8);

			HashMap<String, Object> tempMap9 = new HashMap<String, Object>();
			tempMap9.put("Item_image", R.drawable.view9);
			tempMap9.put("Item_title", "����Ͽ");
			tempMap9.put("Item_abstract", "Ư��������һ��");
			tempList.add(tempMap9);

			HashMap<String, Object> tempMap10 = new HashMap<String, Object>();
			tempMap10.put("Item_image", R.drawable.view10);
			tempMap10.put("Item_title", "ʥ�Ĵ����");
			tempMap10.put("Item_abstract", "����������ͬ��");
			tempList.add(tempMap10);

			adapter = new SimpleAdapter(context, tempList,
					R.layout.travel_view_listview, new String[] { "Item_image",
							"Item_title", "Item_abstract" }, new int[] {
							R.id.travel_info_item_image,
							R.id.travel_info_item_title,
							R.id.travel_info_item_abstract });
		}
		if (tag.equals("carfans")) {
			ArrayList<HashMap<String, Object>> tempList = new ArrayList<HashMap<String, Object>>();

			HashMap<String, Object> tempMap1 = new HashMap<String, Object>();
			tempMap1.put("Item_image", R.drawable.usera);
			tempMap1.put("Item_title", "09�Ű�1�����׿�");
			tempList.add(tempMap1);

			HashMap<String, Object> tempMap2 = new HashMap<String, Object>();
			tempMap2.put("Item_image", R.drawable.userb);
			tempMap2.put("Item_title", "ׯ��");
			tempList.add(tempMap2);

			HashMap<String, Object> tempMap3 = new HashMap<String, Object>();
			tempMap3.put("Item_image", R.drawable.userc);
			tempMap3.put("Item_title", "�⿭��");
			tempList.add(tempMap3);

			HashMap<String, Object> tempMap4 = new HashMap<String, Object>();
			tempMap4.put("Item_image", R.drawable.userd);
			tempMap4.put("Item_title", "������");
			tempList.add(tempMap4);

			HashMap<String, Object> tempMap5 = new HashMap<String, Object>();
			tempMap5.put("Item_image", R.drawable.usere);
			tempMap5.put("Item_title", "��÷��");
			tempList.add(tempMap5);

			HashMap<String, Object> tempMap6 = new HashMap<String, Object>();
			tempMap6.put("Item_image", R.drawable.userf);
			tempMap6.put("Item_title", "κ��");
			tempList.add(tempMap6);

			HashMap<String, Object> tempMap7 = new HashMap<String, Object>();
			tempMap7.put("Item_image", R.drawable.userg);
			tempMap7.put("Item_title", "�μҺ�");
			tempList.add(tempMap7);

			HashMap<String, Object> tempMap8 = new HashMap<String, Object>();
			tempMap8.put("Item_image", R.drawable.userh);
			tempMap8.put("Item_title", "�齡��");
			tempList.add(tempMap8);

			HashMap<String, Object> tempMap9 = new HashMap<String, Object>();
			tempMap9.put("Item_image", R.drawable.useri);
			tempMap9.put("Item_title", "�ϱ���");
			tempList.add(tempMap9);

			HashMap<String, Object> tempMap10 = new HashMap<String, Object>();
			tempMap10.put("Item_image", R.drawable.userj);
			tempMap10.put("Item_title", "��Զ��");
			tempList.add(tempMap10);

			adapter = new SimpleAdapter(context, tempList,
					R.layout.introduce_team_listview, new String[] {
							"Item_image", "Item_title" }, new int[] {
							R.id.team_item_image, R.id.team_item_name });
		}
		return adapter;
	}

	// �б�ĸ��°�ť
	public static Button initUpdateButton(final Context context,
			final String tag) {
		Button result = new Button(context);
		result.setText("����");
		result.getBackground().setAlpha(100);
		if (tag.equals("ShareCarInfo")) {
			result.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Tips.showToastTips(context, "���ڿ�ʼ����ƴ����Ϣ");
					new Thread() {
						@Override
						public void run() {

							for (int i = 0; i < 10; i++) {
								Log.i("ShareCarInfo", "have click");
							}
						}
					}.start();
				}

			});
		}
		if (tag.equals("ShareCarTips")) {
			result.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Tips.showToastTips(context, "���ڿ�ʼ����ƴ��tips");
					new Thread() {
						@Override
						public void run() {

							for (int i = 0; i < 10; i++) {
								Log.i("ShareCarTips", "have click");
							}

						}
					}.start();

				}

			});
		}
		return result;
	}

}

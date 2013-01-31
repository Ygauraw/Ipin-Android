package com.ipin.application;

import java.util.ArrayList;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import com.ipin.beans.User;

import android.app.Activity;
import android.app.Application;

public class IpinApplication extends Application {

	// �����Ѿ���¼���û����
	private User user = null;
	// ���紫������
	private HttpClient httpClient = null;
	// �����Ѿ��򿪵�ҳ��
	private ArrayList<Activity> activityList = null;
	// 
	private int Index_SharecarTips_start;
	private int Index_SharecarTips_size;

	@Override
	public void onCreate() {
		super.onCreate();
		httpClient = createHttpClient();
		user = createUser();
		activityList = new ArrayList<Activity>();
		Index_SharecarTips_start = 1;
		Index_SharecarTips_size = 10;
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		shutdownHttpClient();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		shutdownHttpClient();
	}

	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public HttpClient getHttpClient() {
		return httpClient;
	}

	private HttpClient createHttpClient() {
		// TODO Auto-generated method stub
		HttpParams httpParams = new BasicHttpParams();
		HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(httpParams,
				HTTP.DEFAULT_CONTENT_CHARSET);
		HttpProtocolParams.setUseExpectContinue(httpParams, true);
		HttpConnectionParams.setConnectionTimeout(httpParams, 20 * 1000);   
		HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);   
		HttpConnectionParams.setSocketBufferSize(httpParams, 8192); 
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("https", SSLSocketFactory
				.getSocketFactory(), 443));
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		ClientConnectionManager clientConnectionManager = new ThreadSafeClientConnManager(
				httpParams, schemeRegistry);
		HttpClient client = new DefaultHttpClient(clientConnectionManager, httpParams);
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
		return client;
	}

	public void shutdownHttpClient() {
		if (httpClient != null && httpClient.getConnectionManager() != null) {
			httpClient.getConnectionManager().shutdown();
		}
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public User createUser() {
		return new User();
	}

	public void logoutUser() {
		user = null;
	}

	public void setActivityList(ArrayList<Activity> activityList) {
		this.activityList = activityList;
	}

	public ArrayList<Activity> getActivityList() {
		return activityList;
	}

	public ArrayList<Activity> createActivityList() {
		return new ArrayList<Activity>();
	}

	public void deleteActivityList() {
		activityList = null;
	}

	public void setIndex_SharecarTips_start(int index_SharecarTips_start) {
		Index_SharecarTips_start = index_SharecarTips_start;
	}

	public int getIndex_SharecarTips_start() {
		return Index_SharecarTips_start;
	}

	public void setIndex_SharecarTips_size(int index_SharecarTips_size) {
		Index_SharecarTips_size = index_SharecarTips_size;
	}

	public int getIndex_SharecarTips_size() {
		return Index_SharecarTips_size;
	}
}

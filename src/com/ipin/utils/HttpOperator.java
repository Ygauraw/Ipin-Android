package com.ipin.utils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;

public class HttpOperator {

	public static String httpPost(String URL, List<NameValuePair> params,
			HttpClient httpClient) throws ClientProtocolException, IOException,
			SocketTimeoutException {
		HttpPost httpPost = new HttpPost(URL);
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params);
		httpPost.setEntity(entity);
		HttpResponse response = httpClient.execute(httpPost);
		if (response != null && response.getStatusLine().getStatusCode() == 200) {
			String responseString = EntityUtils.toString(response.getEntity());
			return responseString;
		}
		return null;
	}

	public static String httpGet(String URL, HttpClient httpClient)
			throws URISyntaxException, ClientProtocolException, IOException,
			SocketTimeoutException {
		HttpGet httpGet = new HttpGet(URL);
		HttpResponse response = httpClient.execute(httpGet);
		if (response.getStatusLine().getStatusCode() == 200) {
			String responseString = EntityUtils.toString(response.getEntity());
			return responseString;
		}
		return null;
	}

}

package com.ipin.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class ParseJson {

	public static JSONObject parseString(String string) throws JSONException{
		JSONObject jsonObject = new JSONObject(string);
		return jsonObject;
	}
}

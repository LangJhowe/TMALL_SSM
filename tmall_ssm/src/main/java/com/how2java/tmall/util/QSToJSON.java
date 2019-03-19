package com.how2java.tmall.util;
/** 
* @author Jhowe
* @version 2019年3月20日 上午12:17:54
* tmall_ssm
*/

import com.alibaba.fastjson.JSONObject;

public class QSToJSON {
	public static JSONObject toJSON(String param) {
		JSONObject jo = new JSONObject();
		String[] valueAndKey = param.split("&");
		for(String str : valueAndKey) {
			String[] vk = str.split("=");
			String key = vk[0];
			String value = vk[1];
			jo.put(key, value);
		}
		return jo;
	}
}

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
		if(param.indexOf("&")>0) {
			String[] valueAndKey = param.split("&");
			System.out.println(valueAndKey.length);
			for(String str : valueAndKey) {
				String[] vk = str.split("=");
				String key = vk[0];
				String value="";
				if(vk.length==1) {
					value = "";
				}else if(vk.length==2){
					value = vk[1];
				}else {
					for(int i =1;i<vk.length;i++) {
						value+=vk[i];
					}
				}
				jo.put(key, value);
			}
		}else if(param.indexOf("=")>0) {
			String[] vk = param.split("=");
			String key = vk[0];
			String value="";
			if(vk.length==1) {
				value = "";
			}else if(vk.length==2){
				value = vk[1];
			}else {
				for(int i =1;i<vk.length;i++) {
					value+=vk[i];
				}
			}
			jo.put(key, value);
		}

		return jo;
	}
}

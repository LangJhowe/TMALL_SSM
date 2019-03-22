package com.how2java.tmall.util;
/** 
* @author Jhowe
* @version 2019年3月20日 上午12:17:54
* tmall_ssm
*/

import java.net.URLDecoder;

import com.alibaba.fastjson.JSONObject;

public class QSToJSON {
	public static JSONObject toJSON(String param) {
		String dParam = URLDecoder.decode(param);
		JSONObject jo = new JSONObject();
		if(dParam.indexOf("&")>0) {
			// split by &
			String[] valueAndKey = dParam.split("&");
			System.out.println(valueAndKey.length);
			for(String str : valueAndKey) {
				String[] vk = str.split("=");
				String key = vk[0];
//				if(isArray(vk[0])) {
//					
//				}


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
		}else if(dParam.indexOf("=")>0) {
			String[] vk = dParam.split("=");
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
	
//	public static String getType(String str) {
//		return true;
//	}
}

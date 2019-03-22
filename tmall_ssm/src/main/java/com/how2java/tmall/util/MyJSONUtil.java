package com.how2java.tmall.util;

import java.net.URLDecoder;

import org.springframework.context.support.StaticApplicationContext;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/** 
* @author Jhowe
* @version 2019年3月22日 下午8:34:49
* tmall_ssm
*/
public class MyJSONUtil {
	private static boolean hasThatKey = false;
	private static boolean hasThatValue = false;
	private static JSONObject tjo = new JSONObject();
	private static JSONObject rjo = new JSONObject();
	
	public static void setJo (JSONObject jo) {
		tjo = jo;
		rjo.clear();
	}
	public static boolean isContainKey(String key) {
		boolean b =tjo.containsKey(key);
		hasThatKey = b;
		if(!b) {
			rjo.put("msg","缺少字段" + key);
		}
		return b;
	}
	public static boolean keyHasValue(String key) {
		if(!isContainKey(key)) return false;
		if(tjo.get(key).getClass().getSimpleName().equals("JSONArray")) {
			JSONArray ajo = (JSONArray) tjo.get(key);
			if(ajo.size()==0) {
				rjo.put("msg",key + "数组长度不能为0");
				return false;
			}
			return true;
		}
		String vStr =tjo.get(key).toString();

		if(vStr=="") {
			rjo.put("msg","缺少参数" + key);
			return false;
		}
		return true;
	}
	public static String getErrorResponse(String code) {
		if(!hasThatKey) rjo.put("code", code);
		if(!hasThatValue) rjo.put("code", code);
		return JSONObject.toJSONString(rjo).toString();
	}
	public static String getErrorResponse(int code) {
		if(!hasThatKey) rjo.put("code", "" + code);
		if(!hasThatValue) rjo.put("code", "" + code);
		return JSONObject.toJSONString(rjo).toString();
	}
	public static String getErrorResponse(String code, String msg) {
		if(!hasThatKey) rjo.put("code", code);
		if(!hasThatValue) rjo.put("code", code);
		rjo.put("msg",msg);
		return JSONObject.toJSONString(rjo).toString();
	}
	public static String getErrorResponse(int code,String msg) {
		if(!hasThatKey) rjo.put("code", "" + code);
		if(!hasThatValue) rjo.put("code", "" + code);
		rjo.put("msg",msg);
		return JSONObject.toJSONString(rjo).toString();
	}
	
	public static JSONObject qsToJSON(String qsStr) {
		String dParam = URLDecoder.decode(qsStr);
		return QSToJSON.toJSON(dParam);
	}
}

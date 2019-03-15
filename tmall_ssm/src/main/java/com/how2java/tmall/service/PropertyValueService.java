package com.how2java.tmall.service;

import java.util.List;

import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.PropertyValue;

/** 
* @author Jhowe
* @version 2019年3月12日 下午7:25:15
* tmall_ssm
*/
public interface PropertyValueService {
	void init(Product p);
	void update(PropertyValue pv);
	
	PropertyValue get(int ptid,int pid);
	List<PropertyValue> list(int pid);
}

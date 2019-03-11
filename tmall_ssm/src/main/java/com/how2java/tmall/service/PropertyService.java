package com.how2java.tmall.service;

import java.util.List;

import com.how2java.tmall.pojo.Property;

/** 
* @author Jhowe
* @version 2019年3月11日 下午5:12:44
* tmall_ssm
*/
public interface PropertyService {
	void add(Property p);
	void delete(int id);
	void update(Property c);
	Property get(int id);
	List<Property> list(int cid);
	
}

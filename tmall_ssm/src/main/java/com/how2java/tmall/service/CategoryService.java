package com.how2java.tmall.service;
/** 
* @author Jhowe
* @version 2019年3月10日 上午3:51:36
* tmall_ssm
*/

import java.util.List;

import com.how2java.tmall.pojo.Category;

public interface CategoryService {
	//int total();
	//List<Category> list(Page page);
	List<Category> list();
	void add(Category category);
	
	void delete(int id);
	
	Category get(int id);
	
	void update(Category category);
}

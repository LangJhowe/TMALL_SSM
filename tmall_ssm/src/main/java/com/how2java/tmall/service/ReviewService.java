package com.how2java.tmall.service;

import java.util.List;

import com.how2java.tmall.pojo.Review;

/** 
* @author Jhowe
* @version 2019年3月17日 下午5:59:05
* tmall_ssm
*/
public interface ReviewService {
	void add(Review c);
	void delete(int id);
	void update(Review c);
	Review get(int id);
	List<Review> list(int id);
	
	int getCount(int pid);
}

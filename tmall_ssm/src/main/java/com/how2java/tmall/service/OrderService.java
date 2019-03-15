package com.how2java.tmall.service;
/** 
* @author Jhowe
* @version 2019年3月14日 下午4:12:52
* tmall_ssm
*/

import java.util.List;

import com.how2java.tmall.pojo.Order;

public interface OrderService {

	String waitPay = "waitPay";
	String waitDelivery = "waitDelivery";
	String waitConfirm = "waitConfirm";
	String waitReview = "waitReview";
	String finish = "finish";
	String delete = "delete";
	
	void add(Order c);
	
	void delete(int id);
	void update(Order c);
	Order get(int id);
	List<Order> list();
}

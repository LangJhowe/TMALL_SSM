package com.how2java.tmall.service;
/** 
* @author Jhowe
* @version 2019年3月14日 下午4:12:52
* tmall_ssm
*/

import java.util.List;

import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderItem;

public interface OrderService {

	String waitPay = "waitPay";
	String waitDelivery = "waitDelivery";
	String waitConfirm = "waitConfirm";
	String waitReview = "waitReview";
	String finish = "finish";
	String delete = "delete";
	
	void add(Order c);
    float add(Order c,List<OrderItem> ois);
	void delete(int id);
	void update(Order c);
	Order get(int id);
	List<Order> list();
	List list(int uid, String excludedStatus);
}

package com.how2java.tmall.service;

import java.util.List;

import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderItem;

/** 
* @author Jhowe
* @version 2019年3月14日 下午3:56:31
* tmall_ssm
*/
public interface OrderItemService {
	void add(OrderItem o);
	void delete(int id);
	void update(OrderItem o);
	OrderItem get(int id);
	List<OrderItem> list();
	
	void fill(List<Order> os);
	
	void fill(Order o);
	
	int getSaleCount(int pid);
	
	List<OrderItem> listByUser(int uid);
}

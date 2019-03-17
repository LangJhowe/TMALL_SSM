package com.how2java.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.mapper.OrderMapper;
import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderExample;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.service.OrderService;
import com.how2java.tmall.service.UserService;

/** 
* @author Jhowe
* @version 2019年3月14日 下午4:15:48
* tmall_ssm
*/
@Service
public class OrderServiceImpl implements OrderService{
	@Autowired
	OrderMapper orderMapper;
	@Autowired
	UserService userService;
	
	@Override
	public void add(Order o) {
		orderMapper.insert(o);
	}

	@Override
	public void delete(int id) {
		orderMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(Order o) {
		orderMapper.updateByPrimaryKeySelective(o);
	}

	@Override
	public Order get(int id) {
		return orderMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Order> list() {
		OrderExample example = new OrderExample();
		example.setOrderByClause("id desc");
		List<Order> result = orderMapper.selectByExample(example);
		setUser(result);
		return result;
	}
	
	public void setUser(List<Order> os) {
		for(Order o:os) {
			setUser(o);
		}
	}
	
	public void setUser(Order o) {
		int uid = o.getUid();
		User u = userService.get(uid);
		o.setUser(u);
	}
	
}
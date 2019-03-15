package com.how2java.tmall.service.impl;
/** 
* @author Jhowe
* @version 2019年3月12日 下午11:09:51
* tmall_ssm
*/

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.mapper.UserMapper;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.pojo.UserExample;
import com.how2java.tmall.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UserMapper userMapper;

	@Override
	public void add(User u) {
		userMapper.insert(u);
	}

	@Override
	public void delete(int id) {
		userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(User u) {
		userMapper.updateByPrimaryKeySelective(u);
	}

	@Override
	public User get(int id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<User> list() {
		UserExample example = new UserExample();
		example.setOrderByClause("id desc");
		return userMapper.selectByExample(example);
	}
	
	
}

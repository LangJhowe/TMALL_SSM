package com.how2java.tmall.service.impl;
/** 
* @author Jhowe
* @version 2019��3��10�� ����3:52:37
* tmall_ssm
*/

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.mapper.CategoryMapper;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	CategoryMapper categoryMapper;
	/*
	 * public List<Category> list(Page page){ return categoryMapper.list(page); }
	 */
	public List<Category> list(){
		return categoryMapper.list();
	}

	/*
	 * @Override public int total() { return categoryMapper.total(); }
	 */
	@Override
	public void add(Category category) {
		categoryMapper.add(category);
	}
	@Override
	public void delete(int id) {
		categoryMapper.delete(id);
	}
	@Override
	public Category get(int id) {
		return categoryMapper.get(id);
	}
	@Override
	public void update(Category category) {
		categoryMapper.update(category);
		
	}
	
}

package com.how2java.tmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.service.CategoryService;

/** 
* @author Jhowe
* @version 2019年3月10日 上午3:54:45
* tmall_ssm
*/
@Controller
@RequestMapping("")
public class CategoryController {
	@Autowired
	CategoryService categoryService;
	
	@RequestMapping("admin_category_list")
	public String list(Model model) {
		List<Category> cs = categoryService.list();
		model.addAttribute("cs", cs);
		return "admin/listCategory";
	}
}

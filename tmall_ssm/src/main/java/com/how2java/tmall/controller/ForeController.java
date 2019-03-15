package com.how2java.tmall.controller;

import com.alibaba.fastjson.JSONObject;
import com.how2java.tmall.mapper.CategoryMapper;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.CategoryExample;
import com.how2java.tmall.pojo.CategoryExample.Criteria;
import com.how2java.tmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("")
public class ForeController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;

    @ResponseBody
    @RequestMapping(value="/getRecommendSearch", method=RequestMethod.GET, produces={ "application/json;charset=UTF-8" })
    public String home(Model model) {
    	JSONObject jo = new JSONObject();
    	List<Category> cs= categoryService.list();
    	List<Category> result = cs.subList(0, 4);
    	jo.put("code", "000000");
    	jo.put("data", cs);
    	return JSONObject.toJSON(jo).toString();
    }

}

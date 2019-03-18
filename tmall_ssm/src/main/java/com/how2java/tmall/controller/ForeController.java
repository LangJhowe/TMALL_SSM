package com.how2java.tmall.controller;

import com.alibaba.fastjson.JSONObject;
import com.how2java.tmall.mapper.CategoryMapper;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.CategoryExample;
import com.how2java.tmall.pojo.ProductExample;
import com.how2java.tmall.pojo.CategoryExample.Criteria;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.ArrayList;
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
    	jo.put("code", "000000");
    	jo.put("data", cs);
    	return JSONObject.toJSON(jo).toString();
    }
    
    @ResponseBody
    @RequestMapping(value="/getCategorys/cid={cid}", method=RequestMethod.GET, produces= {"application/json;charset=UTF-8"})
    public String getCategorys(@PathVariable("cid") Integer cid) throws Exception {
		
		  JSONObject jo = new JSONObject();
		  List<Product> ps= productService.list(cid);
		  List<JSONObject> pjos = new ArrayList<JSONObject>();
		  for(Product p:ps) {
			  JSONObject itemJo = new JSONObject();
			  String title= p.getSubTitle().split(" ")[0];
			  itemJo.put("pid", p.getId());
			  itemJo.put("title",title);
			  pjos.add(itemJo);
		  }
		  jo.put("code", "000000");
		  jo.put("data", pjos);
		  
		  Category c = categoryService.get(cid);
		  
		  productService.fill(c);
		  productService.fillByRow(c);
		  List<List<Product>> llps = c.getProductsByRow();
		  JSONObject test = new JSONObject();
		  int rows = llps.size();
		  for(int i = 0; i < rows;i++) {
			  String str = "";
			  for(int j = 0; j < llps.get(i).size();j++) {
				  str+=( j + ":" + llps.get(i).get(j).getSubTitle().split(" ")[0] + ';');
			  }
			  test.put("line"+i,str);
		  }
		  jo.put("javaHandle",test);
		  return JSONObject.toJSON(jo).toString();
    }
    
    @ResponseBody
    @RequestMapping(value="/getProductsByCategory",method=RequestMethod.GET,produces= {"application/json;charset=UTF-8"})
    public String getProductsByCategory() {
    	JSONObject jo = new JSONObject();
    	try {
        	List<Category> cs = categoryService.list();
        	productService.fill(cs);
    		jo.put("code", "000000");
    		jo.put("data", cs);
		} catch (Exception e) {
    		jo.put("code", "123456");
    		jo.put("messge", e.toString());
		}
    	return JSONObject.toJSONString(jo).toString();
     }
    

}

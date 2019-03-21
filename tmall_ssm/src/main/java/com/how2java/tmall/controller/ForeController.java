package com.how2java.tmall.controller;

import com.alibaba.druid.support.monitor.annotation.MField;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.how2java.tmall.mapper.CategoryMapper;
import com.how2java.tmall.mapper.ProductMapper;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.CategoryExample;
import com.how2java.tmall.pojo.ProductExample;
import com.how2java.tmall.pojo.ProductImage;
import com.how2java.tmall.pojo.PropertyValue;
import com.how2java.tmall.pojo.Review;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.pojo.CategoryExample.Criteria;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.how2java.tmall.util.MyPage;
import com.how2java.tmall.util.Page;
import com.how2java.tmall.util.QSToJSON;

import comparator.ProductAllComparator;
import comparator.ProductDateComparator;
import comparator.ProductPriceComparator;
import comparator.ProductReviewComparator;
import comparator.ProductSaleCountComparator;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    ProductMapper productMapper;
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
    @Autowired
    ReviewService reviewService;
    
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
    
    @RequestMapping(value="/loginByUser",method=RequestMethod.POST,produces= {"application/json;charset=UTF-8"})
    @ResponseBody
    public String loginByUser(@RequestBody String param) {
    	JSONObject upjo = QSToJSON.toJSON(param);
    	JSONObject resultJo = new JSONObject();
    	if(!upjo.containsKey("name")) {
    		resultJo.put("code", "600001");
    		resultJo.put("msg", "缺少字段name");
    	}else if(!upjo.containsKey("password")){
    		resultJo.put("code", "600002");
    		resultJo.put("msg", "缺少字段password");
    	}else if(!upjo.containsKey("name")&&!upjo.containsKey("password")) {
    		resultJo.put("code", "600003");
    		resultJo.put("msg", "缺少字段name,password");
    	}else {
        	String username = upjo.get("name").toString();
        	String password = upjo.get("password").toString();
        	User user = userService.get(username,password);

        	if(null == user) {
        		resultJo.put("code", "600004");
        		resultJo.put("msg", "账号密码错误");
        	}else {
        		resultJo.put("code", "000000");
            	resultJo.put("data", user);
        	}
    	}

    	return JSONObject.toJSONString(resultJo).toString();
     }
    @ResponseBody
    @RequestMapping(value="/registry",method=RequestMethod.POST, produces= {"application/json;charset=UTF-8"})
    public String registry(@RequestBody String param) {
    	JSONObject pjo = new JSONObject();
    	JSONObject jo = new JSONObject();
    	String[] up = param.split("&");
    	for(String s:up) {
    		String key = s.split("=")[0];
    		String value = s.split("=")[1];
    		pjo.put(key,value);
    	}
    	if(!pjo.containsKey("name")&&!pjo.containsKey("password")){
    		jo.put("code","600010");
    		jo.put("msg","缺少字段name、password");	   		
    	}else if(!pjo.containsKey("name")) {
    		jo.put("code","600011");
    		jo.put("msg","缺少字段name");	
    	}else if(!pjo.containsKey("password")){
    		jo.put("code","600012");
    		jo.put("msg","缺少字段password");		
    	}else if(jo.get("name") == ""&&jo.get("password") =="") {
    		jo.put("code","600013");
    		jo.put("msg","用户名和密码不能为空");
    	} else if(jo.get("name") == "") {
    		jo.put("code","600014");
    		jo.put("msg","密码不能为空");
    	} else if(jo.get("password") == ""){
    		jo.put("code","600015");
    		jo.put("msg","用户名和密码不能为空");
    	}else {
    		String newName = pjo.get("name").toString();
    		boolean exist = userService.isExist(newName);
    		if(exist) {
    			jo.put("code", "600016");
    			jo.put("msg", "用户名已经被使用,不能使用");
    		}else {
            	User newUser = new User();
            	newUser.setName(pjo.get("name").toString());
            	newUser.setPassword(pjo.get("password").toString());
            	System.out.println(newUser.getName());
            	userService.add(newUser);
            	jo.put("code", "000000");
            	jo.put("msg","成功创建用户");	
    		}

    	}
    	return JSONObject.toJSONString(jo).toString();
    }
    //keywords查询商品
    @ResponseBody
    @RequestMapping(value="/searchByKeywords", method=RequestMethod.POST,produces= {"application/json;charset=UTF-8"})
    public String searchByKeywords(@RequestBody String param) throws Exception {
    	JSONObject rjo = new JSONObject();
    	JSONObject kjo = QSToJSON.toJSON(param);
    	if(!kjo.containsKey("keyword")) {
        	rjo.put("code", "600021");
        	rjo.put("msg","缺少字段keyword");	
    	} else {
    		if(kjo.get("keywowrd") == "") {
    			rjo.put("code", "600022");
            	rjo.put("msg","缺少参数keyword");		
    		}else {
        		Page page = new Page();
        		int pageSize = 16;
        		if(!kjo.containsKey("page")) {
        			page.setStart(0);
        		}else {
        			int requestPageStart = Integer.valueOf(kjo.get("page").toString());
        			int pageStart = requestPageStart>0?requestPageStart-1:0;
        			page.setStart(pageStart*pageSize);
        		}
        		PageHelper.offsetPage(page.getStart(), pageSize);
        		
        		String keyword = URLDecoder.decode(kjo.get("keyword").toString(), "UTF-8");
        		List<Product> ps = productService.search(keyword);
        		int total = (int) new PageInfo<>(ps).getTotal();
        		int pageNum = (int) new PageInfo<>(ps).getPageNum();
        		int pages = (int) new PageInfo<>(ps).getPages();
        		int size = (int) new PageInfo<>(ps).getPageSize();
        		
        		productService.setSaleAndReviewNumber(ps);
            	rjo.put("code", "000000");
            	rjo.put("data",ps);
            	rjo.put("total", total);
            	rjo.put("pageNum", pageNum);
            	rjo.put("pages", pages);
            	rjo.put("size",size);
    		}

    	}
    	
    	return JSONObject.toJSONString(rjo).toString();
    }
    
    @ResponseBody
    @RequestMapping(value="/searchByCategory", method=RequestMethod.POST,produces= {"application/json;charset=UTF-8"})
    public String searchByCategory(@RequestBody String param) {
    	JSONObject rjo = new JSONObject();
    	JSONObject gjo = QSToJSON.toJSON(param);
    	if(!gjo.containsKey("cid")) {
    		rjo.put("code", "600031");
    		rjo.put("msg", "缺少字段cid");
    	} else {
    		String sCid = (String) gjo.get("cid");
    		if(sCid=="") {
    			rjo.put("code", "600032");
    			rjo.put("msg","缺少参数cid");
    		}else {
        		int cid= Integer.parseInt(sCid);
    			Category c = categoryService.get(cid); 
    			if(null == c) c = categoryService.get(cid);
    			productService.fill(c);
    			List<Product> ps = c.getProducts();
    			productService.setFirstProductImage(ps);
    			productService.setSaleAndReviewNumber(ps);
    			String sort;
    			if(!gjo.containsKey("sort")) {
    				sort = "all";
    			}else {
    				sort= gjo.get("sort").toString()==""?"all":gjo.get("sort").toString();
    				
    			}
    			//排序
    			if(null!=sort) {
    				switch(sort) {
    					case "review":
    						Collections.sort(ps,new ProductReviewComparator());
    						break;
    					case "date":
    						Collections.sort(ps,new ProductDateComparator());
    						break;
    					case "saleCount":
    						Collections.sort(ps,new ProductSaleCountComparator());
    						break;
    					case "price":
    						Collections.sort(ps,new ProductPriceComparator());
    						break;
    					case "all":
    						Collections.sort(ps,new ProductAllComparator());
    						break;
    				}
    			}
    			
    			// 价格过滤
        		List<Product> rps = ps;
        		if(gjo.containsKey("max")&&gjo.containsKey("min")) {
        			String maxStr = gjo.get("max").toString();
        			String minStr = gjo.get("min").toString();
        			float maxF,minF;
        			if(maxStr!=""&&minStr!="") {
        				try {
        					maxF= Float.parseFloat(maxStr);
						} catch (NumberFormatException e) {
							rjo.put("code", "600033");
							rjo.put("msg", "最大价格限制请输入数字");
							return JSONObject.toJSONString(rjo).toString();
						}
            			
        				try {
        					minF= Float.parseFloat(minStr);
						} catch (NumberFormatException e) {
							rjo.put("code", "600034");
							rjo.put("msg", "最小价格限制请输入数字");
							return JSONObject.toJSONString(rjo).toString();
						}
						if(maxF<minF) {
        					Float temp = maxF;
        					maxF = minF;
        					minF = temp;
        				}
						final float ffmax = maxF;
						final float ffmin = minF;
						List<Product> psMaxFilter = ps.stream().filter(new Predicate<Product>() {
							@Override
							public boolean test(Product t) {
								return t.getPromotePrice()<=ffmax;
							}
						}).collect(Collectors.toList());
						rps = psMaxFilter.stream().filter(new Predicate<Product>() {

							@Override
							public boolean test(Product t) {
								return  t.getPromotePrice()>=ffmin;
							}
						}).collect(Collectors.toList());
        			}else if(maxStr!="") {
        				maxF= Float.parseFloat(maxStr);
        				final float ffmax = maxF;
						rps = ps.stream().filter(new Predicate<Product>() {

							@Override
							public boolean test(Product t) {
								return  t.getPromotePrice()<=ffmax;
							}
						}).collect(Collectors.toList());
        			}else if(minStr!="") {
        				minF= Float.parseFloat(minStr);
        				final float ffmin = minF;
						rps = ps.stream().filter(new Predicate<Product>() {

							@Override
							public boolean test(Product t) {
								return  t.getPromotePrice()>=ffmin;
							}
						}).collect(Collectors.toList());
        			}
        		}else if(gjo.containsKey("max")&&gjo.get("max")!="") {
        			String maxStr = gjo.get("max").toString();
    				float maxF;
    				try {
    					maxF= Float.parseFloat(maxStr);
					} catch (NumberFormatException e) {
						rjo.put("code", "600033");
						rjo.put("msg", "最大价格限制请输入数字");
						return JSONObject.toJSONString(rjo).toString();
					}
					rps = ps.stream().filter(new Predicate<Product>() {

						@Override
						public boolean test(Product t) {
							return  t.getPromotePrice()<=maxF;
						}
					}).collect(Collectors.toList());
        		}else if(gjo.containsKey("min")&&gjo.get("min")!="") {
        			String minStr = gjo.get("min").toString();
    				float minF;
    				try {
    					minF= Float.parseFloat(minStr);
					} catch (NumberFormatException e) {
						rjo.put("code", "600033");
						rjo.put("msg", "最大价格限制请输入数字");
						return JSONObject.toJSONString(rjo).toString();
					}
					rps = ps.stream().filter(new Predicate<Product>() {

						@Override
						public boolean test(Product t) {
							return  t.getPromotePrice()>=minF;
						}
					}).collect(Collectors.toList());
        		}
    			
    			
    			int size = 8;
    			long total = rps.size();
    			MyPage page = new MyPage(0,size,total);
    			
    			page.setCurrent(1);
    			if(gjo.containsKey("page")) {
    				int pageNum = Integer.valueOf(gjo.get("page").toString()).intValue();
    				page.setCurrent(pageNum);
    			}
        		List<Product> result = rps.subList(page.getStart(), page.getEnd());
        		
        		

    			rjo.put("code", "000000");
    			rjo.put("data",result);
    			rjo.put("total",page.getTotal());
    			rjo.put("PageNum",page.getCurrent());
    			rjo.put("pages",page.getTotalPage());
    			rjo.put("size",page.getCount());
    		}
    	}
    	return JSONObject.toJSONString(rjo).toString();
    }
    
    @ResponseBody
    @RequestMapping(value="/getProduct",method=RequestMethod.POST,produces
    = {"application/json;charset=UTF-8"})
    public String getProduct(@RequestBody String param) {
    	JSONObject rjo = new JSONObject();
    	JSONObject gjo = QSToJSON.toJSON(param);
    	if(!gjo.containsKey("pid")) {
    		rjo.put("code","600041");
    		rjo.put("msg","缺少字段pid");
    		return JSONObject.toJSONString(rjo).toString();
    	}
    	String pidStr = gjo.get("pid").toString();
    	if(pidStr=="") {
    		rjo.put("code","600042");
    		rjo.put("msg", "缺少参数pid");
    		return JSONObject.toJSONString(rjo).toString();
    	}
    	int pid = Integer.parseInt(pidStr);
    	Product result = productService.get(pid);
    	List<ProductImage> productSingleImages = productImageService.list(pid, ProductImageService.type_single);
    	List<ProductImage> productDetailImages = productImageService.list(pid, ProductImageService.type_detail);
    	result.setProductSingleImages(productSingleImages);
    	result.setProductDetailImages(productDetailImages);
    	productService.setSaleAndReviewNumber(result);
    	List<PropertyValue> pvs = propertyValueService.list(pid);
    	
    	JSONObject djo = new JSONObject();
    	djo.put("propertys", pvs);
    	djo.put("data", result);
    	rjo.put("code", "000000");
    	rjo.put("data",djo);
    	return JSONObject.toJSONString(rjo).toString();
    }
	
	@ResponseBody
	@RequestMapping(value="/getReviews",method=RequestMethod.POST, produces= {"application/json;charset=UTF-8"})
	public String getReviews(@RequestBody String param) {
    	JSONObject rjo = new JSONObject();
    	JSONObject gjo = QSToJSON.toJSON(param);
    	if(!gjo.containsKey("pid")) {
    		rjo.put("code", "600051");
    		rjo.put("msg", "缺少字段pid");
    	}else {
    		String pidStr = gjo.get("pid").toString();
    		if(pidStr=="") {
        		rjo.put("code", "600052");
        		rjo.put("msg", "缺少参数pid");
    		}else {
    			int pid = Integer.parseInt(pidStr);
    			Page page = new Page();
        		int pageSize = 10;
        		if(!gjo.containsKey("page")) {
        			page.setStart(0);
        		}else {
        			int requestPageStart = Integer.valueOf(gjo.get("page").toString());
        			int pageStart = requestPageStart>0?requestPageStart-1:0;
        			page.setStart(pageStart*pageSize);
        		}
        		PageHelper.offsetPage(page.getStart(), pageSize);
        		
    			List<Review> reviews = reviewService.list(pid);
    			reviews.forEach(item->{
    				String name = item.getUser().getName();
    				String firstStr = name.substring(0, 1);
    				String endStr = name.substring(name.length()-2, name.length()-1);
    				item.getUser().setName(firstStr + "***" + endStr);
    			});
        		int total = (int) new PageInfo<>(reviews).getTotal();
        		int pageNum = (int) new PageInfo<>(reviews).getPageNum();
        		int pages = (int) new PageInfo<>(reviews).getPages();
        		int size = (int) new PageInfo<>(reviews).getPageSize();
    			rjo.put("code", "000000");
    			rjo.put("data", reviews);
            	rjo.put("total", total);
            	rjo.put("pageNum", pageNum);
            	rjo.put("pages", pages);
            	rjo.put("size",size);
    		}
    	}
    	
		return JSONObject.toJSONString(rjo).toString();
	}
}

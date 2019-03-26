package com.how2java.tmall.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.mapper.CategoryMapper;
import com.how2java.tmall.mapper.OrderItemMapper;
import com.how2java.tmall.mapper.ProductMapper;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.ProductImage;
import com.how2java.tmall.pojo.PropertyValue;
import com.how2java.tmall.pojo.Review;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderItem;
import com.how2java.tmall.pojo.OrderItemExample;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.service.*;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.how2java.tmall.util.MyJSONUtil;
import com.how2java.tmall.util.MyPage;
import com.how2java.tmall.util.Page;
import com.how2java.tmall.util.QSToJSON;

import comparator.ProductAllComparator;
import comparator.ProductDateComparator;
import comparator.ProductPriceComparator;
import comparator.ProductReviewComparator;
import comparator.ProductSaleCountComparator;


import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
    OrderItemMapper orderItemMapper;
    @Autowired
    ReviewService reviewService;
    
    //首页category
    @ResponseBody
    @RequestMapping(value="/getRecommendSearch", method=RequestMethod.GET, produces={ "application/json;charset=UTF-8" })
    public String home(Model model) {
    	JSONObject jo = new JSONObject();
    	List<Category> cs= categoryService.list();
    	jo.put("code", "000000");
    	jo.put("data", cs);
    	return JSONObject.toJSON(jo).toString();
    }
    
    //分类 带商品subTitle
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
    
    //根据分类获取部分商品 首页
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
    
    //登录
    @RequestMapping(value="/loginByUser",method=RequestMethod.POST,produces= {"application/json;charset=UTF-8"})
    @ResponseBody
    public String loginByUser(@RequestBody String param) {
    	JSONObject gjo = QSToJSON.toJSON(param);
    	JSONObject rjo = new JSONObject();
    	MyJSONUtil.setJo(gjo);
    	if(!MyJSONUtil.isContainKey("name")) return MyJSONUtil.getErrorResponse(600001);
    	if(!MyJSONUtil.keyHasValue("name")) return MyJSONUtil.getErrorResponse(600002);
    	if(!MyJSONUtil.isContainKey("password")) return MyJSONUtil.getErrorResponse(600003);
    	if(!MyJSONUtil.keyHasValue("password")) return MyJSONUtil.getErrorResponse(600004);
    	// 改善 需要验证码captcha
    	String username = gjo.get("name").toString();
    	String password = gjo.get("password").toString();
    	User user = userService.get(username,password);
    	if(null == user) {
    		rjo.put("code", "600004");
    		rjo.put("msg", "账号密码错误");
    	}else {
    		List<OrderItem> ois = orderItemService.listByUser(user.getId());
    		int cartNum = 0;
    		for(OrderItem oi:ois) {
    			cartNum += oi.getNumber();
    		}
    		user.setCartNum(cartNum);
    		rjo.put("code", "000000");
    		rjo.put("data", user);
    	}

    	return JSONObject.toJSONString(rjo).toString();
     }
    
    //注册
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
    
    //根据分类获取全部商品 带分页  
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
    
    //获取商品相关信息
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
	
    //获取商品评价
	@ResponseBody
	@RequestMapping(value="/getReviews",method=RequestMethod.POST, produces= {"application/json;charset=UTF-8"})
	public String getReviews(@RequestBody String param) {
    	JSONObject rjo = new JSONObject();
    	JSONObject gjo = QSToJSON.toJSON(param);
    	MyJSONUtil.setJo(gjo);
    	if(!MyJSONUtil.isContainKey("pid")) return MyJSONUtil.getErrorResponse(600051);
    	if(!MyJSONUtil.keyHasValue("pid")) return MyJSONUtil.getErrorResponse(600052);
		int pid = Integer.parseInt(gjo.get("pid").toString());
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
			String endStr = name.substring(name.length()-1, name.length());
			item.getUser().setName(firstStr + "***" + endStr);
			item.getUser().setPassword(null);
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
    	
		return JSONObject.toJSONString(rjo).toString();
	}
	
	//立即购买 
	@ResponseBody
	@RequestMapping(value="/buyOneProduct",method=RequestMethod.POST,produces= {"application/json;charset=UTF-8"})
	public String buyOneProduct(@RequestBody String param) {
		JSONObject gjo = QSToJSON.toJSON(param);
		MyJSONUtil.setJo(gjo);
		if(!MyJSONUtil.isContainKey("uid")) return MyJSONUtil.getErrorResponse(600061);
		if(!MyJSONUtil.keyHasValue("uid")) return MyJSONUtil.getErrorResponse(600062);
		if(!MyJSONUtil.isContainKey("pid")) return MyJSONUtil.getErrorResponse(600063);
		if(!MyJSONUtil.keyHasValue("pid")) return MyJSONUtil.getErrorResponse(600064);
		if(!MyJSONUtil.isContainKey("num")) return MyJSONUtil.getErrorResponse(600065);
		if(!MyJSONUtil.keyHasValue("num")) return MyJSONUtil.getErrorResponse(600066);
		
		JSONObject rjo = new JSONObject();
		int uid = Integer.parseInt(gjo.get("uid").toString());
		int pid = Integer.parseInt(gjo.get("pid").toString());
		int num = Integer.parseInt(gjo.get("num").toString());
		
		int oiid = 0;
		
		boolean found = false;
		List<OrderItem> ois = orderItemService.listByUser(uid);
		for(OrderItem oi : ois) {
			if(oi.getProduct().getId().intValue() == pid) {
				oi.setNumber(oi.getNumber()+num);
				orderItemService.update(oi);
				found = true;
				oiid = oi.getId();
				break;
			}
		}
		if(!found) {
			OrderItem oi = new OrderItem();
			oi.setUid(uid);
			oi.setNumber(num);
			oi.setPid(pid);
			orderItemService.add(oi);
			oiid = oi.getId();
		}
		rjo.put("code", "000000");
		rjo.put("oiid", oiid);
		rjo.put("msg", "成功创建订单");
		return JSONObject.toJSONString(rjo).toString();
	}

	
	//7
	
	//8获取orderItem
	@ResponseBody
	@RequestMapping(value="/getOrderItem",method=RequestMethod.POST,produces= {"appllication/json;charset=UTF-8"})
	public String getOrderItem(@RequestBody String param) {
//		try {
		JSONObject gjo = MyJSONUtil.qsToJSON(param);
		JSONObject rjo = new JSONObject();
		MyJSONUtil.setJo(gjo);
		if(!MyJSONUtil.isContainKey("oiidList")) return MyJSONUtil.getErrorResponse(600081);
		if(!MyJSONUtil.keyHasValue("oiidList")) return MyJSONUtil.getErrorResponse(600082);
		System.out.println(gjo.get("oiidList").toString());
		JSONArray oiidsJa = JSONArray.parseArray(gjo.get("oiidList").toString());
		List<OrderItem> result = new ArrayList<OrderItem>();
		
		boolean isRightData = true;
		String errorMsg = "error";
		String rCode = "000000";
		for(int i = 0;i<oiidsJa.size();i++) {
			if(!oiidsJa.get(i).getClass().getSimpleName().toString().equals("JSONObject")) {
				isRightData = false;
				errorMsg = "oiidList数组内格式不正确";
				rCode = "600084";
				break;
			}
			JSONObject oiidJo = (JSONObject) oiidsJa.get(i);
			MyJSONUtil.setJo(oiidJo);
			if(!MyJSONUtil.isContainKey("oiid")) {
				isRightData = false;
				errorMsg = "元素缺少oiid字段";
				rCode = "600085";
				break;
			}
			if(!MyJSONUtil.keyHasValue("oiid")) {
				isRightData = false;
				errorMsg = "元素字段oiid参数不能为空";
				rCode = "600086";
				break;
			}
			
			int oiid = Integer.parseInt(oiidJo.get("oiid").toString());
			OrderItem oi = orderItemService.get(oiid);
			if(null == oi) {
				isRightData = false;
				errorMsg = "元素字段oiid参数不能为空";
				rCode = "600087";
				break;		
			}
			result.add(oi);
			Product p = productService.get(oi.getPid());
			productService.setFirstProductImage(p);
		}
		
		if(!isRightData) {
			rjo.put("code",rCode);
			rjo.put("msg", errorMsg);
		}else {
			rjo.put("code","000000");
			rjo.put("data", result);			
		}
		return rjo.toJSONString().toString();
//		} catch (Exception e) {
//			return e.toString();
//		}
	}

	//9创建订单
	@ResponseBody
	@RequestMapping(value="/createOrder",method=RequestMethod.POST,produces= {"application/json;charset=UTF-8"})
	public String createOrder(@RequestBody String param) {
		JSONObject rjo = new JSONObject();
		JSONObject gjo = MyJSONUtil.qsToJSON(param);
		JSONObject data =JSONObject.parseObject(gjo.get("data").toString()) ;
		MyJSONUtil.setJo(data);
		if(!MyJSONUtil.isContainKey("address")) return MyJSONUtil.getErrorResponse("600091");
		if(!MyJSONUtil.keyHasValue("address")) return MyJSONUtil.getErrorResponse("600092");
		if(!MyJSONUtil.isContainKey("post")) return MyJSONUtil.getErrorResponse("600093");
		if(!MyJSONUtil.keyHasValue("post")) return MyJSONUtil.getErrorResponse("600094");
		if(!MyJSONUtil.isContainKey("receiver")) return MyJSONUtil.getErrorResponse("600095");
		if(!MyJSONUtil.keyHasValue("receiver")) return MyJSONUtil.getErrorResponse("600096");
		if(!MyJSONUtil.isContainKey("mobile")) return MyJSONUtil.getErrorResponse("600097");
		if(!MyJSONUtil.keyHasValue("mobile")) return MyJSONUtil.getErrorResponse("600098");
		if(!MyJSONUtil.isContainKey("uid")) return MyJSONUtil.getErrorResponse("600099");
		if(!MyJSONUtil.keyHasValue("uid")) return MyJSONUtil.getErrorResponse("600100");
		if(!MyJSONUtil.isContainKey("orderList")) return MyJSONUtil.getErrorResponse("600101");
		if(!MyJSONUtil.keyHasValue("orderList")) return MyJSONUtil.getErrorResponse("600102");
		
		String address = data.get("address").toString();
		String post = data.get("post").toString();
		String receiver = data.get("receiver").toString();
		String mobile = data.get("mobile").toString();
		String uid = data.get("uid").toString();
		String userMessage = data.get("uid").toString();
		String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+ RandomUtils.nextInt(10000);
		Order order = new Order();
		order.setOrderCode(orderCode);
		order.setAddress(address);
		order.setPost(post);
		order.setReceiver(receiver);
		order.setMobile(mobile);
		order.setUid(Integer.parseInt(uid));
		order.setCreateDate(new Date());
		if(MyJSONUtil.isContainKey("userMessage")&&MyJSONUtil.keyHasValue("userMessage")) order.setUserMessage(userMessage);
		order.setStatus(OrderService.waitPay);
		JSONArray jao = (JSONArray) data.get("orderList");
		System.out.println(jao.toJSONString().toString());
		List<OrderItem> ois = new ArrayList<OrderItem>(); 
		for(int i = 0; i<jao.size();i++) {
			JSONObject jaoJo = (JSONObject) jao.get(i);
			int id = Integer.parseInt(jaoJo.get("id").toString());
			OrderItem oi = orderItemService.get(id);
			ois.add(oi);
		}
		float total = orderService.add(order, ois);
		
		List<JSONObject> oidJOs= new ArrayList<JSONObject>();
		for(OrderItem oi:ois) {
			JSONObject oidJo = new JSONObject();
			oidJo.put("oid",oi.getOid());
			oidJo.put("oiid",oi.getId());
			oidJOs.add(oidJo);
		}
		rjo.put("code", "000000");
		rjo.put("msg", "创建订单成功");
		rjo.put("total", total);
		rjo.put("data", oidJOs);
		return rjo.toJSONString().toString();
	}

	//11付款成功后 更新order信息
	@ResponseBody
	@RequestMapping(value="/confirmPay", method=RequestMethod.POST,produces= {"application/json;charset=UTF-8"})
	public String confirmPay(@RequestBody String param) {
		JSONObject gjo = QSToJSON.toJSON(param);
		MyJSONUtil.setJo(gjo);
		if(!MyJSONUtil.isContainKey("oidList")) return MyJSONUtil.getErrorResponse(600111);
		if(!MyJSONUtil.keyHasValue("oidList")) return MyJSONUtil.getErrorResponse(600112);
		System.out.println(gjo.get("oidList").toString());
		JSONArray oidListJA = JSONArray.parseArray(gjo.get("oidList").toString());
		
		boolean isRight = true;
		String rCode = "000000";
		String errMsg = "error";
		float total = 0f;
		int totalNumber = 0;
		for(int i = 0;i<oidListJA.size();i++) {
			if(oidListJA.size() == 0) {
				isRight = false;
				rCode = "600113";
				errMsg = "oidList不能为空";
				break;
			}
			JSONObject oidJO = (JSONObject) oidListJA.get(i);
			MyJSONUtil.setJo(oidJO);
			if(!MyJSONUtil.isContainKey("oid")) {
				isRight = false;
				rCode = "600114";
				errMsg = "oidList元素缺少oid字段";
				break;	
			}
			if(!MyJSONUtil.keyHasValue("oid")) {
				isRight = false;
				rCode = "600115";
				errMsg = "oidList元素缺少oid参数";
				break;	
			}
			int oid = Integer.parseInt(oidJO.get("oid").toString());
			Order o = orderService.get(oid);
			orderItemService.fill(o);
			OrderItem oi = o.getOrderItems().get(0);
			Product p = oi.getProduct();
			o.setStatus(OrderService.waitDelivery);
			o.setPayDate(new Date());
			orderService.update(o);
			total+=p.getPromotePrice()*oi.getNumber();
			totalNumber +=oi.getNumber();
		}
		if(!isRight) {
			return MyJSONUtil.returnErrorRespones(rCode, errMsg);
		}else {
			JSONObject fOidJO = (JSONObject) oidListJA.get(0);
			int fOid = Integer.parseInt(fOidJO.get("oid").toString());
			Order fOrder = orderService.get(fOid);
			String address = fOrder.getAddress();
			JSONObject data = new JSONObject();
			data.put("total",total);
			data.put("address",address);
			data.put("totalNumber",totalNumber);
			return MyJSONUtil.returnRespones("000000", "成功支付交易订单",data);
		}
	} 
	
	//12获取付款后的order信息
	@ResponseBody
	@RequestMapping(value="/getPayedOrder", method=RequestMethod.POST,produces= {"application/json;charset=UTF-8"})
	public String getPayedOrder(@RequestBody String param) {
		JSONObject gjo = QSToJSON.toJSON(param);
		JSONObject rjo = new JSONObject();
		MyJSONUtil.setJo(gjo);
		if(!MyJSONUtil.isContainKey("oid")) return MyJSONUtil.getErrorResponse(600111);
		if(!MyJSONUtil.keyHasValue("oid")) return MyJSONUtil.getErrorResponse(600112);
		int oid = Integer.parseInt(gjo.get("oid").toString());
		Order order = orderService.get(oid);
		orderItemService.fill(order);
		Product p = order.getOrderItems().get(0).getProduct();
		productService.setSaleAndReviewNumber(p);
		
		rjo.put("code","000000");
		rjo.put("msg","成功获取订单信息");
		rjo.put("data", order);
		return rjo.toJSONString().toString();
	} 
	
	///13获取user的的所有order信息
	@ResponseBody
	@RequestMapping(value="/getOrders",method=RequestMethod.POST,produces= {"application/json;charset=UTF-8"})
	public String getOrders(@RequestBody String param) {
		JSONObject gjo = QSToJSON.toJSON(param);
		JSONObject rjo = new JSONObject();
		MyJSONUtil.setJo(gjo);
		if(!MyJSONUtil.isContainKey("uid")) return MyJSONUtil.getErrorResponse(600121);
		if(!MyJSONUtil.keyHasValue("uid")) return MyJSONUtil.getErrorResponse(600121);
		String sortType = "all";
		int uid = Integer.parseInt(gjo.get("uid").toString());
		List<Order> os = orderService.list(uid,OrderService.delete);
		orderItemService.fill(os);
		rjo.put("code", "000000");
		rjo.put("data", os);
		return rjo.toJSONString().toString();
	}
	
	//14催发货
	@ResponseBody
	@RequestMapping(value="/askDelivery",method=RequestMethod.POST,produces= {"application/json;charset=UTF-8"})
	public String askDelivery(@RequestBody String param) {
		JSONObject gjo = QSToJSON.toJSON(param);
		JSONObject rjo = new JSONObject();
		MyJSONUtil.setJo(gjo);
		if(!MyJSONUtil.isContainKey("oid")) return MyJSONUtil.getErrorResponse(600141);
		if(!MyJSONUtil.keyHasValue("oid")) return MyJSONUtil.getErrorResponse(600142);
		int oid = Integer.parseInt(gjo.get("oid").toString());
		Order o = orderService.get(oid);
		o.setDeliveryDate(new Date());
		o.setStatus(OrderService.waitConfirm);
		orderService.update(o);
		rjo.put("code", "000000");
		rjo.put("msg", "成功催卖家发货");
		return rjo.toJSONString().toString();
	}
	
	//15确认收货
	@ResponseBody
	@RequestMapping(value="/confirmGot",method=RequestMethod.POST,produces= {"application/json;charset=UTF-8"})
	public String confirmGot(@RequestBody String param) {
		JSONObject gjo = QSToJSON.toJSON(param);
		JSONObject rjo = new JSONObject();
		MyJSONUtil.setJo(gjo);
		if(!MyJSONUtil.isContainKey("oid")) return MyJSONUtil.getErrorResponse(600151);
		if(!MyJSONUtil.keyHasValue("oid")) return MyJSONUtil.getErrorResponse(600152);
		int oid = Integer.parseInt(gjo.get("oid").toString());
		Order o = orderService.get(oid);
		return "ok";
	}
	//16确认付款
	@ResponseBody
	@RequestMapping(value="/finalConfirmPay",method=RequestMethod.POST,produces= {"application/json;charset=UTF-8"})
	public String finalConfirmPay(@RequestBody String param) {
		JSONObject gjo = QSToJSON.toJSON(param);
		JSONObject rjo = new JSONObject();
		MyJSONUtil.setJo(gjo);	
		if(!MyJSONUtil.isContainKey("oid")) return MyJSONUtil.getErrorResponse(600161);
		if(!MyJSONUtil.keyHasValue("oid")) return MyJSONUtil.getErrorResponse(600162);
		int oid = Integer.parseInt(gjo.get("oid").toString());
		Order o = orderService.get(oid);
		o.setStatus(OrderService.waitReview);
		o.setConfirmDate(new Date());
		orderService.update(o);
		rjo.put("code","000000");
		rjo.put("msg","交易已经成功");
		return rjo.toJSONString().toString();
	}
	
	//17评价
	@ResponseBody
	@RequestMapping(value="/review",method=RequestMethod.POST,produces= {"application/json;charset=UTF-8"})
	public String review(@RequestBody String param) {
		JSONObject gjo = QSToJSON.toJSON(param);
		JSONObject rjo = new JSONObject();
		MyJSONUtil.setJo(gjo);
		if(!MyJSONUtil.isContainKey("oid")) return MyJSONUtil.getErrorResponse(600171);
		if(!MyJSONUtil.keyHasValue("oid")) return MyJSONUtil.getErrorResponse(600172);
		if(!MyJSONUtil.isContainKey("review")) return MyJSONUtil.getErrorResponse(600173);
		if(!MyJSONUtil.keyHasValue("review")) return MyJSONUtil.getErrorResponse(600174);
		int oid = Integer.parseInt(gjo.get("oid").toString());
		Order o = orderService.get(oid);
		o.setStatus(OrderService.finish);
		orderService.update(o);
		orderItemService.fill(o);
		
		Product p = o.getOrderItems().get(0).getProduct();
		String content = gjo.get("review").toString();
		User u = userService.get(o.getUid());
		
		Review review = new Review();
		review.setContent(content);
		review.setPid(p.getId());
		review.setCreateDate(new Date());
		review.setUid(o.getUid());
		reviewService.add(review);
		rjo.put("code", "000000");
		rjo.put("msg", "成功评论");
		rjo.put("pid", p.getId());
		return rjo.toJSONString().toString();
	}
	
	//18添加到购物车
	@ResponseBody
	@RequestMapping(value="/addToCart",method=RequestMethod.POST,produces= {"application/json;charset=UTF-8"})
	public String addToCart(@RequestBody String param) {
		JSONObject gjo = QSToJSON.toJSON(param);
		JSONObject rjo = new JSONObject();
		MyJSONUtil.setJo(gjo);
		if(!MyJSONUtil.isContainKey("uid")) return  MyJSONUtil.getErrorResponse(600181);
		if(!MyJSONUtil.keyHasValue("uid")) return MyJSONUtil.getErrorResponse(600182);
		if(!MyJSONUtil.isContainKey("pid")) return  MyJSONUtil.getErrorResponse(600183);
		if(!MyJSONUtil.keyHasValue("pid")) return MyJSONUtil.getErrorResponse(600184);
		if(!MyJSONUtil.isContainKey("num")) return  MyJSONUtil.getErrorResponse(600185);
		if(!MyJSONUtil.keyHasValue("num")) return MyJSONUtil.getErrorResponse(600186);
		int pid = Integer.parseInt(gjo.get("pid").toString());
		int uid = Integer.parseInt(gjo.get("uid").toString());
		int num = Integer.parseInt(gjo.get("num").toString());
		boolean found = false;
		
		List<OrderItem> ois = orderItemService.listByUser(uid);
		for(OrderItem oi:ois) {
			if(oi.getProduct().getId().intValue() == pid) {
				oi.setNumber(oi.getNumber()+num);
				orderItemService.update(oi);
				found = true;
				break;
			}
			
		}
		
		if(!found) {
			OrderItem oi = new OrderItem();
			oi.setUid(uid);
			oi.setNumber(num);
			oi.setPid(pid);
			orderItemService.add(oi);
		}
		rjo.put("code", "000000");
		rjo.put("msg","已成功添加到购物车");
		return rjo.toJSONString().toString();
	}
	
	//19获取用户购物车信息
	@ResponseBody
	@RequestMapping(value="/getCartsData",method= RequestMethod.POST,produces= {"application/json;charset=UTF-8"})
	public String getCartsData(@RequestBody String param) {
		JSONObject gjo = QSToJSON.toJSON(param);
		JSONObject rjo = new JSONObject();
		MyJSONUtil.setJo(gjo);
		if(!MyJSONUtil.isContainKey("uid")) return MyJSONUtil.getErrorResponse(600191);
		if(!MyJSONUtil.keyHasValue("uid")) return MyJSONUtil.getErrorResponse(600192);
		int uid = Integer.parseInt(gjo.get("uid").toString());
		List<OrderItem> ois = orderItemService.listByUser(uid);
		rjo.put("code","000000");
		rjo.put("data",ois);
		return rjo.toJSONString().toString();
	}
	
	//20删除购物车商品
	@ResponseBody
	@RequestMapping(value="/deleteOrderItem",method=RequestMethod.POST,produces= {"application/json;charset=UTF-8"})
	public String deleteOrderItem(@RequestBody String param) {
		JSONObject gjo = QSToJSON.toJSON(param);
		MyJSONUtil.setJo(gjo);
		if(!MyJSONUtil.isContainKey("uid")) return MyJSONUtil.getErrorResponse(600201);
		if(!MyJSONUtil.keyHasValue("uid")) return MyJSONUtil.getErrorResponse(600202);
		if(!MyJSONUtil.isContainKey("oiid")) return MyJSONUtil.getErrorResponse(600203);
		if(!MyJSONUtil.keyHasValue("oiid")) return MyJSONUtil.getErrorResponse(600204);
		int uid = Integer.parseInt(gjo.get("uid").toString());
		int oiid = Integer.parseInt(gjo.get("oiid").toString());
		System.out.println(oiid);
		User user = userService.get(uid);
		OrderItem oi= orderItemService.get(oiid);
		
		if(null==user) return MyJSONUtil.returnErrorRespones(600205, "没有该用户");
		if(null==oi) return MyJSONUtil.returnErrorRespones(600206, "没有该订单");
		orderItemService.delete(oiid);
		return MyJSONUtil.returnRespones("000000", "成功删除订单");
	}
	
	//21删除订单
	@ResponseBody
	@RequestMapping(value="/deleteOrder",method= RequestMethod.POST,produces= {"application/json;charset=UTF-8"})
	public String  deleteOrder(@RequestBody String param) {
		JSONObject gjo = MyJSONUtil.qsToJSON(param);
		MyJSONUtil.setJo(gjo);
		if(!MyJSONUtil.isContainKey("oid")) return MyJSONUtil.getErrorResponse(600211);
		if(!MyJSONUtil.keyHasValue("oid")) return MyJSONUtil.getErrorResponse(600212);
		int oid = Integer.parseInt(gjo.get("oid").toString());
		Order o = orderService.get(oid);
		if(null == o ) MyJSONUtil.returnErrorRespones(600213, "该交易订单不存在");
		o.setStatus(OrderService.delete);
		orderService.update(o);
		return MyJSONUtil.returnRespones("000000", "成功删除该交易订单");
	}
}
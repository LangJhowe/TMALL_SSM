package com.how2java.tmall.service;
/** 
* @author Jhowe
* @version 2019年3月12日 下午2:08:19
* tmall_ssm
*/

import java.util.List;

import com.how2java.tmall.pojo.Product;

public interface ProductService {
	void add(Product p);
	void delete(int id);
	void updatae(Product p);
	Product get(int id);
	List<Product> list(int cid);
	void setFirstProductImage(Product p);
}

package com.how2java.tmall.pojo;
/** 
* @author Jhowe
* @version 2019年3月9日 下午10:41:37
* tmall_ssm
*/
public class Category {
	private Integer id;
	private String name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}
}

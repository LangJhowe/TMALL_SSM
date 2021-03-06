package com.how2java.tmall.pojo;

public class User {
    private Integer id;

    private String name;

    private String password;

    //非数据库字段
    private int cartNum;
    
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
    
    public int getCartNum() {
    	return cartNum;
    }
    
    public void setCartNum (int cartNum) {
		this.cartNum = cartNum;
	}
}
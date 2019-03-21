package com.how2java.tmall.test;

import java.util.ArrayList;
import java.util.List;

/** 
* @author Jhowe
* @version 2019年3月13日 下午4:58:53
* tmall_ssm
*/
public class Test {
	int t1 = 1;
	public static void main(String[] args) {
		List<String> sl= new ArrayList<String>();
		sl.add("1");
		sl.add("2");
		sl.add("3");
		System.out.println("sl.length="+sl.size());
		System.out.println("=========================");
		List<String> sl2 = sl.subList(0, 1);
		System.out.println("sl.length=" + sl.size());
		System.out.println("sl2.length=" + sl2.size());
		sl2.clear();
		System.out.println("=========================");
		System.out.println("sl.length=" + sl.size());
		System.out.println("sl2.length=" + sl2.size());
		System.out.println(Float.parseFloat("a1"));
	}
	public static void change(int i) {
		i = 2;
	}
	public void changeObj(Test t) {
		t.t1=2;
	}
}

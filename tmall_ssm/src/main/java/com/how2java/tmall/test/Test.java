package com.how2java.tmall.test;
/** 
* @author Jhowe
* @version 2019年3月13日 下午4:58:53
* tmall_ssm
*/
public class Test {
	int t1 = 1;
	public static void main(String[] args) {
		Test t = new Test();
		Test t2 = new Test();
		//System.out.println(t.t1);
		Test.change(t.t1);
		t.changeObj(t2);
		//System.out.println(t.t1);
		System.out.println(t2.t1);
	}
	public static void change(int i) {
		i = 2;
	}
	public void changeObj(Test t) {
		t.t1=2;
	}
}

package comparator;

import java.util.Comparator;

import com.how2java.tmall.pojo.Product;

/** 
* @author Jhowe
* @version 2019年3月20日 下午10:54:51
* tmall_ssm
*/
public class ProductSaleCountComparator implements Comparator<Product>{

	@Override
	public int compare(Product p1, Product p2) {
		// TODO Auto-generated method stub
		return p2.getSaleCount()-p1.getSaleCount();
	}
	

}

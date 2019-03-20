package comparator;

import java.util.Comparator;

import com.how2java.tmall.pojo.Product;

/** 
* @author Jhowe
* @version 2019年3月20日 下午10:51:53
* tmall_ssm
*/
public class ProductPriceComparator implements Comparator<Product>{

	@Override
	public int compare(Product p1, Product p2) {
		// TODO Auto-generated method stub
		return (int)(p1.getPromotePrice() - p2.getPromotePrice());
	}
	

}

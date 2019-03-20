package comparator;

import java.util.Comparator;

import com.how2java.tmall.pojo.Product;

/** 
* @author Jhowe
* @version 2019年3月20日 下午10:47:16
* tmall_ssm
*/
public class ProductAllComparator implements Comparator<Product>{

	@Override
	public int compare(Product p1,Product p2) {
		return p2.getReviewCount()*p2.getSaleCount()-p1.getReviewCount()*p1.getSaleCount();
	}

	
}

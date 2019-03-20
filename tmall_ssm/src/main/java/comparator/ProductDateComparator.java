package comparator;

import java.util.Comparator;

import com.how2java.tmall.pojo.Product;

/** 
* @author Jhowe
* @version 2019年3月20日 下午10:49:50
* tmall_ssm
*/
public class ProductDateComparator implements Comparator<Product>{

	@Override
	public int compare(Product p1, Product p2) {
		return p1.getCreateDate().compareTo(p2.getCreateDate());
	}

}

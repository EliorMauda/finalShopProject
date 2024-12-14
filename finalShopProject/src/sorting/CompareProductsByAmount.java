package sorting;

import java.util.Comparator;

import id_211544440.Product;

public class CompareProductsByAmount implements Comparator<Product> {

	@Override
	public int compare(Product o1, Product o2) {
		if(o1.getQuantity() > o2.getQuantity())
			return 1;
		if(o1.getQuantity() < o2.getQuantity())
			return -1;
		else
			return 0;
	}

}

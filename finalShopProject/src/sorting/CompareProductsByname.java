package sorting;

import java.util.Comparator;

import id_211544440.Product;

public class CompareProductsByname implements Comparator<Product> {

	@Override
	public int compare(Product o1, Product o2) {
		return o1.getName().compareTo(o2.getName());
	}

}
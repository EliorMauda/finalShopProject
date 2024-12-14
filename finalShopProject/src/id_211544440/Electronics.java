package id_211544440;

import java.io.Serializable;

import exceptions.ProductQuantityNotAvailableException;

@SuppressWarnings("serial")
public class Electronics extends Product implements Serializable {
	private String brandName;
	private String modelName;
	private static int numElectronicProductsInCart;

	public Electronics(String name, int quantity, String brandName, String modelName) {
		super(name, quantity);
		this.brandName = brandName;
		this.modelName = modelName;
		numElectronicProductsInCart = 1;
	}

	public String getBrandName() {
		return brandName;
	}

	public String getModelName() {
		return modelName;
	}

	public static int getNumElectronicProductsInCart() {
		return numElectronicProductsInCart;
	}

	public static void setNumElectronicProductsInCart(int numElectronicProductsInCart) {
		Electronics.numElectronicProductsInCart = numElectronicProductsInCart;
	}

	@Override
	public void reserve(int quantity) throws ProductQuantityNotAvailableException {
		numElectronicProductsInCart++;
		super.reserve(quantity);
	}

	@Override
	public String toString() {
		return super.toString() + "\nbrand name is: " + brandName + "\nthe model name is: " + modelName + "\n";
	}
}

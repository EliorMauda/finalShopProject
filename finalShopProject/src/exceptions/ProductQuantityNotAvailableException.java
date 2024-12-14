package exceptions;

public class ProductQuantityNotAvailableException extends Exception {
	public ProductQuantityNotAvailableException() {
		super("The amount you typed is not good! (greater or smaller than available quantity)");
	}
}

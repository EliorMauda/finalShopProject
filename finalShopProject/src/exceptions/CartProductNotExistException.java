package exceptions;

public class CartProductNotExistException extends Exception {
	public CartProductNotExistException() {
		super("Product is not exists in the shopping cart.");
	}
}

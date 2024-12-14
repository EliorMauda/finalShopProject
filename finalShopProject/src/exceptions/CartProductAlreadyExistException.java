package exceptions;

public class CartProductAlreadyExistException extends Exception {
	public CartProductAlreadyExistException() {
		super("The product already exists in the cart - if you want to update the product, press 4.");
	}
}

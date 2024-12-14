package exceptions;

public class ReachedMaxAmountException extends Exception {
	public ReachedMaxAmountException() {
		super("You have reached the maximum amount of products in cart.");
	}
}

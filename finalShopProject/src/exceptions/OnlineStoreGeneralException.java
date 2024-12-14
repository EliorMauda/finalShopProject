package exceptions;

public class OnlineStoreGeneralException extends Exception {
	public OnlineStoreGeneralException(int id) {
		super("ID " + id + " is not exists.");
	}
}

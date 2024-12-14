package interfaces;

import exceptions.ProductQuantityNotAvailableException;

public interface Reservable {
	void reserve(int quantity) throws ProductQuantityNotAvailableException;
}

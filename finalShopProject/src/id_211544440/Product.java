package id_211544440;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import exceptions.ProductQuantityNotAvailableException;
import interfaces.Reservable;

@SuppressWarnings("serial")
public abstract class Product implements Reservable, Serializable {
	protected String name;
	protected int quantity;
	private static int nextId = 1;
	private final int ID;
	private Date timestamp;

	public Product(String name, int quantity) {
		setName(name);
		setQuantity(quantity);
		ID = nextId++;
		this.timestamp = new Date();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getId() {
		return ID;
	}

	public static Product getProductById(Product[] products, int numOfProducts, int id) {
		for (int i = 0; i < numOfProducts; i++) {
			if (products[i].getId() == id) {
				return products[i];
			}
		}
		return null; // Product with the given ID was not found
	}

	public String getTimestamp() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss z");
		return dateFormat.format(timestamp);
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public void reserve(int quantity) throws ProductQuantityNotAvailableException {
		if (quantity <= 0 || quantity > this.quantity) {
			throw new ProductQuantityNotAvailableException();
		} else {
			this.quantity -= quantity;
		}
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "---->" + "\nProduct name: " + name + "\nquantity: " + quantity + "\nID: "
				+ ID;
	}

}

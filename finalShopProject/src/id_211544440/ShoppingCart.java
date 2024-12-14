package id_211544440;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ShoppingCart implements Serializable{
	private Product product;
	private int quantity;

	public ShoppingCart(Product product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}
	
	public static ShoppingCart getShoppingCartProductById(ShoppingCart[] shoppingCarts, int numOfProductsInCart, int id) {
		for (int i = 0; i < numOfProductsInCart; i++) {
			if (shoppingCarts[i].getProduct().getId() == id) {
				return shoppingCarts[i];
			}
		}
		return null; // Product with the given ID was not found
	}
	
	public int getShoppingCartProductByIndex(ShoppingCart[] shoppingCarts, int numOfProductsInCart, int id) {
		for (int i = 0; i < numOfProductsInCart; i++) {
			if (shoppingCarts[i].getProduct().getId() == id) {
				return i;
			}
		}
		return 0; // Product with the given ID was not found
	}

	@Override
	public String toString() {
		return "ShoppingCart:\n" + product.getClass().getSimpleName() + "---->\n" + "product name: " +  product.getName() + "\nquantity: " + quantity + "\nid: " + product.getId(); 
	}

}

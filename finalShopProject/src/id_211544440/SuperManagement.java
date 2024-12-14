package id_211544440;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import exceptions.CartProductAlreadyExistException;
import exceptions.CartProductNotExistException;
import exceptions.OnlineStoreGeneralException;
import exceptions.ProductQuantityNotAvailableException;
import exceptions.ReachedMaxAmountException;
import interfaces.Reservable;

@SuppressWarnings("serial")
public class SuperManagement implements Serializable{
	private String name;
	private Product[] products;
	private int numOfProducts;
	private ShoppingCart[] shoppingCart;
	private int numOfProductsInCart;

	public SuperManagement(String name) {
		this.name = name;
		products = new Product[0];
		shoppingCart = new ShoppingCart[5];
		numOfProducts = 0;
		numOfProductsInCart = 0;
	}

	public String displayProducts() {
		// Sort the products by department name(bubble sort)
//		for (int i = 0; i < numOfProducts - 1; i++) {
//			for (int j = 0; j < numOfProducts - i - 1; j++) {
//				// if the class name at index j is lexicographically greater (comes after) the
//				// class name at index j + 1 ,the value will be greater than 0.
//				if (products[j].getClass().getSimpleName().compareTo(products[j + 1].getClass().getSimpleName()) > 0) {
//					Product temp = products[j];
//					products[j] = products[j + 1];
//					products[j + 1] = temp;
//				}
//			}
//		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < numOfProducts; i++) {
			sb.append((i + 1)).append(")").append(products[i].toString()).append("\n");
		}

		sb.append("The number of products that are saved in the shopping cart is: ").append(numOfProductsInCart);

		return sb.toString();
	}

	public String displayShoppingCart() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < numOfProductsInCart; i++) {
			sb.append((i + 1)).append(")").append(shoppingCart[i].toString()).append("\n").append("The latest update: ")
					.append(shoppingCart[i].getProduct().getTimestamp()).append("\n");
		}

		sb.append("The number of products is: ").append(numOfProductsInCart);

		return sb.toString();
	}

	public void addProduct(Product p) {
		products = Arrays.copyOf(products, numOfProducts + 1);
		products[numOfProducts++] = p;
	}

	public boolean addProductToCart(int productId, int quantity) throws OnlineStoreGeneralException,
			CartProductAlreadyExistException, ProductQuantityNotAvailableException, ReachedMaxAmountException {
		if ((numOfProductsInCart + 1) > shoppingCart.length) {
			throw new ReachedMaxAmountException();
		}
		for (int i = 0; i < numOfProductsInCart; i++) {
			if (shoppingCart[i].getProduct().getId() == productId) {// Product already exists
				throw new CartProductAlreadyExistException();
			}
		}
		if (productQuantityBiggerThan0(productId, quantity) == false || productExists(productId) == false
				|| quantity <= 0)
			return false;

		Product product = Product.getProductById(products, numOfProducts, productId);

		if (product instanceof Reservable) {
			if (product instanceof Electronics) {
				if (Electronics.getNumElectronicProductsInCart() > 3) {
					throw new ReachedMaxAmountException();
				}
			}
			((Reservable) product).reserve(quantity);
		}

//		shoppingCart = Arrays.copyOf(shoppingCart, numOfProductsInCart + 1);
		shoppingCart[numOfProductsInCart++] = new ShoppingCart(product, quantity);// Product added successfully.
		shoppingCart[numOfProductsInCart - 1].getProduct().setTimestamp(new Date());// Update timestamp when a product
		// is added to the cart

		// decrease the amount from the products
//		for (int i = 0; i < numOfProducts; i++) {
//			if (products[i].getId() == productId) {
//				products[i].setQuantity(products[i].getQuantity() - quantity);
//				break;
//			}
//		}
		return true;
	}

	public boolean productQuantityBiggerThan0(int productId, int quantity) throws ProductQuantityNotAvailableException {
		for (int i = 0; i < numOfProducts; i++) {
			if (products[i].getId() == productId) {
				if (products[i].getQuantity() <= 0 || quantity > products[i].getQuantity()) {
					throw new ProductQuantityNotAvailableException();
				}
			}
		}
		return true;
	}

	public boolean productExists(int productId) throws OnlineStoreGeneralException {
		for (int i = 0; i < numOfProducts; i++) {
			if (products[i].getId() == productId) {
				return true;
			}
		}
		// Can not find product.
		throw new OnlineStoreGeneralException(productId);
	}

	public void updateCartProductQuantity(int productId, int changingNumber)
			throws OnlineStoreGeneralException, CartProductNotExistException, ProductQuantityNotAvailableException {

		ShoppingCart cart = ShoppingCart.getShoppingCartProductById(shoppingCart, numOfProductsInCart, productId);
		Product product = Product.getProductById(products, numOfProducts, productId);

		for (int i = 0; i < numOfProductsInCart; i++) {
			if (shoppingCart[i].getProduct().getId() == productId) {
				int newQuantityInCart = shoppingCart[i].getQuantity() + changingNumber;
				if (changingNumber == 0) {// Nothing has changed!
					return;
				}
				if (newQuantityInCart == 0) {
					removeProductFromCart(productId);
					return;
				} else {
					if (((cart.getQuantity() + changingNumber) > product.getQuantity())
							|| ((cart.getQuantity() + changingNumber) < 0)) {
						throw new ProductQuantityNotAvailableException();
					}
					shoppingCart[i].setQuantity(newQuantityInCart);// Product's new quantity updated successfully.
					shoppingCart[i].getProduct().setTimestamp(new Date());// Update timestamp
				}

				for (int j = 0; j < numOfProducts; j++) {
					if (products[j].getId() == productId) {
						products[j].setQuantity(products[j].getQuantity() - changingNumber);
						return;
					}
				}
			}
		}
		// Product does not exist.
		if (Product.getProductById(products, numOfProducts, productId) == null) {
			throw new OnlineStoreGeneralException(productId);
		}
		// Product does not exist in cart.
		if (ShoppingCart.getShoppingCartProductById(shoppingCart, numOfProductsInCart, productId) == null) {
			throw new CartProductNotExistException();
		}
	}

	public void removeProductFromCart(int productId) throws OnlineStoreGeneralException, CartProductNotExistException {
		ShoppingCart cart = ShoppingCart.getShoppingCartProductById(shoppingCart, numOfProductsInCart, productId);
		if (cart != null) {
			int quantityOfProductInCart = cart.getQuantity();
			shoppingCart[cart.getShoppingCartProductByIndex(shoppingCart, productId, productId)] = shoppingCart[--numOfProductsInCart];// Product has removed successfully.

			if (cart.getProduct() instanceof Electronics) {
				Electronics.setNumElectronicProductsInCart(Electronics.getNumElectronicProductsInCart() - 1);
			}

			for (int i = 0; i < numOfProducts; i++) {
				if (products[i].getId() == productId) {
					products[i].setQuantity(products[i].getQuantity() + quantityOfProductInCart);
					return;
				}
			}
		}
		// Product does not exist.
		if (Product.getProductById(products, numOfProducts, productId) == null) {
			throw new OnlineStoreGeneralException(productId);
		}
		// Product does not exist in cart.
		if (ShoppingCart.getShoppingCartProductById(shoppingCart, numOfProductsInCart, productId) == null) {
			throw new CartProductNotExistException();
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Super name: " + name + "\n");

		for (int i = 0; i < numOfProductsInCart; i++) {
			sb.append(shoppingCart[i].toString()).append("\n");
		}
		sb.append("number of items in cart: ").append(numOfProductsInCart);

		return sb.toString();
	}

	public int getNumOfProducts() {
		return numOfProducts;
	}

	public Product[] getProducts() {
		return products;
	}

}

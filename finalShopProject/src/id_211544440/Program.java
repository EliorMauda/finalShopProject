package id_211544440;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Scanner;

import exceptions.CartProductAlreadyExistException;
import exceptions.CartProductNotExistException;
import exceptions.OnlineStoreGeneralException;
import exceptions.ProductQuantityNotAvailableException;
import exceptions.ReachedMaxAmountException;
import sorting.CompareProductsByAmount;
import sorting.CompareProductsByCategory;
import sorting.CompareProductsByname;

public class Program {

	public enum ProductCategory {
		BOOKS, ELECTRONICS, CLOTHING
	}

	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException {
		Scanner s = new Scanner(System.in);
		SuperManagement superManagement;

		File binaryFile = new File("data.dat");
		if (binaryFile.length() > 0) {
			superManagement = loadToBinaryFile("data.dat");
		} else {
			superManagement = loadProductsFromCSV("products_list.csv");
		}

		int productId;
		int quantity;
		char answer = 'y';
		int choice;
		boolean fcontinue = true;

		do {
			System.out.println("------ Menu ------");
			System.out.println("1. Display Product Pool");
			System.out.println("2. Display Shopping Cart");
			System.out.println("3. Add Product to Cart");
			System.out.println("4. Update Cart Item Quantity");
			System.out.println("5. Remove Product from Cart");
			System.out.println("6. Sort products");
			System.out.println("7. Save changes and exit");
			System.out.println("8. exit without saving changes");
			System.out.println("-------------------");
			System.out.print("Enter your choice: ");
			choice = s.nextInt();

			switch (choice) {

			case 1:
				System.out.println(superManagement.displayProducts());
				break;

			case 2:
				System.out.println(superManagement.displayShoppingCart());
				break;

			case 3:
				while ((answer != 'n' && answer != 'N')) {
					System.out.println("Enter product id: ");
					productId = s.nextInt();
					System.out.println("Enter Quantity:");
					quantity = s.nextInt();
					addProductToCart(superManagement, productId, quantity);
					System.out.println("Do you want to add another product? Y/N");
					answer = s.next().charAt(0);
				}
				answer = 'y';
				break;

			case 4:
				while (answer != 'n' && answer != 'N') {
					System.out.println("Your shopping cart:\n" + superManagement.displayShoppingCart() + "\n");
					System.out.println("Enter product id:");
					productId = s.nextInt();
					System.out.println("Write down the amount you want to add/subtract (add -/+ before the number.)");
					quantity = s.nextInt();
					updateCartItemQuantity(superManagement, productId, quantity);
					System.out.println("Do you want to do another changes? Y/N");
					answer = s.next().charAt(0);
				}
				answer = 'y';
				break;

			case 5:
				System.out.println("Enter product id:");
				productId = s.nextInt();
				removeProductFromCart(superManagement, productId);
				break;

			case 6:
				System.out.println(
						"press 1 to sort by product's category.\npress 2 to sort by product's name.\npress 3 to sort by the available amount of product.");
				int sortChoise = s.nextInt();
				Product[] dupProducts = Arrays.copyOf(superManagement.getProducts(),
						superManagement.getNumOfProducts());

				switch (sortChoise) {
				case 1:
					Arrays.sort(dupProducts, new CompareProductsByCategory());
					System.out.println(displaySortedArray(dupProducts));
					break;
				case 2:
					Arrays.sort(dupProducts, new CompareProductsByname());
					System.out.println(displaySortedArray(dupProducts));
					break;
				case 3:
					Arrays.sort(dupProducts, new CompareProductsByAmount());
					System.out.println(displaySortedArray(dupProducts));
					break;
				default:
					System.out.println("Invalid choice.");
					break;
				}
				break;

			case 7:
				superManagement = writeToBinaryFile(superManagement, "data.dat");
				fcontinue = false;
				break;

			case 8:
				fcontinue = false;
				break;

			default:
				System.out.println("Invalid choice. Please try again.");
				break;
			}
		} while (fcontinue);

		System.out.println("Thank you, have a good day!");
		s.close();
	}

	public static String displaySortedArray(Product[] dupProducts) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < dupProducts.length; i++) {
			sb.append((i + 1)).append(")").append(dupProducts[i].toString()).append("\n");
		}
		return sb.toString();
	}

	public static void addProductToCart(SuperManagement superManagement, int id, int quantity) {
		try {
			superManagement.addProductToCart(id, quantity);
		} catch (OnlineStoreGeneralException | CartProductAlreadyExistException | ProductQuantityNotAvailableException
				| ReachedMaxAmountException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void updateCartItemQuantity(SuperManagement superManagement, int productId, int quantity) {
		try {
			superManagement.updateCartProductQuantity(productId, quantity);
		} catch (OnlineStoreGeneralException | CartProductNotExistException | ProductQuantityNotAvailableException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void removeProductFromCart(SuperManagement superManagement, int productId) {
		try {
			superManagement.removeProductFromCart(productId);
		} catch (OnlineStoreGeneralException | CartProductNotExistException e) {
			System.out.println(e.getMessage());
		}
	}

	@SuppressWarnings("resource")
	public static SuperManagement loadProductsFromCSV(String fileName) throws FileNotFoundException {
		File f = new File(fileName);
		Scanner s = new Scanner(f);

		String dataLine[], inputLine;
		SuperManagement superManagement = new SuperManagement("Rami Levi, ");

		inputLine = s.nextLine();
		dataLine = inputLine.split(",");

		while (s.hasNextLine()) {
			inputLine = s.nextLine();
			dataLine = inputLine.split(",");
			ProductCategory productCategory = ProductCategory.valueOf(dataLine[0].toUpperCase());
			String nameOfProduct = dataLine[1];
			int quantity = Integer.parseInt(dataLine[2]);
			String param1 = dataLine[3];
			String param2 = dataLine[4];

			Product product;

			if (productCategory == ProductCategory.BOOKS) {
				String authorName = param1;
				int numOfPages = Integer.parseInt(param2);
				product = new Books(nameOfProduct, quantity, authorName, numOfPages);// polymorphism
			} else if (productCategory == ProductCategory.ELECTRONICS) {
				String brandName = param1;
				String modelName = param2;
				product = new Electronics(nameOfProduct, quantity, brandName, modelName);// polymorphism
			} else if (productCategory == ProductCategory.CLOTHING) {
				String size = param1;
				String color = param2;
				String gender = dataLine[5];
				product = new Clothing(nameOfProduct, quantity, size, color, gender);// polymorphism
			} else {
				return null; // Handle unknown product category
			}

			superManagement.addProduct(product);
		}

		s.close();
		return superManagement;
	}

	public static SuperManagement writeToBinaryFile(SuperManagement superManagement, String fileName)
			throws FileNotFoundException, IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
		out.writeObject(superManagement);
		out.close();
		return superManagement;
	}

	public static SuperManagement loadToBinaryFile(String fileName)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
		SuperManagement superManagement = (SuperManagement) in.readObject();
		in.close();
		return superManagement;
	}

}

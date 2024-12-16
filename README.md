# Shopping Cart Management System

## Overview

This project is a **Shopping Cart Management System** designed to handle products, manage customer shopping carts, and maintain inventory. The system is implemented in Java and provides functionalities such as adding, removing, and updating products in a shopping cart while ensuring inventory constraints are respected.

---

## Features

- **Product Management**:

  - Add new products to the store.
  - Maintain product quantities and enforce availability constraints.

- **Shopping Cart Operations**:

  - Add products to the shopping cart.
  - Remove products from the cart.
  - Update product quantities in the cart.
  - Display all items in the cart with their details.

- **Sorting and Searching**:

  - Sort products by department.
  - Search for products in the cart by ID.

- **Custom Exceptions**:

  - Handle inventory shortages with meaningful error messages.
  - Limit maximum cart capacity to avoid overflow.

---

## Class Structure

### 1. **SuperManagement**

- Manages the store's products and inventory.
- Handles adding and removing products.
- Sorts products by department.

### 2. **ShoppingCart**

- Represents a customer's shopping cart.
- Tracks products and quantities.

### 3. **Custom Exceptions**

- `OnlineStoreGeneralException`: General exception for the store.
- `ProductQuantityNotAvailableException`: Raised when requested quantity exceeds available stock.
- `ReachedMaxAmountException`: Raised when the cart reaches its maximum allowed size.

### 4. **Reservable Interface**

- Ensures that reservable products implement specific methods.

---

## Examples

### Adding a Product to the Store:

```java
SuperManagement store = new SuperManagement("My Store");
Product p1 = new Product(1, "Laptop", 5);
store.addProduct(p1);
```

### Adding a Product to the Shopping Cart:

```java
store.addProductToCart(1, 2); // Adds 2 units of product with ID 1 to the cart.
```

### Sorting Products by Department:

```java
store.sortProductsByDepartment();
```

### Removing a Product from the Cart:

```java
store.removeProductFromCart(1); // Removes product with ID 1 from the cart.
```

---

## Author

Developed by Elior Mauda.


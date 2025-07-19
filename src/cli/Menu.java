package cli;

import model.Product;
import model.OrderItem;
import service.InventoryService;
import service.OrderService;

import java.util.*;

public class Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final InventoryService inventoryService = new InventoryService();
    private final OrderService orderService = new OrderService(inventoryService);

    public void start() {
        System.out.println("Welcome to Mini Ocado!");

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Create product");
            System.out.println("2. List products");
            System.out.println("3. Update product");
            System.out.println("4. Delete product");
            System.out.println("5. Place new order");
            System.out.println("6. Exit");
            System.out.print("> ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> createProduct();
                case "2" -> listProducts();
                case "3" -> updateProduct();
                case "4" -> deleteProduct();
                case "5" -> placeOrder();
                case "6" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option");
            }
        }
    }

    private void createProduct() {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter quantity: ");
        int qty = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter price: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter warehouse location X: ");
        int x = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter warehouse location Y: ");
        int y = Integer.parseInt(scanner.nextLine());

        Product p = new Product(name, qty, price, x, y);
        inventoryService.addProduct(p);
        System.out.println("Product created successfully.");
    }

    private void listProducts() {
        List<Product> products = inventoryService.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }
        System.out.println("Available Products:");
        Locale.setDefault(Locale.US);
        for (Product p : products) {
            System.out.printf("Name: %s | Qty: %d | Price: %.2f | Location: (%d, %d)%n",
                    p.getName(), p.getQuantity(), p.getPrice(), p.getX(), p.getY());
        }
    }

    private void updateProduct() {
        System.out.print("Enter product name to update: ");
        String name = scanner.nextLine();
        if (!inventoryService.exists(name)) {
            System.out.println("Product not found.");
            return;
        }
        System.out.print("Enter new quantity: ");
        int qty = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter new price: ");
        double price = Double.parseDouble(scanner.nextLine());

        if (inventoryService.updateProduct(name, qty, price)) {
            System.out.println("Product updated successfully.");
        }
    }

    private void deleteProduct() {
        System.out.print("Enter product name to delete: ");
        String name = scanner.nextLine();
        if (inventoryService.deleteProduct(name)) {
            System.out.println("Product deleted successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    private void placeOrder() {
        System.out.println("Placing new order ...");
        List<OrderItem> orderItems = new ArrayList<>();

        while (true) {
            System.out.print("Enter product name (or 'done' to finish): ");
            String name = scanner.nextLine();
            if (name.equalsIgnoreCase("done")) break;

            System.out.print("Enter quantity: ");
            int qty = Integer.parseInt(scanner.nextLine());

            orderItems.add(new OrderItem(name, qty));
        }

        boolean canFulfill = orderService.canFulfillOrder(orderItems);

        if (!canFulfill) {
            System.out.println("Processing your order...");
            System.out.println("Order status: FAIL");
            System.out.println("Message: Not enough stock to fulfill your order.");
            return;
        }

        List<int[]> route = orderService.fulfillOrder(orderItems);
        System.out.println("Processing your order...");
        System.out.println("Order status: SUCCESS");
        System.out.print("Visited locations:");
        for (int i = 0; i < route.size(); i++) {
            int[] loc = route.get(i);
            System.out.print("[" + loc[0] + "," + loc[1] + "]");
            if (i != route.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
        System.out.println("Message: Your order is ready! Please collect it at the desk.");
    }
}

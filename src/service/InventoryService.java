package service;

import model.Product;
import java.util.*;

public class InventoryService {
    private final Map<String, Product> inventory = new LinkedHashMap<>();

    public void addProduct(Product product) {
        inventory.put(product.getName().toLowerCase(), product);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(inventory.values());
    }

    public Product getProduct(String name) {
        return inventory.get(name.toLowerCase());
    }

    public boolean updateProduct(String name, int newQty, double newPrice) {
        Product p = getProduct(name);
        if (p != null) {
            p.setQuantity(newQty);
            p.setPrice(newPrice);
            return true;
        }
        return false;
    }

    public boolean deleteProduct(String name) {
        return inventory.remove(name.toLowerCase()) != null;
    }

    public boolean hasEnoughStock(String name, int requestedQty) {
        Product p = getProduct(name);
        return p != null && p.getQuantity() >= requestedQty;
    }

    public void reduceStock(String name, int qty) {
        Product p = getProduct(name);
        if (p != null) {
            p.setQuantity(p.getQuantity() - qty);
        }
    }

    public boolean exists(String name) {
        return inventory.containsKey(name.toLowerCase());
    }
}

package service;

import model.OrderItem;
import model.Product;
import util.RoutePlanner;

import java.util.*;

public class OrderService {
    private final InventoryService inventory;

    public OrderService(InventoryService inventory) {
        this.inventory = inventory;
    }

    public boolean canFulfillOrder(List<OrderItem> items) {
        for (OrderItem item : items) {
            Product product = inventory.getProduct(item.getProductName());
            if (product == null || product.getQuantity() < item.getQuantity()) {
                return false;
            }
        }
        return true;
    }

    public List<int[]> fulfillOrder(List<OrderItem> items) {
        Set<String> visited = new HashSet<>();
        List<int[]> uniqueTargets = new ArrayList<>();

        for (OrderItem item : items) {
            Product product = inventory.getProduct(item.getProductName());

            if (product != null && product.getQuantity() >= item.getQuantity()) {
                product.setQuantity(product.getQuantity() - item.getQuantity());

                String key = product.getX() + "," + product.getY();
                if (!visited.contains(key)) {
                    uniqueTargets.add(new int[]{product.getX(), product.getY()});
                    visited.add(key);
                }
            }
        }

        return RoutePlanner.optimizeRoute(uniqueTargets);
    }
}

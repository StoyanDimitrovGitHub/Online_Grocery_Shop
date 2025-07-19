package model;

public class Product {
    private String name;
    private int quantity;
    private double price;
    private int x, y;

    public Product(String name, int quantity, double price, int x, int y) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.x = x;
        this.y = y;
    }

    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public int getX() { return x; }
    public int getY() { return y; }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name + " | Qty: " + quantity + " | Price: " + price + " | Location: (" + x + "," + y + ")";
    }
}

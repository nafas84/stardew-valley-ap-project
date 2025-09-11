package io.Ap.StardewValley.Common.Model.Shop;

public class ProductData {
    private String name;
    private int price;
    private int number;
    private String description;
    private boolean exists;

    public ProductData(String name, int price, int number, String description) {
        this.name = name;
        this.price = price;
        this.number = number;
        this.description = description;
        this.exists = (number == -1 || number > 0);
    }

    public ProductData(String name, int price, int number, String description, boolean exists) {
        this.name = name;
        this.price = price;
        this.number = number;
        this.description = description;
        this.exists = (exists && (number == -1 || number > 0));
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }

    public boolean exists() {
        return exists;
    }
}

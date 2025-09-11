package io.Ap.StardewValley.Common.Model.Animals;


import io.Ap.StardewValley.Common.Model.Map.Coordinate;

public class Animal {
    private AnimalType type;
    private String name;

    private int friendship = 0;
    private boolean isPetted = false;
    private boolean isFeeded = false;

    private Coordinate coordinate = null; // null or outside coordinate

    private AnimalProduct product = null;

    public Animal() {} //needed for json

    public Animal(AnimalType type, String name) {
        this.type = type;
        this.name = name;
    }

    public void setFriendship(int friendship) {
        this.friendship = friendship;
    }

    public void addFriendship(int friendship) {
        this.friendship += friendship;
    }

    public void setPetted(boolean petted) {
        isPetted = petted;
    }

    public void setFeeded(boolean feeded) {
        isFeeded = feeded;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void setProduct(AnimalProduct product) {
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public boolean isFeeded() {
        return isFeeded;
    }

    public boolean isPetted() {
        return isPetted;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public int getFriendship() {
        return Math.min(this.friendship, 1000);
    }

    public AnimalProduct getProduct() {
        return product;
    }

    public String getProductString() {
        return "Product name: " + this.product.getName() + "\n" +
            "Quality: " + this.product.getQualityString() + "\n";
    }

    public AnimalType getType() {
        return type;
    }


    public int getSellPrice () {
        return (int) (this.type.getPrice() * (((double) this.friendship / 1000) + 0.3 ));
    }

    @Override
    public String toString() {
        String result = "Name: " + this.name + "\n" +
            "Type: " + this.type.getName() + "\n" +
            "Description: " + this.type.getDescription() + "\n" +
            "FriendShip: " + this.friendship + "\n" +
            "Has the animal been petted today? " + this.isPetted + "\n" +
            "Has the animal been fed today? " + this.isFeeded + "\n";

            if (this.coordinate != null)
                result += "Coordinate: (" + this.coordinate.getX() + ", " + this.coordinate.getY() + ")\n";
            else
                result += "Animal is in Coop/Barn:)\n";

            return result + "__________________________________________\n";
    }
}

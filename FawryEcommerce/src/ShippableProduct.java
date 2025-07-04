import java.util.Date;

public class ShippableProduct extends Product implements Shippable  {

    public ShippableProduct(String name, int price, int quantity, double weight) {

        setShippable(true);
        setName(name);
        setPrice(price);
        setQuantity(quantity);
        setWeight(weight);
    }

    public double calculateShippingCost() {
        return getWeight() * 0.1;
    }

    @Override
    public String getTheName() {
        return this.getName();
    }

    @Override
    public double getTheWeight() {
        return this.getWeight();
    }
}

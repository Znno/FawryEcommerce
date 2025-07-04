import java.util.List;

public class ShippingService {
    private static final double ShoppingRate = 0.1;
    List<ShippableProduct> items;
    ShippingService(List<ShippableProduct> items){
        this.items = items;
    }

    public double calculateShippingCost() {
        double totalWeight = 0;
        for (ShippableProduct item : items) {
            totalWeight += item.getTheWeight()* item.getQuantity();
        }
        return totalWeight * ShoppingRate;
    }
}

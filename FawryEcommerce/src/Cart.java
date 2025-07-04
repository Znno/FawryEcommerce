import java.util.ArrayList;

public class Cart {

    ArrayList<Product> products;
    private double totalWeight;

    public Cart() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
        if (product.isShippable()) {
            totalWeight += product.getWeight() * product.getQuantity();
        }
    }
    

    public double calculateTotal() {
        double total = 0;
        for (Product product : products) {
            total += product.getPrice() * product.getQuantity();
        }
        return total;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public double getTotalWeight() {
        double total = 0;
        for (Product product : products) {
            total += product.getWeight() * product.getQuantity();
        }
        return total;
    }
}

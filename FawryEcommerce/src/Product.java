import java.time.LocalDate;
import java.util.Date;

public  class Product {
    private String Name;
    private int price;
    private int quantity;
    private boolean expirable=false;
    private boolean shippable=false;
    private double weight;
    private LocalDate expiryDate;
    public Product(){}
    public Product(String name, int price, int quantity) {
        setName(name);
        setPrice(price);
        setQuantity(quantity);
    }

    public int getPrice() {
        return price;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public double getWeight() {
        return weight;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isExpirable() {
        return expirable;
    }

    public boolean isShippable() {
        return shippable;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return Name;
    }

    public void setExpirable(boolean expirable) {
        this.expirable = expirable;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setShippable(boolean shippable) {
        this.shippable = shippable;
    }
    public boolean isExpired(){
        return (isExpirable() && LocalDate.now().isAfter(expiryDate));
    }

}

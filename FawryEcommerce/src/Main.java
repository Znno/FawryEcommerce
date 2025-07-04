import java.text.SimpleDateFormat;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void revertChanges(ArrayList<Product> inventory, TreeMap<Integer, Integer> Changes){
        for(Map.Entry<Integer,Integer> entry:Changes.entrySet())
        {
            inventory.get(entry.getKey()).setQuantity(entry.getValue());
        }
    }
    public static void main(String[] args) {
        ArrayList<Product> inventory = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        TreeMap<Integer,Integer> Changes = new TreeMap<>();

        String customerName = "";
        double initialBalance = 0;
        try {
            System.out.print("Enter customer name: ");
            customerName = scanner.nextLine();
            if (customerName.trim().isEmpty()) {
                throw new IllegalArgumentException("Name cannot be empty");
            }
            System.out.print("Enter initial balance: $");
            while (true) {
                try {
                    initialBalance = scanner.nextDouble();
                    if (initialBalance < 0) {
                        throw new Exception("Balance cannot be negative");
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Error: Please enter a valid number");
                    System.out.print("Enter initial balance: $");
                    scanner.nextLine();
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scanner.close();
            return;
        }
        Customer customer;
        Cart cart;
        try {
            customer = new Customer(customerName, initialBalance);
            cart = customer.getCart();
        } catch (Exception e) {
            System.out.println("Error creating customer: " + e.getMessage());
            scanner.close();
            return;
        }

        try {
            ShippableProduct tv = new ShippableProduct("TV", 1000, 5, 20);
            ShippableProduct phone = new ShippableProduct("Phone", 500, 10, 0.5);

            Product scratchCard = new Product("Scratch Card", 10, 50);
            scratchCard.setExpirable(true);
            scratchCard.setExpiryDate(LocalDate.of(2026, 2, 1));
            Product Cheese = new ShippableProduct("Cheese", 10, 50, 1);
            Cheese.setExpirable(true);
            Cheese.setExpiryDate(LocalDate.of(2028, 2, 1));
            Product Milk=new ShippableProduct("Milk", 5, 50, 1);
            Milk.setExpirable(true);
            Milk.setExpiryDate(LocalDate.of(2024, 2, 1));

            try {
                inventory.add(tv);
                inventory.add(phone);
                inventory.add(scratchCard);
                inventory.add(Cheese);
                inventory.add(Milk);
            } catch (Exception e) {
                System.out.println("Error adding products to inventory: " + e.getMessage());
                scanner.close();
                return;
            }
        } catch (Exception e) {
            System.out.println("Error creating products: " + e.getMessage());
            scanner.close();
            return;
        }

        while (true) {
            System.out.println("\nAvailable products:");
            for (int i = 0; i < inventory.size(); i++) {
                Product p = inventory.get(i);
                System.out.print(i+1);
                System.out.print(". ");
                System.out.print(p.getName());
                System.out.print(" - $");
                System.out.print(p.getPrice());
                System.out.print(" (Available: ");
                System.out.print(p.getQuantity());
                System.out.println(")");
                if (p.isExpirable()) {
                    System.out.println("    (Expires on: " + p.getExpiryDate() + ")");
                }
                if (p.isShippable()) {
                    System.out.printf("    (Weight: %.2f kg)\n", p.getWeight());
                }
            }

            System.out.print("\nSelect product (0 to checkout): ");
            int choice;
            try {
                choice = scanner.nextInt();
                if (choice == 0) break;
                if (choice < 1 || choice > inventory.size()) {
                    throw new IllegalArgumentException("Invalid selection!");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                continue;
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
                continue;
            }

            Product selected = inventory.get(choice - 1);
            System.out.print("Enter quantity: ");
            int quantity;
            try {
                quantity = scanner.nextInt();
                if (quantity <= 0 || quantity > selected.getQuantity()) {
                    throw new IllegalArgumentException("Invalid quantity!");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                continue;
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
                continue;
            }

            Product existingProduct = null;
            for (Product p : cart.getProducts()) {
                if (p.getName().equals(selected.getName())) {
                    existingProduct = p;
                    break;
                }
            }

            try {
                if (existingProduct != null) {
                    existingProduct.setQuantity(existingProduct.getQuantity() + quantity);
                } else {
                    Product productToAdd = new Product(selected.getName(), selected.getPrice(), quantity);
                    if (selected.isExpirable()) {
                        productToAdd.setExpiryDate(selected.getExpiryDate());
                        productToAdd.setExpirable(true);
                    }
                    if (selected.isShippable()) {
                        productToAdd.setShippable(true);
                        productToAdd.setWeight(selected.getWeight());
                    }
                    cart.addProduct(productToAdd);
                }
            } catch (Exception e) {
                System.out.println("Error updating cart: " + e.getMessage());
                continue;
            }
            selected.setQuantity(selected.getQuantity() - quantity);
            if(!Changes.containsKey(choice-1))
                Changes.put(choice-1,quantity);
        }

        if (cart.getProducts().isEmpty()) {
            System.out.println("Cart is empty!");
            scanner.close();
            return;
        }

        System.out.println("\n=== Order Summary ===");
        System.out.println("Customer: " + customer.getName());
        System.out.printf("Available Balance: $%.2f\n", customer.getBalance());
        for (Product p : cart.getProducts()) {
            System.out.print(p.getName());
            System.out.print(" ( x" + p.getQuantity() + ")");
            System.out.print(" - $");
            System.out.print(p.getPrice() * p.getQuantity());

            if (p.isShippable()) {
                System.out.printf(" (Weight: %.2f kg)", p.getWeight() * p.getQuantity());
            }
            System.out.println();
        }
        System.out.println("===================");

        // Check for expired products
        boolean hasExpiredProducts = false;
        for (Product p : cart.getProducts()) {
            if (p.isExpired()) {
                System.out.println("Error: " + p.getName() + " has expired on " +(p.getExpiryDate()));
                hasExpiredProducts = true;
            }
        }

        if (hasExpiredProducts) {
            System.out.println("Order cancelled due to expired products.");
            revertChanges(inventory,Changes);
            scanner.close();
            return;
        }

        double subtotal = cart.calculateTotal();
        double shippingFees = cart.getTotalWeight() * 0.1;
        System.out.printf("Subtotal: $%.2f\n", subtotal);
        if (cart.getTotalWeight() > 0) {
            System.out.printf("Shipping fees: $%.2f\n", shippingFees);
            System.out.printf("Total weight: %.2f kg\n", cart.getTotalWeight());
        }
        List<ShippableProduct> shippableItems = new ArrayList<>();
        for (Product p : cart.getProducts()) {
            if (p.isShippable()) {
                ShippableProduct sp = new ShippableProduct(p.getName(), p.getPrice(), p.getQuantity(), p.getWeight());
                shippableItems.add(sp);
            }
        }
        System.out.printf("Total cost: $%.2f\n", subtotal + shippingFees);

        System.out.print("\nConfirm order? (y/n) (lower or upper case are accepted): ");
        try {
            String confirm = scanner.next();
            if (confirm.toLowerCase().equals("n")) {
                System.out.println("Order cancelled.");
                revertChanges(inventory,Changes);

            } else if (confirm.toLowerCase().equals("y")){
                double totalCost = subtotal + shippingFees;
                if (totalCost <= customer.getBalance()) {
                    customer.setBalance(customer.getBalance() - totalCost);
                    ShippingService fawryShippingService = new ShippingService(shippableItems);
                    System.out.println("Order confirmed!");
                    System.out.printf("Remaining balance: $%.2f\n", customer.getBalance());
                } else {
                    System.out.println("Insufficient funds! Order cancelled.");
                    revertChanges(inventory,Changes);

                }
            }
            else{
                System.out.println("Invalid input! Order cancelled.");
                revertChanges(inventory,Changes);


            }
        } catch (Exception e) {
            System.out.println("Invalid input! Order cancelled.");
            revertChanges(inventory,Changes);

        } finally {
            scanner.close();
        }
    }
}
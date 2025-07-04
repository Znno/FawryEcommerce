import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class Tests {
    private Cart cart;
    private Product product;
    private ShippableProduct shippableProduct;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        product = new Product("Dummy Product", 10, 5);
        shippableProduct = new ShippableProduct("Dummy Shippable", 20, 3, 2.5);
    }

    @Test
    void testAddProductToCart() {
        cart.addProduct(product);
        assertEquals(1, cart.getProducts().size());
        assertEquals(product, cart.getProducts().get(0));
    }


    @Test
    void testMultipleProductAddition() {
        cart.addProduct(product);
        cart.addProduct(shippableProduct);
        assertEquals(2, cart.getProducts().size());
        assertTrue(cart.getProducts().contains(product));
        assertTrue(cart.getProducts().contains(shippableProduct));
    }

    @Test
    void testCartCalculateTotal() {
        cart.addProduct(product);
        cart.addProduct(shippableProduct);
        assertEquals(110.0, cart.calculateTotal());
    }

    @Test
    void testCartTotalWeight() {
        cart.addProduct(shippableProduct);
        cart.addProduct(new ShippableProduct("Test Product", 15, 2, 1.5));
        assertEquals(10.5, cart.getTotalWeight());
    }

    @Test
    void testProductExpiry() {
        product.setExpirable(true);
        LocalDate expiryDate = LocalDate.of(2025, 12, 31);
        product.setExpiryDate(expiryDate);
        assertTrue(product.isExpirable());
        assertEquals(expiryDate, product.getExpiryDate());
    }

    @Test
    void testExpiredMilkScenario() {
        Product milk = new Product("Fresh Milk", 4, 1);
        milk.setExpirable(true);
        milk.setExpiryDate(LocalDate.of(2026, 6, 1));
        cart.addProduct(milk);
        assertFalse(milk.isExpired());
        milk.setExpiryDate(LocalDate.of(2024, 1, 1));
        assertTrue(milk.isExpired());
    }

    @Test
    void testEmptyCart() {
        assertTrue(cart.getProducts().isEmpty());
        assertEquals(0.0, cart.calculateTotal());
    }



    @Test
    void testOrderValidQuantity() {
        Product limitedProduct = new Product("Limited Item", 10, 5);
        limitedProduct.setQuantity(3);
        assertEquals(3, limitedProduct.getQuantity());
    }

    @Test
    void testExpiredProduct() {
        product.setExpirable(true);
        product.setExpiryDate(LocalDate.of(2024, 1, 1));
        assertTrue(product.isExpired());
    }
    
    @Test
    void testShippableProductProperties() {
        assertTrue(shippableProduct.isShippable());
        assertEquals(2.5, shippableProduct.getWeight());
    }
}

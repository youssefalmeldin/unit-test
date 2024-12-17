package example.store;

import example.account.AccountManagerImpl;
import example.account.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class StoreImpleTestUpdate {
    private StoreImpl storeImpl;
    private Product product;
    private Customer customer;

    @BeforeEach
    void setUp() {
        storeImpl = new StoreImpl(new AccountManagerImpl());
        product = new Product();
        customer = new Customer();
    }

    @Test
    void testBuy_SuccessfulPurchase() {
        // Arrange
        product.setName("Product Name");
        product.setPrice(1);
        product.setQuantity(1);

        customer.setBalance(1);
        customer.setCreditAllowed(true);
        customer.setName("Customer Name");
        customer.setVip(true);

        // Act
        storeImpl.buy(product, customer);

        // Assert
        assertEquals(0, customer.getBalance());
        assertEquals(0, product.getQuantity());
    }

    @Test
    void testBuy_InsufficientQuantity() {
        // Arrange
        product.setName("Product Name");
        product.setPrice(1);
        product.setQuantity(0);

        customer.setBalance(1);
        customer.setCreditAllowed(true);
        customer.setName("Customer Name");
        customer.setVip(true);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> storeImpl.buy(product, customer));
    }

    @Test
    void testBuy_InsufficientFunds() {
        // Arrange
        product.setName("Product Name");
        product.setPrice(Integer.MIN_VALUE);
        product.setQuantity(1);

        customer.setBalance(1);
        customer.setCreditAllowed(false);
        customer.setName("Customer Name");
        customer.setVip(true);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> storeImpl.buy(product, customer));
    }

    @Test
    void testStoreImpl_AccountManagerType() {
        // Arrange & Act & Assert
        assertTrue(storeImpl.accountManager instanceof AccountManagerImpl);
    }
}


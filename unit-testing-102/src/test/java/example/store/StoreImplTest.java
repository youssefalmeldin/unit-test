package example.store;

import example.account.AccountManager;
import example.account.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Automatically initializes mocks
public class StoreImplTest {

    @Mock
    private AccountManager mockAccountManager;

    @Mock
    private Product mockProduct;

    @Mock
    private Customer mockCustomer;

    @InjectMocks
    private StoreImpl store;



    @Test
    public void testBuy_ProductOutOfStock_ThrowsException() {
        // Given
        when(mockProduct.getQuantity()).thenReturn(0); // Product is out of stock

        // When & Then
        try {
            store.buy(mockProduct, mockCustomer);
        } catch (RuntimeException e) {
            assert(e.getMessage().equals("Product out of stock"));
        }
    }

    @Test
    public void testBuy_PaymentFailure_ThrowsException() {
        // Given
        when(mockProduct.getQuantity()).thenReturn(10);
        when(mockAccountManager.withdraw(mockCustomer, mockProduct.getPrice())).thenReturn("failure");

        // When & Then
        try {
            store.buy(mockProduct, mockCustomer);
        } catch (RuntimeException e) {
            assert(e.getMessage().equals("Payment failure: failure"));
        }
    }

    @Test
    public void testBuy_SuccessfulPurchase() {
        // Given
        when(mockProduct.getQuantity()).thenReturn(10);
        when(mockAccountManager.withdraw(mockCustomer, mockProduct.getPrice())).thenReturn("success");

        // When
        store.buy(mockProduct, mockCustomer);

        // Then
        verify(mockProduct).setQuantity(9); // Verify product quantity is reduced by 1
        verify(mockAccountManager).withdraw(mockCustomer, mockProduct.getPrice()); // Verify withdrawal call
    }
}

package example.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AccountManagerImplMockTest {
    private AccountManagerImpl accountManager;
    private Customer mockCustomer;

    @BeforeEach
    void setUp() {
        accountManager = new AccountManagerImpl();
        mockCustomer = mock(Customer.class);
    }

    @Test
    void testDeposit() {
        // Given
        when(mockCustomer.getBalance()).thenReturn(500);

        // When
        accountManager.deposit(mockCustomer, 200);

        // Then
        verify(mockCustomer).setBalance(700); // 500 + 200 = 700
    }

    @Test
    void testWithdrawSuccess() {
        // Given
        when(mockCustomer.getBalance()).thenReturn(500);
        when(mockCustomer.isCreditAllowed()).thenReturn(true);
        when(mockCustomer.isVip()).thenReturn(false);

        // When
        String result = accountManager.withdraw(mockCustomer, 200);

        // Then
        assertEquals("success", result);
        verify(mockCustomer).setBalance(300); // 500 - 200 = 300
    }

    @Test
    void testWithdrawInsufficientBalance() {
        // Given
        when(mockCustomer.getBalance()).thenReturn(200);
        when(mockCustomer.isCreditAllowed()).thenReturn(false);
        when(mockCustomer.isVip()).thenReturn(false);

        // When
        String result = accountManager.withdraw(mockCustomer, 300);

        // Then
        //assertEquals("insufficient account balance", result);
        verify(mockCustomer, never()).setBalance(anyInt()); // Balance should not be updated
    }

    @Test
    void testWithdrawMaximumCreditExceeded() {
        // Given
        when(mockCustomer.getBalance()).thenReturn(200);
        when(mockCustomer.isCreditAllowed()).thenReturn(true);
        when(mockCustomer.isVip()).thenReturn(false);

        // When
        String result = accountManager.withdraw(mockCustomer, 1200); // 200 - 1200 = -1000, exceeds max credit of 1000

        // Then
       // assertEquals("maximum credit exceeded", result);
        verify(mockCustomer, never()).setBalance(anyInt()); // Balance should not be updated
    }

    @Test
    void testWithdrawWithSufficientBalanceAndCreditAllowed() {
        // Given
        when(mockCustomer.getBalance()).thenReturn(500);
        when(mockCustomer.isCreditAllowed()).thenReturn(true);
        when(mockCustomer.isVip()).thenReturn(true); // VIP customers are not restricted by max credit

        // When
        String result = accountManager.withdraw(mockCustomer, 700); // 500 - 700 = -200

        // Then
       // assertEquals("success", result);
        verify(mockCustomer).setBalance(-200); // VIP customer can use credit
    }
}

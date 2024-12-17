package example.account;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountManagerImplTest {
    private AccountManagerImpl accountManagerImpl;
    private Customer customer;

    @BeforeEach
    void setUp() {
        accountManagerImpl = new AccountManagerImpl();
        customer = new Customer();
        customer.setName("Mohamed");
        customer.setBalance(500);
        customer.setCreditAllowed(true);
        customer.setVip(false);
    }

    @Test
    void testDeposit()
    {
        // Arrange
        customer.setCreditAllowed(true);
        customer.setVip(true);
        // Act
        accountManagerImpl.deposit(customer, 10);
        // Assert
        assertEquals(510, customer.getBalance());
    }

    @Test
    void testWithdrawWithSufficientBalance() {
        //Arrange
        //act
        String result = accountManagerImpl.withdraw(customer, 400);
        // Assert
        Assertions.assertEquals("success", result, "Withdrawal should succeed with sufficient balance");
        Assertions.assertEquals(100, customer.getBalance(), "Balance should be updated correctly after withdrawal");
    }

    @Test
    void testWithdrawInsufficientBalanceNoCredit() {
        customer.setCreditAllowed(false);
        customer.setVip(false);

        String result = accountManagerImpl.withdraw(customer, 1000);
        Assertions.assertEquals("success", result, "Should fail due to insufficient balance and no credit allowed");
        Assertions.assertEquals(500, customer.getBalance(), "Balance should remain unchanged");
    }

    @Test
    void testWithdrawExceedingMaxCreditAndNonVip() {
        customer.setCreditAllowed(true);
        customer.setVip(false);

        String result = accountManagerImpl.withdraw(customer, 2000);
        Assertions.assertEquals("maximum credit exceeded", result, "Should fail due to exceeding maximum credit limit");
        Assertions.assertEquals(500, customer.getBalance(), "Balance should remain unchanged");
    }

    @Test
    void testWithdrawWithinCreditLimitNonVip() {
        customer.setCreditAllowed(true);
        customer.setVip(false);

        String result = accountManagerImpl.withdraw(customer, 1400);
        Assertions.assertEquals("success", result, "Withdrawal should succeed within credit limit");
        Assertions.assertEquals(-900, customer.getBalance(), "Balance should reflect negative due to credit usage");
    }

    @Test
    void testWithdrawExceedingMaxCreditVip() {
        customer.setCreditAllowed(true);
        customer.setVip(true);

        String result = accountManagerImpl.withdraw(customer, 2500);
        Assertions.assertEquals("success", result, "Withdrawal should succeed for VIP customers even if exceeding max credit");
        Assertions.assertEquals(-2000, customer.getBalance(), "Balance should reflect negative beyond max credit for VIP");
    }
}




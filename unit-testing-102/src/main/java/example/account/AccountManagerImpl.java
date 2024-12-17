package example.account;

public class AccountManagerImpl implements AccountManager {
    private static final int MAX_CREDIT = 1000;
    @Override
    public void deposit(Customer customer, int amount) {
        customer.setBalance(customer.getBalance() + amount);
    }

    @Override
    public String withdraw(Customer customer, int amount) {
        int expectedBalance = customer.getBalance() - amount;

        if (expectedBalance < 0) {
            if (!customer.isCreditAllowed()) {
                return "insufficient account balance";
            }
            if (Math.abs(expectedBalance) > MAX_CREDIT && !customer.isVip()) {
                return "maximum credit exceeded";
            }
        }
        // Ensure balance is updated only if the conditions above are not met
        customer.setBalance(expectedBalance);
        return "success";
    }

}

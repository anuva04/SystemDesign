/**
 * This pattern is used to abstract complexity of various operations of a class from users.
 * In this example, user has knowledge of only SendMoney() and GetBalance() methods.
 * Other intricate details of UPI service providers, getting details from bank etc. are not made visible to the users.
 */

class FacadePattern {
    public static void main(String[] args) {
        UPIPaymentApp app = new UPIPaymentApp("dummy@okdummy");

        System.out.println("Balance: " + app.GetBalance());

        app.SendMoney(500);
        app.SendMoney(1500);
    }
}

class UPIPaymentApp {
    private String upiId;

    public UPIPaymentApp(String upiId) {
        this.upiId = upiId;
    }
    public void SendMoney(int amount) {
        int balance = GetBalance();

        if(balance >= amount) {
            System.out.println("Money transfer successful");
        } else {
            System.out.println("Not enough balance");
        }
    }

    public int GetBalance() {
        UpiServiceProvider upiServiceProvider = new UpiServiceProvider();
        upiServiceProvider.IsUpiValid(upiId);
        int accountNumber = upiServiceProvider.GetBank(upiId);

        Bank bank = new Bank();
        int balance = bank.GetBankBalance(accountNumber);

        System.out.println("Balance: " + balance);

        return balance;
    }
}

class UpiServiceProvider {
    public void IsUpiValid(String upiId) {
        System.out.println("Validating UPI ID...");
        System.out.println("UPI ID valid.");
    }

    public int GetBank(String upiId) {
        System.out.println("Finding Bank...");
        return 12345;
    }
}

class Bank {
    public int GetBankBalance(int accountNumber) {
        System.out.println("Getting balance for account " + accountNumber + "...");
        return 1000;
    }
}
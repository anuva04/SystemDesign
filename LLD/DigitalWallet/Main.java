import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
class Main {
    public static void main(String[] args) {
        print("Initiating wallet service...");
        WalletService walletService = new WalletService();
        print("Intiated wallet service successfully!");

        print("----- 1. Registering Users -----");
        Wallet wallet1 = walletService.registerUser("userA");
        Wallet wallet2 = walletService.registerUser("userB");
        Wallet wallet3 = walletService.registerUser("userC");

        print("Registered User 1: " + walletService.getUser(wallet1.getUserId()).getName() + " (ID: " + wallet1.getUserId() + ")");
        print("Registered User 2: " + walletService.getUser(wallet2.getUserId()).getName() + " (ID: " + wallet2.getUserId() + ")");
        print("Registered User 3: " + walletService.getUser(wallet3.getUserId()).getName() + " (ID: " + wallet3.getUserId() + ")\n");

        print("----- 2. Loading Money with Payment Sources -----");
        PaymentSource creditCard = new CreditCard();
        PaymentSource upi = new Upi();
        PaymentSource debitCard = new DebitCard();

        print(wallet1.loadMoney(1000.0, creditCard));
        print(wallet2.loadMoney(500.0, upi));
        print(wallet3.loadMoney(200.0, debitCard));
        print(wallet1.loadMoney(0.0, creditCard) + "\n"); // Attempting to load minimum amount (negative case)

        print("----- 3. Checking Wallet Balance -----");
        print("Balance for " + walletService.getUser(wallet1.getUserId()).getName() + ": Rs." + wallet1.getBalance());
        print("Balance for " + walletService.getUser(wallet2.getUserId()).getName() + ": Rs." + wallet2.getBalance());
        print("Balance for " + walletService.getUser(wallet3.getUserId()).getName() + ": Rs." + wallet3.getBalance() + "\n");

        print("----- 4. Sending Money -----");
        print(wallet1.sendMoney(200.0, wallet2));
        print(wallet2.sendMoney(150.0, wallet3));
        print(wallet3.sendMoney(100.0, wallet1));
        print(wallet1.sendMoney(1000.0, wallet2) + "\n"); // Insufficient balance (negative case)

        print("----- 4. Checking Updated Wallet Balance -----");
        print("Balance for " + walletService.getUser(wallet1.getUserId()).getName() + ": Rs." + wallet1.getBalance());
        print("Balance for " + walletService.getUser(wallet2.getUserId()).getName() + ": Rs." + wallet2.getBalance());
        print("Balance for " + walletService.getUser(wallet3.getUserId()).getName() + ": Rs." + wallet3.getBalance() + "\n");

        print("----- 5. Getting Transaction History for " + walletService.getUser(wallet1.getUserId()).getName() + " -----");

        print("--- All Transactions (Sorted by Timestamp) ---");
        List<Transaction> user1History = wallet1.getTransactionHistory(SortBy.TIMESTAMP, null);
        user1History.forEach(t -> print(t.toString()));

        print("\n--- All Transactions (Sorted by Amount) ---");
        user1History = wallet1.getTransactionHistory(SortBy.AMOUNT, null);
        user1History.forEach(t -> print(t.toString()));

        print("\n--- Filtered: Sent Transactions Only ---");
        List<Transaction> sentHistory = wallet1.getTransactionHistory(SortBy.TIMESTAMP, TransactionType.SEND);
        sentHistory.forEach(t -> print(t.toString()));

        print("\n--- Filtered: Received Transactions Only ---");
        List<Transaction> receivedHistory = wallet1.getTransactionHistory(SortBy.TIMESTAMP, TransactionType.RECEIVE);
        receivedHistory.forEach(t -> print(t.toString()));

        print("\n----- Multithreading scenario -----\n");
        Wallet wallet4 = walletService.registerUser("userD");
        Wallet wallet5 = walletService.registerUser("userE");

        print("Registered User 4: " + walletService.getUser(wallet4.getUserId()).getName() + " (ID: " + wallet4.getUserId() + ")");
        print("Registered User 5: " + walletService.getUser(wallet5.getUserId()).getName() + " (ID: " + wallet5.getUserId() + ")\n");

        print(wallet4.loadMoney(1000.0, creditCard));
        print(wallet5.loadMoney(500.0, upi));

        print("Balance for " + walletService.getUser(wallet4.getUserId()).getName() + ": Rs." + wallet4.getBalance());
        print("Balance for " + walletService.getUser(wallet5.getUserId()).getName() + ": Rs." + wallet5.getBalance() + "\n");

        print("----- Concurrent Money Transfers -----");
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                print(wallet4.sendMoney(10.0, wallet5));
            });
            executor.submit(() -> {
                print(wallet5.sendMoney(5.0, wallet4));
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.out.println("Error occurred while terminating executor");
        }
        print("Final Balance for " + walletService.getUser(wallet4.getUserId()).getName() + ": Rs." + wallet4.getBalance());
        print("Final Balance for " + walletService.getUser(wallet5.getUserId()).getName() + ": Rs." + wallet5.getBalance() + "\n");

        print("Getting Transaction History for " + walletService.getUser(wallet4.getUserId()).getName());
        wallet4.getTransactionHistory(SortBy.TIMESTAMP, null).forEach(t -> print(t.toString()));
    }

    private static void print(String message) {
        try {
            System.out.println(message);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("An error occured!!");
        }
    }

    private static void print(TransactionResponse tr) {
        try {
            System.out.println(tr.toString());
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("An error occured!!");
        }
    }
}
import java.util.*;
class Transaction {
    private final String fromUserId;
    private final String toUserId;
    private final double amount;
    private final Date timestamp;
    private final TransactionType type;
    private final double balance;

    public Transaction(String fromUserId, String toUserId, double amount, Date timestamp, TransactionType type, double balance) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.amount = amount;
        this.timestamp = timestamp;
        this.type = type;
        this.balance = balance;
    }

    // Getters
    public String getFromUserId() { return fromUserId; }
    public String getToUserId() { return toUserId; }
    public double getAmount() { return amount; }
    public Date getTimestamp() { return timestamp; }
    public TransactionType getType() { return type; }
    public double getBalance() { return balance; }

    @Override
    public String toString() {
        return String.format("Type: %s | Amount: Rs.%.2f | From: %s | To: %s | Timestamp: %s | Balance: %.2f",
                type, amount, fromUserId, toUserId, timestamp, balance);
    }
}
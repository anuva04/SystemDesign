import java.util.*;
import java.util.stream.Collectors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;
class Wallet {
    private final String userId;
    private double balance;
    private final List<Transaction> transactionList;
    private final ReentrantLock lock;

    public Wallet(String userId) {
        this.userId = userId;
        this.balance = 0.0;
        this.transactionList = new ArrayList<>();
        this.lock = new ReentrantLock();
    }

    public String getUserId() {
        return userId;
    }

    public synchronized TransactionResponse loadMoney(double amount, PaymentSource source) {
        if(amount <= 0) {
            return new TransactionResponse(false, "Minimum load amount must be greater than 0");
        }

        boolean isSuccess = source.transferMoney(amount, userId);

        if(isSuccess) {
            balance += amount;
            return new TransactionResponse(true, String.format("Loaded %.2f via %s in user %s's wallet. Balance is %.2f", amount, source.getClass().getSimpleName(), userId, balance));
        } else {
            return new TransactionResponse(false, "Failed to load money!");
        }
    }

    public synchronized TransactionResponse loadWalletTransfer(double amount, Wallet senderWallet) {
        if(amount <= 0) {
            return new TransactionResponse(false, String.format("Invalid amount received from %s", senderWallet.getUserId()));
        }

        Random rd = new Random();
        if(rd.nextInt()%2 == 0) {
            return new TransactionResponse(false, "Failed to load money due to internal server error");
        }

        balance += amount;
        Transaction receivedTransaction = new Transaction(senderWallet.getUserId(), this.userId, amount, new Date(), TransactionType.RECEIVE, balance);
        transactionList.add(receivedTransaction);

        return new TransactionResponse(true, String.format("User %s received amount %.2f from %s", userId, amount, senderWallet.getUserId()));
    }

    public TransactionResponse sendMoney(double amount, Wallet receiverWallet) {
        if(amount <= 0) {
            return new TransactionResponse(false, "Minimum amount must be greater than 0.");
        }

        Wallet firstLock = this.userId.compareTo(receiverWallet.getUserId()) < 0 ? this : receiverWallet;
        Wallet secondLock = this.userId.compareTo(receiverWallet.getUserId()) < 0 ? receiverWallet : this;
        boolean firstLockAcquired = false;
        boolean secondLockAcquired = false;

        try {
            firstLockAcquired = firstLock.lock.tryLock(100, TimeUnit.MILLISECONDS);
            if(firstLockAcquired) {
                secondLockAcquired = secondLock.lock.tryLock(100, TimeUnit.MILLISECONDS);
                if(secondLockAcquired) {
                    if (this.balance < amount) {
                        return new TransactionResponse(false, "Insufficient balance.");
                    }

                    balance -= amount;
                    TransactionResponse response = receiverWallet.loadWalletTransfer(amount, this);

                    if(response.isSuccess) {
                        Transaction sentTransaction = new Transaction(userId, receiverWallet.getUserId(), amount, new Date(), TransactionType.SEND, balance);
                        transactionList.add(sentTransaction);

                        return new TransactionResponse(true, String.format("Transaction amount %.2f to %s from %s", amount, receiverWallet.getUserId(), userId));
                    } else {
                        balance += amount;
                        return new TransactionResponse(false, String.format("An error occured while transferring amount %.2f to %s from %s. Error message: %s", amount, receiverWallet.getUserId(), userId, response.message));
                    }
                } else {
                    return new TransactionResponse(false, "Could not acquire second lock within the timeout.");
                }
            } else {
                return new TransactionResponse(false, "Could not acquire first lock within the timeout.");
            }
        } catch (InterruptedException e) {
            return new TransactionResponse(false, "Transaction was interrupted.");
        } finally {
            if (firstLockAcquired) {
                firstLock.lock.unlock();
            }
            if (secondLockAcquired) {
                secondLock.lock.unlock();
            }
        }
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactionHistory(SortBy sortBy, TransactionType transactionType) {
        List<Transaction> filteredList = this.transactionList.stream()
                                            .filter(t -> transactionType == null || t.getType() == transactionType)
                                            .collect(Collectors.toList());

        if(sortBy == SortBy.AMOUNT) {
            filteredList.sort(Comparator.comparingDouble(Transaction::getAmount));
        } else if(sortBy == SortBy.TIMESTAMP) {
            filteredList.sort(Comparator.comparing(Transaction::getTimestamp));
        }

        return filteredList;
    }
}
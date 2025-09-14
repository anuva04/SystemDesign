package org.example;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryManager {
    private final List<Transaction> transactionList = new ArrayList<>();

    public void logTransaction(Transaction transaction) {
        transactionList.add(transaction);
    }

    public List<Transaction> getTransactionHistory() {
        return transactionList;
    }
}

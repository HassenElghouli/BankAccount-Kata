package bank;

import bank.transactions.OverdraftException;
import bank.transactions.Transaction;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class AccountHistory {
    private final List<Transaction> transactions = new LinkedList<>();
    public void addTransaction(Transaction transaction){
        if(getBalance().add(transaction.getSignedAmount()).compareTo(BigDecimal.ZERO)<0){
            throw new OverdraftException();
        }
        transactions.add(transaction);
    }

     BigDecimal getBalance() {
        return transactions.stream().map(Transaction::getSignedAmount).reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    List<Transaction> getTransactions(){
        return Collections.unmodifiableList(transactions);
    }
}

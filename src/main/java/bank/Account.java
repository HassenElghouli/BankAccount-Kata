package bank;

import bank.transactions.Transaction;
import bank.transactions.TransactionType;
import services.DateService;
import services.PrinterService;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.List;

public class Account {

    private final AccountHistory accountHistory;

    private  final DateService dateService;

    public Account(AccountHistory accountHistory, DateService dateService) {
        this.accountHistory = accountHistory;
        this.dateService = dateService;
    }

    public void deposit(BigDecimal amount){
        Transaction deposit =Transaction.create(amount, TransactionType.DEPOSIT,dateService.getDate());
        accountHistory.addTransaction(deposit);
    }

    public void withdrawal(BigDecimal amount){
        Transaction withdrawal =Transaction.create(amount, TransactionType.WITHDRAWAL,dateService.getDate());
        accountHistory.addTransaction(withdrawal);
    }

    public BigDecimal getBalance(){
        return  accountHistory.getBalance();
    }

    public void printStatement(ZoneId zoneId, PrinterService printerService){
        List<Transaction> transactions=accountHistory.getTransactions();
        AccountStatementFormatter.printStatement(transactions,zoneId,printerService);
    }

}

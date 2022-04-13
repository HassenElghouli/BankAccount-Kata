package bank;

import bank.transactions.Transaction;
import bank.transactions.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountHistoryTest {
    private AccountHistory accountHistory;


    @BeforeEach
    void setUp() {
        accountHistory = new AccountHistory();
    }

    @DisplayName("The added operations should be saved in order")
    @Test
    void operationsTest() {
        List<Transaction> transactions = List.of(
                Transaction.create(BigDecimal.TEN, TransactionType.DEPOSIT, Instant.now()),
                Transaction.create(BigDecimal.ONE, TransactionType.WITHDRAWAL, Instant.now()),
                Transaction.create(BigDecimal.ONE, TransactionType.DEPOSIT, Instant.now()),
                Transaction.create(BigDecimal.TEN, TransactionType.WITHDRAWAL, Instant.now())
        );

        transactions.forEach(t -> accountHistory.addTransaction(t));

        assertThat(accountHistory.getTransactions()).isEqualTo(transactions);
    }

    @DisplayName("The balance should be computed using the saved operations")
    @ParameterizedTest
    @CsvSource(value = {"10, 5, 5", "100, 20, 80", "300, 75, 225"})
    void balanceTest(int deposit, int withdrawal, int balance) {
        List<Transaction> transactions = List.of(
                Transaction.create(BigDecimal.valueOf(deposit), TransactionType.DEPOSIT, Instant.now()),
                Transaction.create(BigDecimal.valueOf(withdrawal), TransactionType.WITHDRAWAL, Instant.now())
        );

        transactions.forEach(t -> accountHistory.addTransaction(t));

        BigDecimal expectedBalance = BigDecimal.valueOf(balance);

        assertThat(accountHistory.getBalance()).isEqualTo(expectedBalance);
    }

}

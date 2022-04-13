package bank;

import bank.transactions.OverdraftException;
import bank.transactions.Transaction;
import bank.transactions.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import services.DateService;
import services.DateServiceImpl;
import services.InMemoryPrinterService;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account(
                new AccountHistory(),
                new DateServiceImpl()
        );
    }

    @DisplayName("When an account is created, it shouldn't have any money")
    @Test
    void creationTest() {
        assertThat(account.getBalance()).isEqualTo(BigDecimal.ZERO);
    }

    @DisplayName("When a deposit is made, the balance should increase accordingly")
    @ParameterizedTest
    @ValueSource(ints = { 1, 10, 50, 200 })
    void depositTest(int input) {
        BigDecimal amount = BigDecimal.valueOf(input);
        account.deposit(amount);
        assertThat(account.getBalance()).isEqualTo(amount);
    }

    @DisplayName("When a withdrawal is made, the balance should decrease accordingly")
    @ParameterizedTest
    @ValueSource(ints = { 1, 10, 50, 200 })
    void withdrawalTest(int input) {
        BigDecimal amount = BigDecimal.valueOf(input);
        account.deposit(amount);
        assertThat(account.getBalance()).isEqualTo(amount);

        account.withdrawal(amount);
        assertThat(account.getBalance()).isEqualTo(BigDecimal.ZERO);
    }

    @DisplayName("A withdrawal shouldn't result in an overdraft")
    @ParameterizedTest
    @ValueSource(ints = { 1, 10, 50, 200 })
    void overdraftTest(int input) {
        BigDecimal amount = BigDecimal.valueOf(input);
        assertThrows(
                OverdraftException.class,
                () -> account.withdrawal(amount),
                OverdraftException.OVERDRAFT_MESSAGE
        );
    }

    @DisplayName("The balance should be equals to the sum of all transactions")
    @Test
    void balanceTest() {
        List<Transaction> transactions = List.of(
                Transaction.create(BigDecimal.TEN, TransactionType.DEPOSIT, Instant.now()),
                Transaction.create(BigDecimal.ONE, TransactionType.WITHDRAWAL, Instant.now()),
                Transaction.create(BigDecimal.ONE, TransactionType.DEPOSIT, Instant.now()),
                Transaction.create(BigDecimal.TEN, TransactionType.WITHDRAWAL, Instant.now()),
                Transaction.create(BigDecimal.ONE, TransactionType.DEPOSIT, Instant.now())
        );

        AccountHistory accountHistory = new AccountHistory();
        transactions.forEach(accountHistory::addTransaction);

        account = new Account(accountHistory, new DateServiceImpl());

        assertThat(account.getBalance()).isEqualTo(BigDecimal.ONE);
    }

    @DisplayName("When printing the account statement, it should display the date, type, amount and balance for every operation")
    @ParameterizedTest
    @ValueSource(strings = { "2016-01-11T10:07:00.00Z", "2022-04-13T22:51:30.00Z" })
    void dateTest(String input) {
        DateService dateService = () -> Instant.parse(input);

        account = new Account(
                new AccountHistory(),
                dateService
        );

        account.deposit(BigDecimal.TEN);
        account.withdrawal(BigDecimal.ONE);
        account.deposit(BigDecimal.TEN);

        InMemoryPrinterService printerService = new InMemoryPrinterService();
        ZoneId zoneId = Clock.systemDefaultZone().getZone();
        account.printStatement(zoneId, printerService);

        String printedDate = AccountStatementFormatter.getFormattedDate(dateService.getDate(), zoneId);
        List<String> expected = List.of(
                printedDate + " - Deposit of 10. Balance: 10",
                printedDate + " - Withdrawal of 1. Balance: 9",
                printedDate + " - Deposit of 10. Balance: 19"
        );
        assertThat(printerService.getPrintedLines()).isEqualTo(expected);
    }

}

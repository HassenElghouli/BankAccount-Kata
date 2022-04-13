package bank;

import bank.transactions.Transaction;
import services.PrinterService;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AccountStatementFormatter {
    private static final String OPERATION_FORMAT = "%s - %s of %s. Balance: %s";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    static void printStatement(List<Transaction> transactions, ZoneId zoneId, PrinterService printerService) {
        BigDecimal balance = BigDecimal.ZERO;

        for (Transaction transaction : transactions) {
            balance = balance.add(transaction.getSignedAmount());
            String line = String.format(OPERATION_FORMAT,
                    getFormattedDate(transaction.getDate(), zoneId),
                    transaction.getType(),
                    transaction.getAmount(),
                    balance);
            printerService.print(line);
        }
    }

    static String getFormattedDate(Instant instant, ZoneId zoneId) {
        return LocalDateTime.ofInstant(instant, zoneId)
                .format(DATE_FORMATTER);
    }

}

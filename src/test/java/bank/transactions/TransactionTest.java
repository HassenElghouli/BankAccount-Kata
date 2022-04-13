package bank.transactions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionTest {

    @DisplayName("The amount of an operation shouldn't be negative")
    @ParameterizedTest
    @ValueSource(ints = { -5, -20, -100, -350 })
    void negativeAmountTest(int input) {
        BigDecimal amount = BigDecimal.valueOf(input);

        for (TransactionType type : TransactionType.values()) {
            assertThrows(
                    NegativeAmountException.class,
                    () -> Transaction.create(amount, type, Instant.now()),
                    NegativeAmountException.NEGATIVE_AMOUNT_MESSAGE
            );
        }
    }


}

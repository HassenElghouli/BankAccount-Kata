package bank.transactions;

public class NegativeAmountException extends IllegalTransactionException {
    public static final String NEGATIVE_AMOUNT_MESSAGE = "Negative amounts are not allowed";

    public NegativeAmountException() {
        super(NEGATIVE_AMOUNT_MESSAGE);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

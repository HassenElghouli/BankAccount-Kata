package bank.transactions;

import java.math.BigDecimal;
import java.time.Instant;

public class Transaction {
    private final BigDecimal amount;
    private final TransactionType type;

    private final Instant date;

    private Transaction(BigDecimal amount, TransactionType type, Instant date) {
        this.amount = amount;
        this.type = type;
        this.date = date;
    }

    public static Transaction create(BigDecimal amount, TransactionType type, Instant date){
        if(amount.compareTo(BigDecimal.ZERO)<0) {
            throw new NegativeAmountException();
        }
        return new Transaction(amount,  type,  date);
    }

    public BigDecimal getAmount(){
        return amount;
    }

    public BigDecimal getSignedAmount(){
        if(type==TransactionType.DEPOSIT){
            return amount;
        }
        if(type==TransactionType.WITHDRAWAL){
            return amount.negate();
        }
        throw  new UnsupportedOperationException(type.toString());
    }

    public TransactionType getType() {
        return type;
    }

    public Instant getDate() {
        return date;
    }
}

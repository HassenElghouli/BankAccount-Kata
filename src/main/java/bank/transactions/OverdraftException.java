package bank.transactions;

public class OverdraftException extends  IllegalTransactionException{
    public static final String OVERDRAFT_MESSAGE ="No overdraft allowed" ;

    public OverdraftException() {
        super(OVERDRAFT_MESSAGE);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}

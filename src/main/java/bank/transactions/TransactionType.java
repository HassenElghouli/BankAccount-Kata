package bank.transactions;

public enum TransactionType {
    DEPOSIT("Deposit"),
    WITHDRAWAL("Withdrawal");
    private String name;
    TransactionType(String name) {
        this.name=name;
    }

    @Override
    public String toString() {
        return name;
    }
}

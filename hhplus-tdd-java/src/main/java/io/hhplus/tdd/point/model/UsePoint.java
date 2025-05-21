package io.hhplus.tdd.point.model;

import io.hhplus.tdd.point.TransactionType;

public class UsePoint implements Point {
    private final Amount amount;

    private UsePoint(Amount amount) {
        this.amount = amount;
    }
    public static UsePoint of(Amount amount) {
        return new UsePoint(amount);
    }
    @Override
    public long getAmount() {
        return - amount.getValue();
    }

    @Override
    public TransactionType getTransactionType() {
        return TransactionType.USE;
    }
}

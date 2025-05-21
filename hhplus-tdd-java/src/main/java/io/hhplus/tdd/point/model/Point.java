package io.hhplus.tdd.point.model;

import io.hhplus.tdd.point.TransactionType;

public interface Point {
    long getAmount();
    TransactionType getTransactionType();
}

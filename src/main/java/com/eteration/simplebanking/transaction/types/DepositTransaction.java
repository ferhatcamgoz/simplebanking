package com.eteration.simplebanking.transaction.types;


import com.eteration.simplebanking.transaction.Transaction;

import javax.persistence.Entity;

@Entity
public class DepositTransaction extends Transaction {
    public DepositTransaction(Double amount) {
        super(true, amount);
    }

    protected DepositTransaction() {
        this(0.0);
    }
}

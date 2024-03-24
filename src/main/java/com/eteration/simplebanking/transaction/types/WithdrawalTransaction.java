package com.eteration.simplebanking.transaction.types;


import com.eteration.simplebanking.transaction.Transaction;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

// This class is a place holder you can change the complete implementation
@Entity
@Getter
@Setter
public class WithdrawalTransaction extends Transaction {

    public WithdrawalTransaction(Double amount) {
        super(false, amount);
    }

    protected WithdrawalTransaction() {
        this(0.0);
    }
}



package com.eteration.simplebanking.transaction.types;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public final class PhoneBillPaymentTransaction extends WithdrawalTransaction {

    private String companyName;
    private String phoneNumber;
    public PhoneBillPaymentTransaction(String companyName, String phoneNumber, Double amount) {
        super(amount);
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
    }

    protected PhoneBillPaymentTransaction() {
        this("", "", 0.0);
    }
}

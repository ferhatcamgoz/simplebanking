package com.eteration.simplebanking.account;


// This class is a place holder you can change the complete implementation

import com.eteration.simplebanking.common.BaseEntity;
import com.eteration.simplebanking.transaction.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Account extends BaseEntity {

    private String accountNumber;
    private String owner;
    private double balance;
    @OneToMany(cascade = javax.persistence.CascadeType.ALL)
    private List<Transaction> transactions;


    //for test
    public Account(String owner, String accountNumber) {
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    public Account(String owner) {
        this.owner = owner;
        this.accountNumber = "TR" + System.currentTimeMillis();
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }
}

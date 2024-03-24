package com.eteration.simplebanking.transaction;


import com.eteration.simplebanking.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class Transaction extends BaseEntity {

    private Double amount;
    private String approvalCode;
    private String type;

    // true(credit) or false(debit)
    @JsonIgnore
    private boolean operationType;

    protected Transaction(boolean operationType, Double amount) {
        this.operationType = operationType;
        this.amount = amount;
        this.approvalCode = UUID.randomUUID().toString();
        this.type = getClassName();
    }

    @JsonIgnore
    public double getTransactionAmount(){
        if(operationType)
            return amount;
        return amount * -1;
    }

    private String getClassName(){
        return this.getClass().getSimpleName();
    }
}

package com.eteration.simplebanking.transaction;

import lombok.Data;

@Data
public class TransactionStatus {
    public String status;
    public String approvalCode;

    public TransactionStatus() {}

    public TransactionStatus(String status, String approvalCode) {
        this.status = status;
        this.approvalCode = approvalCode;
    }
}
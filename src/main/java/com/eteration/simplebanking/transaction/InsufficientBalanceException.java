package com.eteration.simplebanking.transaction;

public class InsufficientBalanceException extends RuntimeException{
    public InsufficientBalanceException(String insufficientBalance) {
        super(insufficientBalance);
    }
}

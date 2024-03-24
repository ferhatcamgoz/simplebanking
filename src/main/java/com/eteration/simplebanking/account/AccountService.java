package com.eteration.simplebanking.account;

import com.eteration.simplebanking.transaction.InsufficientBalanceException;
import com.eteration.simplebanking.transaction.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account findAccount(String number) {
       Account account = accountRepository.findByAccountNumber(number);
       if(account == null){
           throw new EntityNotFoundException("Account");
       }
       return account;
    }

    @Transactional
    public Transaction createTransaction(String accountNumber, Transaction transaction) {
        Account account = findAccount(accountNumber);
        getUpdateBalance(account, transaction.getTransactionAmount());
        account.getTransactions().add(transaction);
        accountRepository.save(account);
        return account.getTransactions().get(account.getTransactions().size() - 1);
    }

    private void getUpdateBalance(Account account, Double amount) {
        account.setBalance(account.getBalance() + amount);
        if(account.getBalance() < 0){
            throw new InsufficientBalanceException("Insufficient balance");
        }
    }
}

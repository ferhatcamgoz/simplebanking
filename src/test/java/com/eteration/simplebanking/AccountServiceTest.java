package com.eteration.simplebanking;

import com.eteration.simplebanking.account.Account;
import com.eteration.simplebanking.account.AccountRepository;
import com.eteration.simplebanking.account.AccountService;
import com.eteration.simplebanking.transaction.*;
import com.eteration.simplebanking.transaction.types.DepositTransaction;
import com.eteration.simplebanking.transaction.types.WithdrawalTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@ContextConfiguration
class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Test
    void contextLoads() {
    }


    @Test
    public void testCreateAccountAndSetBalance0() {
        Account account = new Account("Kerem Karaca", "17892");
        assertTrue(account.getOwner().equals("Kerem Karaca"));
        assertTrue(account.getAccountNumber().equals("17892"));
        assertTrue(account.getBalance() == 0);
    }

    @Test
    public void testDepositIntoAccount() {
        Account account = new Account("Demet Demircan", "9834");
        doReturn(account).when(accountRepository).findByAccountNumber("9834");

        accountService.createTransaction("9834", new DepositTransaction(100.0));
        assertTrue(account.getBalance() == 100);
    }

    @Test
    public void testWithdrawFromAccount() {
        Account account = new Account("Demet Demircan", "9834");

        doReturn(account).when(accountRepository).findByAccountNumber("9834");

        accountService.createTransaction("9834", new DepositTransaction(100.0));
        assertTrue(account.getBalance() == 100);

        accountService.createTransaction("9834", new WithdrawalTransaction(50.0));
        assertTrue(account.getBalance() == 50);
    }
    @Test
    public void testWithdrawException() {
        Account account = new Account("Demet Demircan", "9834");

        doReturn(account).when(accountRepository).findByAccountNumber("9834");

        Assertions.assertThrows( InsufficientBalanceException.class, () -> {
            accountService.createTransaction("9834", new DepositTransaction(100.0));
            accountService.createTransaction("9834", new WithdrawalTransaction(500.0));
        });

    }


    @Test
    public void testTransactions() {
        // Create account
        Account account = new Account("Canan Kaya", "1234");
        doReturn(account).when(accountRepository).findByAccountNumber("1234");

        assertTrue(account.getTransactions().size() == 0);

        // Deposit Transaction
        DepositTransaction depositTrx = new DepositTransaction(100.0);
        assertTrue(depositTrx.getCreateDate() != null);
        accountService.createTransaction(account.getAccountNumber(), depositTrx);
        assertTrue(account.getBalance() == 100);
        assertTrue(account.getTransactions().size() == 1);

        // Withdrawal Transaction
        WithdrawalTransaction withdrawalTrx = new WithdrawalTransaction(60.0);
        assertTrue(withdrawalTrx.getCreateDate() != null);
        accountService.createTransaction(account.getAccountNumber(), withdrawalTrx);
        assertTrue(account.getBalance() == 40);
        assertTrue(account.getTransactions().size() == 2);
    }


}

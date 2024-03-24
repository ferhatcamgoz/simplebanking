package com.eteration.simplebanking.account;

import com.eteration.simplebanking.transaction.*;
import com.eteration.simplebanking.transaction.types.DepositTransaction;
import com.eteration.simplebanking.transaction.types.PhoneBillPaymentTransaction;
import com.eteration.simplebanking.transaction.types.WithdrawalTransaction;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// This class is a place holder you can change the complete implementation
@AllArgsConstructor
@RestController
@RequestMapping("/account/v1")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestParam String owner) {
        return ResponseEntity.ok(accountService.createAccount(new Account(owner)));
    }
    @PostMapping("/credit/{accountNumber}")
    public ResponseEntity<TransactionStatus> credit(@PathVariable String accountNumber, @RequestBody DepositTransaction transaction) {
        Transaction transactionResult = accountService.createTransaction(accountNumber, transaction);
        return ResponseEntity.ok(new TransactionStatus("OK", transactionResult.getApprovalCode()));

    }

    @PostMapping("/debit/{accountNumber}")
    public ResponseEntity<TransactionStatus> debit(@PathVariable String accountNumber, @RequestBody WithdrawalTransaction transaction) {
        Transaction transactionResult = accountService.createTransaction(accountNumber, transaction);
        return ResponseEntity.ok(new TransactionStatus("OK", transactionResult.getApprovalCode()));
    }

    @PostMapping("/phoneBill/{accountNumber}")
    public ResponseEntity<TransactionStatus> phoneBill(@PathVariable String accountNumber,
                                                       @RequestBody PhoneBillPaymentTransaction transaction) {
        Transaction transactionResult = accountService.createTransaction(accountNumber, transaction);
        return ResponseEntity.ok(new TransactionStatus("OK", transactionResult.getApprovalCode()));
    }

    @GetMapping("/{number}")
    public ResponseEntity<Account> getAccount(@PathVariable  String number) {
        return ResponseEntity.ok(accountService.findAccount(number));
    }
}
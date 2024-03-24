package com.eteration.simplebanking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.eteration.simplebanking.account.Account;
import com.eteration.simplebanking.account.AccountRepository;

import com.eteration.simplebanking.transaction.types.DepositTransaction;
import com.eteration.simplebanking.transaction.types.WithdrawalTransaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
class ControllerTests  {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountRepository accountRepository;

    @Test
    public void givenId_Credit_thenReturnJson()
    throws Exception {
        Account account = new Account("Kerem Karaca", "12345");
        doReturn(account).when(accountRepository).findByAccountNumber("12345");

        DepositTransaction depositTransaction = new DepositTransaction(100.0);

        mockMvc.perform(MockMvcRequestBuilders.post("/account/v1/credit/{accountNumber}", "12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(depositTransaction)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"));

        verify(accountRepository, times(1)).findByAccountNumber("12345");
    }

    @Test
    public void givenId_CreditAndThenDebit_thenReturnJson()
    throws Exception {

        Account account = new Account("Kerem Karaca", "12345");
        doReturn(account).when(accountRepository).findByAccountNumber("12345");

        DepositTransaction depositTransaction = new DepositTransaction(100.00);

        mockMvc.perform(MockMvcRequestBuilders.post("/account/v1/credit/{accountNumber}", "12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(depositTransaction)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"));


        WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction(50.0);

        mockMvc.perform(MockMvcRequestBuilders.post("/account/v1/debit/{accountNumber}", "12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(withdrawalTransaction)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"));



        verify(accountRepository, times(2)).findByAccountNumber("12345");
        assertEquals(50.0, account.getBalance(),0.001);

    }

    @Test
    public void givenId_CreditAndThenDebitMoreGetException_thenReturnJson() throws Exception {

        Account account = new Account("Kerem Karaca", "12345");
        doReturn(account).when(accountRepository).findByAccountNumber("12345");

        DepositTransaction depositTransaction = new DepositTransaction(100.0);

        mockMvc.perform(MockMvcRequestBuilders.post("/account/v1/credit/{accountNumber}", "12345")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(depositTransaction)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"));


        WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction(500.0);

        mockMvc.perform(MockMvcRequestBuilders.post("/account/v1/debit/{accountNumber}", "12345")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(withdrawalTransaction)))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Insufficient balance"));
    }

}

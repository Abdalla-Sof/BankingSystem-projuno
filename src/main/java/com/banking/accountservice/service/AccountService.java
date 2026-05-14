package com.banking.accountservice.service;

import com.banking.accountservice.dto.AccountResponse;
import com.banking.accountservice.dto.CreateAccountRequest;
import com.banking.accountservice.dto.MoneyRequest;
import com.banking.accountservice.dto.UpdateAccountRequest;
import com.banking.accountservice.dto.UpdateStatusRequest;

import java.util.List;

public interface AccountService {

    AccountResponse createAccount(CreateAccountRequest request);

    List<AccountResponse> getAllAccounts();

    AccountResponse getAccountById(Long id);

    AccountResponse getAccountByNumber(String accountNumber);

    AccountResponse updateAccount(Long id, UpdateAccountRequest request);

    AccountResponse updateStatus(Long id, UpdateStatusRequest request);

    AccountResponse deposit(Long id, MoneyRequest request);

    AccountResponse withdraw(Long id, MoneyRequest request);
}
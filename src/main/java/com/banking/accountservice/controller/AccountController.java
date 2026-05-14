package com.banking.accountservice.controller;

import com.banking.accountservice.dto.AccountResponse;
import com.banking.accountservice.dto.CreateAccountRequest;
import com.banking.accountservice.dto.MoneyRequest;
import com.banking.accountservice.dto.UpdateAccountRequest;
import com.banking.accountservice.dto.UpdateStatusRequest;
import com.banking.accountservice.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        AccountResponse response = accountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        List<AccountResponse> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long id) {
        AccountResponse response = accountService.getAccountById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/number/{accountNumber}")
    public ResponseEntity<AccountResponse> getAccountByNumber(@PathVariable String accountNumber) {
        AccountResponse response = accountService.getAccountByNumber(accountNumber);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> updateAccount(@PathVariable Long id,
                                                         @Valid @RequestBody UpdateAccountRequest request) {
        AccountResponse response = accountService.updateAccount(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<AccountResponse> updateStatus(@PathVariable Long id,
                                                        @Valid @RequestBody UpdateStatusRequest request) {
        AccountResponse response = accountService.updateStatus(id, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<AccountResponse> deposit(@PathVariable Long id,
                                                   @Valid @RequestBody MoneyRequest request) {
        AccountResponse response = accountService.deposit(id, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<AccountResponse> withdraw(@PathVariable Long id,
                                                    @Valid @RequestBody MoneyRequest request) {
        AccountResponse response = accountService.withdraw(id, request);
        return ResponseEntity.ok(response);
    }
}
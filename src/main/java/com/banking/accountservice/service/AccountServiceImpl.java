package com.banking.accountservice.service;

import com.banking.accountservice.dto.AccountResponse;
import com.banking.accountservice.dto.CreateAccountRequest;
import com.banking.accountservice.dto.MoneyRequest;
import com.banking.accountservice.dto.UpdateAccountRequest;
import com.banking.accountservice.dto.UpdateStatusRequest;
import com.banking.accountservice.entity.Account;
import com.banking.accountservice.entity.AccountStatus;
import com.banking.accountservice.exception.AccountNotFoundException;
import com.banking.accountservice.exception.InsufficientFundsException;
import com.banking.accountservice.exception.InvalidAccountOperationException;
import com.banking.accountservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public AccountResponse createAccount(CreateAccountRequest request) {
        Account account = Account.builder()
                .accountNumber(generateAccountNumber())
                .ownerName(request.getOwnerName())
                .accountType(request.getAccountType())
                .balance(request.getInitialBalance())
                .status(AccountStatus.ACTIVE)
                .build();

        Account saved = accountRepository.save(account);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponse> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponse getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + id));
        return toResponse(account);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponse getAccountByNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with number: " + accountNumber));
        return toResponse(account);
    }

    @Override
    @Transactional
    public AccountResponse updateAccount(Long id, UpdateAccountRequest request) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + id));

        account.setOwnerName(request.getOwnerName());
        account.setAccountType(request.getAccountType());

        Account saved = accountRepository.save(account);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public AccountResponse updateStatus(Long id, UpdateStatusRequest request) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + id));

        account.setStatus(request.getStatus());

        Account saved = accountRepository.save(account);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public AccountResponse deposit(Long id, MoneyRequest request) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + id));

        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new InvalidAccountOperationException(
                    "Cannot deposit into a " + account.getStatus() + " account"
            );
        }

        account.setBalance(account.getBalance().add(request.getAmount()));

        Account saved = accountRepository.save(account);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public AccountResponse withdraw(Long id, MoneyRequest request) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + id));

        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new InvalidAccountOperationException(
                    "Cannot withdraw from a " + account.getStatus() + " account"
            );
        }

        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientFundsException(
                    "Insufficient funds. Available: " + account.getBalance() + ", Requested: " + request.getAmount()
            );
        }

        account.setBalance(account.getBalance().subtract(request.getAmount()));

        Account saved = accountRepository.save(account);
        return toResponse(saved);
    }

    private String generateAccountNumber() {
        return "ACC" + UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
    }

    private AccountResponse toResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .ownerName(account.getOwnerName())
                .accountType(account.getAccountType())
                .balance(account.getBalance())
                .status(account.getStatus())
                .createdAt(account.getCreatedAt())
                .build();
    }
}
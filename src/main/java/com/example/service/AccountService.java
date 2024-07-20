package com.example.service;

import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.exception.*;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Account registerAccount(Account account) {
        if (account.getUsername().isBlank()) {
            throw new BadRequestException("Invalid username");
        }
        if(account.getPassword().length() < 4) {
            throw new BadRequestException("Password must be at least 4 characters");
        }
        if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
            throw new ConflictException("Username already exists");
        }
        return accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    public Account loginAccount(Account account) {
        Optional<Account> existingAccount = accountRepository.findByUsername(account.getUsername());
        if (!existingAccount.isPresent() ||!existingAccount.get().getPassword().equals(account.getPassword())) {
            throw new UnauthorizedException("Invalid username or password");
        }
        return existingAccount.get();
    }
}

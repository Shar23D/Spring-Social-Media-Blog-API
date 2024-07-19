package com.example.service;

import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.exception.*;

@Service
public class AccountService {
    AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Account registerAccount(Account account) {
        if (account.getUsername().isBlank() || account.getPassword().length() < 4) {
            throw new BadRequestException("Invalid account");
        }
        if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
            throw new ConflictException("Username already exists");
        }
        return accountRepository.save(account);
    }

    @Transactional
    public Account loginAccount(Account account) {
        Optional<Account> existingAccount = accountRepository.findByUsername(account.getUsername());
        if (!existingAccount.isPresent() ||!existingAccount.get().getPassword().equals(account.getPassword())) {
            throw new UnauthorizedException("Invalid username or password");
        }
        return existingAccount.get();
    }
}

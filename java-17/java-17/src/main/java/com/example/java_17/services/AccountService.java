package com.example.java_17.services;

import com.example.java_17.model.Account;
import com.example.java_17.model.AccountType;
import com.example.java_17.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public UUID addAccount(Account account) {
        return accountRepository.save(account).getAccountID();
    }

    public boolean deleteAccount(UUID accountID) {
        if (accountRepository.existsById(accountID)) {
            accountRepository.deleteById(accountID);
            return true;
        }
        return false;
    }

    public boolean updateAccount(UUID accountID, String newAlias, AccountType newAccountType) {
        Account account = accountRepository.findById(accountID).orElse(null);
        if (account != null) {
            account.setAlias(newAlias);
            account.setAccountType(newAccountType);
            accountRepository.save(account);
            return true;
        }
        return false;
    }

    public List<UUID> getAccountIDs(UUID clientID) {
        return accountRepository.findByClientId(clientID).stream()
                .map(Account::getAccountID)
                .collect(Collectors.toList());
    }

    public Account getAccount(UUID accountID) {
        return accountRepository.findById(accountID).orElse(null);
    }

    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }
}

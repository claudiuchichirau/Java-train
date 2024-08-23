package org.example.service;

import org.example.model.Account;
import org.example.model.AccountType;
import org.example.model.ClientType;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class AccountService {
    private Map<UUID, Account> accounts = new HashMap<>();

    public UUID addAccount(Account account){
        accounts.put(account.getAccountID(), account);
        return account.getAccountID();
    }

    public boolean deleteAccount(UUID accountID){
        if(accounts.containsKey(accountID)) {
            accounts.remove(accountID);
            return true;
        }

        return false;
    }

    public boolean updateAccount(UUID accountID, String newAlias, AccountType newAccountType){
        Account account = accounts.get(accountID);
        if(account != null) {
            account.setAlias(newAlias);
            account.setAccountType(newAccountType);
            return true;
        }
        return false;
    }

    public List<UUID> getAccountIDs(UUID clientID){
        return accounts.values().stream()
                .filter(account -> account.getClientID().equals(clientID))
                .map(Account::getAccountID)
                .collect(Collectors.toList());
    }

    public Account getAccount(UUID accountID){
        return accounts.get(accountID);
    }

    public Map<UUID, Account> getAccounts(){
        return this.accounts;
    }
}

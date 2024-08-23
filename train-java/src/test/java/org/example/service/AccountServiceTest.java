package org.example.service;

import org.example.model.Account;
import org.example.model.AccountType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {
    @Test
    void addAccount_AddsAccountSuccessfully() {
        AccountService service = new AccountService();
        Account account = new Account(UUID.randomUUID(), "alias", AccountType.PERSONAL);
        UUID accountID = service.addAccount(account);
        assertEquals(account, service.getAccount(accountID));
    }

    @Test
    void deleteAccount_DeletesExistingAccount_ReturnsTrue() {
        AccountService service = new AccountService();
        Account account = new Account(UUID.randomUUID(), "alias", AccountType.PERSONAL);
        UUID accountID = service.addAccount(account);
        assertTrue(service.deleteAccount(accountID));
    }

    @Test
    void deleteAccount_AttemptsToDeleteNonExistingAccount_ReturnsFalse() {
        AccountService service = new AccountService();
        assertFalse(service.deleteAccount(UUID.randomUUID()));
    }

    @Test
    void updateAccount_UpdatesExistingAccount_ReturnsTrue() {
        AccountService service = new AccountService();
        Account account = new Account(UUID.randomUUID(), "alias", AccountType.PERSONAL);
        UUID accountID = service.addAccount(account);
        assertTrue(service.updateAccount(accountID, "newAlias", AccountType.PERSONAL));
    }

    @Test
    void updateAccount_AttemptsToUpdateNonExistingAccount_ReturnsFalse() {
        AccountService service = new AccountService();
        assertFalse(service.updateAccount(UUID.randomUUID(), "newAlias", AccountType.PERSONAL));
    }

    @Test
    void getAccountIDs_ReturnsCorrectAccountIDs() {
        AccountService service = new AccountService();
        UUID clientID = UUID.randomUUID();
        Account account1 = new Account(UUID.randomUUID(), "alias1", AccountType.PERSONAL);
        Account account2 = new Account(UUID.randomUUID(), "alias2", AccountType.BUSINESS);
        service.addAccount(account1);
        service.addAccount(account2);
        List<UUID> accountIDs = service.getAccountIDs(clientID);
        assertTrue(accountIDs.contains(account1.getAccountID()) && accountIDs.contains(account2.getAccountID()));
    }

    @Test
    void getAccount_ReturnsCorrectAccount() {
        AccountService service = new AccountService();
        Account account = new Account(UUID.randomUUID(), "alias", AccountType.PERSONAL);
        UUID accountID = service.addAccount(account);
        assertEquals(account, service.getAccount(accountID));
    }

    @Test
    void getAccounts_ReturnsAllAccounts() {
        AccountService service = new AccountService();
        Account account1 = new Account(UUID.randomUUID(), "alias1", AccountType.PERSONAL);
        Account account2 = new Account(UUID.randomUUID(), "alias2", AccountType.BUSINESS);
        service.addAccount(account1);
        service.addAccount(account2);
        Map<UUID, Account> accounts = service.getAccounts();
        assertTrue(accounts.values().contains(account1) && accounts.values().contains(account2));
    }
}
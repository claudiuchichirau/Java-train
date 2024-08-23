package org.example.model;

import org.example.service.AccountService;
import org.example.service.ClientService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {
    private AccountService accountService = new AccountService();
    private ClientService clientService = new ClientService();

    @Test
    void validateEmail_ValidGmail_ReturnsTrue() {
        assertTrue(Client.validateEmail("firstname.lastname@gmail.com"));
    }

    @Test
    void validateEmail_ValidYahoo_ReturnsTrue() {
        assertTrue(Client.validateEmail("firstname.lastname@yahoo.com"));
    }

    @Test
    void validateEmail_InvalidDomain_ReturnsFalse() {
        assertFalse(Client.validateEmail("firstname.lastname@hotmail.com"));
    }

    @Test
    void validateEmail_MissingLocalPart_ReturnsFalse() {
        assertFalse(Client.validateEmail("@gmail.com"));
    }

    @Test
    void validateEmail_MissingDomain_ReturnsFalse() {
        assertFalse(Client.validateEmail("firstname.lastname@"));
    }

    @Test
    void validateEmail_MissingAtSymbol_ReturnsFalse() {
        assertFalse(Client.validateEmail("firstnamelastname@gmail.com"));
    }

    @Test
    void validateEmail_MissingDotInLocalPart_ReturnsFalse() {
        assertFalse(Client.validateEmail("firstnamelastname@gmail.com"));
    }

    @Test
    void validateEmail_EmptyString_ReturnsFalse() {
        assertFalse(Client.validateEmail(""));
    }

    @Test
    void hasRecentTransactions_WithRecentTransaction_ReturnsTrue() {
        Client client1 = new Client("John", "Doe", 1990, "New York", new HashSet<>(Arrays.asList("john.doe@gmail.com")), ClientType.PERSONAL);
        Account account1 = new Account(client1.getClientID(), "Primary", AccountType.PERSONAL);
        account1.addSum(1000);
        accountService.addAccount(account1);

        Client client2 = new Client("Alex", "York", 2002, "London", new HashSet<>(Arrays.asList("alex.york@yahoo.com")), ClientType.PERSONAL);
        Account account2 = new Account(client2.getClientID(), "Primary", AccountType.PERSONAL);
        accountService.addAccount(account2);

        Transaction recentTransaction = new Transaction(account1.getAccountID(), account2.getAccountID(), 100);

        client1.addTransaction(recentTransaction);
        client2.addTransaction(recentTransaction);

        assertTrue(client1.hasRecentTransactions(LocalDateTime.now().minusDays(1), account1.getAccountID()));
    }

    @Test
    void hasRecentTransactions_WithNoTransactionForAccount_ReturnsFalse() {
        Client client1 = new Client("John", "Doe", 1990, "New York", new HashSet<>(Arrays.asList("john.doe@gmail.com")), ClientType.PERSONAL);
        Account account1 = new Account(client1.getClientID(), "Primary", AccountType.PERSONAL);
        account1.addSum(1000);
        accountService.addAccount(account1);

        Client client2 = new Client("Alex", "York", 2002, "London", new HashSet<>(Arrays.asList("alex.york@yahoo.com")), ClientType.PERSONAL);
        Account account2 = new Account(client2.getClientID(), "Primary", AccountType.PERSONAL);
        accountService.addAccount(account2);

        Transaction recentTransaction = new Transaction(account1.getAccountID(), account2.getAccountID(), 100);

        client1.addTransaction(recentTransaction);
        client2.addTransaction(recentTransaction);

        assertFalse(client1.hasRecentTransactions(LocalDateTime.now().minusDays(1), account1.getAccountID()));
    }

    @Test
    void hasRecentTransactions_WithNoTransactions_ReturnsFalse() {
        Client client1 = new Client("John", "Doe", 1990, "New York", new HashSet<>(Arrays.asList("john.doe@gmail.com")), ClientType.PERSONAL);
        Account account1 = new Account(client1.getClientID(), "Primary", AccountType.PERSONAL);
        account1.addSum(1000);
        accountService.addAccount(account1);

        assertFalse(client1.hasRecentTransactions(LocalDateTime.now().minusDays(1), account1.getAccountID()));
    }
}
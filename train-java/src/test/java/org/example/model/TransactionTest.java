package org.example.model;

import org.example.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountService = new AccountService();
    }

    @Test
    void getTransactionID_ShouldBeNotNull() {
        Client client1 = new Client("John", "Doe", 1990, "New York", new HashSet<>(Arrays.asList("john.doe@gmail.com")), ClientType.PERSONAL);
        Account account1 = new Account(client1.getClientID(), "Primary", AccountType.PERSONAL);
        account1.addSum(1000);
        accountService.addAccount(account1);

        Client client2 = new Client("Alex", "York", 2002, "London", new HashSet<>(Arrays.asList("alex.york@yahoo.com")), ClientType.PERSONAL);
        Account account2 = new Account(client2.getClientID(), "Primary", AccountType.PERSONAL);
        accountService.addAccount(account2);

        Transaction recentTransaction = new Transaction(account1.getAccountID(), account2.getAccountID(), 100);

        assertNotNull(recentTransaction.getTransactionID());
    }

    @Test
    void getProviderAccountID_ShouldBeTrue() {
        Client client1 = new Client("John", "Doe", 1990, "New York", new HashSet<>(Arrays.asList("john.doe@gmail.com")), ClientType.PERSONAL);
        Account account1 = new Account(client1.getClientID(), "Primary", AccountType.PERSONAL);
        account1.addSum(1000);
        accountService.addAccount(account1);

        Client client2 = new Client("Alex", "York", 2002, "London", new HashSet<>(Arrays.asList("alex.york@yahoo.com")), ClientType.PERSONAL);
        Account account2 = new Account(client2.getClientID(), "Primary", AccountType.PERSONAL);
        accountService.addAccount(account2);

        Transaction recentTransaction = new Transaction(account1.getAccountID(), account2.getAccountID(), 100);

        assertTrue(recentTransaction.getProviderAccountID() == account1.getAccountID());
    }

    @Test
    void getReceiverAccountID_ShouldBeTrue() {
        Client client1 = new Client("John", "Doe", 1990, "New York", new HashSet<>(Arrays.asList("john.doe@gmail.com")), ClientType.PERSONAL);
        Account account1 = new Account(client1.getClientID(), "Primary", AccountType.PERSONAL);
        account1.addSum(1000);
        accountService.addAccount(account1);

        Client client2 = new Client("Alex", "York", 2002, "London", new HashSet<>(Arrays.asList("alex.york@yahoo.com")), ClientType.PERSONAL);
        Account account2 = new Account(client2.getClientID(), "Primary", AccountType.PERSONAL);
        accountService.addAccount(account2);

        Transaction recentTransaction = new Transaction(account1.getAccountID(), account2.getAccountID(), 100);

        assertTrue(recentTransaction.getReceiverAccountID() == account2.getAccountID());
    }

    @Test
    void getAmount_ShouldBeTrue() {
        Client client1 = new Client("John", "Doe", 1990, "New York", new HashSet<>(Arrays.asList("john.doe@gmail.com")), ClientType.PERSONAL);
        Account account1 = new Account(client1.getClientID(), "Primary", AccountType.PERSONAL);
        account1.addSum(1000);
        accountService.addAccount(account1);

        Client client2 = new Client("Alex", "York", 2002, "London", new HashSet<>(Arrays.asList("alex.york@yahoo.com")), ClientType.PERSONAL);
        Account account2 = new Account(client2.getClientID(), "Primary", AccountType.PERSONAL);
        accountService.addAccount(account2);

        Transaction recentTransaction = new Transaction(account1.getAccountID(), account2.getAccountID(), 100);

        assertTrue(recentTransaction.getAmount() == 100);
    }
}
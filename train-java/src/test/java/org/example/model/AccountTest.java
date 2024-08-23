package org.example.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    @Test
    void getAccountID() {
        Account account = new Account(UUID.randomUUID(), "alias", AccountType.PERSONAL);
        assertNotNull(account.getAccountID());
    }

    @Test
    void setAlias() {
        Account account = new Account(UUID.randomUUID(), "alias", AccountType.PERSONAL);
        account.setAlias("newAlias");
        assertEquals("newAlias", account.getAlias());
    }

    @Test
    void getAlias() {
        Account account = new Account(UUID.randomUUID(), "alias", AccountType.PERSONAL);
        assertEquals("alias", account.getAlias());
    }

    @Test
    void addSum_WithPositiveSum_IncreasesAccountSum() {
        Account account = new Account(UUID.randomUUID(), "alias", AccountType.PERSONAL);
        account.setAccountSum(100);
        account.addSum(50.5);
        assertEquals(150.5, account.getAccountSum());
    }

    @Test
    void addSum_WithZeroSum_KeepsAccountSumUnchanged() {
        Account account = new Account(UUID.randomUUID(), "alias", AccountType.PERSONAL);
        account.setAccountSum(100);
        account.addSum(0);
        assertEquals(100, account.getAccountSum());
    }

    @Test
    void decreaseSum_WithPositiveSum_DecreasesAccountSum() {
        Account account = new Account(UUID.randomUUID(), "alias", AccountType.PERSONAL);
        account.setAccountSum(100);
        account.decreaseSum(50.5);
        assertEquals(49.5, account.getAccountSum());
    }

    @Test
    void decreaseSum_WithZeroSum_KeepsAccountSumUnchanged() {
        Account account = new Account(UUID.randomUUID(), "alias", AccountType.PERSONAL);
        account.setAccountSum(100);
        account.decreaseSum(0);
        assertEquals(100, account.getAccountSum());
    }

    @Test
    void setAccountSum() {
        Account account = new Account(UUID.randomUUID(), "alias", AccountType.PERSONAL);
        account.setAccountSum(100);
        assertEquals(100, account.getAccountSum());
    }

    @Test
    void getAccountSum() {
        Account account = new Account(UUID.randomUUID(), "alias", AccountType.PERSONAL);
        assertEquals(0, account.getAccountSum());
    }

    @Test
    void getClientID() {
        UUID clientID = UUID.randomUUID();
        Account account = new Account(clientID, "alias", AccountType.PERSONAL);
        assertEquals(clientID, account.getClientID());
    }

    @Test
    void setAccountActive_ShouldBeFalse() {
        Account account = new Account(UUID.randomUUID(), "alias", AccountType.PERSONAL);
        account.setAccountActive(false);
        assertFalse(account.getAccountActive());
    }

    @Test
    void setAccountActive_ShouldBeTrue() {
        Account account = new Account(UUID.randomUUID(), "alias", AccountType.PERSONAL);
        account.setAccountActive(true);
        assertTrue(account.getAccountActive());
    }

    @Test
    void setAccountDeactivated() {
        Account account = new Account(UUID.randomUUID(), "alias", AccountType.PERSONAL);
        account.setAccountDeactivated();
        assertFalse(account.getAccountActive());
    }

    @Test
    void getAccountActive() {
        Account account = new Account(UUID.randomUUID(), "alias", AccountType.PERSONAL);
        assertTrue(account.getAccountActive());
    }

    @Test
    void getAccountNumber() {
        Account account = new Account(UUID.randomUUID(), "alias", AccountType.PERSONAL);
        assertNotNull(account.getAccountNumber());
    }
}
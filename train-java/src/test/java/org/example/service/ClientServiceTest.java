package org.example.service;

import org.example.model.Client;
import org.example.model.ClientType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ClientServiceTest {
    private ClientService clientService;
    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        clientService = new ClientService();
        accountService = new AccountService();
    }

    @Test
    public void deleteClient_returnsFalseWhenClientDoesNotExist() {
        UUID nonExistentClientId = UUID.randomUUID();
        assertFalse(clientService.deleteClient(nonExistentClientId));
    }

    @Test
    public void updateClient_updatesEmailsSuccessfully() {
        Client client = new Client("John", "Doe", 1990, "New York", new HashSet<>(Arrays.asList("john.doe@gmail.com")), ClientType.PERSONAL);
        UUID clientId = clientService.addClient(client);
        HashSet<String> newEmails = new HashSet<>();
        newEmails.add("test@example.com");
        assertTrue(clientService.updateClient(clientId, newEmails));
        assertEquals(newEmails, client.getEmails());
    }

    @Test
    public void updateClient_returnsFalseWhenClientDoesNotExist() {
        UUID nonExistentClientId = UUID.randomUUID();
        HashSet<String> newEmails = new HashSet<>();
        newEmails.add("test@example.com");
        assertFalse(clientService.updateClient(nonExistentClientId, newEmails));
    }

    @Test
    public void existsEmail_returnsTrueWhenEmailExists() {
        Client client = new Client("John", "Doe", 1990, "New York", new HashSet<>(Arrays.asList("john.doe@gmail.com")), ClientType.PERSONAL);
        HashSet<String> emails = new HashSet<>();
        emails.add("test@example.com");
        client.setEmails(emails);
        clientService.addClient(client);
        assertTrue(clientService.existsEmail("test@example.com"));
    }

    @Test
    public void existsEmail_returnsFalseWhenEmailDoesNotExist() {
        assertFalse(clientService.existsEmail("test@example.com"));
    }

    @Test
    public void summarizeClients_returnsCorrectSummary() {
        Client businessClient = new Client("John", "Doe", 1990, "New York", new HashSet<>(Arrays.asList("john.doe@gmail.com")), ClientType.BUSINESS);
        clientService.addClient(businessClient);

        Client personalClient = new Client("Max", "York", 1990, "London", new HashSet<>(Arrays.asList("john.doe@gmail.com")), ClientType.PERSONAL);
        clientService.addClient(personalClient);

        ClientSummary summary = clientService.summarizeClients();
        assertEquals(1, summary.getBusinessClients().size());
        assertEquals(1, summary.getRegularClients().size());
    }

}
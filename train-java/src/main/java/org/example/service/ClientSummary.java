package org.example.service;

import org.example.model.Client;

import java.util.List;

public class ClientSummary {
    private List<Client> businessClients;
    private List<Client> regularClients;

    public ClientSummary(List<Client> businessClients, List<Client> regularClients) {
        this.businessClients = businessClients;
        this.regularClients = regularClients;
    }

    public List<Client> getBusinessClients() {
        return businessClients;
    }

    public List<Client> getRegularClients() {
        return regularClients;
    }
}

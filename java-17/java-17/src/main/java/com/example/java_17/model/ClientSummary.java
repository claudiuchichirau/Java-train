package com.example.java_17.model;


import java.util.List;

public class ClientSummary extends AbstractEntity{
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

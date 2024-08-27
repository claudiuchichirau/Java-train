package com.example.java_17.services;

import com.example.java_17.model.Client;
import com.example.java_17.model.ClientSummary;
import com.example.java_17.model.ClientType;
import com.example.java_17.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientSummaryService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientSummaryService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientSummary getClientSummary() {
        List<Client> businessClients = clientRepository.findByClientType(ClientType.BUSINESS);
        List<Client> regularClients = clientRepository.findByClientType(ClientType.PERSONAL);

        return new ClientSummary(businessClients, regularClients);
    }
}

package com.example.java_17.services;

import com.example.java_17.model.*;
import com.example.java_17.repositories.ClientRepository;
import com.example.java_17.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final AccountService accountService;
    private final TransactionRepository transactionRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository, AccountService accountService, TransactionRepository transactionRepository) {
        this.clientRepository = clientRepository;
        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
    }

    public UUID addClient(Client client) {
        return clientRepository.save(client).getClientID();
    }

    public boolean deleteClient(UUID clientId) {
        if (clientRepository.existsById(clientId)) {
            clientRepository.deleteById(clientId);
            return true;
        }
        return false;
    }

    public boolean updateClient(UUID clientId, Set<String> newEmails) {
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client != null) {
            client.setEmails(newEmails);
            clientRepository.save(client);
            return true;
        }
        return false;
    }

    public ClientSummary summarizeClients() {
        List<Client> businessClients = clientRepository.findByClientType(ClientType.BUSINESS);
        List<Client> regularClients = clientRepository.findByClientType(ClientType.PERSONAL);
        return new ClientSummary(businessClients, regularClients);
    }

    public List<SupplierRanking> getSupplierRankings() {
        return clientRepository.findByClientType(ClientType.BUSINESS).stream()
                .flatMap(client -> client.getClientTransactions().stream())
                .collect(Collectors.groupingBy(
                        Transaction::getReceiverAccountID,
                        Collectors.counting()))
                .entrySet().stream()
                .map(entry -> new SupplierRanking(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(SupplierRanking::transactionCount).reversed())
                .collect(Collectors.toList());
    }

    public void analyzeAndDeactivateAccounts() {
        LocalDateTime threeMonthsAgo = getThreeMonthsAgoDate();

        clientRepository.findAll().stream()
                .filter(client -> client.getClientType() == ClientType.BUSINESS || client.getClientType() == ClientType.PERSONAL)
                .forEach(client -> {
                    boolean hasRecentTransactions = accountService.getAccountIDs(client.getClientID()).stream()
                            .map(accountService::getAccount)
                            .anyMatch(account -> client.hasRecentTransactions(threeMonthsAgo, account.getAccountID()));

                    if (!hasRecentTransactions) {
                        accountService.getAccountIDs(client.getClientID()).stream()
                                .map(accountService::getAccount)
                                .filter(account -> !client.hasRecentTransactions(threeMonthsAgo, account.getAccountID()))
                                .forEach(account -> {
                                    account.setAccountActive(false);
                                    account.deactivateCard();
                                    System.out.println("Contul " + account.getAccountID() + " al clientului " + client.getClientID() + " dezactivat pentru lipsă de tranzacții recente.");
                                });
                    }
                });
    }

    private LocalDateTime getThreeMonthsAgoDate() {
        return LocalDateTime.now().minusMonths(3);
    }
}

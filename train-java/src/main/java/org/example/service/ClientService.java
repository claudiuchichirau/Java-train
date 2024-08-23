package org.example.service;

import org.example.model.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ClientService {
    private Map<UUID, Client> clients = new HashMap<>();
    private AccountService accountService;

    public UUID addClient(Client client){
        clients.put(client.getClientID(), client);
        return  client.getClientID();
    }

    public boolean deleteClient(UUID clientID){
        if(clients.containsKey(clientID)){
            clients.remove(clientID);
            return true;
        }
        return false;
    }

    public boolean updateClient(UUID clientID, Set<String> newEmails){
        Client client = clients.get(clientID);
        if(client != null){
            client.setEmails(newEmails);
            return true;
        }

        return false;
    }

    public boolean existsEmail(String email){
        for (Client client : clients.values()) {
            if (client.getEmails().contains(email)) {
                return true;
            }
        }
        return false;
    }

    public ClientSummary summarizeClients() {
        List<Client> businessClients = new ArrayList<>();
        List<Client> nonBusinessClients = new ArrayList<>();

        for (Client client : clients.values()) {
            if (client.getClientType() == ClientType.BUSINESS) {
                businessClients.add(client);
            } else {
                nonBusinessClients.add(client);
            }
        }

        return new ClientSummary(businessClients, nonBusinessClients);
    }

    public List<SupplierRanking> getSupplierRankings() {
        return clients.values().stream()
                .filter(client -> client.getClientType() == ClientType.BUSINESS)
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

        clients.values().stream()
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

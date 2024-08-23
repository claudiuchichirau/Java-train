package org.example.model;

import org.example.AbstractEntity;
import org.example.service.ClientService;

import java.time.LocalDateTime;
import java.util.*;

public class Client extends AbstractEntity {
    private UUID clientID;
    private String firstName;
    private String lastName;
    private int birthYear;
    private String birthCity;
    private Set<String> emails;
    private ClientType clientType;
    private List<Transaction> clientTransactions;
    private ClientService clientService = new ClientService();

    public Client(String firstName, String lastName, int birthYear, String birthCity, Set<String> emails, ClientType clientType)
    {
        super();
        if (emails == null || emails.isEmpty()) {
            throw new IllegalArgumentException("Lista de emailuri nu poate fi nulla sau goala.");
        }
        for (String email : emails) {
            if(!validateEmail(email)) {
                throw new IllegalArgumentException("Emailul '" + email + "' nu este valid!");
            }
            if(clientService.existsEmail(email)) {
                throw new IllegalArgumentException("Emailul '" + email + "' deja este asociat unui alt client!");
            }
        }

        clientID = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthYear = birthYear;
        this.birthCity = birthCity;
        this.emails = new HashSet<>(emails);
        this.clientType = clientType;
    }

    public UUID getClientID(){
        return this.clientID;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public int getBirthYear(){
        return this.birthYear;
    }

    public String getBirthCity(){
        return this.birthCity;
    }

    public ClientType getClientType(){
        return this.clientType;
    }

    public void addEmail(String newEmail){
        if(!validateEmail(newEmail))
            throw new IllegalArgumentException("Email-ul nu este valid! Trebuie sa fie de tipul prenume.nume@gmail.com sau prenume.nume@yahoo.com.");
        if(clientService.existsEmail(newEmail)) {
            throw new IllegalArgumentException("Emailul '" + newEmail + "' deja este asociat unui alt client!");
        }
        this.emails.add(newEmail);
    }

    public void setEmails(Set<String> newEmails){
        if (emails == null || emails.isEmpty()) {
            throw new IllegalArgumentException("Lista de emailuri nu poate fi nulla sau goala.");
        }
        for (String email : emails) {
            if (!validateEmail(email)) {
                throw new IllegalArgumentException("Emailul '" + email + "' nu este valid!");
            }
            if(clientService.existsEmail(email)) {
                throw new IllegalArgumentException("Emailul '" + email + "' deja este asociat unui alt client!");
            }
        }
        this.emails = new HashSet<>(emails);
    }

    public Set<String> getEmails(){
        return this.emails;
    }

    public static boolean validateEmail(String email) {
        if (email.endsWith("@gmail.com") || email.endsWith("@yahoo.com")) {
            String[] parts = email.split("@");
            if (parts.length == 2) {    // daca exista "prenume.nume" si "@yahoo.com"
                String[] nameParts = parts[0].split("\\.");  //separ numele
                if (nameParts.length == 2 && !nameParts[0].isEmpty() && !nameParts[1].isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addTransaction(Transaction transaction){
        this.clientTransactions.add(transaction);
    }

    public List<Transaction> getClientTransactions(){
        return this.clientTransactions;
    }

    public boolean hasRecentTransactions(LocalDateTime thresholdDate, UUID accountID) {
        return clientTransactions.stream()
                .anyMatch(transaction -> transaction.getProviderAccountID().equals(accountID) &&
                            transaction.getTransactionDateTime().isAfter(thresholdDate));
    }
}

package com.example.java_17.model;

import com.example.java_17.repositories.ClientRepository;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "clients")
public class Client extends AbstractEntity{
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private UUID clientId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birth_year", nullable = false)
    private int birthYear;

    @Column(name = "birth_city", nullable = false)
    private String birthCity;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "client_emails", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "emails")
    private Set<String> emails;

    @Enumerated(EnumType.STRING)
    @Column(name = "client_type", nullable = false)
    private ClientType clientType;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> clientTransactions;

    @Autowired
    private EmailValidator emailValidator;

    public Client() {
    }

    public Client(String firstName, String lastName, int birthYear, String birthCity, Set<String> emails, ClientType clientType)
    {
        super();
        if (emails == null || emails.isEmpty()) {
            throw new IllegalArgumentException("Lista de emailuri nu poate fi nulla sau goala.");
        }
        for (String email : emails) {
            emailValidator.validate(email);
        }

        clientId = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthYear = birthYear;
        this.birthCity = birthCity;
        this.emails = new HashSet<>(emails);
        this.clientType = clientType;
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

    public UUID getClientID(){
        return this.clientId;
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
        if(!emailValidator.validateEmail(newEmail))
            throw new IllegalArgumentException("Email-ul nu este valid! Trebuie sa fie de tipul prenume.nume@gmail.com sau prenume.nume@yahoo.com.");
        if(emailValidator.exists(newEmail)) {
            throw new IllegalArgumentException("Emailul '" + newEmail + "' deja este asociat unui alt client!");
        }
        this.emails.add(newEmail);
    }

    public void setEmails(Set<String> newEmails){
        if (emails == null || emails.isEmpty()) {
            throw new IllegalArgumentException("Lista de emailuri nu poate fi nulla sau goala.");
        }
        for (String email : emails) {
            emailValidator.validate(email);
        }
        this.emails = new HashSet<>(emails);
    }

    public Set<String> getEmails(){
        return this.emails;
    }
}

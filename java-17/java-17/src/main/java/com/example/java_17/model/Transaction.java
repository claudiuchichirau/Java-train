package com.example.java_17.model;

import com.example.java_17.repositories.AccountRepository;
import com.example.java_17.services.ClientService;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transaction extends AbstractEntity {
    @Id
    @GeneratedValue
    @Column(name = "transaction_id", updatable = false, nullable = false, unique = true)
    private UUID transactionId;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Column(name = "provider_accound_id", nullable = false)
    private UUID providerAccountId;

    @Column(name = "receiver_account_id", nullable = false)
    private UUID receiverAccountId;

    @Column(name = "transaction_sum", nullable = false)
    private double transactionSum;

    @Autowired
    private final AccountRepository accountRepository;

    public Transaction(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Transaction(UUID providerAccountID, UUID receiverAccountID, double amount, AccountRepository accountRepository){
        super();
        this.transactionId = UUID.randomUUID();
        this.transactionDate = LocalDateTime.now();
        this.providerAccountId = providerAccountID;
        this.receiverAccountId = receiverAccountID;
        this.transactionSum = amount;
        this.accountRepository = accountRepository;

        Account providerAccount = accountRepository.getReferenceById(providerAccountID);
        Account receiverAccount = accountRepository.getReferenceById(receiverAccountID);

        if(providerAccount.getAccountActive() == false || providerAccount == null) {
            throw new IllegalArgumentException("Contul providerului nu este activat");
        }
        if(receiverAccount.getAccountActive() == false || receiverAccount == null) {
            throw new IllegalArgumentException("Contul receiverului nu este activat");
        }
        if(providerAccount.getAccountSum() < amount) {
            throw new IllegalArgumentException("Suma tranzactiei (" + amount + ") este mai mare decat cea din contul providerului");
        }
        providerAccount.setAccountSum(providerAccount.getAccountSum() - amount);
        receiverAccount.setAccountSum(receiverAccount.getAccountSum() + amount);
    }

    public LocalDateTime getTransactionDateTime(){
        return this.transactionDate;
    }

    public UUID getTransactionID(){
        return this.transactionId;
    }

    public UUID getProviderAccountID(){
        return this.providerAccountId;
    }

    public UUID getReceiverAccountID(){
        return this.receiverAccountId;
    }

    public double getAmount(){
        return this.transactionSum;
    }
}

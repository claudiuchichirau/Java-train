package org.example.model;

import org.example.AbstractEntity;
import org.example.service.AccountService;
import org.example.service.ClientService;

import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction extends AbstractEntity {
    private UUID transactionID;
    private LocalDateTime dateTime;
    private UUID providerAccountID;
    private UUID receiverAccountID;
    private double amount;
    private ClientService clientService = new ClientService();
    private AccountService accountService = new AccountService();

    public Transaction(UUID providerAccountID, UUID receiverAccountID, double amount){
        super();
        this.transactionID = UUID.randomUUID();
        this.dateTime = LocalDateTime.now();
        this.providerAccountID = providerAccountID;
        this.receiverAccountID = receiverAccountID;
        this.amount = amount;

        Account providerAccount = accountService.getAccounts().get(providerAccountID);
        Account receiverAccount = accountService.getAccounts().get(receiverAccountID);

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
        return this.dateTime;
    }

    public UUID getTransactionID(){
        return this.transactionID;
    }

    public UUID getProviderAccountID(){
        return this.providerAccountID;
    }

    public UUID getReceiverAccountID(){
        return this.receiverAccountID;
    }

    public double getAmount(){
        return this.amount;
    }
}

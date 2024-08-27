package com.example.java_17.model;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class Account extends AbstractEntity {
    @Id
    @GeneratedValue
    @Column(name = "account_id", updatable = false, nullable = false, unique = true)
    private UUID accountId;

    @Column(name = "account_sum", nullable = false)
    private double accountSum;

    @Column(name = "alias")
    private String alias;

    @Column(name = "client_id", nullable = false)
    private UUID clientId;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;

    @Column(name = "is_account_active", nullable = false)
    private boolean isAccountActive;

    @Column(name = "account_number", unique = true, nullable = false)
    private long accountNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    private Card card;

    public Account() {
    }

    public Account(UUID clientID, String alias, AccountType accountType)
    {
        super();
        accountId = UUID.randomUUID();
        accountSum = 0;
        this.alias = alias;
        this.clientId = clientID;
        this.accountType = accountType;
        this.isAccountActive = true;

        SecureRandom random = new SecureRandom();

        BigInteger bigInteger = new BigInteger(53, random);
        String randomNumber = String.format("%016d", bigInteger);

        accountNumber = Long.parseLong(randomNumber);
    }

    public UUID getAccountID(){
        return this.accountId;
    }

    public void setAlias(String newAlias){
        this.alias = newAlias;
    }

    public String getAlias(){
        return this.alias;
    }

    public void addSum(double sum){
        this.accountSum += sum;
    }
    public void decreaseSum(double sum){
        this.accountSum -= sum;
    }
    public void setAccountSum(double newSum){
        this.accountSum = newSum;
    }

    public double getAccountSum(){
        return this.accountSum;
    }

    public UUID getClientID(){
        return this.clientId;
    }

    public void setAccountActive(boolean newStatus){
        this.isAccountActive = newStatus;
    }

    public void setAccountDeactivated(){
        this.isAccountActive = false;
    }

    public boolean getAccountActive(){
        return this.isAccountActive;
    }


    public void setCard(Card card) {
        this.card = card;
    }

    public Card getCard(){
        return this.card;
    }

    public void activateCard(){
        this.card.activateCard();
    }

    public void deactivateCard(){
        this.card.deactivateCard();
    }

    public void setAccountType(AccountType newAccountType){
        this.accountType = newAccountType;
    }

    public AccountType getAccountType(){
        return this.accountType;
    }

    public long getAccountNumber(){
        return this.accountNumber;
    }
}

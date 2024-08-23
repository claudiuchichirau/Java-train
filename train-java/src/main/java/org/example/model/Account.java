package org.example.model;

import org.example.AbstractEntity;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.UUID;

public class Account extends AbstractEntity {
    private UUID accountID;
    private double accountSum;
    private String alias;
    private UUID clientID;
    private AccountType accountType;
    private boolean isAccountActive;
    private long accountNumber;
    private Card card;

    public Account(UUID clientID, String alias, AccountType accountType)
    {
        super();
        accountID = UUID.randomUUID();
        accountSum = 0;
        this.alias = alias;
        this.clientID = clientID;
        this.accountType = accountType;
        this.isAccountActive = true;

        SecureRandom random = new SecureRandom();

        BigInteger bigInteger = new BigInteger(53, random);
        String randomNumber = String.format("%016d", bigInteger);

        accountNumber = Long.parseLong(randomNumber);
    }

    public UUID getAccountID(){
        return this.accountID;
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
        return this.clientID;
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

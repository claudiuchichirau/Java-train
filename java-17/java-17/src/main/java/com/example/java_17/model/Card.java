package com.example.java_17.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Random;

@Entity
@Table(name = "cards")
public class Card extends AbstractEntity{
    @Column(name = "card_number", unique = true, nullable = false)
    private long cardNumber;

    @Column(name = "cvv", nullable = false)
    private int cvv;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "pin_code", nullable = false)
    private int pinCode;

    @Column(name = "is_activated", nullable = false)
    private boolean isActivated;

    public Card() {
    }

    public Card(long accountNumber, String name) {
        super();
        this.cardNumber = accountNumber;
        this.name = name;

        Random random = new Random();
        this.cvv = 100 + random.nextInt(900);
        this.pinCode = 1000 + random.nextInt(9000);
        this.isActivated = false;
    }

    public void activateCard(){
        this.isActivated = true;
    }

    public void deactivateCard(){
        this.isActivated = false;
    }

    public boolean getActivationStatus(){
        return this.isActivated;
    }
}

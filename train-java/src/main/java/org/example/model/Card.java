package org.example.model;

import org.example.AbstractEntity;

import java.util.Random;
import java.util.UUID;

public class Card extends AbstractEntity {
    private long cardNumber;
    private int cvv;
    private String name;
    private int pinCode;
    private boolean isActivated;

    public Card(long accountNumber, String name){
        super();
        this.cardNumber = accountNumber;
        this.name = name;

        Random random = new Random();
        int threeDigitNumber = 100 + random.nextInt(900);
        this.cvv = threeDigitNumber;

        random = new Random();
        int fourDigitNumber = 1000 + random.nextInt(9000);
        this.pinCode = fourDigitNumber;
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

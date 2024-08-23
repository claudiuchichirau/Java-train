package org.example.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    @Test
    void activateCard_ShouldBeTrue() {
        Card card = new Card(123456789, "John Doe");
        card.activateCard();
        assertTrue(card.getActivationStatus());
    }

    @Test
    void deactivateCard_ShouldBeFalse() {
        Card card = new Card(123456789, "John Doe");
        assertFalse(card.getActivationStatus());
    }

    @Test
    void deactivateCard_ShouldBeFalseAfterActivation() {
        Card card = new Card(123456789, "John Doe");
        card.activateCard();
        card.deactivateCard();
        assertFalse(card.getActivationStatus());
    }

    @Test
    void deactivateCard_ShouldBeTrueAfterDeactivation() {
        Card card = new Card(123456789, "John Doe");
        card.activateCard();
        card.deactivateCard();
        card.activateCard();
        assertTrue(card.getActivationStatus());
    }

    @Test
    void deactivateCard_ShouldBeFalseAfterDoubleDeactivation() {
        Card card = new Card(123456789, "John Doe");
        card.activateCard();
        card.deactivateCard();
        card.deactivateCard();
        assertFalse(card.getActivationStatus());
    }

    @Test
    void deactivateCard_ShouldBeTrueAfterDoubleActivation() {
        Card card = new Card(123456789, "John Doe");
        card.activateCard();
        card.deactivateCard();
        card.activateCard();
        card.activateCard();
        assertTrue(card.getActivationStatus());
    }

}
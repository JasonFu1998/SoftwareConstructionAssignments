package com.players;

import com.cards.Card;
import com.cards.Deck;
import com.hand.Hand;

public class Player {
    private int balance;
    private int addedBalance;
    private final Hand hand;

    public Player(int balance){
        this.balance = balance;
        this.hand = new Hand();
        this.addedBalance = 0;
    }

    public void dealCardToPlayer(Deck deck) {
        hand.addCardToHand(deck.nextCard());
    }

    public void addOneCardToHand(Card card) {
        hand.addCardToHand(card);
    }

    public void showPlayerCards() {
        hand.printAllPlayerCards();
    }

    public void clearPlayerHand() {
        hand.clearHand();
    }

    public boolean hasEnoughMoney(int bet){
        return bet <= balance;
    }

    public void depositMoney(int bet){
        balance = balance + bet;
    }

    public void depositOwnMoney(int bet) {
        balance = balance + bet;
        addedBalance = addedBalance + bet;
    }

    public void removeMoney(int bet) {
        balance = balance - bet;
    }

    public int getBalance(){
        return balance;
    }

    public int getAddedBalance() { return addedBalance;}

    public int totalCardValue() {
        return hand.calculateTotalHandValue();
    }
}

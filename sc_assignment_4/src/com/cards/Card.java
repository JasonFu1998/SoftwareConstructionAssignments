package com.cards;

public class Card {
    private final Rank rank;
    private final Suit suit;
    private int value;

    public Card(Rank r, Suit s) {
        this.rank = r;
        this.suit = s;
        this.value = Math.min(rank.ordinal() + 1, 10);
    }

    public Rank getRank() {
        return this.rank;
    }

    public Suit getSuit() {
        return this.suit;
    }

    public int getCardValue() {
        return this.value;
    }

    public void setCardValue(int value) {
        this.value = value;
    }
}

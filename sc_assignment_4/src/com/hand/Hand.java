package com.hand;

import com.cards.Card;
import com.cards.Rank;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    private final List<Card> cards;

    public Hand() {
        this.cards = new ArrayList<>();
    }

    /**
     * @param card adds the passed {@link Card} to the {@link Hand}
     */
    public void addCardToHand(Card card) {
        cards.add(card);
    }

    /**
     * Removes all the elements from this {@link Hand}. The {@link Hand} will be empty after this call returns.
     */
    public void clearHand() {
        cards.clear();
    }

    /**
     * Calculates the total value of this {@link Hand} and takes the dynamic value of an ace into account.
     *
     * @return the total hand value as an integer
     */
    public int calculateTotalHandValue() {
        int value = 0;
        boolean countOneAceAs11 = false;
        for (Card card : cards) {
            if (card.getRank() == Rank.ACE) {
                countOneAceAs11 = true;
                card.setCardValue(11);
            }
            value += card.getCardValue();
            if (countOneAceAs11 && value > 21) {
                Card ace = cards.stream().filter(c -> c.getCardValue() == 11).findFirst().orElse(null);
                if (ace != null) {
                    ace.setCardValue(1);
                    value -= 10;
                }
            }
        }
        return value;
    }

    /**
     * Prints the first {@link Card}'s {@link com.cards.Suit}, {@link Rank}, and value of this {@link Hand} to the
     * console.
     */
    public void printOneDealerCard() {
        System.out.println("\nDealer's card  | " + cards.get(0).getSuit() + " " + cards.get(0).getRank() + " | is worth:     " + cards.get(0).getCardValue());
    }

    /**
     * Prints all the {@link Card}'s {@link com.cards.Suit}, {@link Rank}, and value of this {@link Hand} to the
     * console.
     */
    public void printAllPlayerCards() {
        System.out.println("Total player value:         " + this.calculateTotalHandValue());
        int i = 0;
        for (Card card : cards) {
            i++;
            System.out.println("Your card " + i + " | " + card.getSuit() + " " + card.getRank() + " |  is worth:     " + card.getCardValue());
        }
    }
}

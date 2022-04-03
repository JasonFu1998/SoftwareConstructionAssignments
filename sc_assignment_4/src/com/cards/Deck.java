package com.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> cardlist;

    public Deck() {
        this.cardlist = new ArrayList<>();
        initialize();
        shuffleDeck();
    }

    /**
     *
     */
    private void initialize() {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cardlist.add(new Card(rank, suit));
            }
        }
    }

    /**
     * Removes the next {@link Card} from the {@link Deck} and returns it.
     *
     * @return the next {@link Card}
     */
    public Card nextCard() {
        Card toBeDealt = cardlist.get(0);
        cardlist.remove(0);
        return toBeDealt;
    }

    /**
     *
     */
    public void shuffleDeck() {
        Collections.shuffle(cardlist);
    }

    // public Card getCard() { return cardlist.remove(0); }
}

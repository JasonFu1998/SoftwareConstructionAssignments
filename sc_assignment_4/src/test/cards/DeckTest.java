package test.cards;

import com.cards.Card;
import com.cards.Deck;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    @Test
    public void testShuffleDeck() {
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();
        assertNotEquals(deck1, deck2);
    }

    @Test
    public void testNextCard() {
        Deck deck = new Deck();
        Card card = deck.nextCard();
        for (int x = 0; x < 51; x++) {
            assertNotEquals(card, deck.nextCard());
        }
    }

}
package test.cards;

import com.cards.Card;
import com.cards.Rank;
import com.cards.Suit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    @Test
    public void testGetCardValueAceOne() {
        Card card = new Card(Rank.ACE, Suit.DIAMONDS);
        int actualValue = card.getCardValue();
        assertEquals(1, actualValue);
    }

    @Test
    public void testGetCardValueKing() {
        Card card = new Card(Rank.KING, Suit.SPADES);
        int actualValue = card.getCardValue();
        assertEquals(10, actualValue);
    }

    @Test
    public void testGetCardValueThree() {
        Card card = new Card(Rank.THREE, Suit.CLUBS);
        int actualValue = card.getCardValue();
        assertEquals(3, actualValue);
    }

    @Test
    public void testGetRank() {
        Card card = new Card(Rank.TWO, Suit.HEARTS);
        Rank expected = Rank.TWO;
        Rank actual = card.getRank();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetSuit() {
        Card card = new Card(Rank.TWO, Suit.HEARTS);
        Suit expected = Suit.HEARTS;
        Suit actual = card.getSuit();
        assertEquals(expected, actual);
    }

    @Test
    public void testSetCardValue() {
        Card card = new Card(Rank.ACE, Suit.HEARTS);
        card.setCardValue(11);
        assertEquals(11, card.getCardValue());
        card.setCardValue(1);
        assertEquals(1, card.getCardValue());
    }

}
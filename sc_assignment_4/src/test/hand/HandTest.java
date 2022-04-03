package test.hand;

import com.cards.Card;
import com.cards.Rank;
import com.cards.Suit;
import com.hand.Hand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {
    Hand hand;

    @BeforeEach
    public void initialize() {
        hand = new Hand();
    }

    @Test
    public void testCalculateTotalHandValueOneCard() {
        hand.addCardToHand(new Card(Rank.FOUR, Suit.HEARTS));
        int expected = 4;
        int actual = hand.calculateTotalHandValue();
        assertEquals(expected, actual);
    }

    @Test
    public void testCalculateTotalHandValueSeveralCard() {
        hand.addCardToHand(new Card(Rank.FOUR, Suit.HEARTS));
        hand.addCardToHand(new Card(Rank.TEN, Suit.SPADES));
        int expected = 14;
        int actual = hand.calculateTotalHandValue();
        assertEquals(expected, actual);
    }

    @Test
    public void testCalculateTotalHandValueACE11() {
        hand.addCardToHand(new Card(Rank.FOUR, Suit.HEARTS));
        hand.addCardToHand(new Card(Rank.ACE, Suit.SPADES));
        int expected = 15;
        int actual = hand.calculateTotalHandValue();
        assertEquals(expected, actual);
    }

    @Test
    public void testCalculateTotalHandValueACE1() {
        hand.addCardToHand(new Card(Rank.FOUR, Suit.HEARTS));
        hand.addCardToHand(new Card(Rank.TEN, Suit.CLUBS));
        hand.addCardToHand(new Card(Rank.ACE, Suit.SPADES));
        int expected = 15;
        int actual = hand.calculateTotalHandValue();
        assertEquals(expected, actual);
    }

    @Test
    public void testCalculateTotalHandValueAfterClearHand() {
        hand.addCardToHand(new Card(Rank.FOUR, Suit.HEARTS));
        hand.addCardToHand(new Card(Rank.TEN, Suit.CLUBS));
        hand.addCardToHand(new Card(Rank.ACE, Suit.SPADES));
        int expected = 0;
        hand.clearHand();
        int actual = hand.calculateTotalHandValue();
        assertEquals(expected, actual);
    }

}
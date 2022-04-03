package test.players;

import com.cards.Card;
import com.cards.Deck;
import com.cards.Rank;
import com.cards.Suit;
import com.io.Input;
import com.players.Dealer;
import com.players.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class DealerTest {
    Player player;
    Input scanner;
    Dealer dealer;

    @BeforeEach
    public void initialize() {
        player = new Player(100);
        dealer = new Dealer(scanner, player);
    }

    @Test
    public void testTotalCardValueNoCard() {
        assertEquals(0, dealer.totalCardValue());
    }

    @Test
    public void testTotalCardValueWithCards() {
        dealer.dealCards(new Deck(), player);
        assertNotEquals(0, dealer.totalCardValue());
    }

    @Test
    public void testRemoveDealerHand() {
        dealer.dealCards(new Deck(), player);
        dealer.removeDealerHand();
        assertEquals(0, dealer.totalCardValue());
    }

    @Test
    public void testAskForBet() {
        System.setIn(new ByteArrayInputStream("test\n0\n50".getBytes(StandardCharsets.UTF_8)));
        dealer = new Dealer(new Input(), player);
        assertEquals(50, dealer.askForBet());
    }

    @Test
    public void testIsTotalPlayerValueGreaterThan21Ture() {
        player.addOneCardToHand(new Card(Rank.SEVEN, Suit.HEARTS));
        player.addOneCardToHand(new Card(Rank.FIVE, Suit.SPADES));
        player.addOneCardToHand(new Card(Rank.KING, Suit.DIAMONDS));
        assertTrue(dealer.isTotalPlayerValueGreaterThan21());
    }

    @Test
    public void testIsTotalPlayerValueGreaterThan21False() {
        player.addOneCardToHand(new Card(Rank.TEN, Suit.HEARTS));
        player.addOneCardToHand(new Card(Rank.TEN, Suit.CLUBS));
        player.addOneCardToHand(new Card(Rank.ACE, Suit.HEARTS));
        assertFalse(dealer.isTotalPlayerValueGreaterThan21());
    }

    @Test
    public void testAskHitOrStayHit() {
        System.setIn(new ByteArrayInputStream("h".getBytes(StandardCharsets.UTF_8)));
        dealer = new Dealer(new Input(), player);
        assertTrue(dealer.askHitOrStay());
    }

    @Test
    public void testAskHitOrStayStay() {
        System.setIn(new ByteArrayInputStream("test\nS".getBytes(StandardCharsets.UTF_8)));
        dealer = new Dealer(new Input(), player);
        assertFalse(dealer.askHitOrStay());
    }

    @Test
    public void testCheckWinnerIfPlayerUnder21True() {
        dealer.addOneCardToHand(new Card(Rank.JACK, Suit.HEARTS));
        player.addOneCardToHand(new Card(Rank.FIVE, Suit.CLUBS));
        dealer.addOneCardToHand(new Card(Rank.EIGHT, Suit.SPADES));
        player.addOneCardToHand(new Card(Rank.FOUR, Suit.HEARTS));
        assertTrue(dealer.checkWinnerIfPlayerUnder21());
    }

    @Test
    public void testCheckWinnerIfPlayerUnder21False() {
        dealer.addOneCardToHand(new Card(Rank.JACK, Suit.DIAMONDS));
        player.addOneCardToHand(new Card(Rank.ACE, Suit.CLUBS));
        dealer.addOneCardToHand(new Card(Rank.TEN, Suit.SPADES));
        player.addOneCardToHand(new Card(Rank.KING, Suit.HEARTS));
        assertFalse(dealer.checkWinnerIfPlayerUnder21());
    }

    @Test
    public void testDealerIsUnder17AndHasToPlayWin() {
        dealer.addOneCardToHand(new Card(Rank.QUEEN, Suit.DIAMONDS));
        player.addOneCardToHand(new Card(Rank.TWO, Suit.CLUBS));
        dealer.addOneCardToHand(new Card(Rank.SEVEN, Suit.SPADES));
        player.addOneCardToHand(new Card(Rank.SIX, Suit.HEARTS));
        assertTrue(dealer.dealerIsUnder17AndHasToPlay());
    }

    @Test
    public void testDealerIsUnder17AndHasToPlayLoss() {
        dealer.addOneCardToHand(new Card(Rank.NINE, Suit.DIAMONDS));
        player.addOneCardToHand(new Card(Rank.QUEEN, Suit.CLUBS));
        dealer.addOneCardToHand(new Card(Rank.EIGHT, Suit.HEARTS));
        player.addOneCardToHand(new Card(Rank.ACE, Suit.DIAMONDS));
        assertFalse(dealer.dealerIsUnder17AndHasToPlay());
    }

    @Test
    public void testDealerTakesOverFalse() {
        dealer.addOneCardToHand(new Card(Rank.THREE, Suit.DIAMONDS));
        player.addOneCardToHand(new Card(Rank.ACE, Suit.SPADES));
        dealer.addOneCardToHand(new Card(Rank.THREE, Suit.HEARTS));
        player.addOneCardToHand(new Card(Rank.KING, Suit.CLUBS));
        assertFalse(dealer.dealerTakesOver(new Deck()));
    }

    @Test
    public void testDealerTakesOverTrue() throws NoSuchFieldException, IllegalAccessException {
        dealer.addOneCardToHand(new Card(Rank.KING, Suit.SPADES));
        player.addOneCardToHand(new Card(Rank.NINE, Suit.HEARTS));
        dealer.addOneCardToHand(new Card(Rank.JACK, Suit.CLUBS));
        player.addOneCardToHand(new Card(Rank.TEN, Suit.DIAMONDS));
        Field field = Dealer.class.getDeclaredField("scanner");
        field.setAccessible(true);
        System.setIn(new ByteArrayInputStream("S".getBytes(StandardCharsets.UTF_8)));
        field.set(dealer, new Input());
        assertTrue(dealer.dealerTakesOver(new Deck()));
    }

    @Test
    public void testRoundPlayerWinReset() {
        dealer.dealCards(new Deck(), player);
        dealer.roundPlayerWinReset();
        assertEquals(0, player.totalCardValue() + dealer.totalCardValue());
    }

    @Test
    public void testRoundPlayerLossReset() {
        dealer.dealCards(new Deck(), player);
        dealer.roundPlayerLossReset();
        assertEquals(0, player.totalCardValue() + dealer.totalCardValue());
    }

    @Test
    public void testRoundPlayerDrawReset() {
        dealer.dealCards(new Deck(), player);
        dealer.roundDrawReset();
        assertEquals(0, player.totalCardValue() + dealer.totalCardValue());
    }

}
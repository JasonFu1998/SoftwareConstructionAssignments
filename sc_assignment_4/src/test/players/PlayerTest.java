package test.players;

import com.cards.Deck;
import com.players.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {
    Player player;

    @BeforeEach
    public void initialize() {
        player = new Player(100);
    }

    @Test
    public void testHasEnoughMoneyTrue() {
        boolean actual = player.hasEnoughMoney(50);
        Assertions.assertTrue(actual);
    }

    @Test
    public void testHasEnoughMoneyFalse() {
        boolean actual = player.hasEnoughMoney(150);
        Assertions.assertFalse(actual);
    }

    @Test
    public void testGetBalance() {
        Assertions.assertEquals(100, player.getBalance());
    }

    @Test
    public void testDepositMoney() {
        player.depositMoney(150);
        Assertions.assertEquals(250, player.getBalance());
    }

    @Test
    public void testDepositOwnMoney() {
        player.depositOwnMoney(150);
        Assertions.assertEquals(250, player.getBalance());
        Assertions.assertEquals(150, player.getAddedBalance());
    }

    @Test
    public void testRemoveMoney() {
        player.removeMoney(50);
        Assertions.assertEquals(50, player.getBalance());
    }

    @Test
    public void testTotalCardValueNoCards() {
        Assertions.assertEquals(0, player.totalCardValue());
    }

    @Test
    public void testTotalCardValueWithCard() {
        Deck deck = new Deck();
        player.dealCardToPlayer(deck);
        Assertions.assertNotEquals(0, player.totalCardValue());
    }

    @Test
    public void testClearPlayerHand() {
        Deck deck = new Deck();
        player.dealCardToPlayer(deck);
        player.clearPlayerHand();
        Assertions.assertEquals(0, player.totalCardValue());
    }

}
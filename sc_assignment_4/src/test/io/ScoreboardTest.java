package test.io;

import com.gameplay.Round;
import com.io.Scoreboard;
import com.players.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreboardTest {
    Scoreboard scoreboard;
    Round round;
    Player player;

    @BeforeEach
    public void initialize() {
        round = new Round();
        scoreboard = new Scoreboard(round);
        player = new Player(100);
    }

    @Test
    public void testGain() {
        player.depositMoney(10);
        round.playerWonRound();
        assertEquals("\nWow! You played 1 round(s), won 1 and lost 0! That means your margin is positive: 10 CHF!", scoreboard.stringForFinalRoundsAndBalance(player));
    }

    @Test
    public void testLoss() {
        player.removeMoney(10);
        round.playerLostRound();
        assertEquals("\nWow! You played 1 round(s), won 0 and lost 1! That means your margin is negative: -10 CHF!", scoreboard.stringForFinalRoundsAndBalance(player));
    }

    @Test
    public void testTie() {
        assertEquals("\nYou started with 100CHF and stopped playing with the same amount! Hope you will play again soon!", scoreboard.stringForFinalRoundsAndBalance(player));
    }

}
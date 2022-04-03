package test.gameplay;

import com.gameplay.Round;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class RoundTest {
    Round round;

    @BeforeEach
    public void initialize() {
        round = new Round();
    }

    @Test
    public void getPlayerWonRoundWithNoRounds() {
        Assertions.assertEquals(0, round.getPlayerRoundsWon());
    }

    @Test
    public void getPlayerWonRoundWithLostRound() {
        round.playerLostRound();
        Assertions.assertEquals(0, round.getPlayerRoundsWon());
    }

    @Test
    public void getPlayerWonRoundWithTieRound() {
        round.drawRound();
        Assertions.assertEquals(0, round.getPlayerRoundsWon());
    }

    @Test
    public void getPlayerWonRoundWithWonRound() {
        round.playerWonRound();
        Assertions.assertEquals(1, round.getPlayerRoundsWon());
    }

    @Test
    public void getPlayerLostRoundWithNoRounds() {
        Assertions.assertEquals(0, round.getPlayerRoundsLost());
    }

    @Test
    public void getPlayerLostRoundWithLostRound() {
        round.playerLostRound();
        Assertions.assertEquals(1, round.getPlayerRoundsLost());
    }

    @Test
    public void getPlayerLostRoundWithTieRound() {
        round.drawRound();
        Assertions.assertEquals(0, round.getPlayerRoundsLost());
    }

    @Test
    public void getPlayerLostRoundWithWonRound() {
        round.playerWonRound();
        Assertions.assertEquals(0, round.getPlayerRoundsLost());
    }

    @Test
    public void getPlayerRoundWithNoRounds() {
        Assertions.assertEquals(0, round.getRoundsPlayed());
    }

    @Test
    public void getPlayerRoundWithLostRound() {
        round.playerLostRound();
        Assertions.assertEquals(1, round.getRoundsPlayed());
    }

    @Test
    public void getPlayerRoundWithTieRound() {
        round.drawRound();
        Assertions.assertEquals(1, round.getRoundsPlayed());
    }

    @Test
    public void getPlayerRoundWithWonRound() {
        round.playerWonRound();
        Assertions.assertEquals(1, round.getRoundsPlayed());
    }

}
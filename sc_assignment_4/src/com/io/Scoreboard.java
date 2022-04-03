package com.io;

import com.gameplay.Round;
import com.observer.IObserver;
import com.players.Player;

public class Scoreboard implements IObserver {
    private final Round round;

    public Scoreboard(Round round) {
        this.round = round;
    }

    /**
     * @param player Player
     * @return the game summarizing string of BlackJack for the passed player
     */
    public String stringForFinalRoundsAndBalance(Player player) {
        int gainOrLoss = player.getBalance() - 100 - player.getAddedBalance();
        if (gainOrLoss > 0) {
            return "\nWow! You played " + round.getRoundsPlayed() + " round(s), won " + round.getPlayerRoundsWon() + " and lost " + round.getPlayerRoundsLost() + "! That means your margin is positive: " + gainOrLoss + " CHF!";
        }
        if (gainOrLoss < 0) {
            return "\nWow! You played " + round.getRoundsPlayed() + " round(s), won " + round.getPlayerRoundsWon() + " and lost " + round.getPlayerRoundsLost() + "! That means your margin is negative: " + gainOrLoss + " CHF!";
        }
        return "\nYou started with 100CHF and stopped playing with the same amount! Hope you will play again soon!";
    }

    @Override
    public void update(Player player) {
        System.out.println(stringForFinalRoundsAndBalance(player));
    }
}

package com.gameplay;

public class Round {
    private int playerWin = 0;
    private int playerLost = 0;
    private int roundsPlayed = 0;

    public Round() {
    }

    /**
     *
     */
    public void playerWonRound() {
        playerWin ++;
        roundsPlayed ++;
    }

    /**
     *
     * @return the amount of rounds the player has won
     */
    public int getPlayerRoundsWon() {
        return playerWin;
    }

    /**
     *
     */
    public void playerLostRound() {
        playerLost ++;
        roundsPlayed ++;
    }

    /**
     *
     * @return the amount of rounds the player has lost
     */
    public int getPlayerRoundsLost() {
        return playerLost;
    }

    /**
     *
     */
    public void drawRound() {
        roundsPlayed ++;
    }

    /**
     *
     * @return the amount of rounds the player has played
     */
    public int getRoundsPlayed() {
        return roundsPlayed;
    }
}

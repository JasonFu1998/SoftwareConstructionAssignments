package com.gameplay;

import com.cards.Deck;
import com.io.Input;
import com.observer.IObservable;
import com.observer.IObserver;
import com.players.Dealer;
import com.players.Player;
import exception.ReviseOrStopException;
import exception.YesOrNoException;

import java.util.*;

public class Game implements IObservable {

    private final List<IObserver> observers = new ArrayList<>();
    private final Player player;
    private final Dealer dealer;
    private Deck deck;
    private final Input scanner;
    private boolean isOver;
    private final Round round;
    public static Game INSTANCE;

    /**
     * @param round Round
     */
    private Game(Round round) {
        this.round = round;
        this.scanner = new Input();
        this.isOver = false;
        player = new Player(100);
        dealer = new Dealer(scanner, player);
        System.out.println("\nHi and welcome to BlackJack! Your starting amount will be 100 CHF!\n"); //this will be expanded on later
    }

    /**
     * Returns a Singleton Instance of the Game.
     *
     * @param round an instance of Round
     * @return Game
     */
    public static Game getInstance(Round round) {
        if (INSTANCE == null) {
            INSTANCE = new Game(round);
        }
        return INSTANCE;
    }

    /**
     * <h3>Start Method</h3>
     * While {@link #playAgain()} returns true, it calls {@link #initializeNewRound()}.<br> If the Game is not over, it
     * calls {@link Dealer#dealerTakesOver(Deck)} that returns whether the player has lost or not.<br>
     * Whether the player has lost, won, or it's a draw, {@link Dealer} resets the player and {@link Round} keeps track
     * of the rounds accordingly.<br>
     * When the game is finished, it {@link #notifyObservers()}.
     */
    public void start() {
        while (playAgain()) {
            initializeNewRound();
            if (this.isOver) {
                break;
            }
            boolean playerLost = dealer.dealerTakesOver(deck);
            if (playerLost) {
                System.out.println("\nYou lost!\nYour value: " + player.totalCardValue() + "\nDealer value: " + dealer.totalCardValue() + "\n");
                dealer.roundPlayerLossReset();
                round.playerLostRound();
            } else {
                if (player.totalCardValue() == dealer.totalCardValue()) {
                    System.out.println("\nDraw!\nYour value: " + player.totalCardValue() + "\nDealer value: " + dealer.totalCardValue() + "\n");
                    dealer.roundDrawReset();
                    round.drawRound();
                } else {
                    System.out.println("\nYou won!\nYour value: " + player.totalCardValue() + "\nDealer value: " + dealer.totalCardValue() + "\n");
                    dealer.roundPlayerWinReset();
                    round.playerWonRound();
                }
            }
        }
        notifyObservers();
    }

    /**
     * @return the status whether the {@link Player} wants to play again or not as a boolean value
     */
    public boolean playAgain() {
        if (player.getBalance() == 0) {
            System.out.println("Your Balance is 0! Reminder: If you write Y and want to keep playing you need to add more money!");
        }
        boolean playerWantsToPlay;
        try {
            playerWantsToPlay = Objects.equals(scanner.askPlayerToPlayAgain(), "Y");
        } catch (YesOrNoException e) {
            System.out.println("The input has to be 'Y' or 'N' !!!");
            playerWantsToPlay = playAgain();
        }
        return playerWantsToPlay;
    }

    /**
     *
     */
    public void initializeNewRound() {
        if (player.getBalance() == 0) {
            int newDeposit = scanner.askDepositAmount();
            player.depositOwnMoney(newDeposit);
        }
        deck = new Deck();
        dealer.dealCards(deck, player);
        int checkIfBetPossible = dealer.askForBet();
        while (!player.hasEnoughMoney(checkIfBetPossible)) {
            System.out.println("You don't have enough money to make this bet!");
            int betAmount = scanner.askForNewDeposit();
            if (betAmount == 0) {
                if (askToPlaceANewBetOrStop()) {
                    checkIfBetPossible = dealer.askForBet();
                } else {
                    break;
                }
            } else {
                player.depositOwnMoney(betAmount);
                checkIfBetPossible = dealer.askForBet();
            }
        }
    }


    /**
     * @return the status whether the {@link Player} wants to place a new bet or not as a boolean value
     */
    public boolean askToPlaceANewBetOrStop() {
        boolean continuePlaying;
        try {
            continuePlaying = scanner.askForDifferentBetAmount();
            if (continuePlaying) {
                return true;
            } else {
                endGame();
                return false;
            }
        } catch (ReviseOrStopException e) {
            System.out.println("The input has to be 'R' or 'S'!");
            return askToPlaceANewBetOrStop();
        }
    }

    private void endGame() {
        this.isOver = true;
    }

    @Override
    public void addObserver(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (IObserver observer : observers) {
            observer.update(player);
        }
    }
}

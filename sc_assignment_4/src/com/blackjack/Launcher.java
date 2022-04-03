package com.blackjack;

import com.gameplay.Game;
import com.gameplay.Round;
import com.io.Scoreboard;
import com.observer.IObserver;

public class Launcher {

    public static void main(String[] args) {
        new Launcher().lauch();
    }

    /**
     * <h3>Launch Method</h3>
     * Initializes Round, Game, Scoreboard, adds the Scoreboard as an Observer to the Game, and starts the Game
     */
    private void lauch() {
        Round round = new Round();
        Game game = Game.getInstance(round);
        IObserver Scoreboard = new Scoreboard(round);
        game.addObserver(Scoreboard);
        game.start();
    }
}
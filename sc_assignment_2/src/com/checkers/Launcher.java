package com.checkers;

import com.board.Board;
import com.gameplay.Game;
import com.io.Input;
import com.io.Printer;
import com.observer.IObserver;
import com.validation.Validator;

public class Launcher {
    private final int ROWS = 10;
    private final int COLUMNS = 10;

    public Launcher() {
    }

    private void launch() {
        Board board = new Board(ROWS, COLUMNS);
        IObserver printer = new Printer(board);
        Input parser = new Input();
        Validator validator = new Validator();
        Game game = new Game(board, parser, validator);
        game.addObserver(printer);
        game.notifyObservers();
        game.start();
    }

    public static void main(String[] args) {
        new Launcher().launch();
    }
}

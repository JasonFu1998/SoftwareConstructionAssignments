package com.checkers;

import com.board.Board;
import com.gameplay.Game;
import com.io.Input;
import com.io.Printer;
import com.observer.IObserver;
import com.validation.IValidator;
import com.validation.Validator;

public class Launcher {

    public Launcher() {
    }

    private void launch() {
        int COLUMNS = 10;
        int ROWS = 10;
        Board board = Board.getInstance(ROWS, COLUMNS);
        IObserver printer = new Printer(board);
        Input parser = new Input();
        IValidator validator = new Validator();
        Game game = new Game(board, parser, validator);
        game.addObserver(printer);
        game.notifyObservers();
        game.start();
    }

    public static void main(String[] args) {
        new Launcher().launch();
    }
}

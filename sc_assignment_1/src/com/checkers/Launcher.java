package com.checkers;

import com.board.Board;
import com.gameplay.Game;
import com.io.Input;
import com.io.Printer;
import com.validation.Validator;

public class Launcher {
    private static final int ROWS = 10;
    private static final int COLUMNS = 10;

    public Launcher() {
    }

    private void launch(){
        Board board = new Board(ROWS,COLUMNS);
        Printer printer = new Printer();
        Input parser = new Input();
        Validator validator = new Validator();
        Game game = new Game(board, printer, parser, validator);
        game.start();
    }

    public static void main(String[] args) {
        new Launcher().launch();
    }
}

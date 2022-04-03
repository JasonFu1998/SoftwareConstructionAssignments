package com.io;

import com.board.*;
import com.figures.*;
import com.observer.IObserver;

public class Printer implements IObserver {
    private final Board board;

    public Printer(Board board) {
        this.board = board;
    }

    public StringBuilder prepareBoard() {
        StringBuilder finalBoard = new StringBuilder();
        String columnChars = "      a     b     c     d     e     f     g     h      ";
        String boundaryLine = "  +-------------------------------------------------+  ";
        finalBoard.append("\n").append(columnChars).append("\n").append(boundaryLine).append("\n");

        Field[][] fields = board.getFields();
        int rows = board.getRowLength();
        int columns = board.getColumnLength();

        for (int i = rows - 2; i > 0; i--) {
            finalBoard.append(i).append(" | ");
            for (int j = 1; j < columns - 1; j++) {
                if (!fields[i][j].isEmpty()) {
                    Piece p = fields[i][j].getPiece();
                    finalBoard.append("[").append(p).append("] ");
                } else {
                    finalBoard.append("[   ] ");
                }
            }
            finalBoard.append("| ").append(i).append("\n");
        }
        finalBoard.append(boundaryLine).append("\n").append(columnChars).append("\n");
        return finalBoard;
    }

    private void printFinalBoard() {
        System.out.println(prepareBoard());
    }

    @Override
    public void update() {
        printFinalBoard();
    }
}

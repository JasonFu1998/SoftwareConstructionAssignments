package com.io;

import com.board.*;
import com.figures.*;

public class Printer {

    public void print(String message) {
        System.out.print(message);
    }

    public void print(Board board) {
        String columnChars = "      a     b     c     d     e     f     g     h      ";
        String boundaryLine = "  +-------------------------------------------------+  ";

        System.out.println("\n" + columnChars);
        System.out.println(boundaryLine);

        Field[][] fields = board.getFields();
        int rows = fields.length;
        int columns = fields[0].length;

        for (int i = rows - 2; i > 0; i--) {
            System.out.print(i + " | ");
            for (int j = 1; j < columns - 1; j++) {
                if (!fields[i][j].isEmpty()) {
                    Piece p = fields[i][j].getPiece();
                    System.out.print("[" + p + "] ");
                } else {
                    System.out.print("[   ] ");
                }
            }
            System.out.print("| " + i + "\n");
        }

        System.out.println(boundaryLine);
        System.out.println(columnChars + "\n");
    }
}

package com.gameplay;

import com.figures.Piece;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private final List<Piece> pieces;

    public Player() {
        pieces = new ArrayList<>();
    }

    public void assignPiece(Piece p) {
        this.pieces.add(p);
    }

    public void removePiece(Piece p) {
        this.pieces.remove(p);
    }

    public int playerPiecesSize() {
        return this.pieces.size();
    }

    public boolean isPiecesRed() {
        return this.pieces.get(0).isColorRed();
    }

    public boolean isPiecesWhite() {
        return this.pieces.get(0).isColorWhite();
    }

    public boolean checkPlayerPieceColorWhite(Player current) {
        return current.pieces.get(0).isColorWhite();
    }

    public boolean checkPlayerPieceColorRed(Player current) {
        return current.pieces.get(0).isColorRed();
    }

    public int getNumberOfPawns() {
        int sum = 0;
        for (Piece p : pieces) {
            if (p.isTypePawn())
                sum++;
        }
        return sum;
    }

    public int getNumberOfKings() {
        int sum = 0;
        for (Piece p : pieces) {
            if (p.isTypeKing())
                sum++;
        }
        return sum;
    }

    public int getNumberOfQueens() {
        int sum = 0;
        for (Piece p : pieces) {
            if (p.isTypeQueen())
                sum++;
        }
        return sum;
    }
}

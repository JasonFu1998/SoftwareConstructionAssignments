package com.gameplay;

import com.figures.Color;
import com.figures.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {

    private List<Piece> pieces;

    public Player() {
        pieces = new ArrayList<>();
    }

    public void assignPiece(Piece p){
        this.pieces.add(p);
    }

    public void removePiece(Piece p){
        this.pieces.remove(p);
    }

    public List<Piece> getPieces() {
        return pieces;
    }
}

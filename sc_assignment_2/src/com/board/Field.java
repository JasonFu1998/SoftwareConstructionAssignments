package com.board;

import com.figures.Piece;
import com.mapping.Position;

public class Field {
    private boolean isEmpty;
    private Piece piece;
    private Position position;

    public Field(Position position) {
        this.isEmpty = true;
        this.piece = null;
        this.position = position;
    }

    public Position copyPosition() {
        return new Position(position.getRow(), position.getColumn());
    }

    public boolean checkFieldDestinationRowRed() {
        return position.isFirstRowWhite();
    }

    public boolean checkFieldDestinationRowWhite() {
        return position.isFirstRowRed();
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void removePiece() {
        this.piece = null;
        this.isEmpty = true;
    }

    //TODO remove the getter and setter, but a LOT of work. The business logic relies heavily on this getter.
    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        this.isEmpty = false;

    }
}

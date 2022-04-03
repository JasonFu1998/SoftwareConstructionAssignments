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
        this.position=position;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public void removePiece() {
        this.piece = null;
        setEmpty(true);
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        setEmpty(false);
    }
}

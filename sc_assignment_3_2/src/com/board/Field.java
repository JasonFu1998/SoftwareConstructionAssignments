package com.board;

import com.figures.Piece;
import com.mapping.Position;

import java.util.ArrayList;
import java.util.List;

public class Field {
    private boolean isEmpty;
    private final List<Piece> piece;
    private final Position position;

    public Field(Position position) {
        this.isEmpty = true;
        this.piece = new ArrayList<>();
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

    public Piece removePiece() {
        this.isEmpty = true;
        return piece.remove(0);
    }

    //TODO remove the getter and setter, but a LOT of work. The business logic relies heavily on this getter.
    public Piece getPiece() {
        if (piece.size() > 0)
            return piece.get(0);
        return null;
    }

    public void setPiece(Piece piece) {
        this.piece.add(piece);
        this.isEmpty = false;
    }
}

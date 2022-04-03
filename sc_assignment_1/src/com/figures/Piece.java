package com.figures;


import com.mapping.Position;

public class Piece {
    private PieceType pieceType;
    private Color color;

    public Piece(Color color) {
        this.color = color;
        this.pieceType = PieceType.P;
    }

    public Color getColor() {
        return color;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    @Override
    public String toString() {
        return this.color + "_" + this.pieceType;
    }
}

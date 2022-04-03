package com.figures;

public class Piece {
    private PieceType pieceType;
    private Color color;

    public Piece(Color color) {
        this.color = color;
        this.pieceType = PieceType.P;
    }

    public boolean isColorWhite() {
        return this.color == Color.W;
    }

    public boolean isColorRed() {
        return this.color == Color.R;
    }

    public boolean isTypePawn() {
        return this.pieceType == PieceType.P;
    }

    public boolean isTypeKing() {
        return this.pieceType == PieceType.K;
    }

    public boolean isTypeQueen() {
        return this.pieceType == PieceType.Q;
    }

    public void pawnToKing() {
        if (this.pieceType == PieceType.P)
            this.pieceType = PieceType.K;
    }

    public void kingToQueen() {
        if (this.pieceType == PieceType.K)
            this.pieceType = PieceType.Q;
    }

    @Override
    public String toString() {
        return this.color + "_" + this.pieceType;
    }
}

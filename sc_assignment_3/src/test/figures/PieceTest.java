package test.figures;

import com.figures.Color;
import com.figures.Piece;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PieceTest {

    @Test
    @DisplayName("Check if Color is white")
    public void testPieceColorWhite() {
        Piece pieceW = new Piece(Color.W);
        Assertions.assertTrue(pieceW.isColorWhite());
    }

    @Test
    @DisplayName("Check if Color is red")
    public void testPieceColorRed() {
        Piece pieceR = new Piece(Color.R);
        Assertions.assertTrue(pieceR.isColorRed());
    }

    @Test
    @DisplayName("Check for Type Pawn")
    public void testIsPawn() {
        Piece piece = new Piece(Color.R);
        Assertions.assertTrue(piece.isTypePawn());
    }

    @Test
    @DisplayName("Check for Type King by transforming Pawn")
    public void testIsKing() {
        Piece piece = new Piece(Color.R);
        piece.pawnToKing();
        Assertions.assertTrue(piece.isTypeKing());
    }

    @Test
    @DisplayName("Check for Type Queen by transforming King")
    public void testIsQueen() {
        Piece piece = new Piece(Color.R);
        piece.pawnToKing();
        piece.kingToQueen();
        Assertions.assertTrue(piece.isTypeQueen());
    }
}

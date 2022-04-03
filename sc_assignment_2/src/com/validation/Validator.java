package com.validation;

import com.board.Board;
import com.board.Field;
import com.figures.*;
import com.gameplay.Player;

import java.util.ArrayList;
import java.util.List;


public class Validator {

    public boolean checkQueenTransformation(Field field) {
        Piece piece = field.getPiece();
        return (piece.isTypeKing() && piece.isColorRed() && field.checkFieldDestinationRowWhite()) ||
                (piece.isTypeKing() && piece.isColorWhite() && field.checkFieldDestinationRowRed());
    }

    public boolean checkKingTransformation(Field field) {
        boolean rowForKingCheck = false;
        Piece piece = field.getPiece();
        boolean pieceTypePawn = piece.isTypePawn();
        boolean pieceColorRed = piece.isColorRed();
        boolean pieceColorWhite = piece.isColorWhite();
        if (pieceColorRed) {
            rowForKingCheck = field.checkFieldDestinationRowRed();
        }
        if (pieceColorWhite) {
            rowForKingCheck = field.checkFieldDestinationRowWhite();
        }
        if (pieceColorWhite && pieceTypePawn && rowForKingCheck) {
            return true;
        }
        return pieceColorRed && pieceTypePawn && rowForKingCheck;
    }

    public boolean checkForWin(Board board, Player player) throws Exception {
        if (player.playerPiecesSize() == 0) {
            return true;
        }
        List<Field> jumpableFields = calcJumpableFields(board, player);
        List<Field> movableFields = calcMovableFields(board, player);
        return jumpableFields.isEmpty() && movableFields.isEmpty();
    }

    public List<Field> calcMovableFields(Board board, Player currentPlayer) throws Exception {
        List<Field> movableField = new ArrayList<>();
        Field[][] fields = board.getFields();
        int row = board.getRowLength();
        int col = board.getColumnLength();
        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < col - 1; j++) {
                if (fields[i][j].getPiece() != null) {
                    if (fields[i][j].getPiece().isColorWhite() == currentPlayer.checkPlayerPieceColorWhite(currentPlayer) ||
                            fields[i][j].getPiece().isColorRed() == currentPlayer.checkPlayerPieceColorRed(currentPlayer)) {
                        if (!calcMoveSpace(fields[i][j], board, currentPlayer).isEmpty()) {
                            movableField.add(fields[i][j]);
                        }
                    }
                }
            }
        }
        return movableField;
    }


    public ArrayList<Field> calcMoveSpace(Field originField, Board board, Player player) throws Exception {


        ArrayList<Field> moveSpace = new ArrayList<>();

        int row = originField.copyPosition().getRow();
        int col = originField.copyPosition().getColumn();

        Piece piece = originField.getPiece();
        Field[][] fields = board.getFields();
        boolean isPieceTypePawn = piece.isTypePawn();
        boolean isPieceTypeKing = piece.isTypeKing();
        boolean isPieceTypeQueen = piece.isTypeQueen();
        boolean isPieceColorRed = piece.isColorRed();
        boolean isPieceColorWhite = piece.isColorWhite();

        if (piece.isColorRed() != player.isPiecesRed() || piece.isColorWhite() != player.isPiecesWhite()) {
            throw new Exception("You have to choose your color!");
        }

        if ((isPieceColorRed && isPieceTypePawn) || isPieceTypeKing) {
            if (fields[row - 1][col + 1].isEmpty() && row - 1 != 0 && col + 1 != 9) {
                moveSpace.add(fields[row - 1][col + 1]);
            }
            if (fields[row - 1][col - 1].isEmpty() && row - 1 != 0 && col - 1 != 0) {
                moveSpace.add(fields[row - 1][col - 1]);
            }
        }
        if ((isPieceColorWhite && isPieceTypePawn) || isPieceTypeKing) {
            if (fields[row + 1][col + 1].isEmpty() && row + 1 != 9 && col + 1 != 9) {
                moveSpace.add(fields[row + 1][col + 1]);
            }
            if (fields[row + 1][col - 1].isEmpty() && row + 1 != 9 && col - 1 != 0) {
                moveSpace.add(fields[row + 1][col - 1]);
            }
        }
        if (isPieceTypeQueen) {
            for (int i = row - 1; i < row + 2; i++) {
                for (int j = col - 1; j < col + 2; j++) {
                    if (fields[i][j].isEmpty() && i != 9 && i != 0 && j != 0 && j != 9)
                        moveSpace.add(fields[i][j]);
                }
            }
        }

        return moveSpace;
    }

    public List<Field> calcJumpableFields(Board board, Player currentPlayer) {
        List<Field> jumpableFields = new ArrayList<>();;
        Field[][] fields = board.getFields();
        int row = board.getRowLength();
        int col = board.getColumnLength();
        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < col - 1; j++) {
                if (fields[i][j].getPiece() != null) {
                    if(fields[i][j].getPiece().isColorWhite() == currentPlayer.checkPlayerPieceColorWhite(currentPlayer) ||
                            fields[i][j].getPiece().isColorRed() == currentPlayer.checkPlayerPieceColorRed(currentPlayer)){
                        if (!calcJumpSpace(fields[i][j], board).isEmpty()) {
                            jumpableFields.add(fields[i][j]);
                        }
                    }
                }
            }
        }
        return jumpableFields;
    }

    public List<Field> calcJumpSpace(Field originField, Board board) {

        List<Field> jumpSpace = new ArrayList<>();

        int row = originField.copyPosition().getRow();
        int col = originField.copyPosition().getColumn();

        Piece piece = originField.getPiece();
        Field[][] fields = board.getFields();
        boolean pieceTypePawn = piece.isTypePawn();
        boolean pieceTypeKing = piece.isTypeKing();
        boolean pieceTypeQueen = piece.isTypeQueen();
        boolean pieceColorRed = piece.isColorRed();
        boolean pieceColorWhite = piece.isColorWhite();
        Color color = null;
        if (pieceColorRed) {
            color = Color.R;
        }
        if (pieceColorWhite) {
            color = Color.W;
        }

        if ((piece.isColorRed() && pieceTypePawn) || pieceTypeKing || pieceTypeQueen) {
            if (fields[(row + 8) % 10][(col + 2) % 10].isEmpty() && fields[row - 1][col + 1].getPiece() != null && getPieceColor(fields[row - 1][col + 1].getPiece()) != color) {
                if (row - 2 != 0 && col + 2 != 9)
                    jumpSpace.add(fields[row - 2][col + 2]);
            }
            if (fields[(row + 8) % 10][(col + 8) % 10].isEmpty() && fields[row - 1][col - 1].getPiece() != null && getPieceColor(fields[row - 1][col - 1].getPiece()) != color) {
                if (row - 2 != 0 && col - 2 != 0)
                    jumpSpace.add(fields[row - 2][col - 2]);
            }
        }
        if ((piece.isColorWhite() && pieceTypePawn) || pieceTypeKing || pieceTypeQueen) {
            if (fields[(row + 2) % 10][(col + 2) % 10].isEmpty() && fields[row + 1][col + 1].getPiece() != null && getPieceColor(fields[row + 1][col + 1].getPiece()) != color) {
                if (row + 2 != 9 && col + 2 != 9)
                    jumpSpace.add(fields[row + 2][col + 2]);
            }
            if (fields[(row + 2) % 10][(col + 8) % 10].isEmpty() && fields[row + 1][col - 1].getPiece() != null && getPieceColor(fields[row + 1][col - 1].getPiece()) != color) {
                if (row + 2 != 9 && col - 2 != 0)
                    jumpSpace.add(fields[row + 2][col - 2]);
            }
        }
        return jumpSpace;
    }

    private Color getPieceColor(Piece piece){ //this is a helper method, so that after the checks, a color can be assigned
        boolean isRed = piece.isColorRed();
        if(isRed){ return Color.R;}
        else {return Color.W;}
    }
}
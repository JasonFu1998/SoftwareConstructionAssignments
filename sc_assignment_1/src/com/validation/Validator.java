package com.validation;

import com.board.Board;
import com.board.Field;
import com.figures.*;
import com.gameplay.Player;

import java.util.ArrayList;
import java.util.List;


public class Validator {

    public boolean checkKingTransformation(Field destination) {
        int row = destination.getPosition().getRow();
        Piece piece = destination.getPiece();
        Color color = piece.getColor();
        PieceType type = piece.getPieceType();
        if (color == Color.W && type == PieceType.P && row == 8)
            return true;
        return color == Color.R && type == PieceType.P && row == 1;
    }

    public boolean checkForWin(Board board, Player player) throws Exception {
        if(player.getPieces().size() == 0){
            return true;
        }
        List<Field> jumpableFields = calcJumpableFields(board, player);
        List<Field> moveableFields = calcMovalbeFields(board, player);
        return jumpableFields.isEmpty() && moveableFields.isEmpty();
        //return player.getPieces().size() == 0;
    }

    public List<Field> calcMovalbeFields(Board board, Player currentPlayer) throws Exception {
        List<Field> movableField = new ArrayList<>();
        List<Piece> playersPieces = currentPlayer.getPieces();
        Field[][] fields = board.getFields();
        for (int i = 1; i < fields.length - 1; i++) {
            for (int j = 1; j < fields[0].length - 1; j++) {
                if (fields[i][j].getPiece() != null) {
                    if (fields[i][j].getPiece().getColor() == playersPieces.get(0).getColor()) {
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

        int row = originField.getPosition().getRow();
        int col = originField.getPosition().getColumn();

        Piece piece = originField.getPiece();
        PieceType pieceType = piece.getPieceType();
        Color color = piece.getColor();

        if (color != player.getPieces().get(0).getColor())
            throw new Exception("You have to choose your color!");

        Field[][] fields = board.getFields();

        if (color == Color.R && pieceType == PieceType.P || pieceType == PieceType.K) {

            if (fields[row - 1][col + 1].isEmpty() && row - 1 != 0 && col + 1 != 9) {
                moveSpace.add(fields[row - 1][col + 1]);
            }
            if (fields[row - 1][col - 1].isEmpty() && row - 1 != 0 && col - 1 != 0) {
                moveSpace.add(fields[row - 1][col - 1]);
            }
        }
        if ((color == Color.W && pieceType == PieceType.P) || pieceType == PieceType.K) {

            if (fields[row + 1][col + 1].isEmpty() && row + 1 != 9 && col + 1 != 9) {
                moveSpace.add(fields[row + 1][col + 1]);
            }
            if (fields[row + 1][col - 1].isEmpty() && row + 1 != 9 && col - 1 != 0) {
                moveSpace.add(fields[row + 1][col - 1]);
            }
        }

        return moveSpace;
    }

    public List<Field> calcJumpableFields(Board board, Player currentPlayer) {
        List<Field> jumpableFields = new ArrayList<>();
        List<Piece> playersPieces = currentPlayer.getPieces();
        Field[][] fields = board.getFields();
        for (int i = 1; i < fields.length - 1; i++) {
            for (int j = 1; j < fields[0].length - 1; j++) {
                if (fields[i][j].getPiece() != null) {
                    if (fields[i][j].getPiece().getColor() == playersPieces.get(0).getColor()) {
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

        int row = originField.getPosition().getRow();
        int col = originField.getPosition().getColumn();

        Piece piece = originField.getPiece();
        PieceType pieceType = piece.getPieceType();
        Color color = piece.getColor();

        Field[][] fields = board.getFields();

        if (color == Color.R && pieceType == PieceType.P || pieceType == PieceType.K) {

            if (fields[(row + 8) % 10][(col + 2) % 10].isEmpty() && fields[row - 1][col + 1].getPiece() != null && fields[row - 1][col + 1].getPiece().getColor() != color) {
                if (row - 2 != 0 && col + 2 != 9)
                    jumpSpace.add(fields[row - 2][col + 2]);
            }
            if (fields[(row + 8) % 10][(col + 8) % 10].isEmpty() && fields[row - 1][col - 1].getPiece() != null && fields[row - 1][col - 1].getPiece().getColor() != color) {
                if (row - 2 != 0 && col - 2 != 0)
                    jumpSpace.add(fields[row - 2][col - 2]);
            }
        }
        if (color == Color.W && pieceType == PieceType.P || pieceType == PieceType.K) {

            if (fields[(row + 2) % 10][(col + 2) % 10].isEmpty() && fields[row + 1][col + 1].getPiece() != null && fields[row + 1][col + 1].getPiece().getColor() != color) {
                if (row + 2 != 9 && col + 2 != 9)
                    jumpSpace.add(fields[row + 2][col + 2]);
            }
            if (fields[(row + 2) % 10][(col + 8) % 10].isEmpty() && fields[row + 1][col - 1].getPiece() != null && fields[row + 1][col - 1].getPiece().getColor() != color) {
                if (row + 2 != 9 && col - 2 != 0)
                    jumpSpace.add(fields[row + 2][col - 2]);
            }
        }
        return jumpSpace;
    }
}
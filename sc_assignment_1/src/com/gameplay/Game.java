package com.gameplay;


import com.board.Field;
import com.figures.Color;
import com.figures.Piece;
import com.board.Board;
import com.figures.PieceType;
import com.io.Input;
import com.io.Printer;
import com.mapping.Position;
import com.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    private Board board;
    private Printer printer;
    private Input parser;
    private Validator validator;
    private Player[] players;
    private Player currentPlayer;
    private Player opponentPlayer;

    private boolean isOver;

    public Game(Board board, Printer printer, Input parser, Validator validator) {
        isOver = false;
        this.board = board;
        this.printer = printer;
        this.parser = parser;
        this.validator = validator;
        players = new Player[2];
        initializePieces();
        printBoard();
    }

    public void start() {
        currentPlayer = players[0];
        opponentPlayer = players[1];
        while (!isOver) {
            askPlayer();
            isOver = checkForWinner(this.board, opponentPlayer);
            switchPlayer();
        }
    }

    private void movePiece(Field startField, Field destField) throws Exception {
        destField.setPiece(startField.getPiece());
        startField.removePiece();

        if (this.validator.checkKingTransformation(destField)) {
            destField.getPiece().setPieceType(PieceType.K);
        }
        printBoard();
    }

    private boolean checkForMove(Field startField, Field destField) throws Exception {
        List<Field> moveSpace = this.validator.calcMoveSpace(startField, this.board, currentPlayer);
        if (!moveSpace.contains(destField)) {
            throw new Exception("This move is not valid!");
        } else {
            return true;
        }
    }

    private boolean checkForJump(List<Field> jumpSpace, Field destField) throws Exception {
        if (!jumpSpace.contains(destField)) {
            throw new Exception("You have to take a valid JUMP and kill the enemy!");
        }
        return true;
    }

    private List<Field> calcJumpSpace(Field startField) {
        return this.validator.calcJumpSpace(startField, this.board);
    }

    private Field jump(Field startField, Field destField) {
        Position startPosition = startField.getPosition();
        Position destPosition = destField.getPosition();

        destField.setPiece(startField.getPiece());
        startField.removePiece();

        if (this.validator.checkKingTransformation(destField)) {
            destField.getPiece().setPieceType(PieceType.K);
        }

        Position posInBetween = new Position((destPosition.getRow() + startPosition.getRow()) / 2, (destPosition.getColumn() + startPosition.getColumn()) / 2);

        Field fieldInBetween = board.getField(posInBetween);
        Piece pieceInBetween = fieldInBetween.getPiece();
        opponentPlayer.removePiece(pieceInBetween);
        fieldInBetween.removePiece();

        printBoard();
        return destField;
    }

    private void initializePieces() {
        players[0] = new Player();
        players[1] = new Player();
        Field[][] fields = board.getFields();
        int rows = fields.length;
        int columns = fields[0].length;
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < columns - 1; j++) {
                if ((i + j) % 2 == 0) {
                    Piece p = new Piece(Color.W);
                    players[1].assignPiece(p);
                    fields[i][j].setPiece(p);
                }
            }
        }
        for (int i = 6; i < rows - 1; i++) {
            for (int j = 1; j < columns - 1; j++) {
                if ((i + j) % 2 == 0) {
                    Piece p = new Piece(Color.R);
                    players[0].assignPiece(p);
                    fields[i][j].setPiece(p);
                }
            }
        }
    }

    private void switchPlayer() {
        if (currentPlayer == players[0]) {
            currentPlayer = players[1];
            opponentPlayer = players[0];
        } else {
            currentPlayer = players[0];
            opponentPlayer = players[1];
        }
    }

    private void printBoard() {
        this.printer.print(this.board);
    }

    private List<Field> convertUserInputToFields(String input) throws Exception {
        List<Position> positions;
        positions = this.parser.read(input);
        List<Field> fields = new ArrayList<>();
        fields.add(this.board.getField(positions.get(0)));
        fields.add(this.board.getField(positions.get(1)));
        return fields;
    }

    private void makeMultipleJumps(Field startField, Field destField) throws Exception {
        PieceType checkIfTypeChanged = startField.getPiece().getPieceType();
        Field newStartFieldAfterJump = jump(startField, destField);
        if (checkIfTypeChanged != destField.getPiece().getPieceType()){
            return;
        }

        List<Field> newJumpSpace = calcJumpSpace(newStartFieldAfterJump);
        if (!newJumpSpace.isEmpty()) {
            String input = getUserInput();
            startField = convertUserInputToFields(input).get(0);
            destField = convertUserInputToFields(input).get(1);
            if (startField != newStartFieldAfterJump) {
                throw new Exception("You have to choose your previous destination as your new start!");
            }
            makeMultipleJumps(startField, destField);
        }
    }

    private boolean checkForWinner(Board board, Player opponent)  {
        try {
            if (this.validator.checkForWin(board, opponent)) {
                System.out.println(currentPlayer.getPieces().get(0).getColor() + " player has won!!!");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void askPlayer() {
        while (true) {
            List<Field> jumpableFields = this.validator.calcJumpableFields(board, currentPlayer);
            String input = getUserInput();
            try {

                Field startField = convertUserInputToFields(input).get(0);
                Field destField = convertUserInputToFields(input).get(1);

                if (!jumpableFields.isEmpty() && !jumpableFields.contains(startField)) {
                    throw new Exception("You have to choose a piece that can jump!");
                }

                List<Field> jumpSpace = calcJumpSpace(startField);

                if (!jumpSpace.isEmpty()) {
                    if (checkForJump(jumpSpace, destField)) {
                        makeMultipleJumps(startField, destField);
                    }
                }
                if (jumpSpace.isEmpty() && checkForMove(startField, destField)) {
                    movePiece(startField, destField);
                }
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage() + " Try again!");
            }
        }

    }

    private String getUserInput() {
        this.printer.print(currentPlayer.getPieces().get(0).getColor() + " player , enter your move: ");
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

}

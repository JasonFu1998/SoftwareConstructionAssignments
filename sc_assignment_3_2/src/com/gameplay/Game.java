package com.gameplay;


import com.board.Field;
import com.figures.Color;
import com.board.Board;
import com.figures.Piece;
import com.io.Input;
import com.mapping.Position;
import com.observer.IObservable;
import com.observer.IObserver;
import com.validation.IValidator;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Game implements IObservable {

    private final Board board;
    private final Input parser;
    private final IValidator validator;
    private final Player[] players;
    private Player currentPlayer;
    private Player opponentPlayer;
    private final List<IObserver> observers;

    private boolean isOver;

    public Game(Board board, Input parser, IValidator validator) {
        isOver = false;
        this.board = board;
        this.parser = parser;
        this.validator = validator;
        players = new Player[2];
        initializePieces();
        observers = new ArrayList<>();
        currentPlayer = players[0];
        opponentPlayer = players[1];
    }

    public void start() {
        while (!isOver) {
            askPlayer();
            isOver = checkForWinner(this.board, opponentPlayer);
            switchPlayer();
        }
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
            throw new Exception("You have to make a valid JUMP and kill the enemy!");
        }
        return true;
    }

    private Field movePiece(Field startField, Field destField) {
        destField.setPiece(startField.getPiece());
        startField.removePiece();

        if (this.validator.checkKingTransformation(destField)) {
            destField.getPiece().pawnToKing();
        } else if (this.validator.checkQueenTransformation(destField)) {
            destField.getPiece().kingToQueen();
        }

        return destField;
    }

    private Piece removePieceInBetween(Field startField, Field destField) {
        Position startPosition = startField.copyPosition();
        Position destPosition = destField.copyPosition();
        Position posInBetween = new Position((destPosition.getRow() + startPosition.getRow()) / 2, (destPosition.getColumn() + startPosition.getColumn()) / 2);

        Field fieldInBetween = board.copyField(posInBetween);
        Piece pieceInBetween = fieldInBetween.getPiece();
        opponentPlayer.removePiece(pieceInBetween);
        return fieldInBetween.removePiece();
    }

    private void addPieceInBetween(Field startField, Field destField, Piece p) {
        Position startPosition = startField.copyPosition();
        Position destPosition = destField.copyPosition();
        Position posInBetween = new Position((destPosition.getRow() + startPosition.getRow()) / 2, (destPosition.getColumn() + startPosition.getColumn()) / 2);

        Field fieldInBetween = board.copyField(posInBetween);
        opponentPlayer.assignPiece(p);
        fieldInBetween.setPiece(p);
    }

    private void initializePieces() {
        players[0] = new Player();
        players[1] = new Player();
        Field[][] fields = board.getFields();
        int rows = board.getRowLength();
        int columns = board.getColumnLength();
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

    public void switchPlayer() {
        if (currentPlayer == players[0]) {
            currentPlayer = players[1];
            opponentPlayer = players[0];
        } else {
            currentPlayer = players[0];
            opponentPlayer = players[1];
        }
    }


    private List<Field> convertUserInputToFields(String input) throws Exception {
        List<Position> positions;
        positions = this.parser.read(input);
        List<Field> fields = new ArrayList<>();
        fields.add(this.board.copyField(positions.get(0)));
        fields.add(this.board.copyField(positions.get(1)));
        return fields;
    }

    private void makeMultipleJumps(Field startField, Field destField) throws Exception {

        boolean checkIfChangedKing = startField.getPiece().isTypeKing();
        boolean checkIfChangedQueen = startField.getPiece().isTypeQueen();

        Field newStartFieldAfterJump = movePiece(startField, destField);
        removePieceInBetween(startField, destField);
        notifyObservers();

        if (checkIfChangedKing != destField.getPiece().isTypeKing() || checkIfChangedQueen != destField.getPiece().isTypeQueen()) {
            return;
        }

        List<Field> newJumpSpace = this.validator.calcJumpSpace(newStartFieldAfterJump, this.board);
        if (!newJumpSpace.isEmpty()) {
            String input = getUserInput();
            if (input.equals("help")) {
                throw new Exception("You have to choose your previous destination as your new start!");
            }
            if (input.equals("auto")) {
                List<Field> jumpableFields = new ArrayList<>();
                jumpableFields.add(newStartFieldAfterJump);
                makeAutoAction(jumpableFields);
                return;
            }
            startField = convertUserInputToFields(input).get(0);
            destField = convertUserInputToFields(input).get(1);
            if (startField != newStartFieldAfterJump) {
                throw new Exception("You have to choose your previous destination as your new start!");
            }
            if (!newJumpSpace.contains(destField)) {
                throw new Exception("You cannot jump to that field!");
            }
            makeMultipleJumps(startField, destField);
        }
    }

    public boolean checkForWinner(Board board, Player opponent) {
        try {
            if (this.validator.checkForWin(board, opponent)) {
                System.out.println(getCurrentPlayerColor() + " player has won!!!");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void printHelp(List<Field> jumpableFields) throws Exception {
        if (jumpableFields.isEmpty()) {
            List<Field> movableFields = this.validator.calcMovableFields(this.board, currentPlayer);
            System.out.println("You can move one of the following pieces: ");
            for (Field f : movableFields) {
                System.out.print("[" + (char) (f.copyPosition().getColumn() + 96) + f.copyPosition().getRow() + "] ");
            }
        } else {
            System.out.println("You must jump with one of the following pieces: ");
            for (Field f : jumpableFields) {
                System.out.print("[" + (char) (f.copyPosition().getColumn() + 96) + f.copyPosition().getRow() + "] ");
            }
        }
        System.out.println();
    }

    public List<Field> calcHeuristicJumpVal(List<List<Field>> autoActions) {

        List<Integer> scoreList = new ArrayList<>();
        for (List<Field> lf : autoActions) {
            Field tempStartField = lf.get(0);
            Field tempDestField = lf.get(1);
            movePiece(tempStartField, tempDestField);
            Piece p = removePieceInBetween(tempStartField, tempDestField);

            int pieceTypeScore = (currentPlayer.getNumberOfPawns() - opponentPlayer.getNumberOfPawns()) * 5
                    + (currentPlayer.getNumberOfKings() - opponentPlayer.getNumberOfKings()) * 10
                    + (currentPlayer.getNumberOfQueens() - opponentPlayer.getNumberOfQueens()) * 20;

            List<Field> currentPlayerJumpableFields = this.validator.calcJumpableFields(board, currentPlayer);
            List<Field> opponentPlayerJumpableFields = this.validator.calcJumpableFields(board, opponentPlayer);

            int jumpOrMoveScore = 0;
            if (!currentPlayerJumpableFields.isEmpty()) {
                for (Field currentPlayerJumpable : currentPlayerJumpableFields) {
                    jumpOrMoveScore += this.validator.calcJumpSpace(currentPlayerJumpable, this.board).size() * 4;
                }
            }
            if (!opponentPlayerJumpableFields.isEmpty()) {
                for (Field opponentPlayerJumpable : opponentPlayerJumpableFields) {
                    jumpOrMoveScore -= this.validator.calcJumpSpace(opponentPlayerJumpable, this.board).size() * 5;
                }
            }
            scoreList.add(pieceTypeScore + jumpOrMoveScore);
            movePiece(tempDestField, tempStartField);
            addPieceInBetween(tempDestField, tempStartField, p);
        }
        Integer maxVal = Collections.max(scoreList);
        int maxIdx = scoreList.indexOf(maxVal);
        return autoActions.get(maxIdx);
    }


    public List<Field> calcHeuristicMoveVal(List<List<Field>> autoActions) {
        List<Integer> scoreList = new ArrayList<>();
        for (List<Field> lf : autoActions) {
            Field tempStartField = lf.get(0);
            Field tempDestField = lf.get(1);
            movePiece(tempStartField, tempDestField);

            int pieceTypeScore = (currentPlayer.getNumberOfPawns() - opponentPlayer.getNumberOfPawns()) * 5
                    + (currentPlayer.getNumberOfKings() - opponentPlayer.getNumberOfKings()) * 10
                    + (currentPlayer.getNumberOfQueens() - opponentPlayer.getNumberOfQueens()) * 20;

            List<Field> currentPlayerJumpableFields = this.validator.calcJumpableFields(board, currentPlayer);
            List<Field> opponentPlayerJumpableFields = this.validator.calcJumpableFields(board, opponentPlayer);

            int jumpOrMoveScore = 0;
            if (!currentPlayerJumpableFields.isEmpty()) {
                for (Field currentPlayerJumpable : currentPlayerJumpableFields) {
                    jumpOrMoveScore += this.validator.calcJumpSpace(currentPlayerJumpable, this.board).size() * 4;
                }
            }
            if (!opponentPlayerJumpableFields.isEmpty()) {
                for (Field opponentPlayerJumpable : opponentPlayerJumpableFields) {
                    jumpOrMoveScore -= this.validator.calcJumpSpace(opponentPlayerJumpable, this.board).size() * 5;
                }
            }
            scoreList.add(pieceTypeScore + jumpOrMoveScore);
            movePiece(tempDestField, tempStartField);
        }
        Integer maxVal = Collections.max(scoreList);
        int maxIdx = scoreList.indexOf(maxVal);
        return autoActions.get(maxIdx);
    }

    public void makeAutoAction(List<Field> jumpableFields) throws Exception {
        List<List<Field>> autoActions = new ArrayList<>();
        if (jumpableFields.isEmpty()) {
            List<Field> movableFields = this.validator.calcMovableFields(this.board, currentPlayer);
            for (Field f : movableFields) {
                List<Field> tempMoveSpace = this.validator.calcMoveSpace(f, this.board, currentPlayer);
                for (Field mf : tempMoveSpace) {
                    autoActions.add(List.of(f, mf));
                }
            }
            List<Field> autoAction = calcHeuristicMoveVal(autoActions);
            movePiece(autoAction.get(0), autoAction.get(1));
            notifyObservers();
        } else {
            for (Field f : jumpableFields) {
                List<Field> tempJumpSpace = this.validator.calcJumpSpace(f, this.board);
                for (Field jf : tempJumpSpace) {
                    autoActions.add(List.of(f, jf));
                }
            }
            List<Field> autoAction = calcHeuristicJumpVal(autoActions);
            makeMultipleJumps(autoAction.get(0), autoAction.get(1));
        }
    }

    public void askPlayer() {
        while (true) {
            List<Field> jumpableFields = this.validator.calcJumpableFields(board, currentPlayer);
            String input = getUserInput();
            try {
                if (input.equals("help")) {
                    printHelp(jumpableFields);
                    continue;
                }
                if (input.equals("auto")) {
                    makeAutoAction(jumpableFields);
                    break;
                }
                Field startField = convertUserInputToFields(input).get(0);
                Field destField = convertUserInputToFields(input).get(1);

                if (!jumpableFields.isEmpty() && !jumpableFields.contains(startField)) {
                    throw new Exception("You have to choose a piece that can jump!");
                }

                List<Field> jumpSpace = this.validator.calcJumpSpace(startField, this.board);

                if (!jumpSpace.isEmpty()) {
                    if (checkForJump(jumpSpace, destField)) {
                        makeMultipleJumps(startField, destField);
                    }
                }
                if (jumpSpace.isEmpty() && checkForMove(startField, destField)) {
                    movePiece(startField, destField);
                    notifyObservers();
                }
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage() + " Try again!");
            }
        }

    }

    private String getUserInput() {
        System.out.print(getCurrentPlayerColor() + " player , enter your move: ");
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    public String getCurrentPlayerColor() {
        boolean isColorRed = currentPlayer.isPiecesRed();
        if (isColorRed) {
            return "Red";
        } else {
            return "White";
        }
    }

    @Override
    public void addObserver(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (IObserver observer : observers) {
            observer.update();
        }
    }
}

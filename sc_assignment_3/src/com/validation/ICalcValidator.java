package com.validation;

import com.board.Board;
import com.board.Field;
import com.gameplay.Player;

import java.util.ArrayList;
import java.util.List;

public interface ICalcValidator {
    List<Field> calcMovableFields(Board board, Player currentPlayer) throws Exception;

    ArrayList<Field> calcMoveSpace(Field originField, Board board, Player player) throws Exception;

    List<Field> calcJumpableFields(Board board, Player currentPlayer);

    List<Field> calcJumpSpace(Field originField, Board board);
}

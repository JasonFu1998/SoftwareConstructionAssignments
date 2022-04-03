package com.validation;

import com.board.Board;
import com.board.Field;
import com.gameplay.Player;

public interface ICheckValidator {
    boolean checkQueenTransformation(Field field);

    boolean checkKingTransformation(Field field);

    boolean checkForWin(Board board, Player player) throws Exception;
}

package com.board;

import com.mapping.Position;


public class Board {
    private Field[][] fields;

    public Board(int rows, int columns) {
        this.fields = new Field[rows][columns];
        initializeBoard();
    }

    public Field[][] getFields() {
        return fields;
    }

    public int getRowLength() {
        return fields.length;
    }

    public int getColumnLength() {
        return fields[0].length;
    }

    public Field copyField(Position position) {
        Field copyOfField;
        copyOfField = fields[position.getRow()][position.getColumn()];
        return copyOfField;
    }

    private void initializeBoard() {
        int rows = fields.length;
        int columns = fields[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                fields[i][j] = new Field(new Position(i, j));
            }
        }
    }

}

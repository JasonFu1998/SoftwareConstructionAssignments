package com.mapping;

public class Position {

    private final int row;
    private final int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Position(int row, char column) {
        this.row = row + 1;
        this.column = column - 96;
    }

    public int getRow() {
        return row;
    }

    public boolean isFirstRowWhite() {
        int edgeRed = 1;
        return this.row == edgeRed;
    }

    public boolean isFirstRowRed() {
        int edgeWhite = 8;
        return this.row == edgeWhite;
    }


    public int getColumn() {
        return column;
    }

}

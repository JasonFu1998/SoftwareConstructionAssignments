package com.board;

import com.figures.*;
import com.mapping.Position;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private Field[][] fields;
    private final ArrayList<Position> positions = new ArrayList<>();

    public Board(int rows, int columns) {
        this.fields = new Field[rows][columns];
        initialize();
    }

    public Field[][] getFields() {
        return fields;
    }

    public Field getField(Position position){
        return fields[position.getRow()][position.getColumn()];
    }

    private void initialize()
    {
        int rows=fields.length;
        int columns=fields[0].length;
        for( int i = 0; i < rows; i++)
        {
            for( int j = 0; j < columns; j++)
            {
                fields[i][j] = new Field(new Position(i, j));
            }
        }
    }
}

package test.mapping;

import com.mapping.Position;

import org.junit.jupiter.api.*;

public class PositionTest {

    @Test
    @DisplayName("Test the getRow method with the all integers Constructor")
    public void testGetIntRow(){
        Position position = new Position(1,2);
        Assertions.assertEquals(1,position.getRow());
    }

    @Test
    @DisplayName("Test the getColumn method with the all integers Constructor")
    public void testGetIntColumn(){
        Position position = new Position(1,2);
        Assertions.assertEquals(2,position.getColumn());
    }

    @Test
    @DisplayName("Test the getRow method with the intger row and char column Constructor")
    public void testGetCharRow(){
        char c = 'b';
        Position position = new Position(1,c);
        Assertions.assertEquals(2,position.getRow());
    }

    @Test
    @DisplayName("Test the getColumn method with the intger row and char column Constructor")
    public void testGetCharColumn(){
        char c = 'b';
        Position position = new Position(1,c);
        Assertions.assertEquals(2,position.getColumn());
    }

    @Test
    @DisplayName("Test the isFirstRowWhite method with row not equal to edgeRed")
    public void testFalseFirstRowWhite(){
        char c = 'b';
        Position position = new Position(1,c);
        Assertions.assertFalse(position.isFirstRowWhite());
    }

    @Test
    @DisplayName("Test the isFirstRowWhite method with row equal to edgeRed")
    public void testTrueFirstRowWhite(){
        Position position = new Position(1,1);
        Assertions.assertTrue(position.isFirstRowWhite());
    }

    @Test
    @DisplayName("Test the isFirstRowRed method with row not equal to edgeWhite")
    public void testFalseFirstRowRed(){
        Position position = new Position(4,1);
        Assertions.assertFalse(position.isFirstRowRed());
    }

    @Test
    @DisplayName("Test the isFirstRowRed method with row equal to edgeWhite")
    public void testTrueFirstRowRed(){
        Position position = new Position(8,1);
        Assertions.assertTrue(position.isFirstRowRed());
    }

}


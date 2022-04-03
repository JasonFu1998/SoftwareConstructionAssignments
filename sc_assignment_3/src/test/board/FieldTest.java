package test.board;

import com.board.Field;
import com.figures.Color;
import com.figures.Piece;
import com.mapping.Position;

import org.junit.jupiter.api.*;

public class FieldTest {

    private Position position;
    private Field field;

    @BeforeEach
    public void initializePositionAndField() {
        position = new Position(8, 1);
        field = new Field(position);
    }

    @Test
    @DisplayName("Check that Position copy has the same Row")
    public void testCopyPositionAttributesRow() {
        Position positionCopy = new Position(8, 1);
        Assertions.assertEquals(positionCopy.getRow(), position.getRow());
    }

    @Test
    @DisplayName("Check that Position copy has the same Column")
    public void testCopyPositionAttributesColumn() {
        Position positionCopy = new Position(8, 1);
        Assertions.assertEquals(positionCopy.getColumn(), position.getColumn());
    }

    @Test
    @DisplayName("Check that Position copy does not have the same pointer")
    public void testCopyPositionPointer() {
        Position positionCopy = new Position(8, 1);
        Assertions.assertNotEquals(positionCopy, field.copyPosition());
    }

    @Test
    @DisplayName("Check Field Edge Destination Row Red ")
    public void testEdgeFieldDestinationRowRed() {
        position = new Position(1, 8);
        field = new Field(position);
        Assertions.assertTrue(field.checkFieldDestinationRowRed());
    }

    @Test
    @DisplayName("Check Field Edge Destination Row White")
    public void testEdgeFieldDestinationRowWhite() {
        Assertions.assertTrue(field.checkFieldDestinationRowWhite());
    }

    @Test
    @DisplayName("Check for correct isEmpty method of Field")
    public void testIsEmpty() {
        Piece piece = new Piece(Color.R);
        field.setPiece(piece);
        Assertions.assertFalse(field.isEmpty());
    }

    @Test
    @DisplayName("Check for correct setting of a piece")
    public void testSettingOfPiece() {
        Piece piece = new Piece(Color.R);
        field.setPiece(piece);
        Assertions.assertNotNull(field.getPiece());
    }

    @Test
    @DisplayName("Check for correct removal of a piece")
    public void testRemovalOfPiece() {
        Piece piece = new Piece(Color.R);
        field.setPiece(piece);
        field.removePiece();
        Assertions.assertNull(field.getPiece());
    }

    @AfterEach
    public void tearDown() {
        position = null;
        field = null;
    }
}

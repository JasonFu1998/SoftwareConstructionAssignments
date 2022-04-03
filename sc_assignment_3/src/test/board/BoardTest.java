package test.board;

import com.board.Board;
import com.board.Field;
import com.mapping.Position;

import org.junit.jupiter.api.*;

public class BoardTest {

    private Board board;

    @BeforeEach
    public void initializeBoard() {
        board = new Board(10, 10);
    }

    @Test
    @DisplayName("Check correct Board Row length")
    public void testBoardRowLength() {
        Assertions.assertEquals(10, board.getRowLength());
    }

    @Test
    @DisplayName("Check correct Board Column length")
    public void testBoardColumnLength() {
        Assertions.assertEquals(10, board.getColumnLength());
    }


    @Test
    @DisplayName("Check for Copy Field to have the same pointer")
    public void testCopyOfField() {
        Position position = new Position(8, 1);
        Field[][] fields = board.getFields();
        Field fieldCopy = fields[position.getRow()][position.getColumn()];
        Assertions.assertEquals(fieldCopy, board.copyField(position));
    }

    @AfterEach
    public void tearDown() {
        board = null;
    }
}
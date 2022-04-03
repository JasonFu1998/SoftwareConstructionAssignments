package test.io;

import com.board.Board;
import com.io.Printer;
import org.junit.jupiter.api.*;

public class PrinterTest {

    private Printer printer;

    @BeforeEach
    private void initializeBoard() {
        Board board = new Board(10, 10);
        printer = new Printer(board);
        printer.update();
    }

    @Test
    @DisplayName("Test initial Board state")
    public void testInitialPrint() {
        Assertions.assertEquals("\n" +
                "      a     b     c     d     e     f     g     h      \n" +
                "  +-------------------------------------------------+  \n" +
                "8 | [   ] [   ] [   ] [   ] [   ] [   ] [   ] [   ] | 8\n" +
                "7 | [   ] [   ] [   ] [   ] [   ] [   ] [   ] [   ] | 7\n" +
                "6 | [   ] [   ] [   ] [   ] [   ] [   ] [   ] [   ] | 6\n" +
                "5 | [   ] [   ] [   ] [   ] [   ] [   ] [   ] [   ] | 5\n" +
                "4 | [   ] [   ] [   ] [   ] [   ] [   ] [   ] [   ] | 4\n" +
                "3 | [   ] [   ] [   ] [   ] [   ] [   ] [   ] [   ] | 3\n" +
                "2 | [   ] [   ] [   ] [   ] [   ] [   ] [   ] [   ] | 2\n" +
                "1 | [   ] [   ] [   ] [   ] [   ] [   ] [   ] [   ] | 1\n" +
                "  +-------------------------------------------------+  \n" +
                "      a     b     c     d     e     f     g     h      \n", printer.prepareBoard().toString());
    }

    @AfterEach
    private void cleanUp() {
        printer = null;
    }
}

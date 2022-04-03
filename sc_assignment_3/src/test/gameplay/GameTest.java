package test.gameplay;

import com.board.Board;
import com.board.Field;
import com.figures.Color;
import com.figures.Piece;
import com.gameplay.Game;
import com.gameplay.Player;
import com.io.Input;
import com.validation.Validator;

import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class GameTest {

    private Game game;
    private Board board;
    private Field[][] fields;

    @BeforeEach
    public void initialize(){
        Validator validator = new Validator();
        board = new Board(10, 10);
        Input input = new Input();
        game = new Game(board, input, validator);
    }

    @Test
    @DisplayName("Check if Red Pieces were set correctly on the board") //All red pieces were grouped together since we are only testing positions
    public void testRedPiecesOnBoard() {
        fields = board.getFields();
        Assertions.assertNotNull(fields[7][1].getPiece());
        Assertions.assertNotNull(fields[8][2].getPiece());
        Assertions.assertNotNull(fields[6][2].getPiece());
        Assertions.assertNotNull(fields[7][3].getPiece());
        Assertions.assertNotNull(fields[8][4].getPiece());
        Assertions.assertNotNull(fields[6][4].getPiece());
        Assertions.assertNotNull(fields[7][5].getPiece());
        Assertions.assertNotNull(fields[8][6].getPiece());
        Assertions.assertNotNull(fields[6][6].getPiece());
        Assertions.assertNotNull(fields[7][7].getPiece());
        Assertions.assertNotNull(fields[8][8].getPiece());
        Assertions.assertNotNull(fields[6][8].getPiece());
    }

    @Test
    @DisplayName("Check if White Pieces were set correctly on the board") //All red pieces were grouped together since we are only testing positions
    public void testWhitePiecesOnBoard() {
        fields = board.getFields();
        Assertions.assertNotNull(fields[1][1].getPiece());
        Assertions.assertNotNull(fields[3][1].getPiece());
        Assertions.assertNotNull(fields[2][2].getPiece());
        Assertions.assertNotNull(fields[1][3].getPiece());
        Assertions.assertNotNull(fields[3][3].getPiece());
        Assertions.assertNotNull(fields[2][4].getPiece());
        Assertions.assertNotNull(fields[1][5].getPiece());
        Assertions.assertNotNull(fields[3][5].getPiece());
        Assertions.assertNotNull(fields[2][6].getPiece());
        Assertions.assertNotNull(fields[1][7].getPiece());
        Assertions.assertNotNull(fields[3][7].getPiece());
        Assertions.assertNotNull(fields[2][8].getPiece());
    }

    @Test
    @DisplayName("Check for correct Player Color")
    public void testPlayerColor(){
        Assertions.assertEquals("Red", game.getCurrentPlayerColor());
    }

    @Test
    @DisplayName("Check for correct Player switch")
    public void testSwitchPlayer() {
        String color = game.getCurrentPlayerColor();
        game.switchPlayer();
        Assertions.assertNotEquals(color, game.getCurrentPlayerColor());
    }

    @Test
    @DisplayName("Check for simple starting move")
    public void testSimpleMove() {
        String input = "[b6]x[a5]";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        game.askPlayer();
        fields = board.getFields();
        Assertions.assertNotNull(fields[5][1].getPiece());
    }

    @Test
    @DisplayName("Check for Exception when jump is available with piece that can't jump")
    public void testForMissedJump() {
        String input = "[b6]x[a5]";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        game.askPlayer();
        game.switchPlayer();
        input = "[c3]x[b4]";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        game.askPlayer();
        game.switchPlayer();
        input = "[d6]x[c5]";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Assertions.assertThrows(Exception.class, () -> {
            game.askPlayer();
        });
    }

    @Test
    @DisplayName("Check for Exception when jump is available but destination is wrong")
    public void testOtherException() {
        String input = "[b6]x[a5]";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        game.askPlayer();
        game.switchPlayer();
        input = "[c3]x[b4]";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        game.askPlayer();
        game.switchPlayer();
        input = "[a5]x[b6]";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Assertions.assertThrows(Exception.class, () -> {
            game.askPlayer();
        });
    }

    @Test
    @DisplayName("Check for correct Piece kill after jump")
    public void testPieceKill() {
        String input = "[b6]x[a5]";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        game.askPlayer();
        game.switchPlayer();
        input = "[c3]x[b4]";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        game.askPlayer();
        game.switchPlayer();
        input = "[a5]x[c3]";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        game.askPlayer();
        fields = board.getFields();
        Assertions.assertNull(fields[4][2].getPiece());
    }

    @Test
    @DisplayName("Check for game over if player has 0 pieces")
    public void testGameOver() {
        Player opponent = new Player();
        Assertions.assertTrue(game.checkForWinner(board, opponent));
    }

    @Test
    @DisplayName("Check for correct continuation of game if player has at least 1 piece")
    public void testGameContinuation() {
        Player opponent = new Player();
        Piece piece = new Piece(Color.W);
        opponent.assignPiece(piece);
        Assertions.assertFalse(game.checkForWinner(board, opponent));
    }

    @AfterEach
    public void tearDown() {
        game = null;
        board = null;
    }
}

package test.gameplay;

import com.figures.Color;
import com.figures.Piece;
import com.gameplay.Player;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

public class PlayerTest {

    Player player;

    @BeforeEach
    public void intializePiece(){
        player = new Player();
    }

    @Test
    @DisplayName("check the player piece Size")
    public void testPlayerPieceSizeEmpty(){
        Assertions.assertEquals(0,player.playerPiecesSize());
    }

    @Test
    @DisplayName("check the player piece Size by adding piece")
    public void testPlayerPieceSizeAdd(){
        Piece p1 = new Piece(Color.R);
        player.assignPiece(p1);
        Assertions.assertEquals(1,player.playerPiecesSize());
    }

    @Test
    @DisplayName("check the player piece Size by removing piece")
    public void testPlayerPieceSizeRemove(){
        Piece p1 = new Piece(Color.R);
        player.assignPiece(p1);
        player.removePiece(p1);
        Assertions.assertEquals(0,player.playerPiecesSize());
    }

    @Test
    @DisplayName("check the reaction when removing an piece from an empty list")
    public void testPlayerPieceSizeRemoveByEmpty(){
        Piece p1 = new Piece(Color.R);
        player.removePiece(p1);
        Assertions.assertEquals(0,player.playerPiecesSize());
    }

    @Test
    @DisplayName("check the red Piece color method with red piece")
    public void testPlayerPieceColorRedTrue(){
        Piece p1 = new Piece(Color.R);
        player.assignPiece(p1);
        Assertions.assertTrue(player.isPiecesRed());
    }

    @Test
    @DisplayName("check the white Piece color method with red piece")
    public void testPlayerPieceColorRedFalse(){
        Piece p1 = new Piece(Color.R);
        player.assignPiece(p1);
        Assertions.assertFalse(player.isPiecesWhite());
    }

    @Test
    @DisplayName("check the white Piece color method with white piece")
    public void testPlayerPieceColorWhiteTrue(){
        Piece p1 = new Piece(Color.W);
        player.assignPiece(p1);
        Assertions.assertTrue(player.isPiecesWhite());
    }

    @Test
    @DisplayName("check the red Piece color method with white piece")
    public void testPlayerPieceColorWhiteFalse() {
        Piece p1 = new Piece(Color.W);
        player.assignPiece(p1);
        Assertions.assertFalse(player.isPiecesRed());
    }

    @Test
    @DisplayName("check the Piece color Red method with empty piece ArrayList")
    public void testPlayerPieceColorRedEmptyArrayList(){
        try{
            player.isPiecesRed();
            Assertions.fail("IndexOutOfBoundsException should have been thrown");
        }catch (IndexOutOfBoundsException e){}
    }

    @Test
    @DisplayName("check the Piece color Red method with empty piece ArrayList")
    public void testPlayerPieceColorWhiteEmptyArrayList(){
        try{
            player.isPiecesWhite();
            Assertions.fail("IndexOutOfBoundsException should have been thrown");
        }catch (IndexOutOfBoundsException e){}
    }

    @Test
    @DisplayName("check the Players Piece color red method with red piece")
    public void testCurrentPlayerPieceColorRedTrue(){
        Piece p1 = new Piece(Color.R);
        player.assignPiece(p1);
        Assertions.assertTrue(player.checkPlayerPieceColorRed(player));
    }

    @Test
    @DisplayName("check the Players Piece color white method with red piece")
    public void testCurrentPlayerPieceColorRedFalse(){
        Piece p1 = new Piece(Color.R);
        player.assignPiece(p1);
        Assertions.assertFalse(player.checkPlayerPieceColorWhite(player));
    }

    @Test
    @DisplayName("check the Players Piece color red method with white piece")
    public void testCurrentPlayerPieceColorWhiteFalse(){
        Piece p1 = new Piece(Color.W);
        player.assignPiece(p1);
        Assertions.assertFalse(player.checkPlayerPieceColorRed(player));
    }

    @Test
    @DisplayName("check the Players Piece color white method with white piece")
    public void testCurrentPlayerPieceColorWhiteTrue(){
        Piece p1 = new Piece(Color.W);
        player.assignPiece(p1);
        Assertions.assertTrue(player.checkPlayerPieceColorWhite(player));
    }

    @Test
    @DisplayName("check the Players piece color Red method with empty piece ArrayList")
    public void testCurrentPlayerPieceColorRedEmptyArrayList(){
        try{
            player.checkPlayerPieceColorRed(player);
            Assertions.fail("IndexOutOfBoundsException should have been thrown");
        }catch (IndexOutOfBoundsException e){}
    }
    @Test
    @DisplayName("check the Players piece color White method with empty piece ArrayList")
    public void testCurrentPlayerPieceColorWhiteEmptyArrayList(){
        try{
            player.checkPlayerPieceColorWhite(player);
            Assertions.fail("IndexOutOfBoundsException should have been thrown");
        }catch (IndexOutOfBoundsException e){}
    }

}

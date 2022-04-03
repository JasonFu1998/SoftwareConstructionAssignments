package test.validation;

import com.board.Board;
import com.board.Field;
import com.figures.Color;
import com.figures.Piece;
import com.gameplay.Player;
import com.mapping.Position;
import com.validation.Validator;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

public class ValidatorTest {
    private Validator validator;
    Player player;
    Board board ;

    @BeforeEach
    public void Instatiation(){
        validator = new Validator();
        player = new Player();
        board = new Board(10,10);
    }
    @Test
    @DisplayName("Checks for queen transformation with pawn piece in the field")
    public void testCheckQueenTransformationPawn(){
        Field field = new Field(new Position(8,8));
        Piece piece = new Piece(Color.R);
        field.setPiece(piece);
        Assertions.assertFalse(validator.checkQueenTransformation(field));
    }
    @Test
    @DisplayName("Checks for queen transformation with King piece in the field")
    public void testCheckQueenTransformationKing(){
        Field field = new Field(new Position(8,8));
        Piece piece = new Piece(Color.R);
        piece.pawnToKing();
        field.setPiece(piece);
        Assertions.assertTrue(validator.checkQueenTransformation(field));
    }
    @Test
    @DisplayName("Checks for queen transformation with Queen piece in the field")
    public void testCheckQueenTransformationQueen(){
        Field field = new Field(new Position(8,8));
        Piece piece = new Piece(Color.R);
        piece.pawnToKing();
        piece.kingToQueen();
        field.setPiece(piece);
        Assertions.assertFalse(validator.checkQueenTransformation(field));
    }
    @Test
    @DisplayName("Checks for king transformation with red pawn piece in the field")
    public void testCheckKingTransformationPawnRed(){
        Field field = new Field(new Position(1,1));
        Piece piece = new Piece(Color.R);
        field.setPiece(piece);
        Assertions.assertTrue(validator.checkKingTransformation(field));
    }
    @Test
    @DisplayName("Checks for king transformation with white pawn piece in the field")
    public void testCheckKingTransformationPawnWhite(){
        Field field = new Field(new Position(8,2));
        Piece piece = new Piece(Color.W);
        field.setPiece(piece);
        Assertions.assertTrue(validator.checkKingTransformation(field));
    }
    @Test
    @DisplayName("Checks for king transformation with King piece in the field")
    public void testCheckKingTransformationKing(){
        Field field = new Field(new Position(1,1));
        Piece piece = new Piece(Color.R);
        piece.pawnToKing();
        field.setPiece(piece);
        Assertions.assertFalse(validator.checkKingTransformation(field));
    }
    @Test
    @DisplayName("Checks for king transformation with Queen piece in the field")
    public void testCheckKingTransformationQueen(){
        Field field = new Field(new Position(1,1));
        Piece piece = new Piece(Color.W);
        piece.pawnToKing();
        piece.kingToQueen();
        field.setPiece(piece);
        Assertions.assertFalse(validator.checkKingTransformation(field));
    }
    @Test
    @DisplayName("Check for Win without any pieces assigned to the player")
    public void testCheckForWinNoPiece() throws Exception {
        Assertions.assertTrue(validator.checkForWin(board,player));
    }
    @Test
    @DisplayName("Check for Win with a moveable piece assigned to the player")
    public void testCheckForWinMoveablePiece() throws Exception {
        Piece piece = new Piece(Color.R);
        Field[][] fields = board.getFields();
        fields[4][4].setPiece(piece);
        player.assignPiece(piece);
        Assertions.assertFalse(validator.checkForWin(board,player));
    }
    @Test
    @DisplayName("Check for Win with a not moveable piece assigned to the player")
    public void testCheckForWinNotMoveablePiece() throws Exception {
        Piece piece1 = new Piece(Color.R);
        Piece piece2 = new Piece(Color.W);
        Piece piece3 = new Piece(Color.W);
        Field[][] fields = board.getFields();
        fields[2][2].setPiece(piece1);
        fields[1][1].setPiece(piece2);
        fields[1][3].setPiece(piece3);
        player.assignPiece(piece1);
        Assertions.assertTrue(validator.checkForWin(board,player));
    }

    @Test
    @DisplayName("Calculate moveSpace with wrong color")
    public void testCalcMoveSpaceWrongColor() {
        Piece piece1 = new Piece(Color.R);
        Piece piece2 = new Piece(Color.W);
        Field[][] fields = board.getFields();
        fields[4][4].setPiece(piece1);
        player.assignPiece(piece2);
        Assertions.assertThrows(Exception.class, () -> {
            validator.calcMoveSpace(fields[4][4], board, player);
        });
    }

    @Test
    @DisplayName("Calculate an existing movespace Pawn")
    public void testCalcMoveSpacePawnExistent() throws Exception {
        Piece piece = new Piece(Color.R);
        Field[][] fields = board.getFields();
        fields[4][4].setPiece(piece);
        player.assignPiece(piece);
        ArrayList<Field> moveSpace = new ArrayList<>();
        moveSpace.add(fields[3][5]);
        moveSpace.add(fields[3][3]);
        ArrayList<Field> actualMoveSpace = validator.calcMoveSpace(fields[4][4], board, player);
        Assertions.assertEquals(moveSpace, actualMoveSpace);
    }
    @Test
    @DisplayName("Calculate a non existing movespace Pawn")
    public void testCalcMoveSpacePawnNonExistent() throws Exception {
        Piece piece1 = new Piece(Color.R);
        Piece piece2 = new Piece(Color.W);
        Piece piece3 = new Piece(Color.W);
        Field[][] fields = board.getFields();
        fields[4][4].setPiece(piece1);
        fields[3][5].setPiece(piece2);
        fields[3][3].setPiece(piece3);
        player.assignPiece(piece1);
        ArrayList<Field> moveSpace = new ArrayList<>();
        ArrayList<Field> actualMoveSpace = validator.calcMoveSpace(fields[4][4], board, player);
        Assertions.assertEquals(moveSpace, actualMoveSpace);
    }
    @Test
    @DisplayName("Calculate an existing movespace King")
    public void testCalcMoveSpaceKingExistent() throws Exception {
        Piece piece = new Piece(Color.R);
        piece.pawnToKing();
        Field[][] fields = board.getFields();
        fields[4][4].setPiece(piece);
        player.assignPiece(piece);
        ArrayList<Field> moveSpace = new ArrayList<>();
        moveSpace.add(fields[3][5]);
        moveSpace.add(fields[3][3]);
        moveSpace.add(fields[5][5]);
        moveSpace.add(fields[5][3]);
        ArrayList<Field> actualMoveSpace = validator.calcMoveSpace(fields[4][4], board, player);
        Assertions.assertEquals(moveSpace, actualMoveSpace);
    }
    @Test
    @DisplayName("Calculate a partial existing movespace King")
    public void testCalcMoveSpaceKingPartialExistent() throws Exception {
        Piece piece1 = new Piece(Color.R);
        piece1.pawnToKing();
        Piece piece2 = new Piece(Color.W);
        Piece piece3 = new Piece(Color.W);
        Field[][] fields = board.getFields();
        fields[4][4].setPiece(piece1);
        fields[3][5].setPiece(piece2);
        fields[3][3].setPiece(piece3);
        player.assignPiece(piece1);
        ArrayList<Field> moveSpace = new ArrayList<>();
        moveSpace.add(fields[5][5]);
        moveSpace.add(fields[5][3]);
            ArrayList<Field> actualMoveSpace = validator.calcMoveSpace(fields[4][4], board, player);
            Assertions.assertEquals(moveSpace, actualMoveSpace);
    }

    @Test
    @DisplayName("Calculate a non existing movespace King")
    public void testCalcMoveSpaceCalcKingNonExistent() throws Exception {
        Piece piece1 = new Piece(Color.R);
        piece1.pawnToKing();
        Piece piece2 = new Piece(Color.W);
        Piece piece3 = new Piece(Color.W);
        Piece piece4 = new Piece(Color.W);
        Piece piece5 = new Piece(Color.W);
        Field[][] fields = board.getFields();
        fields[4][4].setPiece(piece1);
        fields[3][5].setPiece(piece2);
        fields[3][3].setPiece(piece3);
        fields[5][5].setPiece(piece4);
        fields[5][3].setPiece(piece5);
        player.assignPiece(piece1);
        ArrayList<Field> moveSpace = new ArrayList<>();
        ArrayList<Field> actualMoveSpace = validator.calcMoveSpace(fields[4][4], board, player);
        Assertions.assertEquals(moveSpace, actualMoveSpace);
    }
    @Test
    @DisplayName("Calculate an existing movespace Queen")
    public void testCalcMoveSpaceQueenExistent() throws Exception {
        Piece piece = new Piece(Color.R);
        piece.pawnToKing();
        piece.kingToQueen();
        Field[][] fields = board.getFields();
        fields[4][4].setPiece(piece);
        player.assignPiece(piece);
        ArrayList<Field> moveSpace = new ArrayList<>();
        moveSpace.add(fields[3][3]);
        moveSpace.add(fields[3][4]);
        moveSpace.add(fields[3][5]);
        moveSpace.add(fields[4][3]);
        moveSpace.add(fields[4][5]);
        moveSpace.add(fields[5][3]);
        moveSpace.add(fields[5][4]);
        moveSpace.add(fields[5][5]);
        ArrayList<Field> actualMoveSpace = validator.calcMoveSpace(fields[4][4], board, player);
        Assertions.assertEquals(moveSpace, actualMoveSpace);
    }
    @Test
    @DisplayName("Calculate a partial existing movespace Queen")
    public void testCalcMoveSpaceQueenPartialExistent() throws Exception {
        Piece piece1 = new Piece(Color.R);
        piece1.pawnToKing();
        piece1.kingToQueen();
        Piece piece2 = new Piece(Color.W);
        Piece piece3 = new Piece(Color.W);
        Piece piece4 = new Piece(Color.W);
        Piece piece5 = new Piece(Color.W);
        Field[][] fields = board.getFields();
        fields[4][4].setPiece(piece1);
        fields[3][5].setPiece(piece2);
        fields[3][3].setPiece(piece3);
        fields[5][5].setPiece(piece4);
        fields[5][3].setPiece(piece5);
        player.assignPiece(piece1);
        ArrayList<Field> moveSpace = new ArrayList<>();
        moveSpace.add(fields[3][4]);
        moveSpace.add(fields[4][3]);
        moveSpace.add(fields[4][5]);
        moveSpace.add(fields[5][4]);
        ArrayList<Field> actualMoveSpace = validator.calcMoveSpace(fields[4][4], board, player);
        Assertions.assertEquals(moveSpace, actualMoveSpace);
    }

    @Test
    @DisplayName("Calculate a non existing movespace Queen")
    public void testCalcMoveSpaceCalcQueenNonExistent() throws Exception {
        Piece piece1 = new Piece(Color.R);
        piece1.pawnToKing();
        piece1.kingToQueen();
        Piece piece2 = new Piece(Color.W);
        Piece piece3 = new Piece(Color.W);
        Piece piece4 = new Piece(Color.W);
        Piece piece5 = new Piece(Color.W);
        Piece piece6 = new Piece(Color.W);
        Piece piece7 = new Piece(Color.W);
        Piece piece8 = new Piece(Color.W);
        Piece piece9 = new Piece(Color.W);
        Field[][] fields = board.getFields();
        fields[4][4].setPiece(piece1);
        fields[3][5].setPiece(piece2);
        fields[3][3].setPiece(piece3);
        fields[5][5].setPiece(piece4);
        fields[5][3].setPiece(piece5);
        fields[4][5].setPiece(piece6);
        fields[4][3].setPiece(piece7);
        fields[5][4].setPiece(piece8);
        fields[3][4].setPiece(piece9);
        player.assignPiece(piece1);
        ArrayList<Field> moveSpace = new ArrayList<>();
        ArrayList<Field> actualMoveSpace = validator.calcMoveSpace(fields[4][4], board, player);
        Assertions.assertEquals(moveSpace, actualMoveSpace);
    }
    @Test
    @DisplayName("Calculate No Movable Fields")
    public void testNoMovableFields() throws Exception {
        Piece piece1 = new Piece(Color.R);
        Piece piece2 = new Piece(Color.W);
        Piece piece3 = new Piece(Color.W);
        Field[][] fields = board.getFields();
        fields[4][4].setPiece(piece1);
        fields[3][5].setPiece(piece2);
        fields[3][3].setPiece(piece3);
        player.assignPiece(piece1);
        List<Field> ExpectedMovableFields = new ArrayList<>();
        List<Field> ActualMovalbeFields = validator.calcMovableFields(board,player);
        Assertions.assertEquals(ExpectedMovableFields,ActualMovalbeFields);
    }
    @Test
    @DisplayName("Calculate an existenting Movable Fields")
    public void testExistentMovableFields() throws Exception {
        Piece piece1 = new Piece(Color.R);
        Field[][] fields = board.getFields();
        fields[4][4].setPiece(piece1);
        player.assignPiece(piece1);
        List<Field> ExpectedMovableFields = new ArrayList<>();
        ExpectedMovableFields.add(fields[4][4]);
        List<Field> ActualMovalbeFields = validator.calcMovableFields(board,player);
        Assertions.assertEquals(ExpectedMovableFields,ActualMovalbeFields);
    }


    @Test
    @DisplayName("Calculate an existing jumpspace Pawn")
    public void testCalcJumpSpacePawnExistent()  {
        Piece piece = new Piece(Color.R);
        Piece piece2 = new Piece(Color.W);
        Piece piece3 = new Piece(Color.W);
        Field[][] fields = board.getFields();
        fields[4][4].setPiece(piece);
        fields[3][5].setPiece(piece2);
        fields[3][3].setPiece(piece3);
        player.assignPiece(piece);
        List<Field> jumpSpace = new ArrayList<>();
        jumpSpace.add(fields[2][6]);
        jumpSpace.add(fields[2][2]);
        List<Field> actualJumpSpace = validator.calcJumpSpace(fields[4][4], board);
        Assertions.assertEquals(jumpSpace, actualJumpSpace);
    }
    @Test
    @DisplayName("Calculate a non existing jumpspace Pawn")
    public void testCalcJumpSpacePawnNonExistent()  {
        Piece piece1 = new Piece(Color.R);
        Field[][] fields = board.getFields();
        fields[4][4].setPiece(piece1);
        player.assignPiece(piece1);
        List<Field> jumpSpace = new ArrayList<>();
        List<Field> actualJumpSpace = validator.calcJumpSpace(fields[4][4], board);
        Assertions.assertEquals(jumpSpace, actualJumpSpace);
    }
    @Test
    @DisplayName("Calculate an existing jumpspace King")
    public void testCalcJumpSpaceKingExistent()  {
        Piece piece1 = new Piece(Color.W);
        piece1.pawnToKing();
        Piece piece2 = new Piece(Color.R);
        Piece piece3 = new Piece(Color.R);
        Field[][] fields = board.getFields();
        fields[4][4].setPiece(piece1);
        fields[3][5].setPiece(piece2);
        fields[3][3].setPiece(piece3);
        fields[5][3].setPiece(piece2);
        fields[5][5].setPiece(piece3);
        player.assignPiece(piece1);
        List<Field> jumpSpace = new ArrayList<>();
        jumpSpace.add(fields[2][6]);
        jumpSpace.add(fields[2][2]);
        jumpSpace.add(fields[6][6]);
        jumpSpace.add(fields[6][2]);
        List<Field> actualMoveSpace = validator.calcJumpSpace(fields[4][4], board);
        Assertions.assertEquals(jumpSpace, actualMoveSpace);

    }
    @Test
    @DisplayName("Calculate a partial existing jumpspace King")
    public void testCalcJumpSpaceKingPartialExistent()  {
        Piece piece1 = new Piece(Color.W);
        piece1.pawnToKing();
        Piece piece2 = new Piece(Color.R);
        Piece piece3 = new Piece(Color.R);
        Field[][] fields = board.getFields();
        fields[4][4].setPiece(piece1);
        fields[3][5].setPiece(piece2);
        fields[3][3].setPiece(piece3);
        player.assignPiece(piece1);
        List<Field> jumpSpace = new ArrayList<>();
        jumpSpace.add(fields[2][6]);
        jumpSpace.add(fields[2][2]);
        List<Field> actualMoveSpace = validator.calcJumpSpace(fields[4][4], board);
        Assertions.assertEquals(jumpSpace, actualMoveSpace);
    }

    @Test
    @DisplayName("Calculate a non existing jumpspace King")
    public void testCalcJumpSpaceCalcKingNonExistent()  {
        Piece piece1 = new Piece(Color.R);
        piece1.pawnToKing();
        Piece piece2 = new Piece(Color.W);
        Field[][] fields = board.getFields();
        fields[4][4].setPiece(piece1);
        fields[3][5].setPiece(piece2);
        fields[3][3].setPiece(piece2);
        fields[2][6].setPiece(piece2);
        fields[2][2].setPiece(piece2);
        player.assignPiece(piece1);
        List<Field> jumpSpace = new ArrayList<>();
        List<Field> actualJumpSpace = validator.calcJumpSpace(fields[4][4], board);
        Assertions.assertEquals(jumpSpace, actualJumpSpace);
    }
    @Test
    @DisplayName("Calculate an existing jumpspace Queen")
    public void testCalcJumpSpaceQueenExistent() {
        Piece pieceR = new Piece(Color.R);
        pieceR.pawnToKing();
        pieceR.kingToQueen();
        Piece pieceW = new Piece(Color.W);
        Field[][] fields = board.getFields();
        fields[4][4].setPiece(pieceR);
        fields[3][5].setPiece(pieceW);
        fields[3][3].setPiece(pieceW);
        fields[5][5].setPiece(pieceW);
        fields[5][3].setPiece(pieceW);
        fields[4][5].setPiece(pieceW);
        fields[4][3].setPiece(pieceW);
        fields[5][4].setPiece(pieceW);
        fields[3][4].setPiece(pieceW);
        player.assignPiece(pieceR);
        List<Field> jumpSpace = new ArrayList<>();
        jumpSpace.add(fields[2][6]);
        jumpSpace.add(fields[2][2]);
        jumpSpace.add(fields[6][6]);
        jumpSpace.add(fields[6][2]);
        List<Field> actualJumpSpace = validator.calcJumpSpace(fields[4][4], board);
        Assertions.assertEquals(jumpSpace, actualJumpSpace);


    }
    @Test
    @DisplayName("Calculate a partial existing jumpspace Queen")
    public void testCalcJumpSpaceQueenPartialExistent()  {
        Piece piece1 = new Piece(Color.W);
        piece1.pawnToKing();
        piece1.kingToQueen();
        Piece piece2 = new Piece(Color.R);
        Piece piece3 = new Piece(Color.R);
        Field[][] fields = board.getFields();
        fields[4][4].setPiece(piece1);
        fields[3][5].setPiece(piece2);
        fields[3][3].setPiece(piece3);
        player.assignPiece(piece1);
        List<Field> jumpSpace = new ArrayList<>();
        jumpSpace.add(fields[2][6]);
        jumpSpace.add(fields[2][2]);
        List<Field> actualMoveSpace = validator.calcJumpSpace(fields[4][4], board);
        Assertions.assertEquals(jumpSpace, actualMoveSpace);
    }

    @Test
    @DisplayName("Calculate a non existing jumpspace Queen")
    public void testCalcJumpSpaceCalcQueenNonExistent()  {
        Piece piece1 = new Piece(Color.R);
        piece1.pawnToKing();
        piece1.kingToQueen();
        Field[][] fields = board.getFields();
        fields[4][4].setPiece(piece1);
        player.assignPiece(piece1);
        List<Field> jumpSpace = new ArrayList<>();
        List<Field> actualJumpSpace = validator.calcJumpSpace(fields[4][4], board);
        Assertions.assertEquals(jumpSpace, actualJumpSpace);
    }
    @Test
    @DisplayName("Calculate an existenting Jumpable Fields")
    public void testExistentJumpableFields()  {
        Piece piece1 = new Piece(Color.R);
        Piece piece2 = new Piece(Color.W);
        Piece piece3 = new Piece(Color.W);
        Field[][] fields = board.getFields();
        fields[4][4].setPiece(piece1);
        fields[3][5].setPiece(piece2);
        fields[3][3].setPiece(piece3);
        player.assignPiece(piece1);
        List<Field> ExpectedJumpableFields = new ArrayList<>();
        ExpectedJumpableFields.add(fields[4][4]);
        List<Field> ActualJumpalbeFields = validator.calcJumpableFields(board,player);
        Assertions.assertEquals(ExpectedJumpableFields, ActualJumpalbeFields);
    }
    @Test
    @DisplayName("Calculate a non-existent Jumpable Field")
    public void testNoJumpableFields()  {
        Piece piece1 = new Piece(Color.R);
        Field[][] fields = board.getFields();
        fields[4][4].setPiece(piece1);
        player.assignPiece(piece1);
        List<Field> ExpectedMovableFields = new ArrayList<>();
        List<Field> ActualJumpableFields = validator.calcJumpableFields(board,player);
        Assertions.assertEquals(ExpectedMovableFields,ActualJumpableFields);
    }



    @AfterEach
    public void tearDown() {
        board = null;
        validator = null;
        player = null;
    }
}

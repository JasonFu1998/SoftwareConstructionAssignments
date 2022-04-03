package test.gameplay;

import com.gameplay.Game;
import com.gameplay.Round;
import com.io.Input;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game game;
    Round round;

    @BeforeEach
    public void initialize() {
        round = new Round();
        game = Game.getInstance(round);
    }

    @Test
    public void testSingleton() {
        Game game2 = Game.getInstance(round);
        assertEquals(game, game2);
    }

    @Test
    public void testPlayerAgainYes() throws NoSuchFieldException, IllegalAccessException {
        Field field = Game.class.getDeclaredField("scanner");
        field.setAccessible(true);
        System.setIn(new ByteArrayInputStream("Y".getBytes(StandardCharsets.UTF_8)));
        field.set(game, new Input());
        assertTrue(game.playAgain());
    }

    @Test
    public void testPlayerAgainNo() throws NoSuchFieldException, IllegalAccessException {
        Field field = Game.class.getDeclaredField("scanner");
        field.setAccessible(true);
        System.setIn(new ByteArrayInputStream("test\nn".getBytes(StandardCharsets.UTF_8)));
        field.set(game, new Input());
        assertFalse(game.playAgain());
    }

    @Test
    public void testAskToPlaceANewBetOrStopTrue() throws NoSuchFieldException, IllegalAccessException {
        Field field = Game.class.getDeclaredField("scanner");
        field.setAccessible(true);
        System.setIn(new ByteArrayInputStream("R".getBytes(StandardCharsets.UTF_8)));
        field.set(game, new Input());
        assertTrue(game.askToPlaceANewBetOrStop());
    }

    @Test
    public void testAskToPlaceANewBetOrStopFalse() throws NoSuchFieldException, IllegalAccessException {
        Field field = Game.class.getDeclaredField("scanner");
        field.setAccessible(true);
        System.setIn(new ByteArrayInputStream("test\ns".getBytes(StandardCharsets.UTF_8)));
        field.set(game, new Input());
        assertFalse(game.askToPlaceANewBetOrStop());
    }

    @AfterEach
    public void resetSingleton() throws NoSuchFieldException, IllegalAccessException {
        Field instance = Game.class.getDeclaredField("INSTANCE");
        instance.setAccessible(true);
        instance.set(game, null);
    }

}
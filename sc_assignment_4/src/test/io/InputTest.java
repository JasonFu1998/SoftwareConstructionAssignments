package test.io;

import com.io.Input;
import exception.HitOrStandException;
import exception.ReviseOrStopException;
import exception.YesOrNoException;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class InputTest {
    Input input;

    @Test
    public void testAskPlayerToPlayAgainYes() {
        System.setIn(new ByteArrayInputStream("Y".getBytes(StandardCharsets.UTF_8)));
        input = new Input();
        try {
            assertEquals("Y", input.askPlayerToPlayAgain());
        } catch (YesOrNoException e) {
            fail();
        }
    }

    @Test
    public void testAskPlayerToPlayAgainNo() {
        System.setIn(new ByteArrayInputStream("N".getBytes(StandardCharsets.UTF_8)));
        input = new Input();
        try {
            assertEquals("N", input.askPlayerToPlayAgain());
        } catch (YesOrNoException e) {
            fail();
        }
    }

    @Test
    public void testAskPlayerToPlayAgainFail() {
        System.setIn(new ByteArrayInputStream("test".getBytes(StandardCharsets.UTF_8)));
        input = new Input();
        try {
            input.askPlayerToPlayAgain();
            fail("YesOrNoException expected");
        } catch (YesOrNoException e) {
            //all good this is expected
        }
    }

    @Test
    public void testAskPlayerHitOrStayHit() {
        System.setIn(new ByteArrayInputStream("H".getBytes(StandardCharsets.UTF_8)));
        input = new Input();
        try {
            assertEquals("H", input.askPlayerHitOrStay());
        } catch (HitOrStandException e) {
            fail();
        }
    }

    @Test
    public void testAskPlayerHitOrStayStay() {
        System.setIn(new ByteArrayInputStream("S".getBytes(StandardCharsets.UTF_8)));
        input = new Input();
        try {
            assertEquals("S", input.askPlayerHitOrStay());
        } catch (HitOrStandException e) {
            fail();
        }
    }

    @Test
    public void testAskPlayerHitOrStayFail() {
        System.setIn(new ByteArrayInputStream("test".getBytes(StandardCharsets.UTF_8)));
        input = new Input();
        try {
            input.askPlayerHitOrStay();
            fail("HitOrStandException expected");
        } catch (HitOrStandException e) {
            //all good this is expected
        }
    }

    @Test
    public void testAskPlayerBetAmount() {
        System.setIn(new ByteArrayInputStream("test".getBytes(StandardCharsets.UTF_8)));
        input = new Input();
        assertEquals("test", input.askPlayerBetAmount());
    }

    @Test
    public void testAskForDepositAmountValid() {
        System.setIn(new ByteArrayInputStream("10".getBytes(StandardCharsets.UTF_8)));
        input = new Input();
        assertEquals(10, input.askDepositAmount());
    }

    @Test
    public void testAskDifferentBetAmountTrue() {
        System.setIn(new ByteArrayInputStream("r".getBytes(StandardCharsets.UTF_8)));
        input = new Input();
        try {
            assertTrue(input.askForDifferentBetAmount());
        } catch (ReviseOrStopException e) {
            fail();
        }
    }

    @Test
    public void testAskDifferentBetAmountFalse() {
        System.setIn(new ByteArrayInputStream("s".getBytes(StandardCharsets.UTF_8)));
        input = new Input();
        try {
            assertFalse(input.askForDifferentBetAmount());
        } catch (ReviseOrStopException e) {
            fail();
        }
    }

    @Test
    public void testAskDifferentBetAmountFail() {
        System.setIn(new ByteArrayInputStream("test".getBytes(StandardCharsets.UTF_8)));
        input = new Input();
        try {
            input.askForDifferentBetAmount();
            fail("RevisitOrStopException expected");
        } catch (ReviseOrStopException e) {
            //all good this is expected
        }
    }

    @Test
    public void testAskForNewDepositNo() {
        System.setIn(new ByteArrayInputStream("N".getBytes(StandardCharsets.UTF_8)));
        input = new Input();
        assertEquals(0, input.askForNewDeposit());
    }

    @Test
    public void testAskForNewDepositYes() {
        System.setIn(new ByteArrayInputStream("Y\n500".getBytes(StandardCharsets.UTF_8)));
        input = new Input();
        assertEquals(500, input.askForNewDeposit());
    }

    @Test
    public void testAskDepositAmount() {
        System.setIn(new ByteArrayInputStream("test\n500".getBytes(StandardCharsets.UTF_8)));
        input = new Input();
        assertEquals(500, input.askDepositAmount());
    }

}
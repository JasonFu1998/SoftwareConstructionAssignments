package test.io;

import com.io.Input;
import com.mapping.Position;

import org.junit.jupiter.api.*;
import java.util.List;

public class InputTest {

    private Input input;

    @BeforeEach
    public void initializeInput(){
        input = new Input();
    }

    @Test
    @DisplayName("Test Exception with too little input")
    public void testTooLittleInputException() {
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            input.read("[b6]");
        });
        Assertions.assertEquals("Too less inputs!", exception.getMessage());
    }

    @Test
    @DisplayName("Test Exception with too little input and wrong grammar")
    public void testTooLittleInputExceptionWithWrongInput() {
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            input.read("Some weird input");
        });
        Assertions.assertEquals("Too less inputs!", exception.getMessage());
    }

    @Test
    @DisplayName("Test Exception with too much input")
    public void testInputLengthWithTooMuchInputException() {
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            input.read("[b6]x[a5]x[b4]");
        });
        Assertions.assertEquals("Too many inputs!", exception.getMessage());
    }

    @Test
    @DisplayName("Test Exception with too little valid input but still correct grammar")
    public void testValidInput() {
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            input.read("[b12]x[a23]");
        });
        Assertions.assertEquals("Too less valid inputs!", exception.getMessage());
    }

    @Test
    @DisplayName("Test if valid input is added correctly to ArrayList")
    public void testAddInputToList() {
        try {
            List<Position> validInputs;
            validInputs = input.read("[b6]x[a5]");
            Assertions.assertEquals(6, validInputs.get(0).getRow());
            Assertions.assertEquals(2, validInputs.get(0).getColumn());
            Assertions.assertEquals(5, validInputs.get(1).getRow());
            Assertions.assertEquals(1, validInputs.get(1).getColumn());
        }
        catch (Exception e){
            Assertions.assertNotNull(e.getMessage());
        }
    }

    @AfterEach
    public void tearDown() {
        input = null;
    }
}

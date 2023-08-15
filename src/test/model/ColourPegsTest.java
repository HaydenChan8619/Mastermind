package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/*
This class is the test class for ColourPegs
 */

public class ColourPegsTest {

    ColourPegs emptyPeg;

    @BeforeEach
    void runBefore()
    {
        emptyPeg = new ColourPegs();
    }

    @Test
    void testConstructor()
    {
        assertEquals("empty",emptyPeg.getColour());
    }

    @Test
    void testCheckInputValidity() {
        assertTrue(emptyPeg.checkInputValidity("red"));
        assertFalse(emptyPeg.checkInputValidity("Hayden"));
    }

    @Test
    void testSetColour() {
        emptyPeg.setColour("red");
        assertEquals("red",emptyPeg.getColour());

        emptyPeg.setColour("blue");
        emptyPeg.setColour("yellow");
        assertEquals("yellow", emptyPeg.getColour());
    }

    @Test
    void testGetAcceptedColours() {
        ArrayList<String> acceptedColours =
                new ArrayList<>(Arrays.asList("red","blue","green","yellow","brown","orange","black","white"));
        assertEquals(acceptedColours,emptyPeg.getAcceptedColours());
    }

}
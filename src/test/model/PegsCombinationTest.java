package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Mastermind;

import static org.junit.jupiter.api.Assertions.*;

/*
This class is the test class for PegsCombination
 */
// TODO: make test for combinePegsPins

public class PegsCombinationTest {
    PegsCombination pc;
    ColourPegs red;
    ColourPegs blue;
    ColourPegs yellow;
    ColourPegs black;
    ColourPegs empty;
    PegsCombination pins;

    @BeforeEach
    void runBefore() {
        pc = new PegsCombination();
        red = new ColourPegs();
        red.setColour("red");
        blue = new ColourPegs();
        blue.setColour("blue");
        yellow = new ColourPegs();
        yellow.setColour("yellow");
        black = new ColourPegs();
        black.setColour("black");
        empty = new ColourPegs();
        pins = new PegsCombination();
    }

    @Test
    void testAddPegs() {
        pc.addPegs(red);
        assertEquals("red",pc.getPosition(0).getColour());

        pc.addPegs(blue);
        assertEquals("blue",pc.getPosition(1).getColour());
        assertEquals("red",pc.getPosition(0).getColour());

        pc.addPegs(blue);
        pc.addPegs(blue);
        pc.addPegs(blue);
        assertEquals(5,pc.getSize());
    }

    @Test
    void testGenerateAnswerKey() {
        pc.generateAnswerKey(Mastermind.getMaxSize());

        for (ColourPegs c : pc.getAnswerKey()) {
            assertTrue(pc.getAnswerKey().contains(c));
        }

    }

    @Test
    void testClearPegs() {
        pc.addPegs(blue);
        assertEquals(1, pc.getSize());

        pc.clearPegs();
        assertEquals(0,pc.getSize());
    }

    @Test
    void testCheckWinWin() {
        pc.addPegs(black);
        pc.addPegs(black);
        pc.addPegs(black);
        pc.addPegs(black);

        assertTrue(pc.checkWin(pc));
    }

    @Test
    void testCheckWinLose() {
        pc.addPegs(black);
        pc.addPegs(black);
        pc.addPegs(black);
        pc.addPegs(empty);

        assertFalse(pc.checkWin(pc));
    }

    @Test
    void testCombinePegsAndPins() {
        pc.addPegs(red);
        pc.addPegs(blue);
        pins.addPegs(black);
        pins.addPegs(empty);
        PegsCombination combine = new PegsCombination();
        combine.combinePegsAndPins(pc,pins);

        assertEquals(4,combine.getSize());
    }

    @Test
    void testUndo() {
        pc.addPegs(red);
        pc.addPegs(blue);
        assertEquals(2,pc.getSize());
        pc.undo();
        assertEquals(1,pc.getSize());
        assertEquals(red,pc.getCombination().get(0));
    }
}

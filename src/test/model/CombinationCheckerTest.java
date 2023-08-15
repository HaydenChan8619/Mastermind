package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
This class is the test class for CombinationChecker
 */

public class CombinationCheckerTest {

    CombinationChecker test;

    ColourPegs redPeg;
    ColourPegs bluePeg;
    ColourPegs greenPeg;
    ColourPegs yellowPeg;
    ColourPegs brownPeg;
    ColourPegs orangePeg;
    ColourPegs blackPeg;
    ColourPegs whitePeg;
    ColourPegs emptyPeg;
    ColourPegs emptyPeg2;
    ColourPegs greenPeg2;

    PegsCombination redBlueGreenYellow;
    PegsCombination testAnswer;
    PegsCombination allBlackPins;
    PegsCombination blueGreenYellowRed;
    PegsCombination redRedYellowBlack;

    @BeforeEach
    void runBefore() {
        test = new CombinationChecker();

        redPeg = new ColourPegs();
        bluePeg = new ColourPegs();
        greenPeg = new ColourPegs();
        yellowPeg = new ColourPegs();
        brownPeg = new ColourPegs();
        orangePeg = new ColourPegs();
        blackPeg = new ColourPegs();
        whitePeg = new ColourPegs();
        emptyPeg = new ColourPegs();
        emptyPeg2 = new ColourPegs();
        greenPeg2 = new ColourPegs();

        redPeg.setColour("red");
        bluePeg.setColour("blue");
        greenPeg.setColour("green");
        yellowPeg.setColour("yellow");
        brownPeg.setColour("brown");
        orangePeg.setColour("orange");
        blackPeg.setColour("black");
        whitePeg.setColour("white");
        greenPeg2.setColour("green");

        redBlueGreenYellow = new PegsCombination();
        redBlueGreenYellow.addPegs(redPeg);
        redBlueGreenYellow.addPegs(bluePeg);
        redBlueGreenYellow.addPegs(greenPeg);
        redBlueGreenYellow.addPegs(yellowPeg);

        blueGreenYellowRed = new PegsCombination();
        blueGreenYellowRed.addPegs(bluePeg);
        blueGreenYellowRed.addPegs(greenPeg);
        blueGreenYellowRed.addPegs(yellowPeg);
        blueGreenYellowRed.addPegs(redPeg);

        redRedYellowBlack = new PegsCombination();
        redRedYellowBlack.addPegs(redPeg);
        redRedYellowBlack.addPegs(redPeg);
        redRedYellowBlack.addPegs(yellowPeg);
        redRedYellowBlack.addPegs(blackPeg);


        allBlackPins = new PegsCombination();
        allBlackPins.addPegs(blackPeg);
        allBlackPins.addPegs(blackPeg);
        allBlackPins.addPegs(blackPeg);
        allBlackPins.addPegs(blackPeg);

        testAnswer = new PegsCombination();

    }

    @Test
    void testColourChecker() {
        assertFalse(test.colourChecker(redPeg,bluePeg));
        assertTrue(test.colourChecker(blackPeg,blackPeg));
        assertFalse(test.colourChecker(bluePeg,redPeg));

        assertFalse(test.colourChecker(greenPeg,blackPeg));
        assertFalse(test.colourChecker(blackPeg,whitePeg));

        assertTrue(test.colourChecker(emptyPeg,emptyPeg2));
        assertTrue(test.colourChecker(greenPeg,greenPeg2));
    }

    @Test
    void testGeneratePinsAllBlack() {
        redBlueGreenYellow.setAnswerKey(redBlueGreenYellow.getCombination());

        test.generatePins(redBlueGreenYellow,redBlueGreenYellow);

        assertEquals("black",test.getPins().getPosition(0).getColour());
        assertEquals("black",test.getPins().getPosition(1).getColour());
        assertEquals("black",test.getPins().getPosition(2).getColour());
        assertEquals("black",test.getPins().getPosition(3).getColour());
    }

    @Test
    void testGeneratePinsAllWhite() {
        redBlueGreenYellow.setAnswerKey(blueGreenYellowRed.getCombination());

        test.generatePins(redBlueGreenYellow,redBlueGreenYellow);

        assertEquals("white",test.getPins().getPosition(0).getColour());
        assertEquals("white",test.getPins().getPosition(1).getColour());
        assertEquals("white",test.getPins().getPosition(2).getColour());
        assertEquals("white",test.getPins().getPosition(3).getColour());
    }

    @Test
    void testGeneratePinsAllEmpty() {
        redBlueGreenYellow.setAnswerKey(allBlackPins.getCombination());

        test.generatePins(redBlueGreenYellow,redBlueGreenYellow);

        assertEquals("empty",test.getPins().getPosition(0).getColour());
        assertEquals("empty",test.getPins().getPosition(1).getColour());
        assertEquals("empty",test.getPins().getPosition(2).getColour());
        assertEquals("empty",test.getPins().getPosition(3).getColour());
    }

    @Test
    void testGeneratePinsGeneral() {
        redBlueGreenYellow.setAnswerKey(redRedYellowBlack.getCombination());

        test.generatePins(redBlueGreenYellow,redBlueGreenYellow);

        assertEquals("black",test.getPins().getPosition(0).getColour());
        assertEquals("white",test.getPins().getPosition(1).getColour());
        assertEquals("empty",test.getPins().getPosition(2).getColour());
        assertEquals("empty",test.getPins().getPosition(3).getColour());
    }


    @Test
    void testGeneratePinsInputDuplicate() {
        redRedYellowBlack.setAnswerKey(blueGreenYellowRed.getCombination());

        test.generatePins(redRedYellowBlack,redRedYellowBlack);

        assertEquals("black",test.getPins().getPosition(0).getColour());
        assertEquals("white",test.getPins().getPosition(1).getColour());
        assertEquals("empty",test.getPins().getPosition(2).getColour());
        assertEquals("empty",test.getPins().getPosition(3).getColour());
    }


}
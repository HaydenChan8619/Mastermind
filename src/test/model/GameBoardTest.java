package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameBoardTest {

    private ColourPegs whitePeg = new ColourPegs();
    private ColourPegs blackPeg = new ColourPegs();
    private ColourPegs blocker = new ColourPegs();
    private GameBoard gameBoard = new GameBoard();
    PegsCombination currentCombination = new PegsCombination();

    @BeforeEach
    void runBefore() {
        whitePeg.setColour("white");
        blackPeg.setColour("black");
        blocker.setColour("|");
    }

    @Test
    void testAddPegsPinsToBoard() {
        buildCurrentCombination();
        gameBoard.addPegsPinsToBoard(currentCombination);
        assertEquals(1,gameBoard.getBoard().size());
    }

    private void buildCurrentCombination() {
        ColourPegs emptyPeg = new ColourPegs();
        currentCombination.addPegs(whitePeg);
        currentCombination.addPegs(blackPeg);
        currentCombination.addPegs(blocker);
        currentCombination.addPegs(emptyPeg);
        currentCombination.addPegs(emptyPeg);

    }
}

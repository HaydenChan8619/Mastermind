package persistence;

import model.ColourPegs;
import model.GameBoard;
import model.PegsCombination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {

    private int maxsize;
    private int attemptNumber;
    private PegsCombination answerKeyCombination;
    private PegsCombination current;
    private GameBoard board;
    private ColourPegs whitePeg = new ColourPegs();
    private ColourPegs blackPeg = new ColourPegs();
    private ColourPegs blocker = new ColourPegs();
    private JsonWriter writer;
    private JsonReader reader;

    @BeforeEach
    void runBefore() {
        maxsize = 0;
        attemptNumber = 0;
        answerKeyCombination = new PegsCombination();
        current = new PegsCombination();
        board = new GameBoard();
        whitePeg.setColour("white");
        blackPeg.setColour("black");
        blocker.setColour("|");
        writer = new JsonWriter("./data/mastermind.json");
        reader = new JsonReader("./data/mastermind.json");
    }

    @Test
    void testWriteInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("Should have IO Exception!");
        } catch (IOException e) {
            //Pass
        }
    }

    @Test
    void testWriteFile() {
        try {
            writer.open();
            maxsize = 2;
            attemptNumber = 3;
            buildAnswerKey();
            buildGameBoard();
            buildCurrentCombination();
            writer.write(maxsize,attemptNumber,answerKeyCombination,current,board);
            writer.close();

            reader.read();
            int newMaxSize = reader.readMaxSize();
            int newAttemptNumber = reader.readAttemptNumber();
            PegsCombination newAnswerKeyCombination = reader.readAnswerKey();
            PegsCombination newCurrent = reader.readCurrentCombination();
            GameBoard newBoard = reader.readGameBoard();

            assertEquals(maxsize,newMaxSize);
            assertEquals(attemptNumber,newAttemptNumber);
            assertEquals(answerKeyCombination.getAnswerKey().get(0).getColour(),
                         newAnswerKeyCombination.getAnswerKey().get(0).getColour());
            assertEquals(2,newCurrent.getCombination().size());
            assertEquals(1,newBoard.getBoard().size());

        } catch (FileNotFoundException e) {
            fail("File not found");
        } catch (IOException e) {
            fail("IO Exception");
        }
    }

    private void buildAnswerKey() {
        ArrayList<ColourPegs> pegs = new ArrayList<>();
        pegs.add(whitePeg);
        pegs.add(blackPeg);
        answerKeyCombination.setAnswerKey(pegs);
    }

    private void buildGameBoard() {
        PegsCombination currentCombination = new PegsCombination();
        ColourPegs emptyPeg = new ColourPegs();
        currentCombination.addPegs(whitePeg);
        currentCombination.addPegs(blackPeg);
        currentCombination.addPegs(blocker);
        currentCombination.addPegs(emptyPeg);
        currentCombination.addPegs(emptyPeg);
        board.addPegsPinsToBoard(currentCombination);
    }

    private void buildCurrentCombination() {
        current.addPegs(blackPeg);
        current.addPegs(whitePeg);
    }

}

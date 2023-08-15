package ui;

import model.ColourPegs;
import model.GameBoard;
import model.PegsCombination;
import model.CombinationChecker;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

/*
This class is home to the actual game method.
The masterMindGame method operates the game and loops until the game ends
 */

public class Mastermind {

    private static int MAXSIZE = 4;
    private static final int MAXROUNDS = 10;
    int attemptNumber;
    private UserPrompt tester = new UserPrompt();
    private CombinationChecker checker = new CombinationChecker();

    private PegsCombination currentCombination;
    private static PegsCombination answerKeyCombination = new PegsCombination();
    private PegsCombination currentPins;
    private PegsCombination pegsPinsCombination;
    private GameBoard gameBoard = new GameBoard();

    // These fields are modelled on the JsonSerializationDemo example on EDX
    private static final String JSON_STORE = "./data/mastermind.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: Initialize Mastermind with nothing
    public Mastermind() throws FileNotFoundException {
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: Operates the game: asks for 4 pegs per round, and provide feedback.
    //          Loops until MAXROUNDS is reached or all pins are black
    public void masterMindGame() {
        startGame();
        while (attemptNumber < MAXROUNDS) {
            checkSaveOrLoad(tester.saveOrLoadPrompt());
            currentCombination = new PegsCombination();
            pegsPinsCombination = new PegsCombination();
            currentPins = new PegsCombination();
            System.out.println();
            currentCombination = tester.askForPegs();
            System.out.println();
            currentPins = checker.generatePins(currentCombination, answerKeyCombination);
            System.out.println();
            pegsPinsCombination.combinePegsAndPins(currentCombination,currentPins);
            gameBoard.addPegsPinsToBoard(pegsPinsCombination);
            printBoard();
            if (currentPins.checkWin(currentPins)) {
                System.out.println();
                System.out.println("YOU WIN!");
                System.exit(0);
            }
            System.out.println();
            attemptNumber++;
        }
        printLoseMessage();
    }

    public static int getMaxSize() {
        return MAXSIZE;
    }

    public static PegsCombination getAnswerKeyCombination() {
        return answerKeyCombination;
    }

    // EFFECTS: Welcomes user, asks for the maxsize for the round, generates answer key, and set attempt to 0
    private void startGame() {
        System.out.println("Welcome to the Mastermind Game!");
        MAXSIZE = tester.askForMaxSize();
        answerKeyCombination.generateAnswerKey(MAXSIZE);
        attemptNumber = 0;
    }

    // EFFECTS: print out the current list of pins reflecting the accuracy of the guess
    private void printPins() {
        for (ColourPegs p : currentPins.getCombination()) {
            System.out.print(p.getColour() + " ");
        }
        System.out.println();
    }

    // EFFECTS: print out the answer key
    private void printAns() {
        for (int j = 0; j < answerKeyCombination.getAnswerKey().size(); j++) {
            System.out.print(answerKeyCombination.getAnswerKey().get(j).getColour() + " ");
        }
    }

    // EFFECTS: prints the pegs and pins on one line
    private void printPegsPinsCombination(PegsCombination ppc) {
        for (ColourPegs p : ppc.getCombination()) {
            System.out.print(p.getColour() + " ");
        }
        System.out.println();
    }

    // EFFECTS: prints the board onto the console
    private void printBoard() {
        for (PegsCombination pc : gameBoard.getBoard()) {
            printPegsPinsCombination(pc);
        }
    }


    // EFFECTS: print out the loss message
    private void printLoseMessage() {
        System.out.println("YOU RAN OUT OF ATTEMPTS!");
        System.out.println("YOU LOSE!");
    }

    // EFFECTS: check if game should save, load, or continue
    private void checkSaveOrLoad(Integer i) {
        if (i == 1) {
            saveMastermind();
        } else if (i == 2) {
            loadMastermind();
        }
    }

    // MODIFIES: JSON_STORE
    // EFFECTS: saves game to JSON_STORE
    private void saveMastermind() {
        try {
            jsonWriter.open();
            jsonWriter.write(MAXSIZE, attemptNumber,answerKeyCombination,currentCombination,gameBoard);
            jsonWriter.close();
            System.out.println("Save Complete!");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save!");
        }
    }

    // MODIFIES: this
    // EFFECTS: Load game from JSON_STORE
    private void loadMastermind() {
        try {
            jsonReader.read();
            MAXSIZE = jsonReader.readMaxSize();
            attemptNumber = jsonReader.readAttemptNumber();
            answerKeyCombination = jsonReader.readAnswerKey();
            gameBoard = jsonReader.readGameBoard();
            System.out.println("Game Loaded!");
        } catch (IOException e) {
            System.out.println("Unable to load file!");
        }
    }



}

package ui;

import model.ColourPegs;
import model.PegsCombination;

import java.util.Scanner;  // Scanner Class used to get user input

/*
This class is used to prompt the user for strings to fill the combination for guessing.
The strings are converted accordingly into ColourPegs, then pushed into the combination field of the currentCombination.
 */

public class UserPrompt {

    private String colour;
    private PegsCombination currentCombination;
    private ColourPegs currentPeg;
    Scanner input;  // Create a Scanner object

    // EFFECTS: initialize UserPrompt with input initialized
    public UserPrompt() {
        input = new Scanner(System.in);
    }

    // EFFECTS: ask user for how many pegs in one round
    public int askForMaxSize() {
        System.out.println("What would you like the size of the combination to be?");
        int num = input.nextInt();
        return num;
    }

    // EFFECTS: Ask for inputs to fill the currentCombination
    public PegsCombination askForPegs() {
        currentCombination = new PegsCombination();
        while (currentCombination.getSize() < Mastermind.getMaxSize()) {
            askForPeg(currentCombination.getSize() + 1);
        }
        return currentCombination;
    }

    // MODIFIES: this, PegsCombination
    // EFFECTS: asks for an input for one Peg, and provides feedback for input
    private void askForPeg(int i) {
        currentPeg = new ColourPegs();
        System.out.println("Input your guess for position " + i);
        System.out.println("Acceptable inputs include:");
        System.out.println(currentPeg.getAcceptedColours());
        colour = input.nextLine();
        if (currentPeg.checkInputValidity(colour)) {
            System.out.println("You inputted " + colour);
            System.out.println();
            currentPeg.setColour(colour);
            currentCombination.addPegs(currentPeg);
        } else {
            System.out.println("Your input is not valid, try again!");
            System.out.println();
        }
    }

    // EFFECTS: Prompts user to save or load game
    public int saveOrLoadPrompt() {
        System.out.println("Would you like to save/load or continue?");
        System.out.println("Type 's' for save, 'l' for load, anything else to continue!'");

        String choice;
        choice = input.nextLine();

        if (choice.equals("s")) {
            return 1;
        } else if (choice.equals("l")) {
            return 2;
        }

        return 0;
    }

}

package model;

import java.util.ArrayList;

/*
This class contains the methods used for comparing the input PegsCombination and the answerKey.

The result of the comparing is represented by a PegsCombination named pins.
 */

public class CombinationChecker {

    private PegsCombination pins;
    private ColourPegs blackPeg = new ColourPegs();
    private ColourPegs whitePeg = new ColourPegs();
    private ColourPegs emptyPeg = new ColourPegs();
    private int boardSize;
    private ArrayList<Integer> duplicateJList;
    private ArrayList<Integer> duplicateIList;

    // EFFECTS: initialize CombinationChecker with nothing
    public CombinationChecker() {
    }

    // MODIFIES: this
    // EFFECTS: generate pins by using generateBlackPeg and generateWhitePeg
    public PegsCombination generatePins(PegsCombination current, PegsCombination answer) {
        blackPeg.setColour("black");
        whitePeg.setColour("white");
        boardSize = answer.getAnswerKey().size();
        pins = new PegsCombination();
        duplicateIList = new ArrayList<Integer>();
        duplicateJList = new ArrayList<Integer>();

        generateBlackPeg(current, answer);
        generateWhitePeg(current, answer);
        EventLog.getInstance().logEvent(new Event("The pins have been generated"));
        return pins;
    }


    // EFFECTS: return true if the current peg and the answer peg are the same colour
    public boolean colourChecker(ColourPegs current, ColourPegs answerKey) {
        return current.getColour().equals(answerKey.getColour());
    }

    // MODIFIES: this
    // EFFECTS: Generate white pegs by comparing the current and answer combination, and add white peg to pins
    //          if there are same colour pegs that haven't been accounted for in generateBlackPeg;
    //          Generate empty pegs to fill up pins if it isn't filled at the end of checking
    private void generateWhitePeg(PegsCombination current, PegsCombination answer) {
        if (pins.getSize() != boardSize) {
            for (int i = 0; i < current.getSize(); i++) {
                for (int j = 0; j < answer.getAnswerKey().size(); j++) {
                    if ((colourChecker(current.getPosition(i), answer.getAnswerKey().get(j)))
                            && !duplicateJList.contains(j) && !duplicateIList.contains(i)) {
                        duplicateJList.add(j);
                        duplicateIList.add(i);
                        pins.addPegs(whitePeg);
                        if (pins.getSize() == boardSize) {
                            break;
                        }
                    }
                }
            }
            while (pins.getSize() != boardSize) {
                pins.getCombination().add(emptyPeg);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Generate black pegs by comparing the current and answer combination, and add black pegs to pins if
    //          the colour and location both matches.
    private void generateBlackPeg(PegsCombination current, PegsCombination answer) {
        for (int i = 0; i < current.getSize(); i++) {
            if (colourChecker(current.getPosition(i), answer.getAnswerKey().get(i))) {
                duplicateIList.add(i);
                duplicateJList.add(i);
                pins.addPegs(blackPeg);
            }
        }
    }

    public PegsCombination getPins() {
        return pins;
    }

}
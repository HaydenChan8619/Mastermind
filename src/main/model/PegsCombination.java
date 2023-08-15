package model;

import java.util.ArrayList;
import ui.Mastermind;

import java.util.Arrays;
import java.util.Random;

/*
This class represents a combination of ColourPegs, stored in an ArrayList named combination.
It also contains the answerKey of the game, which is used to compare to the combination in the checkWin method.
 */

public class PegsCombination {

    private ArrayList<ColourPegs> combination;
    private ArrayList<ColourPegs> answerKey = new ArrayList<>();
    private boolean isPegListSetUp;

    private int winCounter = 0;

    private ArrayList<ColourPegs> pegList;
    private ColourPegs redPeg;
    private ColourPegs bluePeg;
    private ColourPegs greenPeg;
    private ColourPegs yellowPeg;
    private ColourPegs brownPeg;
    private ColourPegs orangePeg;
    private ColourPegs blackPeg;
    private ColourPegs whitePeg;
    private ColourPegs emptyPeg;

    // EFFECTS: initialize PegsCombination with an empty combination and setting blocker's colour
    public PegsCombination() {
        combination = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: fills up pegList with the appropriate pegs
    public void setUpPegList() {

        redPeg = new ColourPegs();
        bluePeg = new ColourPegs();
        greenPeg = new ColourPegs();
        yellowPeg = new ColourPegs();
        brownPeg = new ColourPegs();
        orangePeg = new ColourPegs();
        blackPeg = new ColourPegs();
        whitePeg = new ColourPegs();
        emptyPeg = new ColourPegs();

        redPeg.setColour("red");
        bluePeg.setColour("blue");
        greenPeg.setColour("green");
        yellowPeg.setColour("yellow");
        brownPeg.setColour("brown");
        orangePeg.setColour("orange");
        blackPeg.setColour("black");
        whitePeg.setColour("white");

        pegList = new ArrayList<>(Arrays.asList(
                redPeg,bluePeg,greenPeg,yellowPeg,brownPeg,orangePeg,blackPeg,whitePeg,emptyPeg));
        isPegListSetUp = true;
    }

    // REQUIRES: combination.getSize() < MAXSIZE
    // MODIFIES: this
    // EFFECTS: Adds peg into combination ArrayList if size is smaller than maxsize
    public void addPegs(ColourPegs peg) {
        combination.add(peg);
        EventLog.getInstance().logEvent(new
                Event("A " + peg.getColour() + " peg has been added to the combination"));

    }

    // MODIFIES: this
    // EFFECTS: Remove all pegs from combination ArrayList
    public void clearPegs() {
        combination.clear();
        EventLog.getInstance().logEvent(new Event("All the pegs have been removed from the combination"));
    }

    // MODIFIES: this
    // EFFECTS: generate a 4 peg combination as the answer key, and returns it
    public void generateAnswerKey(Integer max) {
        Random chooser = new Random();
        answerKey = new ArrayList<>();
        while (answerKey.size() < max) {
            answerKey.add(Mastermind.getAnswerKeyCombination().getPegList().get(chooser.nextInt(8)));
        }
        EventLog.getInstance().logEvent(new Event("The Answer Key has been generated"));
    }

    // EFFECTS: return true if all pins are black
    public boolean checkWin(PegsCombination pins) {
        for (int i = 0; i < pins.getCombination().size(); i++) {
            if (pins.getPosition(i).getColour().equals("black")) {
                winCounter += 1;
            }
        }

        if (winCounter == pins.getCombination().size()) {
            winCounter = 0;
            return true;
        } else {
            winCounter = 0;
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: Combine combination guess and pins into one PegsCombination
    public void combinePegsAndPins(PegsCombination current, PegsCombination pins) {

        for (ColourPegs p : current.getCombination()) {
            combination.add(p);
        }

        for (ColourPegs p : pins.getCombination()) {
            combination.add(p);
        }

        EventLog.getInstance().logEvent(new Event("The Pegs and Pins have been combined"));
    }

    public ColourPegs getPosition(int num) {
        return combination.get(num);
    }

    public int getSize() {
        return combination.size();
    }

    public ArrayList<ColourPegs> getAnswerKey() {
        return answerKey;
    }

    // EFFECT: ensure pegList is filled up, and returns pegList
    public ArrayList<ColourPegs> getPegList() {
        if (isPegListSetUp == false) {
            setUpPegList();
        }
        return pegList;
    }

    public ArrayList<ColourPegs> getCombination() {
        return combination;
    }


    // MODIFIES: this
    // EFFECTS: sets the answerKey to c
    public void setAnswerKey(ArrayList<ColourPegs> c) {
        answerKey = c;
    }

    // MODIFIES: this
    // EFFECTS: removes the ColourPeg at the end of the combination ArrayList
    public void undo() {
        ColourPegs removed = combination.remove(combination.size() - 1);
        EventLog.getInstance().logEvent(new Event("A " + removed.getColour()
                + " peg has been removed from the combination"));
    }
}


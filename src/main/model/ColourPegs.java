package model;

import java.util.ArrayList;
import java.util.Arrays;

/*
This class represents each of the Pegs in the mastermind game. It has two fields:
ArrayList<String> acceptedColours: the list of accepted colours in the game
String colour: the colour of the Peg

When initialized, a ColourPegs have "empty" colour.

The methods available include checkInputValidity, setColour, getColour, and getAcceptedColours

 */
public class ColourPegs {

    private ArrayList<String> acceptedColours =
            new ArrayList<>(Arrays.asList("red","blue","green","yellow","brown","orange","black","white"));
    String colour;

    // MODIFIES: this
    // EFFECTS: Initialize ColourPegs with colour "empty"
    public ColourPegs() {
        this.colour = "empty";
    }

    // EFFECTS: return true if input is one of the accepted colours
    public boolean checkInputValidity(String input) {
        return acceptedColours.contains(input);
    }

    // REQUIRES: String is one-of:"red","blue","green","yellow","brown","orange","black","white"
    // MODIFIES: this
    // EFFECTS: set peg colour to parameter colour
    public void setColour(String colour) {
        this.colour = colour;
        //EventLog.getInstance().logEvent(new Event("The peg's colour has been set to " + colour));
    }

    public String getColour() {
        return this.colour;
    }

    public ArrayList<String> getAcceptedColours() {
        return acceptedColours;
    }
}

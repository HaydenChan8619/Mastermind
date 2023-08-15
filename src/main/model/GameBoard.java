package model;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {

    private List<PegsCombination> board;

    // EFFECTS: initialize GameBoard with board as new ArrayList
    public GameBoard() {
        board = new ArrayList();
    }

    // MODIFIES: this
    // EFFECTS: add pegsPinsCombination to board
    public void addPegsPinsToBoard(PegsCombination p) {
        board.add(p);
        EventLog.getInstance().logEvent(new Event("The pegs and pins have been added to the gameBoard"));
    }

    public List<PegsCombination> getBoard() {
        return board;
    }



}

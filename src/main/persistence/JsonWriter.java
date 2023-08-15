package persistence;


import model.ColourPegs;
import model.GameBoard;
import model.PegsCombination;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// This class represents a writer that writes JSON representation of mastermind to file
// Modelled on the JsonSerializationDemo example on EDX
public class JsonWriter {

    private PrintWriter writer;
    private String destination;

    // EFFECTS: initialize JsonWriter with destination set as argument string
    public JsonWriter(String s) {
        destination = s;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throw FNFE if it can't open file
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of MAXSIZE and attemptsNumber to file
    public void write(Integer maxSize, Integer attempts, PegsCombination answer,
                      PegsCombination current, GameBoard board) {
        JSONObject json = new JSONObject();
        json.put("max size", maxSize);
        json.put("attempts", attempts);
        json.put("answer key",answerExtraction(answer));
        json.put("current combination",currentCombinationExtraction(current));
        json.put("game board",boardExtraction(board));
        saveToFile(json.toString());
    }

    // EFFECTS: returns a JSONArray with the colours of the ColourPegs inside the currentCombination
    private JSONArray currentCombinationExtraction(PegsCombination pc) {
        JSONArray jsonArray = new JSONArray();
        for (ColourPegs p: pc.getCombination()) {
            jsonArray.put(p.getColour());
        }
        return jsonArray;
    }

    // EFFECTS: returns a JSONArray with the colours of the ColourPegs inside the answerKey
    private JSONArray answerExtraction(PegsCombination pc) {
        JSONArray jsonArray = new JSONArray();
        for (ColourPegs p: pc.getAnswerKey()) {
            jsonArray.put(p.getColour());
        }
        return jsonArray;
    }

    // EFFECTS: returns a JSONArray with the colours of the ColourPegs inside the PegsCombination
    private JSONArray colourExtraction(PegsCombination pc) {
        JSONArray jsonArray = new JSONArray();
        for (ColourPegs p: pc.getCombination()) {
            jsonArray.put(p.getColour());
        }
        return jsonArray;
    }

    // EFFECTS: returns a JSONArray with the gameBoard's Combination's ColourPegs
    private JSONArray boardExtraction(GameBoard board) {
        JSONArray jsonArray = new JSONArray();
        for (PegsCombination pc : board.getBoard()) {
            jsonArray.put(colourExtraction(pc));
        }

        return jsonArray;
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

}

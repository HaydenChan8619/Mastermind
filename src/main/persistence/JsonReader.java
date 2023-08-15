package persistence;


import model.ColourPegs;
import model.GameBoard;
import model.PegsCombination;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// Represents a reader that reads mastermind from JSON data stored in file
// Entire Class is modelled on the JsonSerializationDemo example on EDX
public class JsonReader {

    private String source;
    private JSONObject jsonObject;

    // EFFECTS: initialize JsonReader with source assigned as the argument String
    public JsonReader(String s) {
        source = s;
    }

    // EFFECTS: Fills the jsonObject with the JSON data
    public void read() throws IOException {
        String jsonData = readFile(source);
        jsonObject = new JSONObject(jsonData);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: read max size from file and returns it
    public Integer readMaxSize() {
        return jsonObject.getInt("max size");
    }

    // EFFECTS: read attempt number from file and returns it
    public Integer readAttemptNumber() {
        return jsonObject.getInt("attempts");
    }

    // EFFECTS: read answer key from file and returns it
    public PegsCombination readAnswerKey() {
        return parseAnswerKeyCombination(jsonObject);
    }

    // EFFECTS: read currentCombination from file and returns it
    public PegsCombination readCurrentCombination() {
        return parseCurrentCombination(jsonObject);
    }


    // EFFECTS: read game board from file and returns it
    public GameBoard readGameBoard() {
        return parseGameBoard(jsonObject);
    }

    //EFFECTS: parse currentCombination from JSON object and returns it
    private PegsCombination parseCurrentCombination(JSONObject jsonObject) {
        JSONArray current = jsonObject.getJSONArray("current combination");
        PegsCombination currentCombination = new PegsCombination();
        return addCurrentPegs(currentCombination,current);
    }

    // EFFECTS: parses answerKeyCombination from JSON object and returns it
    private PegsCombination parseAnswerKeyCombination(JSONObject jsonObject) {
        JSONArray answerKey = jsonObject.getJSONArray("answer key");
        PegsCombination answerKeyCombination = new PegsCombination();
        return addAnswerKeyPegs(answerKeyCombination,answerKey);
    }

    // EFFECTS: parse ColourPegs from JSON object and adds them to currentCombination's combination
    private PegsCombination addCurrentPegs(PegsCombination pc, JSONArray jsonArray) {
        for (Object json: jsonArray) {
            ColourPegs peg = new ColourPegs();
            peg.setColour(json.toString());
            pc.getCombination().add(peg);
        }
        return pc;
    }

    // EFFECTS: parses ColourPegs from JSON object and adds them to answerKeyCombination's answerKey
    private PegsCombination addAnswerKeyPegs(PegsCombination pc, JSONArray jsonArray) {
        for (Object json : jsonArray) {
            ColourPegs peg = new ColourPegs();
            peg.setColour(json.toString());
            pc.getAnswerKey().add(peg);
        }
        return pc;
    }

    // EFFECTS: parses GameBoard from JSON object and returns it
    private GameBoard parseGameBoard(JSONObject jsonObject) {
        JSONArray gameBoardArray = jsonObject.getJSONArray("game board");
        GameBoard gameBoard = new GameBoard();
        return addAttemptHistory(gameBoard,gameBoardArray);
    }

    // EFFECTS: Rebuild the gameBoard by adding the attempt history retrieved from the JSON Array
    private GameBoard addAttemptHistory(GameBoard gameBoard, JSONArray gameBoardArray) {

        for (int i = 0; i < gameBoardArray.length(); i++) {
            JSONArray attempt = gameBoardArray.getJSONArray(i);
            PegsCombination currentCombination = new PegsCombination();

            for (Object json : attempt) {
                ColourPegs peg = new ColourPegs();
                peg.setColour(json.toString());
                currentCombination.getCombination().add(peg);
            }

            gameBoard.addPegsPinsToBoard(currentCombination);
        }
        return gameBoard;
    }
}
